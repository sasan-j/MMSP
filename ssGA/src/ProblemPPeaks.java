/***********************************************/
/** PROBLEM SOLVING                           **/
/** UNIVERSITY OF LUXEMBOURG                  **/
/** DEC 2010                                  **/
/** Prof. Pascal Bouvry                       **/
/** Assistant Patricia Ruiz                   **/
/** Assistant Cesar Diaz                      **/
/***********************************************/

public class ProblemPPeaks extends Problem {

    /** Creates new ProblemPPeaks */
    //public ProblemPPeaks() {
    //    super() ;
    //}

    
  public double Evaluate(Individual Indiv)
  {
    return P_PEAKS(Indiv);

  }

  //    PRIVATE METHODS


  // Massively Multimodal Deceptive Problem with 6 bits
  private double MMDP6(Individual indiv)
  {
    int    i, j, index, len;
    int    unitation;
    double  v;

    v     = 0.0;
    index = 0;
    len   = CL/6;   // The result must be an integer

    for(i=0; i<len; i++)
    { unitation = 0;
      for(j=0; j<6; j++)
      if(indiv.get_allele(index++)==1)	unitation++;

      switch (unitation)           		// 6-BIT BIPOLAR DECEPTION
      { case	0 : v += 1.000000; break;
        case	1 : v += 0.000000; break;
        case	2 : v += 0.360384; break;
        case	3 : v += 0.640576; break;
        case	4 : v += 0.360384; break;
        case	5 : v += 0.000000; break;
        case	6 : v += 1.000000; break;
      }
    }
    indiv.set_fitness(v);
    return v;
  }


  // SUBSET SUM PROBLEM	- NPcomplete, Jelasity and Dombi (Artificial Intelligence)
  // Parameter ranges: 0 and 1 (bit string)
  // Strlen = 128 bits
private static int w[]={2902, 5235, 357, 6058, 4846, 8280, 1295, 181, 3264, 
                        7285, 8806, 2344, 9203, 6806, 1511, 2172, 843, 4697, 
                        3348, 1866, 5800, 4094, 2751, 64, 7181, 9167, 5579, 
                        9461, 3393, 4602, 1796, 8174, 1691, 8854, 5902, 4864, 
                        5488, 1129, 1111, 7597, 5406, 2134, 7280, 6465, 4084, 
                        8564, 2593, 9954, 4731, 1347, 8984, 5057, 3429, 7635, 
                        1323, 1146, 5192, 6547, 343, 7584, 3765, 8660, 9318, 
                        5098, 5185, 9253, 4495, 892, 5080, 5297, 9275, 7515, 
                        9729, 6200, 2138, 5480, 860, 8295, 8327, 9629, 4212, 
                        3087, 5276, 9250, 1835, 9241, 1790, 1947, 8146, 8328, 
                        973, 1255, 9733, 4314, 6912, 8007, 8911, 6802, 5102, 
                        5451, 1026, 8029, 6628, 8121, 5509, 3603, 6094, 4447, 
                        683, 6996, 3304, 3130, 2314, 7788, 8689, 3253, 5920, 
                        3660, 2489, 8153, 2822, 6132, 7684, 3032, 9949, 59, 
                        6669, 6334};
  private static int C = 300500;
  private double SUBSET_SUM(Individual indiv)
  {

    int i;
    double fitness = 0.0;

    if(CL!=128)	
      System.out.println("Length mismatch error in Subset sum function.");


    for( i=0; i<CL; i++ )
      fitness += indiv.get_allele(i)*w[i];

    if (fitness>C)
    {
      fitness = C - fitness*0.1;
      if(fitness<0.0) fitness=0.0;
    }
    indiv.set_fitness(fitness);
    return fitness;
  }


  // P-PEAKS PROBLEM GENERATOR
  // De Jong et al. 97 - 7th ICGA pags. 338-345
  // Complex multimodal function generator having strong epistasis
  // Solution fitness ===> 1.0	(minimum fitness is 0.0)
  // Parameter ranges ===> 0 and 1
  // GOAL: locate a peak from P peaks
  private static int   N;			// Length of chromosome
  private static int   P          = 1000;	// Number of Peaks
  private static short peak[][];		// The peaks
  private double P_PEAKS(Individual indiv)
  {

    double fitness = 0.0;		// Fitness of the individual
    int    nearest_peak;		// Bits in common with nearest peak
    int    j, p, hd;			// Counters

    if(fitness_counter==1) 	// Only the first time create the peaks
    {
      N = indiv.get_length();
      peak = new short[P][N];  	// GET MEMORY
      for(p=0;p<P;p++)
      {
        for(j=0;j<N;j++)
        if(r.nextDouble()<0.5)	peak[p][j] = 1;
        else	peak[p][j] = 0;
      }
    }

    nearest_peak = 0;

    for(p=0; p<P; p++)	// For every peak do
    {
      // Compute Hamming distance
      for(hd=0,j=0;j<N;j++) if(peak[p][j]!=indiv.get_allele(j))	hd++;

      if((N-hd)>nearest_peak)	nearest_peak = N-hd;
    }

    fitness = (double)nearest_peak / (double)N;
    indiv.set_fitness(fitness);
    return fitness;

  }
  
}


