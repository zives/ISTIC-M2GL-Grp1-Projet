package group1.project.synthlab.module.eg;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;

/**
 * Module EG
 * 
 * @author Groupe 1
 * 
 */
public interface IEGModule extends IModule {

	/**
	 * @return le temps de montee en milliseconde
	 */
	public int getAttack();
	

	/**
	 * @param definit le temps de montee en milliseconde
	 */
	public void setAttack(int attack);
	
	
	/**
	 * @return le temps d'extinction en milliseconde
	 */
	public int getDecay();
	

	/**
	 * @param definit le temps d'extinction en milliseconde
	 */
	public void setDecay(int decay);


	/**
	 * @return le temps de relachement en milliseconde
	 */
	public int getRelease();
	
	
	/**
	 * @param le temps de relachement en milliseconde
	 */
	public void setRelease(int release);
	

	/**
	 * @return le temps entre le temps de montee et le temps de relachement en milliseconde
	 */
	public int getHold();
	
	
	/**
	 * @param definit le temps entre le temps de montee et le temps de relachement en milliseconde
	 */
	public void setHold(int hold);
	
	
	/**
	 * @return l'attenuation en dB du niveau maximum atteint en fin de phase de montee
	 */
	public double getSustain();
	

	/**
	 * @param l'attenuation en dB du niveau maximum atteint en fin de phase de montee
	 */
	public void setSustain(double sustain);
	

	/**
	 * @return le port d'entree de declenchement
	 */
	public IInPort getGate();
	

	/**
	 * @return le port de sortie
	 */
	public IOutPort getOut();

	/**
	 * Fonction qui gere la connexion d'un cable a un port d'entree
	 * @param port : le port de l'EG auquel on vient de connecter un cable
	 */
	public void cableConnected(IPort port);
	
	/**
	 * Fonction qui gere la deconnexion d'un cable d'un port d'entree
	 * @param port : le port de l'EG duquel on vient de deconnecter un cable
	 */
	public void cableDisconnected(IPort port);
}
