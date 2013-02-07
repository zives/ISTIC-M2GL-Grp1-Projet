package group1.project.synthlab.unitExtensions.FilterAttenuator;

import group1.project.synthlab.signal.Tools;

import com.jsyn.unitgen.UnitBinaryOperator;

/**
 * Applique la formule de modulation d'amplitude
 * @author Groupe 1
 * 
 */
public class FilterAmplitudeModulation extends UnitBinaryOperator {
	
	@Override
	public void generate(int start, int limit) {
		
		double[] inputsSignal = inputA.getValues();	// Notre entree A correspond au signal a moduler en entree
		double[] inputsModulation = inputB.getValues();	// Notre entree B correspond au signal modulant
		double[] outputs = output.getValues();

		for (int i = start; i < limit; i++) {
			double a = inputsSignal[i];
			double b = inputsModulation[i] * 5 * 12; // Les valeurs de notre signal modulant sont comprises entre -1 et 1, on multiplie donc par 5 pour avoir entre -5V et + 5V, et par 12 pour avoir la correspondance : 1V -> 12dB
			outputs[i] = a *Tools.dBToV(b) ; // On convertit nos decibels et on calcule la nouvelle tension

		}
	}

}