package group1.project.synthlab.ihm.module;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public abstract class PModule extends JPanel implements IPModule {

	private static final long serialVersionUID = -3047834031036120214L;
	private PModule self;
	private Point locationComponentOnDrag = new Point();
	protected List<IPModuleObserver> observers = new ArrayList<IPModuleObserver>();

	public PModule(final ICModule controller) {
		self = this;
		
		this.setLayout(null);
		this.setBackground(new Color(70,70,70));
		this.setSize(300, 300);
		this.setPreferredSize(this.getSize());
		this.setMaximumSize(this.getSize());

		// Label
		JLabel label = new JLabel(controller.getName());
		label.setForeground(Color.LIGHT_GRAY);
		label.setOpaque(false);
		label.setLocation(13, 10);
		label.setSize(100,20);
		label.setPreferredSize(label.getSize());
		label.setFont(new Font("Arial", Font.BOLD, 14));		
		add(label);

		// OnOff
		final JToggleButton onOffButton = new JToggleButton("Off");
		onOffButton.setOpaque(false);
		onOffButton.setForeground(new Color(70, 70, 70));
		onOffButton.setSelected(false);
		onOffButton.setFont(new Font("Arial", 0, 10));
		onOffButton.setSize(20, 20);
		onOffButton.setBorder(null);
		onOffButton.setPreferredSize(onOffButton.getSize());
		onOffButton.setLocation(getWidth() - onOffButton.getWidth() - 13, 13);	
		onOffButton.setFocusPainted(false);
		add(onOffButton);

		// Events
		onOffButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				if (onOffButton.isSelected()) {
					controller.start();
					onOffButton.setText("On");
				} else {
					controller.stop();
					onOffButton.setText("Off");

				}

			}
		});

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

	public void register(IPModuleObserver observer) {
		observers.add(observer);
	}

	public void unregister(IPModuleObserver observer) {
		observers.remove(observer);
	}

	public void updateAll() {
		for (IPModuleObserver obs : observers)
			if (obs != null)
				obs.update(this);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D gi = (Graphics2D) g;
		gi.setStroke(new BasicStroke(8f));
		g.setColor(new Color(50, 50, 50));
		g.drawRect(5, 5, getWidth() - 10, getHeight() - 10);

	}
}
