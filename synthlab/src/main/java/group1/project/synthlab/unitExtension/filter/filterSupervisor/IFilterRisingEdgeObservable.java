package group1.project.synthlab.unitExtension.filter.filterSupervisor;

/**
 * @author Groupe 1
 * Filtre observabledetectant les fronts montant
 */
public interface IFilterRisingEdgeObservable {
	
	/**
	 * Enregistre un observateur aupres du filtre
	 * @param observer
	 */
	public void register(IFilterRisingEdgeObserver observer);
	
	/**
	 * Annule l'observation d'un observateur aupres du filtre
	 * @param observer
	 */
	public void unregister(IFilterRisingEdgeObserver observer);
	
	
	/**
	 * Notifie les observateurs d'un nouveau front montant
	 */
	public void notifyAllObserversNewEdge();
}
