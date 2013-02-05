package group1.project.synthlab.unitExtensions.FilterAttenuator;

import com.jsyn.unitgen.UnitFilter;

/**
 * Atténue un son sur son amplitude
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
	
	/** 
	 * @param attenuationDB une valeur en exprimée dB
	 * @return une valeur en volt à multiplier par l'amplitude courante pour attenuer un son
	 */
	public static double attenuationDBToV(double attenuationDB){	
		double voltage = Math.pow(10.0, new Double(attenuationDB) / 20.0);
		return voltage - 1;		
	}

}
