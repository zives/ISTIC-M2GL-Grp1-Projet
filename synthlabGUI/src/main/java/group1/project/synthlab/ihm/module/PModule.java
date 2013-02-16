package group1.project.synthlab.ihm.module;

import group1.project.synthlab.ihm.workspace.CWorkspace;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	
	/**
	 * Le module lui meme (sa presentation)
	 */
	protected PModule self;
		
	/**
	 * Position du module pendant un drag
	 */
	protected Point locationComponentOnDrag;
		
	/**
	 * Liste des observateurs
	 */
	protected transient List<IPModuleObserver> observers;
		
	/**
	 * Bouton on/off
	 */
	protected final JToggleButton onOffButton;
	
	/**
	 * Bouton de suppression du module
	 */
	protected final JToggleButton removeButton;
		
	/**
	 * Defini si le module peut se deplacer
	 */
	protected boolean canMove;
	
	/**
	 * Le controlleur du module
	 */
	protected transient ICModule controller;

	public PModule(final ICModule controller) {
		self = this;

		//Initialisation des attributs
		this.locationComponentOnDrag = new Point();
		this.observers = new ArrayList<IPModuleObserver>(); 
		this.controller = controller;
		this.setLayout(null);
		this.setOpaque(true);
		this.setBackground(new Color(65, 65, 65));
		this.setSize(300, 300);
		this.setPreferredSize(this.getSize());
		this.setMaximumSize(this.getSize());
		this.canMove = true;

		//Nom du module
		JLabel label = new JLabel(controller.getName());
		label.setForeground(Color.LIGHT_GRAY);
		label.setOpaque(false);
		label.setLocation(13, 10);
		label.setSize(100, 20);
		label.setPreferredSize(label.getSize());
		label.setFont(new Font("Arial", Font.BOLD, 14));
		add(label);

		//Bouton OnOff
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

		//Bouton supprimer
		removeButton = new JToggleButton("X");
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

		//Evenements
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
					self.setCursor(Cursor
							.getPredefinedCursor(Cursor.MOVE_CURSOR));
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
				// On d�place le module si on n'est pas en train de lier un
				// cable
				if (canMove && !CWorkspace.getInstance().isDrawingCable()) {
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
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.IPModuleObservable#register(group1.project.synthlab.ihm.module.IPModuleObserver)
	 */
	public void register(IPModuleObserver observer) {
		observers.add(observer);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.IPModuleObservable#unregister(group1.project.synthlab.ihm.module.IPModuleObserver)
	 */
	public void unregister(IPModuleObserver observer) {
		observers.remove(observer);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.IPModuleObservable#updateAllMove()
	 */
	public void updateAllMove() {
		for (IPModuleObserver obs : observers)
			if (obs != null)
				obs.moduleMove(this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.IPModule#start()
	 */
	public void start() {
		onOffButton.setSelected(true);
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.IPModule#stop()
	 */
	public void stop() {
		onOffButton.setSelected(false);
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.IPModule#updatePresentation()
	 */
	@Override
	public void updatePresentation() {
		if(controller.isStarted()){
			this.onOffButton.setSelected(true);
			this.onOffButton.setText("Off");
			controller.start();
		}
		else{
			this.onOffButton.setSelected(false);
			this.onOffButton.setText("On");
			controller.stop();
		}
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.IPModule#updateLocation(int, int)
	 */
	@Override
	public void updateLocation(int x, int y) {
		setLocation(x, y);
		
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D gi = (Graphics2D) g;
		gi.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gi.setStroke(new BasicStroke(6f));
		g.setColor(getParent().getBackground());
		RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0,
				getWidth(), getHeight(), 0, 0);
		gi.draw(roundedRectangle);

		g.setColor(new Color(150, 150, 150));
		roundedRectangle = new RoundRectangle2D.Float(4, 4, getWidth() - 8,
				getHeight() - 8, 3, 3);
		gi.draw(roundedRectangle);

		gi.setStroke(new BasicStroke(2f));
		g.setColor(new Color(30, 30, 30));
		roundedRectangle = new RoundRectangle2D.Float(5, 5, getWidth() - 10,
				getHeight() - 10, 5, 5);
		gi.draw(roundedRectangle);

	}
}
