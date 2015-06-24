package bscipt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;



public class Evolver {
	
	BNFapplier mapper;
	Evaluator evaluator;
	String bnf;
	Individual[] population;
	
	int best_individual;
	int number_of_codons;
	
	String directory;
	
	boolean position_independent;
	boolean created_directory = false;
	
	
	
	// settings
	
	int tournament_size = 3;
	int parents_per_child = 2;
	
	int population_size = 30;   // vast
	int number_of_runs = 500;	// vast
	int number_of_generations = 50; // vast
	int number_of_codons_pd = 50;
	int number_of_codons_pi = 100;
	
	int max_wraps = 4;
	int codonsize = 8; // Number of bits per codon
	
	double mutation_rate = 0.01;
	
	int mastermind_color_range = 6;
			
	int repeat_index;
	int number_of_repeats = 100;
	
	double required_fitness = 2; // max 1
	
	
	//boolean storing_stats = false;
	
	boolean store_individuals = true;
	boolean include_geno = false;
	
	boolean print_details = false; // individual details
	boolean print_to_file = false;
	boolean pause = true;
	// end settings
	
	// if false, dont print anything
	boolean printing_allowed = false;
	
	// dont edit:
	boolean PI = false;
	boolean PD = false;
	
	String best_PI;
	String best_PD;
	
	String experiment;
	
	public Evolver()
	{
		
		experiment = "mathexpr";
		evolve(experiment, "pd");
		evolve(experiment, "pi");
		
		
		
		
		//experiment = "mathexpr";
		//experiment = "multiplex";
		
		/*
		experiment = "mastermind3";
		for(repeat_index = 1; repeat_index < (number_of_repeats + 1); repeat_index++)
		{
			created_directory = false;
			evolve(experiment, "pd");
			evolve(experiment, "pi");
			mastermind_color_range--;
		}
		*/
		
		
		
		
		
		/// loop to decrease number of codons
		/*
		for(repeat_index = 1; repeat_index < (number_of_repeats + 1); repeat_index++)
		{
			created_directory = false;
			evolve(experiment, "pd");
			evolve(experiment, "pi");
			number_of_codons_pd -= 5;
			number_of_codons_pi -= 5;
		}
		*/
		
	}
	
	
	
	
	public void evolve(String type, String method)
	{
		String color_index;
		switch(type)
		{
			case "mastermind":
				color_index = ""+mastermind_color_range;
				if(mastermind_color_range < 10)
				{
					color_index = "0"+mastermind_color_range;
				}
				mapper = new BNFapplier("_mastermind", max_wraps);
				bnf = mapper.bnf;
							
			break;
			
			case "mastermind2":
				color_index = ""+mastermind_color_range;
				if(mastermind_color_range < 10)
				{
					color_index = "0"+mastermind_color_range;
				}
				mapper = new BNFapplier("_mastermind2", max_wraps);
				bnf = mapper.bnf;
				
				
			break;
			
			case "mastermind3":
				color_index = ""+mastermind_color_range;
				if(mastermind_color_range < 10)
				{
					color_index = "0"+mastermind_color_range;
				}
				mapper = new BNFapplier("_mastermind3/"+color_index, max_wraps);
				bnf = mapper.bnf;
				
			break;
			
			
			case "mathexpr":
				mapper = new BNFapplier("_mathexpr", max_wraps);
				bnf = mapper.bnf;
				evaluator = new Evaluator_mathexpr();
				
			break;
			
			case "multiplex":
				mapper = new BNFapplier("_multiplex", max_wraps);
				bnf = mapper.bnf;
				evaluator = new Evaluator_multiplex();
				
			break;
			
			default:
			
			break;
		}
		log(true, type);
		
		// initialize general variables
		int succesful_runs_PI = 0;
		int succesful_runs_PD = 0;
		
		long[] pi_timer = new long[number_of_runs]; // in milliseconds
		long[] pd_timer = new long[number_of_runs]; // in milliseconds
				
		// start the clock for the entire program
		final long startTime = System.nanoTime();
		
		
		int range = mastermind_color_range;
		
		for(int i = 0; i < number_of_runs; i++)
		{	
			int[] colors = { 
				    (int) (Math.random()*range),
				    (int) (Math.random()*range), 
				    (int) (Math.random()*range), 
				    (int) (Math.random()*range)
				};
			
			// only mastermind
			//evaluator = new Evaluator_mastermind(colors);
			
			switch(method)
			{
				case "pi":
					System.out.println("<<<<--------  PI  -------->>>>");
					// POSITION INDEPENDENT
					// ------------------------------------------------------------------------------------------
					PI = true;
					PD = false;
					number_of_codons = number_of_codons_pi;
					position_independent = true;
					
					final long pi_startTime = System.nanoTime();
					boolean PI_succes = run(i, number_of_generations);
					if(PI_succes)
					{
						succesful_runs_PI++;
					}
					pi_timer[i] = (System.nanoTime() - pi_startTime) / 1000000; // from nano- to milliseconds
					// ------------------------------------------------------------------------------------------
					System.out.println("PI = " + best_PI + "\n f:" + evaluator.calculate_fitness(best_PI));
					System.out.println("<<<<--------  PI  -------->>>>");
				break;
				
				case "pd":
					System.out.println("<<<<--------  PD  -------->>>>");
					// POSITION DEPENDENT
					// ------------------------------------------------------------------------------------------
					PD = true;
					PI = false;
					number_of_codons = number_of_codons_pd;
					position_independent = false;
					
					final long pd_startTime = System.nanoTime();
					boolean PD_succes = run(i, number_of_generations);
					if(PD_succes)
					{
						succesful_runs_PD++;
					}
					pd_timer[i] = (System.nanoTime() - pd_startTime) / 1000000; // from nano- to milliseconds
					// ------------------------------------------------------------------------------------------
					System.out.println("PD = " + best_PD + "\n f:" + evaluator.calculate_fitness(best_PD));
					System.out.println("<<<<--------  PD  -------->>>>");
					
				break;
				
				default:
				
				break;
			}
			
				
			
			/*
			
			if(PI_succes)
			{
				log(true, "[pi RUN: " + i + "] took " + pi_timer[i] + " ms and succeeded.");
			}
			else
			{
				log(true, "[pi RUN: " + i + "] took " + pi_timer[i] + " ms and FAILED.");
			}
			
			if(PD_succes)
			{
				log(true, "[pd RUN: " + i + "] took " + pd_timer[i] + " ms and succeeded.");
			}
			else
			{
				log(true, "[pd RUN: " + i + "] took " + pd_timer[i] + " ms and FAILED.");
			}
			
			*/
					
		}
		

		
		long pi_total = sum(pi_timer);
		long pd_total = sum(pd_timer);
		
		log(false, "\nPosition Independent:");
		for(long i : pi_timer)
		{
			log(false, i);
		}
		
		log(false, "\nPosition Dependent:");
		for(long i : pd_timer)
		{
			log(false, i);
		}
		
		
		double avg_pi_duration = (double)pi_total / (double)number_of_runs;
		double avg_pd_duration = (double)pd_total / (double)number_of_runs;
		
		
		log(true, "");
		log(true, "PI: " + succesful_runs_PI + "/" + number_of_runs + " with an average of " + avg_pi_duration + "ms per run.");
		log(true, "PD: " + succesful_runs_PD + "/" + number_of_runs + " with an average of " + avg_pd_duration + "ms per run.");
		
		
		final long total_duration = (System.nanoTime() - startTime) / 1000000;
		log(true, "\n");
		log(true, "Total Duration: (H:M:S)");
		int seconds = (int) total_duration/1000;
		int s = seconds % 60;
		int m = (seconds / 60) % 60;
		int h = (seconds / (60 * 60)) % 24;
		log(false, h + ":" + m + ":" + s);
		
		
	}
	
	
	
	
	public Boolean run(int run, int number_of_generations)
	{
		population = generate_individuals(population_size, codonsize, number_of_codons);
		generate_phenotypes();
		
		
		
		boolean target_reached = false;
		
		for(int i = 0; i < number_of_generations && !target_reached; i++)
		{
			for(int j = 0; j < population_size; j++)
			{
				population[j].fitness = evaluator.calculate_fitness(population[j].phenotype);
				if(population[j].fitness >= required_fitness && !target_reached)
				{
					target_reached = true;
				}				
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
			
			if(printing_allowed)
			{

				System.out.println("\n\n\nThe best Phenotype at Generation: " + i);
				System.out.println(store_best());
				
			}
			
			
			population = create_next_generation(population, parents_per_child, tournament_size, mutation_rate);
			
			
			if(print_details)
			{
				if(i/10*10 == i)
				{
					log(true, i);
				}
				
			}
			
		}	
		
		
		System.out.println("\n\n\nThe best Phenotype this Run:");
		System.out.println(store_best());
		
		return target_reached;
	}
	
	public String store_best()
	{
		if(PI)
		{
			best_PI = evaluator.get_best();
			return best_PI;
		}
		if(PD)
		{
			best_PD = evaluator.get_best();
			return best_PD;
		}		
		return null;		
	}
	
	
	// select parent(s) from group by tournament (assumes fitness is already calculated
	public Individual[] select_P_by_tournament(Individual[] candidates, int number_of_parents, int tournament_size)
	{
		Individual[] parents = new Individual[number_of_parents];
		
		for(int i = 0; i < parents.length; i++)
		{
			int random_number = (int) (Math.random()*candidates.length);
			parents[i] = candidates[random_number];
			
			for(int j = 0; j < tournament_size-1; j++)
			{
				random_number = (int) (Math.random()*candidates.length);
				
				if(candidates[random_number].fitness > parents[i].fitness)
				{
					parents[i] = candidates[random_number]; // may be unnecessary, but copy_individual()?
				}				
			}
		}
		
		return parents;
	}
	
	// make an exact copy
	public Individual copy_individual(Individual original)
	{
		Individual copy = new Individual();
		
		copy.genotype = original.genotype;
		copy.phenotype = original.phenotype;
		copy.fitness = original.fitness;
			
		return copy;
	}
	
	
	// for N parents, create N children using one-point crossover, 
	// swapping the genotype from that point with the next parent, 
	// while the last parent swaps again with the first:
	/*					        
	 *                                 random point 
	 * 									      |
	 * 										  V
	 *    	parentN		geno = 00000000000000|00000000000000
	 * 		parentN+1	geno = 11111111111111|11111111111111
	 * 		parentN+2	geno = 22222222222222|22222222222222
	 * 								LH				RH
	 * 		childN		geno = 00000000000000|11111111111111
	 * 		childN+1 	geno = 11111111111111|22222222222222
	 * 		childN+2	geno = 22222222222222|00000000000000
	 * 
	 *  
	 *  Using only one parent would result in an exact copy,
	 *  so (while inefficiënt) it can be used for cloning 
	 *  without mutation
	 */
	
	public Individual[] one_point_crossover(Individual[] parents)
	{
		Individual[] children = new Individual[parents.length];
		
		int crossover_point = codonsize * (int) (Math.random() * number_of_codons);
		String[] L_half = new String[parents.length+1];
		String[] R_half = new String[parents.length+1];
		
		
		for(int i = 0; i < parents.length; i++)
		{
			L_half[i] = parents[i].genotype.substring(0, crossover_point);
			R_half[i] = parents[i].genotype.substring(crossover_point);			
		}
		
		L_half[parents.length] = L_half[0]; // makes looping easy
		R_half[parents.length] = R_half[0];	
		
		for(int i = 0; i < children.length; i++)
		{
			children[i] = new Individual();
			children[i].genotype = L_half[i] + R_half[i+1];
			children[i].phenotype = generate_phenotype(children[i].genotype);
		}
		
		return children;
	}
	

	public Individual clone_individual(Individual parent, double mutation_rate)
	{
		Individual child = new Individual();
		child.set_geno(parent.get_geno());
		child = mutate(child, mutation_rate);
		
		child.set_pheno(generate_phenotype(child.get_geno()));
		
		return child;
	}
	
	
	public Individual mutate(Individual individual, double mutation_rate)
	{
		individual.genotype = mutate(individual.genotype, mutation_rate);
		individual.phenotype = generate_phenotype(individual.genotype);		
		return individual;
	}
	
	public String mutate(String dna, double mutation_rate)
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
	
	//
	
	
	public Individual[] create_next_generation(Individual[] ancestors, int parents_per_child, int tournament_size, double mutation_chance)
	{
		Individual[] next_gen = new Individual[ancestors.length];
		
		int i = 0;
		while(i < next_gen.length)
		{
			Individual[] parents = select_P_by_tournament(ancestors, parents_per_child, tournament_size);
			Individual[] children = one_point_crossover(parents);
			
			for(int j = 0; j < children.length && i < next_gen.length; j++)
			{
				next_gen[i] = mutate(children[j], mutation_chance);
				i++;
			}
			
		}
				
		return next_gen;
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
	


	// helper methods:
	public long sum(long[] values)
	{
		long sum = 0;
		for(long i : values)
		{
			sum += i;
		}
		return sum;
	}
	
		
	private void R_config_file(String path)
	{
		BufferedWriter bw = null;
		try {
			String nl = System.getProperty("line.separator"); // new line character
			bw = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream(path + "rconfig.yml"), "utf-8"));
		    bw.write("---" + nl);
		    bw.write("# Certain variables from the experiment" + nl);
		    bw.write("experiment:       " + experiment + nl);
		    if(experiment == "mastermind")
		    {
		    	bw.write("number of colors: " + mastermind_color_range + nl);
		    }
		    bw.write("runs:             " + number_of_runs + nl);
		    bw.write("generations:      " + number_of_generations + nl);
		    bw.write("population:       " + population_size + nl);
		    bw.write("codon length:     " + codonsize + nl);
		    bw.write("max wraps:        " + max_wraps + nl);
		    bw.write("codon amount PI:  " + number_of_codons_pi + nl);
		    bw.write("codon amount PD:  " + number_of_codons_pd + nl);			
		    bw.write("mutation rate:    " + mutation_rate + nl);		    
		} catch (IOException ex) {
		  // report
		} finally {
		   try {bw.close();} catch (Exception ex) {}
		}
		
	}
	
	//logging & storing
	private String settings_to_file() 
	{
		final long current_time = System.currentTimeMillis() / 1000;
	
		int seconds = (int) current_time + 3600; // DST or whatever. Just to make it right.
		//int s = seconds % 60;
		int m = (seconds / 60) % 60;
		int h = (seconds / (60 * 60)) % 24;
		int d = (int) ((seconds / (60 * 60 * 24)) % 365.25);
		
		String time = d + " " + h + " " + m;  // nevermind the seconds
		
		String path;
		//String path = new File("").getAbsolutePath();
		if(experiment == "mastermind3")
		{
			path = "C:/_bout/mastermind_multicolor";
			File dir = new File(path);
			dir.mkdir();
			String rind = "/"+repeat_index;
			if(repeat_index < 10)
			{
				rind = "/0"+repeat_index;
			}
			path += rind + "_" + number_of_repeats + "/";
		}
		else
		{
			path = "C:/_bout";
			path += "/run_ifn" + time + " " + experiment + " " + repeat_index + "_" + number_of_repeats + "/";
		}
		
		File dir = new File(path);
		dir.mkdir();
		
		R_config_file(path);
		
		BufferedWriter bw = null;
				
		try {
			String nl = System.getProperty("line.separator"); // new line character
			bw = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream(path + "_log.txt"), "utf-8"));
		    bw.write("[Settings] " + nl);
		    bw.write("...................................................." + nl);
		    bw.write("number of generations = " + number_of_generations + nl);
		    bw.write("number of runs        = " + number_of_runs + nl);	
		    bw.write("print details         = " + print_details + nl);
		    bw.write("tournament size       = " + tournament_size + nl);
		    bw.write("population size       = " + population_size + nl);
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
		    bw.write(nl);
		    bw.write("[LOG] " + nl);
		    bw.write("...................................................." + nl);
		    
		   
		    
		} catch (IOException ex) {
		  // report
		} finally {
		   try {bw.close();} catch (Exception ex) {}
		}
		
		return path;
	}
	
	
	private void log(boolean print, long i) 
	{
		log(print, Long.toString(i));
	}

	private void log(boolean print, int i) 
	{
		log(print, Integer.toString(i));
		
	}

	private void log(boolean print, String string) 
	{
		if(!created_directory)
		{
			directory = settings_to_file();
			created_directory = true;
		}
		
		if(print && printing_allowed)
		{
			System.out.println(string);
		}
		BufferedWriter bw = null;
		try 
		{
			String nl = System.getProperty("line.separator"); // new line character
			bw = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream(directory + "_log.txt", true), "utf-8"));
			bw.write(string + nl);			
		} catch (IOException ex) {
			  // report
		} finally {
		   try {bw.close();} catch (Exception ex) {}
		}
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
		          new FileOutputStream(dir + "/" + type + ".csv", true), "utf-8"));
		    if(run == 0 && generation == 0)
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

		
		log(true, "Stored " + generation + "th generation to file");
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
	
	
}
