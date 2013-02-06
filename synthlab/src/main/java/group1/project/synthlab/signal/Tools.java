package group1.project.synthlab.signal;

public class Tools {
	
	/** 
	 * @param attenuationDB une valeur en exprimée dB
	 * @return une valeur en volt à multiplier par l'amplitude courante pour attenuer un son
	 */
	public static double attenuationDBToV(double attenuationDB){	
		double voltage = Math.pow(10.0, new Double(attenuationDB) / 20.0);
		return voltage - 1;		
	}
}
