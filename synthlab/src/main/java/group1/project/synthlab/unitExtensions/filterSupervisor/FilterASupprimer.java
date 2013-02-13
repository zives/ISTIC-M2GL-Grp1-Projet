package group1.project.synthlab.unitExtensions.filterSupervisor;

import java.util.List;
import com.jsyn.unitgen.UnitFilter;

public class FilterASupprimer extends UnitFilter {

	protected List<IFilterObserver> observers;
	
	public void generate(int start, int limit) {	
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();
		
		for (int i = start; i < limit; i++) {
			System.out.println("inputs[i] = " + inputs[i]);
			outputs[i] = inputs[i];
		}
	}

}
