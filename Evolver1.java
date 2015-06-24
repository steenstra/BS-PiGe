package bscipt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;



public class Evolver1 {
	
	BNFapplier mapper;
	Evaluator evaluator;
	String bnf;
	
	Individual[] population;
	
	int best_individual;
	int number_of_codons;
	
	String directory;
	
	boolean position_independent;
	
	
	Statistics[] pi_run_stats;
	Statistics[] pd_run_stats;
	
	
	// settings
	
	int tournament_size = 2;
	
	int population_size = 20;
	int number_of_generations = 30;
	int number_of_runs = 100;
	
	
	int max_wraps = 100;
	int number_of_codons_pi = 30;
	int number_of_codons_pd = 30;
	
	int codonsize = 8; // Number of bits per codon
	
	
	double required_fitness = 1; // max 1, but not working? because of storing statistics
	
	double mutation_rate = 0.01;
	boolean storing_stats = false;
	
	boolean store_individuals = false;
	boolean include_geno = false;
	
	boolean printing = false; // individual details
	boolean print_to_file = false;
	boolean pause = false;
	// end settings
	
	
	
	public Evolver1()
	{
		//evolve_math_expr();
		evolve_mastermind();
	}
	
	
	public void evolve_mastermind()
	{
		final long startTime = System.nanoTime();
		mapper = new BNFapplier("_mastermind", max_wraps);
		bnf = mapper.bnf;
		int[] colors = {3, 1, 4, 1, 6};
		evaluator = new Evaluator_mastermind(colors);
		
		
		
		
		
		
		pi_run_stats = new Statistics[number_of_runs];
		pd_run_stats = new Statistics[number_of_runs];
		
		int succesful_runs_PI = 0;
		int succesful_runs_PD = 0;
		
		
		long[] pi_timer = new long[number_of_runs]; // in milliseconds
		long[] pd_timer = new long[number_of_runs]; // in milliseconds
		
		
		directory = settings_to_file();
		
		for(int i = 0; i < number_of_runs; i++)
		{	
			
			// POSITION INDEPENDENT
			// ------------------------------------------------------------------------------------------
			number_of_codons = number_of_codons_pi;
			position_independent = true;
			
			
			final long pi_startTime = System.nanoTime();
			pi_run_stats[i] = run(i, number_of_generations);
			pi_timer[i] = (System.nanoTime() - pi_startTime) / 1000000; // from nano- to milliseconds
			// ------------------------------------------------------------------------------------------
			
			
			
			// POSITION DEPENDENT
			// ------------------------------------------------------------------------------------------
			number_of_codons = number_of_codons_pd;
			position_independent = false;
			
			final long pd_startTime = System.nanoTime();
			pd_run_stats[i] = run(i, number_of_generations);
			pd_timer[i] = (System.nanoTime() - pd_startTime) / 1000000; // from nano- to milliseconds
			// ------------------------------------------------------------------------------------------
			
			
			
			
			System.out.println("[pi RUN: " + i + "] took " + pi_timer[i] + " ms");
			System.out.println("[pd RUN: " + i + "] took " + pd_timer[i] + " ms");
			System.out.println();
			
					
		}
		
		for(int i = 0; i < number_of_runs; i++)
		{
			if(pi_run_stats[i].target_reached_at != -1)
			{
				succesful_runs_PI++;
			}
			if(pd_run_stats[i].target_reached_at != -1)
			{
				succesful_runs_PD++;
			}
		}
		
			
		
		
		
		long pi_total = sum(pi_timer);
		long pd_total = sum(pd_timer);
		
		System.out.println("\nPosition Independent:");
		for(long i : pi_timer)
		{
			System.out.println(i);
		}
		
		System.out.println("\nPosition Dependent:");
		for(long i : pd_timer)
		{
			System.out.println(i);
		}
		
		
		double avg_pi_duration = (double)pi_total / (double)number_of_runs;
		double avg_pd_duration = (double)pd_total / (double)number_of_runs;
		
		//print_stats(population);
		
		System.out.println();
		System.out.println("PI: " + succesful_runs_PI + "/" + number_of_runs + " with an average of " + avg_pi_duration + "ms per run.");
		System.out.println("PD: " + succesful_runs_PD + "/" + number_of_runs + " with an average of " + avg_pd_duration + "ms per run.");
		
		
		System.out.println("\n[Position Independent]___________________________");
		print_multirun_stats(pi_run_stats);
		System.out.println("_________________________________________________");
		
		System.out.println("\n[Position Dependent]_____________________________");
		print_multirun_stats(pd_run_stats);
		System.out.println("_________________________________________________");
		
		
		final long total_duration = (System.nanoTime() - startTime) / 1000000;
		System.out.println("\n");
		System.out.println("Total Duration: (H:M:S)");
		int seconds = (int) total_duration/1000;
		int s = seconds % 60;
		int m = (seconds / 60) % 60;
		int h = (seconds / (60 * 60)) % 24;
		System.out.println(h + ":" + m + ":" + s);
	}
	
	
	public void evolve_math_expr()
	{
		final long startTime = System.nanoTime();
		mapper = new BNFapplier("_mathexpr", max_wraps);
		bnf = mapper.bnf;
		
		pi_run_stats = new Statistics[number_of_runs];
		pd_run_stats = new Statistics[number_of_runs];
		
		int succesful_runs_PI = 0;
		int succesful_runs_PD = 0;
		
		
		long[] pi_timer = new long[number_of_runs]; // in milliseconds
		long[] pd_timer = new long[number_of_runs]; // in milliseconds
		
		
		directory = settings_to_file();
		
		for(int i = 0; i < number_of_runs; i++)
		{	
			int target_value = (int) (Math.random()*1000);
			//evaluator = new Evaluator_mathexpr(target_value);
			
			if(printing)
			{
				System.out.println("Target: " + target_value);		
			}
			
			// POSITION INDEPENDENT
			// ------------------------------------------------------------------------------------------
			number_of_codons = number_of_codons_pi;
			position_independent = true;
			
			
			final long pi_startTime = System.nanoTime();
			pi_run_stats[i] = run(i, number_of_generations);
			pi_timer[i] = (System.nanoTime() - pi_startTime) / 1000000; // from nano- to milliseconds
			// ------------------------------------------------------------------------------------------
			
			
			
			// POSITION DEPENDENT
			// ------------------------------------------------------------------------------------------
			number_of_codons = number_of_codons_pd;
			position_independent = false;
			
			final long pd_startTime = System.nanoTime();
			pd_run_stats[i] = run(i, number_of_generations);
			pd_timer[i] = (System.nanoTime() - pd_startTime) / 1000000; // from nano- to milliseconds
			// ------------------------------------------------------------------------------------------
			
			
			
			
			System.out.println("[pi RUN: " + i + "] took " + pi_timer[i] + " ms");
			System.out.println("[pd RUN: " + i + "] took " + pd_timer[i] + " ms");
			System.out.println();
			
					
		}
		
		for(int i = 0; i < number_of_runs; i++)
		{
			if(pd_run_stats[i].target_reached_at != -1)
			{
				succesful_runs_PI++;
			}
			if(pi_run_stats[i].target_reached_at != -1)
			{
				succesful_runs_PD++;
			}
		}
		
			
		
		
		
		long pi_total = sum(pi_timer);
		long pd_total = sum(pd_timer);
		
		System.out.println("\nPosition Independent:");
		for(long i : pi_timer)
		{
			System.out.println(i);
		}
		
		System.out.println("\nPosition Dependent:");
		for(long i : pd_timer)
		{
			System.out.println(i);
		}
		
		
		double avg_pi_duration = (double)pi_total / (double)number_of_runs;
		double avg_pd_duration = (double)pd_total / (double)number_of_runs;
		
		//print_stats(population);
		
		System.out.println();
		System.out.println("PI: " + succesful_runs_PI + "/" + number_of_runs + " with an average of " + avg_pi_duration + "ms per run.");
		System.out.println("PD: " + succesful_runs_PD + "/" + number_of_runs + " with an average of " + avg_pd_duration + "ms per run.");
		
		
		System.out.println("\n[Position Independent]___________________________");
		print_multirun_stats(pi_run_stats);
		System.out.println("_________________________________________________");
		
		System.out.println("\n[Position Dependent]_____________________________");
		print_multirun_stats(pd_run_stats);
		System.out.println("_________________________________________________");
		
		
		final long total_duration = (System.nanoTime() - startTime) / 1000000;
		System.out.println("\n");
		System.out.println("Total Duration: (H:M:S)");
		int seconds = (int) total_duration/1000;
		int s = seconds % 60;
		int m = (seconds / 60) % 60;
		int h = (seconds / (60 * 60)) % 24;
		System.out.println(h + ":" + m + ":" + s);
	}
	
	
	
	
	private String settings_to_file() 
	{
		final long current_time = System.currentTimeMillis() / 1000;
	
		int seconds = (int) current_time + 3600; // DST or whatever. Just to make it right.
		//int s = seconds % 60;
		int m = (seconds / 60) % 60;
		int h = (seconds / (60 * 60)) % 24;
		int d = (int) ((seconds / (60 * 60 * 24)) % 365.25);
		
		String time = d + " " + h + " " + m;  // nevermind the seconds
		
		
		//String path = new File("").getAbsolutePath();
		String path = "C:/_bout";		
		path += "/run_" + time + "/";
		File dir = new File(path);
		dir.mkdir();
	
		
		System.out.println("File created at: " + path);
				
		BufferedWriter bw = null;
				
		try {
			String nl = System.getProperty("line.separator"); // new line character
			bw = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream(path + "_settings.txt"), "utf-8"));
		    bw.write("[Settings] " + nl);
		    bw.write("...................................................." + nl);
		    bw.write("print details         = " + printing + nl);
		    bw.write("tournament size       = " + tournament_size + nl);
		    bw.write("population size       = " + population_size + nl);
		    bw.write("number of generations = " + number_of_generations + nl);
		    bw.write("number of runs        = " + number_of_runs + nl);		
		    bw.write("max wraps             = " + max_wraps + nl);
		    bw.write("number of codons pI   = " + number_of_codons_pi + nl);
		    bw.write("number of codons pD   = " + number_of_codons_pd + nl);		
		    bw.write("bits per codon        = " + codonsize + nl);
		    bw.write("required fitness      = " + required_fitness + nl);	
		    bw.write("mutation rate         = " + mutation_rate + nl);
		    bw.write("...................................................." + nl);
		    bw.write(nl);
		    bw.write("[BNF] " + nl);
		    bw.write("...................................................." + nl);
		    bw.write(bnf + nl);
		    bw.write("...................................................." + nl);
		    
		} catch (IOException ex) {
		  // report
		} finally {
		   try {bw.close();} catch (Exception ex) {}
		}
		
		return path;
	}
									// pi / pd
	public void store_population(int run, String type, int generation, Individual[] population)
	{
		File dir = new File(directory);
		dir.mkdir();
		
		BufferedWriter bw = null;
		try {
			String nl = System.getProperty("line.separator"); // new line character
			bw = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream(dir + "/" + type + " " + run + ".csv", true), "utf-8"));
		    if(generation == 0)
		    {
		    	if(include_geno)
			    {
			    	bw.write("Run, Generation, Individual, Fitness, Phenotype, Genotype" + nl);
			    }
			    else
			    {
			    	bw.write("Run, Generation, Individual, Fitness, Phenotype" + nl);
			    }
		    }			
			
		    
		    String ind;
		    for(int i = 0; i < population.length; i++)
		    {
		    	if(include_geno)
		    	{
		    		ind = run + ", " + generation + ", " + i + ", " + population[i].fitness + ", " + population[i].phenotype + ", " + population[i].genotype + nl;
		    	}
		    	else
		    	{
		    		ind = run + ", " + generation + ", " + i + ", " + population[i].fitness + ", " + population[i].phenotype + nl;
		    	}
		    	
		    	bw.write(ind);
		    }
		    
		    
		} catch (IOException ex) {
		  // report
		} finally {
		   try {bw.close();} catch (Exception ex) {}
		}

		
		System.out.println("Stored " + generation + "th generation to file");
	}

	public void print_multirun_stats(Statistics[] stats)
	{
		double[][] multirunstats = new double[number_of_generations][3];
		
		int times_reached_target = 0;
		int generation_to_reach_target = 0;
		
		for(int i = 0; i < number_of_runs; i++)
		{
			for(int j = 0; j < number_of_generations; j++)
			{
				// calculate sum of all runs per generation 
				multirunstats[j][0] += stats[i].fitness_stats[j][0]; // min
				multirunstats[j][1] += stats[i].fitness_stats[j][1]; // max
				multirunstats[j][2] += stats[i].fitness_stats[j][2]; // avg	
			}		
			if(stats[i].target_reached_at != -1)
			{
				times_reached_target++;
				generation_to_reach_target += stats[i].target_reached_at;
			}
			
		}
		
		if(times_reached_target != 0)
		{
			double avg_target_gen = ((double) generation_to_reach_target / times_reached_target);
			double avg_target_all = ((double) ((number_of_runs - times_reached_target) * number_of_generations + generation_to_reach_target) / number_of_runs);
			System.out.println("Average generation to reach target: " + avg_target_gen + " (" + times_reached_target + " times)");
			System.out.println("So a total average of " + avg_target_all);
			
		}
		else
		{
			System.out.println("Never reached target... ");
		}
		
		
		
		
		// calculate average by dividing over number of runs
		for(int i = 0; i < number_of_generations; i++)
		{
			multirunstats[i][0] /= number_of_runs;
			multirunstats[i][1] /= number_of_runs;
			multirunstats[i][2] /= number_of_runs;
		}
		
		
		if(storing_stats && printing)
		{
			System.out.println("\nAverages of " + number_of_runs + " runs");
			for(int i = 0; i < number_of_generations; i++)
			{
				System.out.println("\nGeneration " + (i+1));
				System.out.println("  min: " + multirunstats[i][0]);
				System.out.println("  max: " + multirunstats[i][1]);
				System.out.println("  avg: " + multirunstats[i][2]);
			}
		}
		
		
		
	}
	
	
	
	public Statistics run(int run, int number_of_generations)
	{
		population = generate_individuals(population_size, codonsize, number_of_codons);
		generate_phenotypes();
		
		
		Statistics stats = new Statistics(population_size, number_of_generations, number_of_codons, mutation_rate);
		
		boolean target_reached = false;
		int target_reached_at = -1;
		
		for(int i = 0; i < number_of_generations && !target_reached; i++)
		{
		//System.out.println("evaluating gen " + i);	
			
			for(int j = 0; j < population_size; j++)
			{
				population[j].fitness = evaluator.calculate_fitness(population[j].phenotype);
				if(population[j].fitness >= required_fitness && !target_reached)
				{
					if(printing)
					{
						System.out.println("-------------------------     Target found at generation " + i);
					}
					target_reached = true;
					target_reached_at = i;
				}				
			}
			

			if(storing_stats)
			{
				double min = calculate_minimum_fitness(population);
				double max = calculate_maximum_fitness(population);
				double avg = calculate_average_fitness(population);
				
				stats.store(i, min, max, avg);	
			}
			
			if(store_individuals) // make sure fitness is calculated
			{
				String type;
				if(position_independent)
				{
					type = "PI";
				}
				else
				{
					type = "PD";
				}
				store_population(run, type, i, population);
			}
				
			
			population = generate_next_generation_crossover(population);
			
			
			if(printing)
			{
				if(i/10*10 == i)
				{
					System.out.print(i);
				}
				System.out.print(".");
			}
			
				
		}
		if(printing)
		{
			System.out.println("------------------------- Target NOT found at generation " + population_size);
		}
		
		stats.target_reached(target_reached_at);
		
		if(printing)
		{
			System.out.println(number_of_generations);
		}
		
		
		return stats;
	}
	
	
	
	// Tournament Selection
	public Individual[] generate_next_generation_crossover(Individual[] ancestors)
	{
		Individual[] parents = new Individual[population_size*2];
				
		for(int j = 0; j < population_size*2; j++)
		{
			int current_best_individual_index = (int) (Math.random()*ancestors.length);;
			double current_best_individual_fitness = ancestors[current_best_individual_index].fitness;
			
			if(printing)
			{
				System.out.println("\nTournament " + j);
			}
			
			
			for(int i = 0; i < tournament_size; i++)
			{			
				int x = (int) (Math.random()*ancestors.length);
				
				if(printing)
				{
					System.out.println("Random individual: " + x + "/" + population_size);
					System.out.println("Has a fitness of:  " + ancestors[x].fitness);
						
				}
				
				if(ancestors[x].fitness > current_best_individual_fitness)
				{
					current_best_individual_index = x;
					current_best_individual_fitness = ancestors[x].fitness;
				}						
			}
			
			if(printing)
			{
				System.out.println();
				System.out.println("The winner is:     " + current_best_individual_index + "/" + population_size);
				System.out.println("With a fitness of: " + current_best_individual_fitness);
				pause();
			}
			
			
			
			parents[j] = clone_individual(ancestors[current_best_individual_index]);				
		}
		
		Individual[] descendants = new Individual[population_size];
		
		// mating!
		for(int i = 0; i < population_size; i++)
		{
			descendants[i] = crossover(parents[i], parents[i+population_size]);
		}
		
		
		
		return descendants;
	}
	
	// Tournament Selection
	public Individual[] generate_next_generation(Individual[] ancestors)
	{
		Individual[] descendants = new Individual[population_size];
				
		for(int j = 0; j < population_size; j++)
		{
			int current_best_individual_index = (int) (Math.random()*ancestors.length);
			double current_best_individual_fitness = ancestors[current_best_individual_index].fitness;
			
			if(printing)
			{
				System.out.println("\nTournament " + j);
			}
			
			
			for(int i = 0; i < tournament_size; i++)
			{			
				int x = (int) (Math.random()*ancestors.length);
				
				if(printing)
				{
					System.out.println("Random individual: " + x + "/" + population_size);
					System.out.println("Has a fitness of:  " + ancestors[x].fitness);
						
				}
				
				if(ancestors[x].fitness > current_best_individual_fitness)
				{
					current_best_individual_index = x;
					current_best_individual_fitness = ancestors[x].fitness;
				}
				
			}
			
			if(printing)
			{
				System.out.println();
				System.out.println("The winner is:     " + current_best_individual_index + "/" + population_size);
				System.out.println("With a fitness of: " + current_best_individual_fitness);
				pause();
			}
			
			
			
			
			descendants[j] = clone_individual(ancestors[current_best_individual_index]);				
		}
		
		return descendants;
	}
	
	
	public Individual clone_individual(Individual parent)
	{
		Individual child = new Individual();
		child.set_geno(parent.get_geno());
		child = mutate(child);
		
		child.set_pheno(generate_phenotype(child.get_geno()));
		
		return child;
	}
	
	public Individual crossover(Individual parent1, Individual parent2)
	{
		Individual child = new Individual();
		int crossover_point = codonsize * (int) (Math.random() * number_of_codons);
		
		String first_half = parent1.genotype.substring(0, crossover_point);
		String second_half = parent2.genotype.substring(crossover_point);
		
		child.set_geno(first_half + second_half);
		child.set_pheno(generate_phenotype(child.get_geno()));
		//child.set_fitness(((double)child.phenotype.length() / (double)child.genotype.length()));
		
		return child;
	}
	
	public Individual mutate(Individual individual)
	{
		individual.set_geno(mutate(individual.genotype));
		individual.set_pheno(generate_phenotype(individual.get_geno()));
		//individual.set_fitness(((double)individual.phenotype.length() / (double)individual.genotype.length()));
		
		return individual;
	}
	
	public String mutate(String dna)
	{
		char[] original_dna = dna.toCharArray();
		char[] new_dna = new char[original_dna.length];
		
		
		for(int i = 0; i < original_dna.length; i++)
		{
			new_dna[i] = original_dna[i];
			if(Math.random() < mutation_rate)
			{
				
				if(new_dna[i] == '1')
				{
					new_dna[i] = '0';
				}
				else
				{
					new_dna[i] = '1';
				}
			}
		}
		
		return new String(new_dna);
		
	}
	
	// generates a number of individuals with a certain codonsize and a certain amount of codons.
	public Individual[] generate_individuals(int amount, int bits_per_codon, int number_of_codons)
	{
		Individual[] population = new Individual[amount];
		for(int i = 0; i < amount; i++)
		{
			population[i] = new Individual();
			population[i].set_geno(generate_genotype(bits_per_codon,number_of_codons));		
		}
		
		return population;
	}
	
	// returns a random string of "0" and "1"
	public String generate_genotype(int bits_per_codon, int number_of_codons)
	{
		int[] randomIntegers = new int[number_of_codons];
		String bitstring = "";
		
		for(int i = 0; i < number_of_codons; i++)
		{
			randomIntegers[i] = (int) (Math.random() * Math.pow(2, bits_per_codon));
		}
						
		for(int i=0; i < randomIntegers.length; i++)
		{
			String binaryString = Integer.toBinaryString(randomIntegers[i]);
			for(int j=0; j<bits_per_codon-binaryString.length(); j++)
			{
				bitstring += "0";
			}
			bitstring += Integer.toBinaryString(randomIntegers[i]);
		}
		
		return bitstring;
	}

	
	private void generate_phenotypes()
	{
		for(int i = 0; i < population_size; i++)
		{
			
			population[i].set_pheno(generate_phenotype(population[i].genotype));		
			
		}
	}
	
	private String generate_phenotype(String genotype)
	{
		List genotype_list = mapper.convert_bitstring_to_nodelist(genotype, position_independent);
		
		if(genotype_list.vars != 0)
		{
			return "0";
		}
		
		String pheno = genotype_list.toString();
		return pheno;
	}
	


	public double evaluate_individual(Individual individual)
	{
		return 0;
	}

	public double calculate_average_fitness(Individual[] population)
	{
		double total_fitness = 0;
		for(int i = 0; i < population_size; i++)
		{
			total_fitness += population[i].fitness;
		}
		
		return total_fitness/population_size;		
	}
	
	public double calculate_standard_deviation(Individual[] population)
	{
		double mean = calculate_average_fitness(population);
		double total_deviation = 0;
		
		for(int i = 0; i < population_size; i++)
		{
			double deviation = (mean - population[i].fitness);
			total_deviation += deviation*deviation;
		}
		
		double variance = total_deviation/population_size;
		
		return Math.sqrt(variance);
	}
	
	
	public double calculate_maximum_fitness(Individual[] population)
	{
		double maximum_fitness = -1;
		
		for(int i = 0; i < population_size; i++)
		{
			if(population[i].fitness < 0)
			{
				System.out.println("Too low?");
				System.out.println("F: " + population[i].fitness);
				System.out.println(population[i].phenotype);
				pause();
			}
			if(population[i].fitness > maximum_fitness)
			{
				maximum_fitness = population[i].fitness;
				best_individual =  i;
			}
		}
		return maximum_fitness;
	}
	
	public double calculate_minimum_fitness(Individual[] population)
	{
		double minimum_fitness = 1;
		
		for(int i = 0; i < population_size; i++)
		{
			if(population[i].fitness < minimum_fitness)
			{
				minimum_fitness = population[i].fitness;
			}
		}
		
		return minimum_fitness;
	}
	
	
	public int sum(int[] values)
	{
		int sum = 0;
		for(int i : values)
		{
			sum += i;
		}
		return sum;
	}
	
	public long sum(long[] values)
	{
		long sum = 0;
		for(long i : values)
		{
			sum += i;
		}
		return sum;
	}
	
	public double sum(double[] values)
	{
		int sum = 0;
		for(double i : values)
		{
			sum += i;
		}
		return sum;
	}
	
	
	
	
	
	
	public void print_stats(Individual[] population)
	{	
		for(int j = 0; j < population_size; j++)
		{
			population[j].fitness = evaluator.calculate_fitness(population[j].phenotype);
		}
		
		
		
		
		System.out.println();
		
		double max = calculate_maximum_fitness(population);
		double min = calculate_minimum_fitness(population);
		
		double avg = calculate_average_fitness(population);
		double std = calculate_standard_deviation(population);
		
		
		System.out.println("Maximum Fitness:    " + max);
		System.out.println("Minimum Fitness:    " + min);

		System.out.println("Average Fitness:    " + avg);
		System.out.println("Standard Deviation: " + std);
		
		
		
		System.out.println("\n\nThe best individual has the following properties:");
		print(population[best_individual]);
	}
	
	
	public void print(Individual[] population)
	{
		System.out.println("Printing Population: ");
		for(int i = 0; i < population.length; i++)
		{
			print(population[i]);
		}
		print_stats(population);
	}
	
	
	// prints an individual
	public void print(Individual individual)
	{
		System.out.println("");
		System.out.println(" genotype: " + individual.genotype);
		System.out.println("phenotype: " + individual.phenotype);
		System.out.println("  fitness: " + individual.fitness);
	}
	
	
	
	public void pause()
	{
		if(pause)
		{
			System.out.println("\n    \"ENTER\" to continue...");
			   
			   @SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			   scanner.nextLine();
		}
	   
	}
	
	
	
	/*
	 * 
	 * GENERATE GENO
	 * CONVERT TO PHENO
	 * EVALUATE PHENO
	 * SELECT PARENTS
	 * ...
	 * 
	 * 
	 * 
	 */
}
