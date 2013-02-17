package group1.project.synthlab.module.eg;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.unitExtension.filter.filterEnvelope.FilterEnvelopeAHDSR;

/**
 * Module EG 
 * @author Groupe 1
 * Cree une enveloppe sur un front montant
 */
public interface IEGModule extends IModule {

	/**
	 * @return le generateur d'enveloppe
	 */
	public FilterEnvelopeAHDSR getEnvelope();
	
	/**
	 * @return le temps de montee en secondes
	 */
	public double getAttack();
	

	/**
	 * @param attack defini le temps de montee en secondes
	 */
	public void setAttack(double attack);
	
	
	/**
	 * @return le temps d'extinction en secondes
	 */
	public double getDecay();
	

	/**
	 * @param decay definit le temps d'extinction en secondes
	 */
	public void setDecay(double decay);


	/**
	 * @return le temps de relachement en secondes
	 */
	public double getRelease();
	
	
	/**
	 * @param release le temps de relachement en secondes
	 */
	public void setRelease(double release);
	

	/**
	 * @return le temps entre le temps de montee et le temps de relachement en secondes
	 */
	public double getHold();
	
	
	/**
	 * @param hold defini le temps entre le temps de montee et le temps de relachement en secondes
	 */
	public void setHold(double hold);
	
	
	/**
	 * @return l'attenuation en dB du niveau maximum atteint en fin de phase de montee
	 */
	public double getSustain();
	

	/**
	 * @param sustain l'attenuation en dB du niveau maximum atteint en fin de phase de montee
	 */
	public void setSustain(double sustain);
	
	
	/**
	 * @return la valeur en decibel de sustain
	 */
	public double getDecibel();
	

	/**
	 * @return le port d'entree de declenchement
	 */
	public IInPort getGate();
	

	/**
	 * @return le port de sortie
	 */
	public IOutPort getOut();

}
