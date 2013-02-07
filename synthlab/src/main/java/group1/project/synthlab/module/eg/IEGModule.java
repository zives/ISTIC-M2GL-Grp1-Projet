package group1.project.synthlab.module.eg;

import com.jsyn.unitgen.SineOscillator;


import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Tools;

/**
 * Interface du module EG
 * @author Groupe 1
 *
 */
/**
 * @author 13010902
 *
 */
/**
 * @author 13010902
 *
 */
/**
 * @author 13010902
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
	 * @param definit le temps d'extinction en milliseconde
	 */
	public void setRelease(int release);
	

	/**
	 * @return le temps entre le temps de montee et le temps d'extinction en milliseconde
	 */
	public int getHold();
	
	
	/**
	 * @param definit le temps entre le temps de montee et le temps d'extinction en milliseconde
	 */
	public void setHold(int hold);
	
	
	/**
	 * @return le niveau de maintien de l amplitude
	 */
	public double getSustain();
	

	/**
	 * @param definit le niveau de maintien de l amplitude
	 */
	public void setSustain(double sustain);
	

	/**
	 * @return l entree de declenchement
	 */
	public IInPort getGate();
	

	/**
	 * @return la sortie
	 */
	public IOutPort getOut();


}
