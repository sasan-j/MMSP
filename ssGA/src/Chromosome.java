/***********************************************/
/** PROBLEM SOLVING                           **/
/** UNIVERSITY OF LUXEMBOURG                  **/
/** DEC 2010                                  **/
/** Prof. Pascal Bouvry                       **/
/** Assistant Patricia Ruiz                   **/
/** Assistant Cesar Diaz                      **/
/***********************************************/ 

import java.util.Random;
import java.io.* ;

public class Chromosome implements Serializable
{
  private byte alleles[];		// Allele vector
  private int  L;			// Length of the allele vector
  private static Random r = new Random(); // Only the first time it is initialized


  // CONSTRUCTOR - FILL UP THE CONTENTS
  public Chromosome(int length)
  {

    alleles = new byte[length];
    L = length;
    for (int i=0; i<length; i++)
    if(r.nextDouble()>0.5)          // Returns values in [0..1]
    alleles[i] = 1;
    else
    alleles[i] = 0;
  }

  public void set_allele(int index, byte value)
  {
    alleles[index] = value;
  }

  public byte get_allele(int index)
  {
    return alleles[index];
  }

  public void print()
  {
    for(int i=0; i<L; i++)
    System.out.print(alleles[i]);
  }

}
// END OF CLASS: Chromosome
