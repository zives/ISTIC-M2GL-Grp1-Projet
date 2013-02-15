package group1.project.synthlab.unitExtension.filter.filterSupervisor;

import java.util.ArrayList;
import java.util.List;

import com.jsyn.unitgen.UnitGate;

/**
 * @author Groupe 1
 * Filtre detectant les fronts montant
 * Previens les observateurs lors d'un nouveau front montant
 */
public class FilterRisingEdge extends UnitGate implements IFilterRisingEdgeObservable {

	/**
	 * Les observateurs
	 */
	protected List<IFilterRisingEdgeObserver> observers;
	
	public FilterRisingEdge (){
		this.observers = new ArrayList<IFilterRisingEdgeObserver>();
	}
	
	public void generate(int start, int limit) {	
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();
		
		for (int i = start; i < limit; i++) {
			if(input.checkGate(i))
				notifyAllObserversNewEdge();
			outputs[i] = inputs[i];
		}
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.unitExtension.filter.filterSupervisor.IFilterObservable#register(group1.project.synthlab.unitExtension.filter.filterSupervisor.IFilterObserver)
	 */
	public void register(IFilterRisingEdgeObserver observer) {
		observers.add(observer);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.unitExtension.filter.filterSupervisor.IFilterObservable#unregister(group1.project.synthlab.unitExtension.filter.filterSupervisor.IFilterObserver)
	 */
	public void unregister(IFilterRisingEdgeObserver observer) {
		observers.remove(observer);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.unitExtension.filter.filterSupervisor.IFilterObservable#notifyAllObservers()
	 */
	public void notifyAllObserversNewEdge() {
		for(IFilterRisingEdgeObserver observer: observers)
			observer.update();
	}

}
