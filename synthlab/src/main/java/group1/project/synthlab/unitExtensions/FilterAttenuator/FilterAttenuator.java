package group1.project.synthlab.unitExtensions.filterAttenuator;

import com.jsyn.unitgen.UnitFilter;

/**
 * Attenue un son sur son amplitude
 * @author Groupe 1
 * 
 */
public class FilterAttenuator extends UnitFilter {
	
	private double attenuationV;

	
	public FilterAttenuator() {
		super();
		attenuationV = 0;
	}
	
	@Override
	public void generate(int start, int limit) {
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();

		for (int i = start; i < limit; i++) {
			double x = inputs[i];
			double tmpAttenuation = attenuationV * x;
			x = x + tmpAttenuation;
			outputs[i] = x ;
		}

	}

	/**
	 * 
	 * @return the amplitude attenuation 
	 */
	public double getAttenuation() {
		return attenuationV;
	}

	/**
	 * @param attenuation  the amplitude attenuation 
	 */
	public void setAttenuation(double attenuationV) {
		this.attenuationV = attenuationV;
	}
	

}
