package group1.project.synthlab.module.sequencer;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.unitExtensions.filterSupervisor.FilterRisingEdge;

public interface ISequencerModule extends IModule {

	/**
	 * Ramene le pas courant au pas 1
	 */
	public void resetSteps();
	
	/**
	 * Met a jour la valeur d'un pas
	 * @param step : le numero du pas (de 1 a 8)
	 * @param value : la nouvelle valeur, comprise entre -1V et +1V
	 */
	public void setStepValue(int step, double value);
	
	/**
	 * Passe au pas suivant lorsqu'un front montant est detecte
	 */
	public void update();
	
	/**
	 * @return le filtre de detection des fronts montants
	 */
	public FilterRisingEdge getFilterRisingEdge();
	
	/**
	 * @return le pas courant
	 */
	public int getCurrentStep();
	
	/**
	 * @return le port d'entree Gate
	 */
	public IInPort getGate();
	
	/**
	 * @return le port de sortie Out
	 */
	public IOutPort getOut();
	
	/**
	 * Fonction qui gere la connexion d'un cable a un port d'entree
	 * @param port : le port du Sequenceur auquel on vient de connecter un cable
	 */
	public void cableConnected(IPort port);
	
	/**
	 * Fonction qui gere la connexion d'un cable a un port d'entree
	 * @param port : le port du Sequencer duquel on vient de deconnecter un cable
	 */
	public void cableDisconnected(IPort port);
	
}
