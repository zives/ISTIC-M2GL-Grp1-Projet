package group1.project.synthlab.unitExtension.filter.filterSupervisor;

import java.util.ArrayList;
import java.util.List;

import com.jsyn.unitgen.UnitGate;

public class FilterRisingEdge extends UnitGate implements IFilterObservable {

	protected List<IFilterObserver> observers;
	
	public void generate(int start, int limit) {	
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();
		
		for (int i = start; i < limit; i++) {
			if(input.checkGate(i))
				notifyAllObservers();
			outputs[i] = inputs[i];
		}
	}

	public FilterRisingEdge (){
		this.observers = new ArrayList<IFilterObserver>();
	}
	
	public void register(IFilterObserver observer) {
		observers.add(observer);
	}

	public void unregister(IFilterObserver observer) {
		observers.remove(observer);
	}

	public void notifyAllObservers() {
		for(IFilterObserver observer: observers)
			observer.update();
	}

}
