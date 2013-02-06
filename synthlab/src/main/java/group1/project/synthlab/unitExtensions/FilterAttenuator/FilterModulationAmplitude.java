package group1.project.synthlab.unitExtensions.FilterAttenuator;

import group1.project.synthlab.signal.Tools;

import com.jsyn.unitgen.UnitBinaryOperator;

public class FilterModulationAmplitude extends UnitBinaryOperator {
	
	@Override
	public void generate(int start, int limit) {
		
		double[] inputsSignal = inputA.getValues();	// Notre entree A correspond au signal a moduler en entree
		double[] inputsModulation = inputB.getValues();	// Notre entree B correspond au signal modulant
		double[] outputs = output.getValues();

		for (int i = start; i < limit; i++) {
			double a = inputsSignal[i];
			double b = inputsModulation[i];
			outputs[i] = a * (1 + Tools.attenuationDBToV(60 * b)) ;
		}
	}

}
