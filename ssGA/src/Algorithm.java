/***********************************************/
/** PROBLEM SOLVING                           **/
/** UNIVERSITY OF LUXEMBOURG                  **/
/** DEC 2010                                  **/
/** Prof. Pascal Bouvry                       **/
/** Assistant Patricia Ruiz                   **/
/** Assistant Cesar Diaz                      **/
/***********************************************/

import java.util.Random;

public class Algorithm
{
	private  int          chrom_length; // Alleles per chromosome
	private  int          gene_number;  // Number of genes in every chromosome
	private  int          gene_length;  // Number of bits per gene
	private  int          popsize;      // Number of individuals in the population
	private  double pc, pm;      // Probability of applying crossover and mutation
	private  Problem       problem;     // The problem being solved
	private  Population    pop;         // The population
	private  static Random r;           // Source for random values in this class
	private  Individual aux_indiv;  // Internal auxiliar individual being computed
	private  Individual aux_indiv2;  // Second Internal auxiliar individual being computed


	// CONSTRUCTOR
	public Algorithm(Problem p, int popsize, int gn, int gl, double pc, double pm)
			throws Exception
			{
		this.gene_number   = gn;
		this.gene_length   = gl;
		this.chrom_length  = gn*gl;
		this.popsize       = popsize;
		this.pc            = pc;
		this.pm            = pm;
		this.problem       = p;                     
		this.pop = new Population(popsize,chrom_length);// Create initial population
		this.r             = new Random();
		this.aux_indiv     = new Individual(chrom_length,0);
		this.aux_indiv2     = new Individual(chrom_length,0);


		for(int i=0;i<popsize;i++)
			pop.set_fitness(i,problem.evaluateStep(pop.get_ith(i)));
		pop.compute_stats();
			}

	// BINARY TOURNAMENT
	public Individual select_tournament() throws Exception
	{
		int p1, p2;

		p1 = (int)(r.nextDouble()*
				(double)popsize + 0.5); // Round and then trunc to int

		if(p1>popsize-1) p1=popsize-1;
		do
		{  p2 = (int)(r.nextDouble()*
				(double)popsize + 0.5);  // Round and then trunc to int
		if(p2>popsize-1) p2=popsize-1;
		}
		while (p1==p2);
		if (pop.get_ith(p1).get_fitness()<pop.get_ith(p2).get_fitness())
			return pop.get_ith(p1);
		else
			return pop.get_ith(p2);
	}

	// RANDOM SELECTION
	public Individual select_random() throws Exception
	{
		int p1;

		p1 = (int)(r.nextDouble()*
				(double)popsize + 0.5); // Round and then trunc to int

		if(p1>popsize-1) p1=popsize-1;
		return pop.get_ith(p1);
	}

	// TWO POINT CROSSOVER - ONLY ONE CHILD IS CREATED (RANDOMLY DISCARD 
	// DE OTHER)
	public Individual DPX (Individual p1, Individual p2)
	{
		int       crosspoint1,crosspoint2;

		crosspoint1 = (int)(r.nextDouble()*
				(double)chrom_length-1+0.5); // From 0 to L-1 rounded

		crosspoint2 = (int)(r.nextDouble()*
				(double)chrom_length-1+0.5); // From 0 to L-1 rounded
		while(crosspoint1==crosspoint2)
			crosspoint2 = (int)(r.nextDouble()*(double)chrom_length-1+0.5); // From 0 to L-1 rounded


		if(r.nextDouble()>pc)  // If no crossover then randomly returns one parent
			return r.nextDouble()>0.5?p1:p2;

			if (crosspoint1 > crosspoint2) {
				int swap ;
				swap        = crosspoint1;
				crosspoint1 = crosspoint2;
				crosspoint2 = swap;
			} // if

			// Copy CHROMOSOME 1 first portion
			for (int i=0; i<crosspoint1; i++)
			{
				aux_indiv.set_allele(i,p1.get_allele(i));
			}
			// Copy CHROMOSOME 2 Second portion
			for (int i=crosspoint1; i<crosspoint2; i++)
			{
				aux_indiv.set_allele(i,p2.get_allele(i));
			}

			// Copy CHROMOSOME 1 Third portion
			for (int i=crosspoint1; i<crosspoint2; i++)
			{
				aux_indiv.set_allele(i,p1.get_allele(i));
			}

			return aux_indiv;
	}

	// SINGLE POINT CROSSOVER - ONLY ONE CHILD IS CREATED (RANDOMLY DISCARD 
	// DE OTHER)
	public Individual SPX (Individual p1, Individual p2)
	{
		int       rand;

		rand = (int)(r.nextDouble()*
				(double)chrom_length-1+0.5); // From 0 to L-1 rounded
		if(rand>chrom_length-1) rand=chrom_length-1;

		if(r.nextDouble()>pc)  // If no crossover then randomly returns one parent
			return r.nextDouble()>0.5?p1:p2;

			// Copy CHROMOSOME 1
			for (int i=0; i<rand; i++)
			{
				aux_indiv.set_allele(i,p1.get_allele(i));
			}
			// Copy CHROMOSOME 2
			for (int i=rand; i<chrom_length; i++)
			{
				aux_indiv.set_allele(i,p2.get_allele(i));
			}

			return aux_indiv;
	}

	// SINGLE POINT CROSSOVER - TWO child version
	public Individual SPXB (Individual p1, Individual p2)
	{
		int       rand;

		rand = (int)(r.nextDouble()*
				(double)chrom_length-1+0.5); // From 0 to L-1 rounded
		if(rand>chrom_length-1) rand=chrom_length-1;

		if(r.nextDouble()>pc)  // If no crossover then randomly returns one parent
		{
			aux_indiv2 = p2;
			//return r.nextDouble()>0.5?p1:p2;
			return p1;
		}
		// Copy CHROMOSOME 1 to first
		for (int i=0; i<rand; i++)
		{
			aux_indiv.set_allele(i,p1.get_allele(i));
		}
		// Copy CHROMOSOME 2 to first
		for (int i=rand; i<chrom_length; i++)
		{
			aux_indiv.set_allele(i,p2.get_allele(i));
		}

		// Copy CHROMOSOME 1 to second
		for (int i=0; i<rand; i++)
		{
			aux_indiv2.set_allele(i,p2.get_allele(i));
		}
		// Copy CHROMOSOME 2 to second
		for (int i=rand; i<chrom_length; i++)
		{
			aux_indiv2.set_allele(i,p1.get_allele(i));
		}

		return aux_indiv;//second offspring is in aux_indiv2
	}


	// MUTATE A INT CHROMOSOME In range 0 to 15 including the bounds
	public Individual mutate(Individual p1)
	{
		byte alelle=0;
		Random r = new Random();

		double start = 0;
		double end = 16;
		byte result;// = start + (r.nextDouble() * (end - start));


		aux_indiv.assign(p1);

		for(int i=0; i<chrom_length; i++)
			if (r.nextDouble()<=pm)  // Check mutation bit by bit...
			{
				do{
					result=(byte)(start + (r.nextDouble() * (end - start)));
				}while(aux_indiv.get_allele(i)==result);
				aux_indiv.set_allele(i,result);
			}

		return aux_indiv;
	}

	/*
  // MUTATE A BINARY CHROMOSOME
  public Individual mutate(Individual p1)
  {
    byte alelle=0;
    Random r = new Random();

    aux_indiv.assign(p1);

    for(int i=0; i<chrom_length; i++)
    if (r.nextDouble()<=pm)  // Check mutation bit by bit...
    {
      if(aux_indiv.get_allele(i)==1)
      aux_indiv.set_allele(i,(byte)0);
      else
      aux_indiv.set_allele(i,(byte)1);
    }

    return aux_indiv;

  }
	 */

	// REPLACEMENT - THE WORST INDIVIDUAL IS ALWAYS DISCARDED
	public void replace(Individual new_indiv) throws Exception
	{
		pop.set_ith(pop.get_worstp(),new_indiv);
		pop.compute_stats();                  // Recompute avg, best, worst, etc.
	}

	// EVALUATE THE FITNESS OF AN INDIVIDUAL
	private double evaluateStep(Individual indiv)
	{
		return problem.evaluateStep(indiv);
	}

	public void go_one_step() throws Exception
	{
		/* crssover with two offspring *//*
    aux_indiv.assign( SPXB(select_tournament(),select_tournament()) );
    aux_indiv2.assign(aux_indiv2);
    aux_indiv.set_fitness(problem.evaluateStep(mutate(aux_indiv)));
    aux_indiv2.set_fitness(problem.evaluateStep(mutate(aux_indiv2)));
    replace(aux_indiv);
    replace(aux_indiv2);
		 */

		/* crssover with one offspring */

		//	    aux_indiv.assign( SPX(select_random(),select_random()) );
		//	    aux_indiv.assign( DPX(select_tournament(),select_tournament()) );
		//    aux_indiv.assign( DPX(select_tournament(),select_tournament()) );
		aux_indiv.assign( SPX(select_tournament(),select_tournament()) );
		aux_indiv.set_fitness(problem.evaluateStep(mutate(aux_indiv)));
		replace(aux_indiv);

	}

	public Individual get_solution() throws Exception
	{
		return pop.get_ith(pop.get_bestp());// The better individual is the solution
	}


	public int    get_worstp() { return pop.get_worstp(); }
	public int    get_bestp()  { return pop.get_bestp();  }
	public double get_worstf() { return pop.get_worstf(); }
	public double get_avgf()   { return pop.get_avgf();   }
	public double get_bestf()  { return pop.get_bestf();  }
	public double get_BESTF()  { return pop.get_BESTF();  }

	public Individual get_ith(int index) throws Exception
	{
		return pop.get_ith(index);
	}

	public void set_ith(int index, Individual indiv) throws Exception
	{
		pop.set_ith(index,indiv);
	}
}
// END OF CLASS: Algorithm

