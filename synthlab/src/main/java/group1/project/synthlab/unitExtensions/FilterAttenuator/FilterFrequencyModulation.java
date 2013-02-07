package group1.project.synthlab.unitExtensions.FilterAttenuator;

import com.jsyn.unitgen.UnitFilter;

public class FilterFrequencyModulation extends UnitFilter {
	
	private double f0;
	
	public FilterFrequencyModulation(double f0) {
		super();
		this.f0 = f0;
	}

	@Override
	public void generate(int start, int limit) {
		
		double[] inputs = input.getValues();	// Notre entree A correspond au signal modulant
		double[] outputs = output.getValues();

		for (int i = start; i < limit; i++) {
			double Vfm = inputs[i] * 5; // On multiplie par 5 pour passer des JSyn ([-1 ; 1]) aux Volts ([-5 ; 5])
			outputs[i] = f0 * Math.pow(2, Vfm) ;
		}
	}

	/**
	 * 
	 * @return the amplitude attenuation 
	 */
	public double getf0() {
		return f0;
	}

	/**
	 * @param attenuation  the amplitude attenuation 
	 */
	public void setf0(double f0) {
		this.f0 = f0;
	}
	
}
