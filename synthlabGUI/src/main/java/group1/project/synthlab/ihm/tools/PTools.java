package group1.project.synthlab.ihm.tools;


public class PTools {

	public static String freqToString(double freq) {
		String val = String.valueOf(freq);
		if (val.indexOf(".") < 0)
			val += ".00";
		else {
			int pos = val.indexOf(".") + 1;
			String tronq = val.substring(pos);
			if (tronq.length() == 1)
				tronq += "0";
			val = val.substring(0, pos) + tronq.substring(0, 2);
		}
		if (val.length() > 7)
			val = val.substring(0, 7);
		return val;
	}
}
