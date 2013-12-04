/***********************************************/
/** PROBLEM SOLVING                           **/
/** UNIVERSITY OF LUXEMBOURG                  **/
/** DEC 2010                                  **/
/** Prof. Pascal Bouvry                       **/
/** Assistant Patricia Ruiz                   **/
/** Assistant Cesar Diaz                      **/
/***********************************************/

public class Exe
{
  public static void main(String args[]) throws Exception
  {
/*     
    // PARAMETERS PPEAKS 
    int    gn         = 512;                           // Gene number
    int    gl         = 1;                            // Gene length
    int    popsize    = 512;                          // Population size
    double pc         = 0.8;                          // Crossover probability
    double pm  = 1.0/(double)((double)gn*(double)gl); // Mutation probability
    double tf         = (double)1 ;              // Target fitness beign sought
    long   MAX_ISTEPS = 50000;
*/
      
    // PARAMETERS ONEMAX
    int    gn         = 512;                          // Gene number
    int    gl         = 1;                            // Gene length
    int    popsize    = 512;                          // Population size
    double pc         = 0.8;                          // Crossover probability
    double pm  = 1.0/(double)((double)gn*(double)gl); // Mutation probability
    double tf         = (double)gn*gl ;               // Target fitness being sought
    long   MAX_ISTEPS = 50000;
    
    Problem   problem;                             // The problem being solved

    // problem = new ProblemPPeaks(); 
    problem = new ProblemOneMax();
    
    problem.set_geneN(gn);
    problem.set_geneL(gl);
    problem.set_target_fitness(tf);



    Algorithm ga;          // The ssGA being used
    ga = new Algorithm(problem, popsize, gn, gl, pc, pm);


    for (int step=0; step<MAX_ISTEPS; step++)
    {  
      ga.go_one_step();
      System.out.print(step); System.out.print("  ");
      System.out.println(ga.get_bestf());

      if(     (problem.tf_known())                    &&
      (ga.get_solution()).get_fitness()>=problem.get_target_fitness()
      )
      { System.out.print("Solution Found! After ");
        System.out.print(problem.get_fitness_counter());
        System.out.println(" evaluations");
        break;
      }

    }

    // Print the solution
    for(int i=0;i<gn*gl;i++)
      System.out.print( (ga.get_solution()).get_allele(i) ); System.out.println();
    System.out.println((ga.get_solution()).get_fitness());
  }

}
// END OF CLASS: Exe
