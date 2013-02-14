package group1.project.synthlab.signal;

import com.jsyn.ports.UnitInputPort;

/**
 * @author Groupe 1 Specification des signaux de l'application
 */
public class Signal {

	/** Frequence minimale */
	public static final double FMIN = 0;
	/** Frequence maximale */
	public static final double FMAX = 22000;
	/** Frequence minimale audible */
	public static final double FMAXAUDIBLE = 6000;

	/** Amplitude maximale */
	public static final double AMAX = 5;
	/** Amplitude neutre */
	public static final double AMIN = 0;
	/** Amplitude maximale pour un signal modulant */
	public static final double AMAXMODULATION = 10;

	public static void turnOff(UnitInputPort portJSyn) {
		portJSyn.set(AMIN);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
	}

}
