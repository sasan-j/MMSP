import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class ProblemMMSP extends Problem {

	InstanceParser instanceParser; 
	
	ProblemMMSP(String _prob_instance){
		instanceParser = new InstanceParser(_prob_instance);
		instanceParser.readInstanceFile();
	}
	
	@Override
	public double Evaluate(Individual indiv) {
		
		double[] execTimeArray = new double[16];
		// TODO Auto-generated method stub
		double totalExecTime=0.0;
		for(int i=0;i<CL;i++){
			int _machine = indiv.get_allele(i);
			execTimeArray[_machine]+=instanceParser.getInstanceValue(i,_machine);
		}

		return getMaxValue(execTimeArray);
	}
	
	// getting the maximum value
	public static double getMaxValue(double[] array){  
	      double maxValue = array[0];  
	      for(int i=1;i < array.length;i++){  
	      if(array[i] > maxValue){  
	      maxValue = array[i];  

	        }  
	     }  
	             return maxValue;  
	} 

}
