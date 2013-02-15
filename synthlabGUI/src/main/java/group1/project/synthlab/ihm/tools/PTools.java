package group1.project.synthlab.ihm.tools;

import java.awt.Point;
import java.awt.geom.Line2D;

/**
 * @author Groupe 1
 * Des outils pour les presentations
 */
public class PTools {

	/**
	 * Convertie une frequence en valeur affichable
	 * @param freq la frequence
	 * @return un string avec le meme nombre de decimale
	 */
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

	/**
	 * Calcul l'intersection entre deux lignes
	 * @param line1
	 * @param line2
	 * @return le point d'intersection
	 */
	public static Point intersection(Line2D line1, Line2D line2) {
		double x1, x2, x3, x4;
		double y1, y2, y3, y4;
		x1 = line1.getX1();
		x2 = line1.getX2();
		x3 = line2.getX1();
		x4 = line2.getX2();
		y1 = line1.getY1();
		y2 = line1.getY2();
		y3 = line2.getY1();
		y4 = line2.getY2();
		
		double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (d == 0)
			return null;

		double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2)
				* (x3 * y4 - y3 * x4))
				/ d;
		double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2)
				* (x3 * y4 - y3 * x4))
				/ d;

		return new Point((int)xi, (int)yi);
	}
}
