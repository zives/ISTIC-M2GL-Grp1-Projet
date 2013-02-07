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
public interface IEGModule extends IModule {

	/**
	 * @return le temps de montee en milliseconde
	 */
	public int getAttack();
	

	/**
	 * @param attack
	 */
	public void setAttack(int attack);
	
	

	/**
	 * @return le temps d'extinction en milliseconde
	 */
	public int getDecay();

	public void setDecay(int decay);

	/**
	 * @return le temps de relachement en milliseconde
	 */
	public int getRelease();

	public void setRelease(int release);

	/**
	 * @return le temps entre le temps de montee et le temps d'extinction en milliseconde
	 */
	public int getHold();

	public void setHold(int hold);

	public double getSustain();

	public void setSustain(double sustain);

	public IInPort getGate();

	public IOutPort getOut();


}
