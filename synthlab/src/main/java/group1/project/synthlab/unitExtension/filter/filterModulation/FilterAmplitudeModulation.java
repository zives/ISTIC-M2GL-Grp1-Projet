package group1.project.synthlab.unitExtension.filter.filterModulation;

import group1.project.synthlab.signal.Signal;
import group1.project.synthlab.signal.Tools;

import com.jsyn.unitgen.UnitBinaryOperator;

/**
 * Applique la formule de modulation d'amplitude
 * @author Groupe 1
 * 
 */
public class FilterAmplitudeModulation extends UnitBinaryOperator {
	
	/* (non-Javadoc)
	 * @see com.jsyn.unitgen.UnitBinaryOperator#generate(int, int)
	 */
	@Override
	public void generate(int start, int limit) {
		
		double[] inputsSignal = inputA.getValues();	// Notre entree A correspond au signal a moduler en entree
		double[] inputsModulation = inputB.getValues();	// Notre entree B correspond au signal modulant
		double[] outputs = output.getValues();

		for (int i = start; i < limit; i++) {
			double a = inputsSignal[i];
			double dB = inputsModulation[i]  * Signal.AMAX * 12 ; // Les valeurs de notre signal modulant sont comprises entre -1 et 1, on multiplie donc par 5 pour avoir entre -5V et + 5V, et par 12 pour avoir la correspondance : 1V -> 12dB
			outputs[i] = a * Tools.dBToV(dB)  ; // On converti nos decibels et on calcule la nouvelle tension
			
		}
	}

}
