package group1.project.synthlab.unitExtension.producer;

import com.jsyn.unitgen.UnitFilter;

public class SimpleProducer extends UnitFilter {

	
	@Override
	public void generate(int start, int limit) {
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();

		for (int i = start; i < limit; i++) {
			double x = inputs[i];
			outputs[i] = x ;
		}
		
	}

}
