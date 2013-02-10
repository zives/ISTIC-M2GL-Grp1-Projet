package group1.project.synthlab.unitExtensions.filterModulation;

import com.jsyn.unitgen.UnitFilter;

/**
 * Applique la formule de modulation de frequence : f = f0 * 2^Vfm
 * @author Groupe 1
 * 
 */
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
	 * @return la frequence de base
	 */
	public double getf0() {
		return f0;
	}

	/**
	 * @param la nouvelle frequence de base
	 */
	public void setf0(double f0) {
		this.f0 = f0;
	}
	
}
