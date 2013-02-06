package group1.project.synthlab.signal;

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
}
