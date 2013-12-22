import com.sun.org.apache.xml.internal.serializer.ToSAXHandler;

/**
 * 
 */

/**
 * @author sasan
 *
 */
public class Experiment {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int numberRuns = 50;
		//Exe exe = new Exe();
		
		double fitness = 0;
		String[] arg = {"Hi"};
		for(double p=0;p<1.0;p++){
			fitness = 0;
			arg[0]=String.valueOf(p);
			for(int i=0;i<numberRuns;i++){
				fitness+=Exe.main(arg);
			}
			System.out.println("Average fitness with p crossover="+String.valueOf(p)+" is: "+String.valueOf(fitness/numberRuns));
		}
		
	
	}

}
