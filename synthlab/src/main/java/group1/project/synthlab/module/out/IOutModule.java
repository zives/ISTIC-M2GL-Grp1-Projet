package group1.project.synthlab.module.out;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.in.IInPort;

import com.jsyn.unitgen.LineOut;

/**
 * Interface de creation du module de sortie.
 * @author Groupe 1
 *
 */
public interface IOutModule extends IModule {
	
	public enum Distribution {
		NORMAL, DISTRIBUTED
	}
	
	public void setDistribution(Distribution distribution);

	/**
	 * Attenuation du son Formule pour attenuer le son 10 ^ (valeurEnDB / 20) *
	 * tensionNominale http://fr.wikipedia.org/wiki/Niveau_(audio)
	 * 
	 * @param db
	 *            (valeurs : -inf a +12 dB)
	 */
	public void setAttenuation(double db);

	/**
	 * @return the amplitude attenuation.
	 */
	public double getAttenuation();

	/**
	 * @return l'objet JSyn correspondant a la sortie brute (son non filtre).
	 */
	public LineOut getLineOut();

	/**
	 * @return le port d'entree gauche.
	 */
	public IInPort getLeftPort();

	/**
	 * @return le port d'entree droite.
	 */
	public IInPort getRightPort();

	/**
	 * @return la distribution du son.
	 */
	public Distribution getDistribution();

}
