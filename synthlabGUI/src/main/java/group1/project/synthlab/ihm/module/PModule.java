package group1.project.synthlab.ihm.module;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class PModule extends JPanel implements IPModule {

	private static final long serialVersionUID = -3047834031036120214L;
	private PModule self;
	private Point locationComponentOnDrag = new Point();
	protected List<PModuleObserver> observers = new ArrayList<PModuleObserver>();

	public PModule() {
		self = this;

		this.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent ev) {
				locationComponentOnDrag = ev.getPoint();
			}

			public void mouseExited(MouseEvent arg0) {
				self.setCursor(Cursor
						.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			}

			public void mouseEntered(MouseEvent arg0) {
				self.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));

			}

			public void mouseClicked(MouseEvent arg0) {
				((JLayeredPane) getParent()).moveToFront(self);

			}
		});

		this.addMouseMotionListener(new MouseMotionListener() {

			public void mouseMoved(MouseEvent ev) {

			}

			public void mouseDragged(MouseEvent ev) {
				Point loc = new Point(
						(int) (self.getX() + ev.getX() - locationComponentOnDrag
								.getX()),
						(int) (self.getY() + ev.getY() - locationComponentOnDrag
								.getY()));
				if (loc.x < 0 || loc.y < 0) {
					locationComponentOnDrag = ev.getPoint();
					return;
				}

				self.setLocation(loc);
				updateAll();
			}
		});
	}

	public void register(PModuleObserver observer) {
		observers.add(observer);
	}

	public void unregister(PModuleObserver observer) {
		observers.remove(observer);
	}

	public void updateAll() {
		for (PModuleObserver obs : observers)
			obs.update(this);
	}
}
