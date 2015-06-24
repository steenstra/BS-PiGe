package bscipt;

public class Statistics {

	double[][] fitness_stats;
	
	int population_size;
	int number_of_generations;
	int number_of_codons;
	double mutation_rate;
	int target_reached_at;
	
	public Statistics(int pop_size, int num_gen, int dna_length, double mut_rate)
	{
		population_size = pop_size;
		number_of_generations = num_gen;
		number_of_codons = dna_length;
		mutation_rate = mut_rate;
		
		
		fitness_stats = new double[number_of_generations][3];
		
		/*   GEN 0 1 2 3 ...
		 * min 0 x x x x  
		 * max 1 x x x x
		 * avg 2 x x x x
		 * 
		 * 
		 */
	}
	
	public void store(int generation_number, double min, double max, double avg)
	{
		fitness_stats[generation_number][0] = min;
		fitness_stats[generation_number][1] = max;
		fitness_stats[generation_number][2] = avg;
	}
	
	public void target_reached(int i)
	{
		target_reached_at = i;
	}
	
	
	public void print()
	{
		for(int i = 0; i < fitness_stats.length; i++)
		{
			System.out.println("GENERATION " + i);
			System.out.println("min: " + fitness_stats[i][0]);
			System.out.println("max: " + fitness_stats[i][1]);
			System.out.println("avg: " + fitness_stats[i][2]);
		}
	}
	
	
}
