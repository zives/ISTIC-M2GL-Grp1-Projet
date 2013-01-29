package group1.project.synthlab.unitExtensions;

import com.jsyn.unitgen.UnitBinaryOperator;
import com.jsyn.unitgen.UnitFilter;

public class Attenuator extends UnitFilter {

	//en db
	private double attenuation;
	
	public Attenuator() {
		super();
		attenuation = 0;
	}
	 
	
	@Override
	public void generate(int start, int limit) {
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();

		for (int i = start; i < limit; i++) {
			double x = inputs[i];
			if (x > 0){
				x += attenuation;
				if (x < 0) x = 0;
			}
			else if (x < 0) {
				x -= attenuation;
				if (x > 0) x = 0;
			}
			outputs[i] = x ;
		}

	}


	public double getAttenuation() {
		return attenuation;
	}


	public void setAttenuation(double attenuation) {
		this.attenuation = attenuation;
	}

}
