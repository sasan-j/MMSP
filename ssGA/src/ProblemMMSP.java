
public class ProblemMMSP extends Problem {

	InstanceParser instanceParser; 
	
	ProblemMMSP(String _prob_instance){
		instanceParser = new InstanceParser(_prob_instance);
		instanceParser.readInstanceFile();
	}
	
	@Override
	public double Evaluate(Individual indiv) {
		// TODO Auto-generated method stub
		double totalExecTime=0.0;
		for(int i=0;i<CL;i++){
			totalExecTime+=instanceParser.getInstanceValue(i,indiv.get_allele(i));
		}
		
		return totalExecTime;
	}

}
