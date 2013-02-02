package group1.project.synthlab.module.vco;

import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;

public interface IVCOModule extends IModule {
	
	/**
	 * @return la valeur actuelle de la frequence de base
	 */
	public double getf0();
	
	/**
	 * @return l'oscillateur JSyn qui genere le signal sinusoidale
	 */
	public SineOscillator getSineOsc();

	/**
	 * @return l'oscillateur JSyn qui genere le signal carre
	 */
	public SquareOscillator getSquareOsc();

	/**
	 * @return l'oscillateur JSyn qui genere le signal triangulaire
	 */
	public TriangleOscillator getTriangleOsc();
	
	/**
	 * @return l'etat de l'entree fm (signal modulant connecte ou non)
	 */
	public boolean getFmConnected();
	
	/**
	 * @return le port d'entree Fm
	 */
	public IInPort getFm();

	/**
	 * @return le port de sortie pour le signal sinusoidale
	 */
	public IOutPort getOutSine();

	/**
	 * @return le port de sortie pour le signal carre
	 */
	public IOutPort getOutSquare();

	/**
	 * @return le port de sortie pour le signal triangulaire
	 */
	public IOutPort getOutTriangle();
	
	/**
	 * Reglage grossier (dixieme de la plage de frequences)
	 * @return la valeur du reglage grossier de la frequence f0 (entier de 0 a 9)
	 */
	public int getCoarseAdjustment();

	/**
	 * @param coarseadjustment : la nouvelle valeur du reglage grossier de la frequence f0 (entier de 0 a 9)
	 */
	public void setCoarseAdjustment(int coarseadjustment);

	/**
	 * Reglage fin
	 * @return la valeur du reglage fin de la frequence f0 (double entre 0 et 1)
	 */
	public double getFineAdjustment();

	/**
	 * @param fineadjustment : la nouvelle valeur du reglage fin de la frequence f0 (double entre 0 et 1)
	 */
	public void setFineAdjustment(double fineadjustment);
	
	/**
	 * Met a jour la frequence f0 des 3 signaux en fonction des valeurs de reglage grossier et fin
	 */
	public void changeFrequency();
	
	/**
	 * Si un cable est connecte dans le port d'entree fm, le VCO doit activer la modulation de frequence
	 * @param port : le port du VCO auquel on vient de connecter un cable
	 */
	public void cableConnected(IPort port);
	
	/**
	 * Si un cable est deconnecte du port d'entree fm, le VCO doit desactiver la modulation de frequence et remettre les 3 signaux a la frequence f0
	 * @param port : le port du VCO duquel on vient de deconnecter un cable
	 */
	public void cableDisconnected(IPort port);

}
