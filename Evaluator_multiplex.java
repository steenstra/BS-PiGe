package bscipt;

import java.util.Scanner;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;




public class Evaluator_multiplex implements Evaluator
{
	boolean pause = true;
	boolean printing = false;
	
	
	String target_formula;
		
	
	double max_fit = 0;
	String best_individual;
	
	
	
	public Evaluator_multiplex()
	{
		Boolean[][] truth_table = new Boolean[8][3];
		
		// 000
		truth_table[0][0] = false;
		truth_table[0][1] = false;
		truth_table[0][2] = false;
		
		// 001
		truth_table[1][0] = false;
		truth_table[1][1] = false;
		truth_table[1][2] = true; 
		
		// 010
		truth_table[2][0] = false;
		truth_table[2][1] = true;
		truth_table[2][2] = false;
		
		// 011
		truth_table[3][0] = false;
		truth_table[3][1] = true;
		truth_table[3][2] = true;
		 
		// 100
		truth_table[4][0] = true;
		truth_table[4][1] = false;
		truth_table[4][2] = false;
		
		// 101
		truth_table[5][0] = true;
		truth_table[5][1] = false;
		truth_table[5][2] = true;
		
		// 110
		truth_table[6][0] = true;
		truth_table[6][1] = true;
		truth_table[6][2] = false;
		
		// 111
		truth_table[7][0] = true;
		truth_table[7][1] = true;
		truth_table[7][2] = true;
		
	}
	
	
	
	public String get_best()
	{
		boolean prin = printing;
		printing = true;
		calculate_fitness(best_individual);
		printing = prin;
		
		return best_individual;
	}
	
	
	public double calculate_fitness(String phenotype) 
	{
		if(best_individual == null)
		{
			best_individual = phenotype;
			
		}
		double fitsum = 0;
				
		
		if(printing)
		{
			System.out.println("\nTarget:");
			System.out.println(target_formula);
			System.out.println("Evaluation:");
			System.out.println(" " + phenotype);
			System.out.println("in:   out:   target:   difference");
		}
		
	
		for(int a = 0; a < 8; a++)
		{
			
			fitsum += calculate_fitness(phenotype, target_formula, a); 
				
		}
		
		double fitness = (fitsum/8);
		
		
		
		if(printing)
		{
			System.out.println("Best: " + max_fit );
			System.out.println("Fitness: " + fitness);
			
		}
	
		if(fitness > max_fit)
		{
			max_fit = fitness;	
			best_individual = phenotype;
		}
		

		if(fitness < 0 || fitness == Double.NaN)
		{
			System.out.println("ERROR");
			pause();
			fitness = 0;
		}
		return fitness; 
		
	}
	
	public boolean isNaN(double x)
	{
		return x != x;
	}
	
	public double calculate_fitness(String phenotype, String formula, int amount)
	{
		
		double result = 0;
		return result;
			
		
		
	}
	
	/*
	 * public double calculate_fitness(String phenotype, String formula)
	{
		double target = Double.NaN;
		double result = 0;
		Expression e;
		double random = 0;	
		int retries = 0;
			
		
		while(isNaN(target) && retries < max_retries)
		{
			random = Math.random()*range_size + range_min;	
			
			e = new ExpressionBuilder(phenotype)
			.variables("x")
			.build()
			.setVariable("x", random);
			result = e.evaluate();
			
			e = new ExpressionBuilder(formula)
			.variables("x")
			.build()
			.setVariable("x", random);
			target = e.evaluate();
		
			retries++;

		}

		if(isNaN(target))
		{
			System.out.println("STOP THAT SHIT");
			pause();
		}
		
		//System.out.println("|" + target + "-" + result + "| = " +  Math.abs(target-result));
		
		double difference = Math.abs(target - result)+1;
		
		

		if(printing)
		{
			System.out.println(Math.round(random) + "     " + Math.round(result) +  "     " + Math.round(target) + "     " + Math.round(difference-1));
		}
		
		if(isNaN(1/difference))
		{
			return 0;
		}
		else
		{
			return 1/difference;
		}
		
		
		
		
	}
	
	 */
	
	
	
	public double calculate_fitness(String phenotype, double x, double target_value)
	{
		
		
		Expression e = new ExpressionBuilder(phenotype)
        .variables("x")
        .build()
        .setVariable("x", x);
		double result = e.evaluate();
		
		if(result > 123456789)
		{
			result = 123456789;
		} else 
		if(result < -123456789)
		{
			result = -123456789;	
		}
		
		double diff = Math.abs(result - target_value);
		
		
		
		if(printing)
		{
			System.out.println(x + "     " + Math.round(result) +  "     " + Math.round(target_value) + "     " + Math.round(diff));
		}
		
		return diff;
		
		
		
		
		
		
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
