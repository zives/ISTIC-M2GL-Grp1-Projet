package group1.project.synthlab.ihm.tools;

import java.awt.Component;
import java.awt.Container;
import java.awt.Panel;
import java.awt.Point;

public class PTools {
	public static Point getPositionOfComponentInPanel(Panel panel, Component component) {
		int x = component.getX();
		int y = component.getY();
		Container parent = component.getParent();
		while (parent != null || parent != panel) {
			x += parent.getX();
			y += parent.getY();
			parent = parent.getParent();
		}
		return new Point(x, y);
		
	}
}
