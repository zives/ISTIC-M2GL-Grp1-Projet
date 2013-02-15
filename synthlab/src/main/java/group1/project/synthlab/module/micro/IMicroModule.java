package group1.project.synthlab.module.micro;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.out.IOutPort;

import com.jsyn.unitgen.LineIn;

/**
 * Interface du module du microphone
 * @author Groupe 1
 *
 */
public interface IMicroModule extends IModule {

	/**
	 * Attenuation du son Formule pour attenuer le son 10 ^ (valeurEnDB / 20) *
	 * tensionNominale http://fr.wikipedia.org/wiki/Niveau_(audio)
	 * 
	 * @param db (valeurs : -inf a +12 dB)
	 */
	public void setAttenuation(double db);

	/**
	 * @return l'attenuation en dB
	 */
	public double getAttenuation();

	/**
	 * @return l'objet JSyn correspondant a l'entree brute (son non filtre).
	 */
	public LineIn getLineIn();
	
	/**
	 * @return le port de sortie
	 */
	public IOutPort getOutPort();

	

}
