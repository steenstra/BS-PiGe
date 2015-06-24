package bscipt;

public class Individual 
{
//	int codonsize;
//	int number_of_codons;
		
	String genotype;
	String phenotype;
	double fitness;
	
	public Individual()
	{
	
	}
	
	public String get_geno()
	{
		return genotype;
	}
	
	public void set_geno(String dna)
	{
		genotype = dna;
	}
	
	
	public String get_pheno()
	{
		return phenotype;
	}
	
	public void set_pheno(String pheno)
	{
		phenotype = pheno;
	}
	
	public void set_fitness(double f)
	{
		fitness = f;
	}
	
	public double get_fitness()
	{
		return fitness;
	}
	
	public String get_all_info()
	{
		return fitness + ", " + phenotype + ", " + genotype;
	}
	
	
}
