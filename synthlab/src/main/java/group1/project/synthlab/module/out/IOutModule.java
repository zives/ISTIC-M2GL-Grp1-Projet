package group1.project.synthlab.module.out;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.module.out.OutModule.Distribution;
import group1.project.synthlab.port.in.IInPort;

import com.jsyn.unitgen.LineOut;

public interface IOutModule extends IModule {
	public void setDistribution(Distribution distribution);

	/**
	 * Atténuation du son Formule pour attenuer le son 10 ^ (valeurEnDB / 20) *
	 * tensionNominale http://fr.wikipedia.org/wiki/Niveau_(audio)
	 * 
	 * @param db
	 *            (valeurs : -inf à +12 dB)
	 */
	public void setAttenuation(double db);

	/**
	 * 
	 * @return the amplitude attenuation
	 */
	public double getAttenuation();

	/**
	 * 
	 * @return l'objet JSyn correspondant à la sortie brute (son non filtré)
	 */
	public LineOut getLineOut();

	/**
	 * @return Le port d'entrée gauche
	 */
	public IInPort getLeftPort();

	/**
	 * @return Retourne le port d'entrée droite
	 */
	public IInPort getRightPort();

	/**
	 * @return Retourne la distribution du son
	 */
	public Distribution getDistribution();

}
