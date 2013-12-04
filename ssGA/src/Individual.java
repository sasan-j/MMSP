/***********************************************/
/** PROBLEM SOLVING                           **/
/** UNIVERSITY OF LUXEMBOURG                  **/
/** DEC 2010                                  **/
/** Prof. Pascal Bouvry                       **/
/** Assistant Patricia Ruiz                   **/
/** Assistant Cesar Diaz                      **/
/***********************************************/

import java.io.* ;

public class Individual implements Serializable
{
  private Chromosome chrom;
  private int        L;
  private double     fitness;

  public Individual(int L)
  {
     chrom   = new Chromosome(L);
     fitness = 0.0;
     this.L  = L;
  }

  public void print()
  {
     chrom.print();
     System.out.print("   ");
     System.out.println(fitness);
  }

  public int get_length()
  {
     return L;
  }

  public void set_fitness(double fit)
  {
     fitness = fit;
  }

  public double get_fitness()
  {
     return fitness;
  }

  public void set_allele(int index, byte value)
  {
     chrom.set_allele(index,value);
  }

  public byte get_allele(int index)
  {
     return chrom.get_allele(index);
  }

  private void copy(Chromosome source, Chromosome destination)
  {
     for (int i=0; i<L; i++)
     {
         destination.set_allele(i,source.get_allele(i));
     }
  }

  public void assign(Individual I)
  {
    copy(I.get_chromosome(),chrom);
    fitness = I.get_fitness();
    L       = I.get_length();
  }

  public void set_chrom(Chromosome ch)
  {
     copy(ch,chrom);
  }

  public Chromosome get_chromosome()
  {
     return chrom;
  }

}
// END OF CLASS: Individual
