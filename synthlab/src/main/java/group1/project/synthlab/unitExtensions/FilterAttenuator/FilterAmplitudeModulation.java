package group1.project.synthlab.unitExtensions.FilterAttenuator;

import group1.project.synthlab.signal.Tools;

import com.jsyn.unitgen.UnitBinaryOperator;

public class FilterAmplitudeModulation extends UnitBinaryOperator {
	
	@Override
	public void generate(int start, int limit) {
		
		double[] inputsSignal = inputA.getValues();	// Notre entree A correspond au signal a moduler en entree
		double[] inputsModulation = inputB.getValues();	// Notre entree B correspond au signal modulant
		double[] outputs = output.getValues();

		for (int i = start; i < limit; i++) {
			double a = inputsSignal[i];
			double b = inputsModulation[i];
			outputs[i] = a *Tools.dBToV(60 * b) ; // Plus besoin du +1 car j'ai modifie la fnc dans tools (j'ai enleve le -1)

		}
	}

}
