package group1.project.synthlab.unitExtensions.filterModulation;

import com.jsyn.unitgen.UnitFilter;

/**
 * Renvoie 1 si il y a un signal ou 0 sinon
 * @author Groupe 1
 * 
 */
public class FilterBinaryModulation extends UnitFilter {
	protected double sensibility;
	protected double previousState;
	
	public FilterBinaryModulation() {
		super();
		this.sensibility = 0;
		this.previousState = 0;
	}
	
	public FilterBinaryModulation(double sensibility) {
		super();
		this.sensibility = sensibility;
		this.previousState = 0;
	}

	@Override
	public void generate(int start, int limit) {
		
		double[] inputs = input.getValues();	
		double[] outputs = output.getValues();

		double nextState = 0;
		for (int i = start; i < limit - 1; i++) {
			if (inputs[i] <= sensibility)
				outputs[i] = -1;
			else 
				nextState = 1;
			outputs[i] =  previousState;

		}
		previousState = nextState;
		outputs[limit - 1] = nextState;
	}

}
