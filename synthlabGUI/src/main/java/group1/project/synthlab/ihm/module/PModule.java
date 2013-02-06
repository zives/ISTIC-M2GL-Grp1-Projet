package group1.project.synthlab.ihm.module;

import group1.project.synthlab.ihm.workspace.CWorkspace;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;
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
	protected final JToggleButton onOffButton;

	public PModule(final ICModule controller) {
		self = this;

		this.setLayout(null);
		this.setOpaque(true);
		this.setBackground(new Color(70, 70, 70));
		this.setSize(300, 300);
		this.setPreferredSize(this.getSize());
		this.setMaximumSize(this.getSize());

		// Label
		JLabel label = new JLabel(controller.getName());
		label.setForeground(Color.LIGHT_GRAY);
		label.setOpaque(false);
		label.setLocation(13, 10);
		label.setSize(100, 20);
		label.setPreferredSize(label.getSize());
		label.setFont(new Font("Arial", Font.BOLD, 14));
		add(label);

		// OnOff
		onOffButton = new JToggleButton("On");
		onOffButton.setOpaque(false);
		onOffButton.setForeground(new Color(80, 80, 80));
		onOffButton.setSelected(false);
		onOffButton.setFont(new Font("Arial", 0, 10));
		onOffButton.setSize(20, 20);
		onOffButton.setBorder(null);
		onOffButton.setPreferredSize(onOffButton.getSize());
		onOffButton.setLocation(getWidth() - onOffButton.getWidth() - 13, 13);
		onOffButton.setFocusPainted(false);
		add(onOffButton);

		// Remove
		final JToggleButton removeButton = new JToggleButton("X");
		removeButton.setOpaque(false);
		removeButton.setForeground(new Color(80, 80, 80));
		removeButton.setSelected(false);
		removeButton.setFont(new Font("Arial", 0, 10));
		removeButton.setSize(20, 20);
		removeButton.setBorder(null);
		removeButton.setPreferredSize(removeButton.getSize());
		removeButton.setLocation(getWidth() - removeButton.getWidth()
				- onOffButton.getWidth() - 15, 13);
		removeButton.setFocusPainted(false);
		add(removeButton);

		// Events
		onOffButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				if (onOffButton.isSelected()) {
					controller.start();
					onOffButton.setText("Off");

				} else {
					controller.stop();
					onOffButton.setText("On");

				}

			}
		});

		removeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				CWorkspace.getInstance().removeModule(controller);
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
				if (!CWorkspace.getInstance().isDrawingCable()) 
					self.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));

			}

			public void mouseClicked(MouseEvent arg0) {
				// On place le module en avant plan
				((JLayeredPane) getParent()).moveToFront(self);
				updateAllMove();

			}
		});

		this.addMouseMotionListener(new MouseMotionListener() {

			public void mouseMoved(MouseEvent ev) {

			}

			public void mouseDragged(MouseEvent ev) {
				// On déplace le module si on n'est pas en train de leir un
				// cable
				if (!CWorkspace.getInstance().isDrawingCable()) {
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
					updateAllMove();
				}
			}
		});
	}

	public void register(IPModuleObserver observer) {
		observers.add(observer);
	}

	public void unregister(IPModuleObserver observer) {
		observers.remove(observer);
	}

	public void updateAllMove() {
		for (IPModuleObserver obs : observers)
			if (obs != null)
				obs.moduleMove(this);
	}

	public void unregisterAllCables() {
		observers.clear();

	}

	
	@Override
	public void paint(Graphics g) {
		  super.paint(g);
		Graphics2D gi = (Graphics2D) g;
		gi.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gi.setStroke(new BasicStroke(6f));
		g.setColor(getParent().getBackground()	);
		 RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth() , getHeight() , 0, 0);
	        gi.draw(roundedRectangle); 
	      
		g.setColor(new Color(50, 50, 50));
		  roundedRectangle = new RoundRectangle2D.Float(4, 4, getWidth() - 8, getHeight() - 8, 5, 5);
	        gi.draw(roundedRectangle); 
	        
	    	gi.setStroke(new BasicStroke(2f));
	        g.setColor(new Color(30, 30, 30));
			  roundedRectangle = new RoundRectangle2D.Float(5, 5, getWidth() - 10, getHeight() - 10, 5, 5);
		        gi.draw(roundedRectangle); 
	      
		
	}
}
