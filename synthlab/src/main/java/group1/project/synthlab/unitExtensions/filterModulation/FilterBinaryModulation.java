package group1.project.synthlab.unitExtensions.filterModulation;

import group1.project.synthlab.signal.Tools;

import com.jsyn.unitgen.UnitBinaryOperator;
import com.jsyn.unitgen.UnitFilter;

/**
 * Renvoie 1 si il y a un signal ou 0 sinon
 * @author Groupe 1
 * 
 */
public class FilterBinaryModulation extends UnitFilter {
	protected double sensibility;
	
	public FilterBinaryModulation() {
		super();
		this.sensibility = 0;
	}
	
	public FilterBinaryModulation(double sensibility) {
		super();
		this.sensibility = sensibility;
	}

	@Override
	public void generate(int start, int limit) {
		
		double[] inputs = input.getValues();	
		double[] outputs = output.getValues();

		for (int i = start; i < limit; i++) {
			if (inputs[i] <= sensibility)
				outputs[i] = -1;
			else 
				outputs[i] = 1;

		}
	}

}
