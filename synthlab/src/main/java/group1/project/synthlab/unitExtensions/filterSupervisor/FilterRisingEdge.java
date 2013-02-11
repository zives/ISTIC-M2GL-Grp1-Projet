package group1.project.synthlab.unitExtensions.filterSupervisor;

import java.util.ArrayList;
import java.util.List;

import com.jsyn.unitgen.UnitGate;

public class FilterRisingEdge extends UnitGate implements IFilterObservable {

	protected List<IFilterObserver> observers;
	
	public void generate(int start, int limit) {		
		for (int i = start; i < limit; i++) {
			if(input.checkGate(i))
				notifyAllObservers();
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
