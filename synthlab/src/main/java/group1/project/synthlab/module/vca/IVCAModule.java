package group1.project.synthlab.module.vca;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.unitExtensions.filterModulation.FilterAmplitudeModulation;

public interface IVCAModule extends IModule {
	
	/**
	 * Met a jour le gain de base en fonction du reglage dans l'IHM
	 */
	public void changeGain();
	
	/**
	 * @return la valeur actuelle du gain de base
	 */
	public double geta0() ;
	
	/**
	 * Definit une valeur de gain de base personnalisee
	 */
	public void seta0(double a0) ;
	
	/**
	 * @return le filtre de changement d'amplitude en fonction de a0
	 */
	public FilterAmplitudeModulation getFiltera0();	
	
	/**
	 * @return le filtre de changement d'amplitude en fonction de am
	 */
	public FilterAmplitudeModulation getFilteram();	
	
	/**
	 * @return le port d'entree In (signal d'entree)
	 */
	public IInPort getIn();	
	
	/**
	 * @return le port d'entree Am (modulation d'amplitude)
	 */
	public IInPort getAm() ;
	
	/**
	 * @return le port de sortie
	 */
	public IOutPort getOut() ;
	
	/**
	 * Fonction qui gere la connexion d'un cable a un port d'entree
	 * @param port : le port du VCA auquel on vient de connecter un cable
	 */
	public void cableConnected(IPort port);
	
	/**
	 * Fonction qui gere la deconnexion d'un cable d'un port d'entree
	 * @param port : le port du VCA auquel on vient de connecter un cable
	 */
	public void cableDisconnected(IPort port);
}