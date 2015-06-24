package bscipt;

import java.util.Scanner;


public class Evaluator_mastermind implements Evaluator
{
	boolean pause = false;
	
	String best_individual;
	
	int[] target_color_code;
	int[] color_code;
	double worst_fitness = 0;
	double best_fitness;
	
	
	public Evaluator_mastermind(int[] c)
	{
		target_color_code = c;
	}
	
	public double calculate_fitness(String phenotype) // assumes the string is in the correct format, for example 1 2 3 4
	{
		if(phenotype == null)
		{
			return 0;
		}
		double fitness = 0;
		String[] colors = phenotype.split("_");
		int[] color_code = new int[colors.length];
		
		//System.out.println(phenotype);
		
		for(int i = 0; i < colors.length; i++)
		{
			color_code[i] = Integer.parseInt(colors[i]);
		}
		
		for(int i = 0; i < color_code.length; i++)
		{
			if(color_code[i] == target_color_code[i])
			{
				fitness += 3;
			}
			else
			{
				for(int j = 0; j < target_color_code.length; j++)
				{
					if(color_code[i] == target_color_code[j])
					{
						fitness += 1;
						break;
					}
				}
			}
			
		}
				
		// normalize, max fitness = 1;
		fitness /= (3*target_color_code.length);
		
		if(fitness > best_fitness)
		{
			best_fitness = fitness;
			best_individual = phenotype;
		}
		
		return fitness;
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

	@Override
	public String get_best() {
		calculate_fitness(best_individual);
		
		return best_individual;
	}
	
}
