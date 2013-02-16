package group1.project.synthlab.unitExtension.filter.filterAttenuator;

import com.jsyn.unitgen.UnitFilter;

/**
 * Attenue un son sur son amplitude d'entree
 * @author Groupe 1
 * 
 */
public class FilterAttenuator extends UnitFilter {
	
	/**
	 * Attenuation en volt a attenuer
	 */
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
	 * @return l'attenuation courante en volt
	 */
	public double getAttenuation() {
		return attenuationV;
	}

	/**
	 * @param attenuationV defini une attenuation en volt
	 */
	public void setAttenuation(double attenuationV) {
		this.attenuationV = attenuationV;
	}
	

}
