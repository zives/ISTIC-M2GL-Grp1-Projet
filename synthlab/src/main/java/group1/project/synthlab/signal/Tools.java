package group1.project.synthlab.signal;

import com.jsyn.Synthesizer;

/**
 * @author Groupe 1
 * Quelques outils
 */
public class Tools {
	
	/** 
	 * Converti une valeur en dB en V
	 * db = 0 renvoie la tension nominale, db = 6 renvoie 2 x la tension nominale
	 * @param dB une valeur en exprimée dB
	 * @return la tension en volt à multiplier par l'amplitude courante pour attenuer un son
	 */
	public static double dBToV(double dB){	
		double voltage = Math.pow(10.0, new Double(dB) / 20.0);
		return voltage;		
	}
	
	/**
	 * Ordonne au synthetiseur de faire patienter le thread courant pendant x secondes
	 * Le temps est bas� sur l'horloge du sythetiseur ce qui fait la specificite de la methode
	 * @param synth
	 * @param sec
	 */
	public static void wait(Synthesizer synth, double sec){
		try
		{
			double time = synth.getCurrentTime();
			synth.sleepUntil( time + sec );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}
	}
}
