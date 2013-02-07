package group1.project.synthlab.signal;

import com.jsyn.Synthesizer;

public class Tools {
	
	/** 
	 * db = 0 renvoie la tension nominale, db = 6 renvoie 2 x la tension nominale
	 * @param dB une valeur en exprimée dB
	 * @return la tension en volt à multiplier par l'amplitude courante pour attenuer un son
	 */
	public static double dBToV(double dB){	
		double voltage = Math.pow(10.0, new Double(dB) / 20.0);
		return voltage;		
	}
	
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
