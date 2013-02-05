package group1.project.synthlab.unitExtensions.Attenuator;

import com.jsyn.unitgen.UnitFilter;

/**
 * Atténue un son via en soustrayant l'amplitude par une autre valeur
 * @author Groupe 1
 * 
 */
public class Attenuator extends UnitFilter {
	
	private double attenuationV;

	
	public Attenuator() {
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
	
	public static double attenuationDBToV(double attenuationDB){	
		double voltage = Math.pow(10.0, new Double(attenuationDB) / 20.0);
		return voltage - 1;		
	}

}
