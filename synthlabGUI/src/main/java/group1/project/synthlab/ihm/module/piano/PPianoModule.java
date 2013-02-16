package group1.project.synthlab.ihm.module.piano;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Groupe 1
 * La presentation du piano
 */
public class PPianoModule extends PModule implements IPPianoModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected transient ICPianoModule controller;

	/**
	 * Zone neutre où aucun evenement ne se produira
	 */
	protected Rectangle2D neutralZone;
		
	/**
	 * Liste des touches normales
	 */
	protected List<Rectangle2D> buttonsN;
		
	/**
	 * Liste des touches dieses
	 */
	protected List<Rectangle2D> buttonsSharp;
		
	/**
	 * Touche actuellement enfoncee
	 */
	protected Rectangle2D pressedButton;
	
	/**
	 * Touche actuellement survolee
	 */
	protected Rectangle2D overflownButton;
	
	
	/**
	 * La touche enfoncee est une note diese
	 */
	protected boolean selectedButtonIsSharp;
	
	/**
	 * Le slider de debut d'octave
	 */
	protected final JSlider octaveSlider ;

	public PPianoModule(final ICPianoModule controller) {
		super(controller);
		this.controller = controller;
		// Taille et couleur définie dans la super classe

		// Label et onoff boutons déjà rajoutés dans la super classe

		this.pressedButton = null;
		this.overflownButton = null;
		this.buttonsN = new ArrayList<Rectangle2D>();
		this.buttonsSharp = new ArrayList<Rectangle2D>();
		this.selectedButtonIsSharp = false;

		// Ports
		PPort pportOut = (PPort) (((ICOutPort) controller.getOut())
				.getPresentation());
		pportOut.setLocation(getWidth() - pportOut.getWidth() - 10, 245);
		PPort pportSinalOn = (PPort) (((ICOutPort) controller.getOutGate())
				.getPresentation());
		pportSinalOn.setLocation(pportOut.getX() - pportSinalOn.getWidth() - 10, 245);

		// Sliders
		octaveSlider = new JSlider();
		octaveSlider.setMaximum(3);
		octaveSlider.setMinimum(1);
		octaveSlider.setOrientation(JSlider.HORIZONTAL);
		octaveSlider.setValue(2);
		octaveSlider.setSize(100, 30);
		octaveSlider.setFont(new Font("Arial", 0, 8));
		octaveSlider.setForeground(Color.LIGHT_GRAY);
		octaveSlider.setPreferredSize(octaveSlider.getSize());
		octaveSlider.setOpaque(false);
		octaveSlider.setFocusable(false);
		octaveSlider.setBorder(null);
		octaveSlider.setLocation(getWidth() / 2 - octaveSlider.getWidth() / 2,
				30);
		octaveSlider.setPaintTicks(false);

		JLabel octaveMinLabel = new JLabel("0-3");
		octaveMinLabel.setForeground(Color.LIGHT_GRAY);
		octaveMinLabel.setOpaque(false);
		octaveMinLabel.setSize(30, 20);
		octaveMinLabel.setBorder(null);
		octaveMinLabel.setHorizontalTextPosition(JLabel.RIGHT);
		octaveMinLabel.setPreferredSize(octaveMinLabel.getSize());
		octaveMinLabel.setLocation(octaveSlider.getX() - 17, 55);
		octaveMinLabel.setFont(new Font("Arial", Font.ITALIC, 10));

		JLabel octaveMiddleLabel = new JLabel("2-5");
		octaveMiddleLabel.setForeground(Color.LIGHT_GRAY);
		octaveMiddleLabel.setOpaque(false);
		octaveMiddleLabel.setSize(16, 20);
		octaveMiddleLabel.setBorder(null);
		octaveMinLabel.setHorizontalTextPosition(JLabel.CENTER);
		octaveMiddleLabel.setPreferredSize(octaveMiddleLabel.getSize());
		octaveMiddleLabel.setLocation(
				octaveSlider.getX() + octaveSlider.getWidth() / 2
						- octaveMiddleLabel.getWidth() / 2, 55);
		octaveMiddleLabel.setFont(new Font("Arial", Font.ITALIC, 10));

		JLabel octaveMaxLabel = new JLabel("4-7");
		octaveMaxLabel.setForeground(Color.LIGHT_GRAY);
		octaveMaxLabel.setOpaque(false);
		octaveMaxLabel.setSize(60, 20);
		octaveMaxLabel.setBorder(null);
		octaveMaxLabel.setPreferredSize(octaveMaxLabel.getSize());
		octaveMaxLabel.setLocation(
				octaveSlider.getX() + octaveSlider.getWidth(), 55);
		octaveMaxLabel.setFont(new Font("Arial", Font.ITALIC, 10));

		// Ajouts des composants
		add(pportOut);
		add(pportSinalOn);
		add(octaveSlider);
		add(octaveMinLabel);
		add(octaveMiddleLabel);
		add(octaveMaxLabel);

		initButtons();

		// Events
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent ev) {
				mouseEvent(ev);
			}

			@Override
			public void mousePressed(MouseEvent ev) {
				mouseEvent(ev);
			}

			@Override
			public void mouseExited(MouseEvent ev) {
			}

			@Override
			public void mouseEntered(MouseEvent ev) {
			}

			@Override
			public void mouseClicked(MouseEvent ev) {
			}
		});
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent ev) {
				mouseEvent(ev);
			}

			@Override
			public void mouseDragged(MouseEvent ev) {
				mouseEvent(ev);
			}
		});
		octaveSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent ev) {
				controller.changeOctaveStart(octaveSlider.getValue());

			}
		});

		repaint();

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.piano.IPPianoModule#mouseEvent(java.awt.event.MouseEvent)
	 */
	public void mouseEvent(MouseEvent me) {
		seekButton(me.getX(), me.getY());
		switch (me.getID()) {

		case MouseEvent.MOUSE_EXITED:
			canMove = true; //On peut bouger la presentation si on appuit pas sur une touche
			break;
		case MouseEvent.MOUSE_DRAGGED:
		case MouseEvent.MOUSE_PRESSED:
			//Transmet au controleur la touche enfoncee
			if (overflownButton != null) {
				canMove = false;
				pressedButton = overflownButton;
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				int pos = -1;
				if (!selectedButtonIsSharp) {
					pos = buttonsN.indexOf(pressedButton);
					controller.playFromPresentation(pos % 7, pos / 7, false);
				} else {
					pos = buttonsSharp.indexOf(pressedButton);
					controller.playFromPresentation(pos % 5, pos / 5, true);
				}
				if (pos == -1)
					return;
				repaint();
			} else {
				setCursor(new Cursor(Cursor.MOVE_CURSOR));
				// canMove = true;
			}
			if (neutralZone.contains(me.getX(), me.getY()))
				canMove = false;
			break;
		case MouseEvent.MOUSE_RELEASED:
			//La touche est relachee donc on informe le controleur d'arreter de jouer la note
			controller.stopPlay();
			canMove = true;
			break;
		case MouseEvent.MOUSE_MOVED:
			//Si on survol une touche, on met a jour la presentation pour un retour graphique a l'utilisateur
			if (overflownButton != null) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				repaint();
			} else
				setCursor(new Cursor(Cursor.MOVE_CURSOR));
			if (neutralZone.contains(me.getX(), me.getY()))
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			break;

		default:
		}
	}

	/**
	 * Trouve la touche en fonction d'une coordonne sur la presentation du module
	 * @param x
	 * @param y
	 */
	private void seekButton(int x, int y) {
		Rectangle2D previousButton = overflownButton;
		overflownButton = null;
		
		//Recherche a travers les boutons normals
		for (Rectangle2D button : buttonsN) {
			if (button.contains(x, y)) {
				overflownButton = button;
				selectedButtonIsSharp = false;
				break;
			}
		}
		
		//Recherche a travers les boutons dieses
		for (Rectangle2D button : buttonsSharp) {
			if (button.contains(x, y)) {
				overflownButton = button;
				selectedButtonIsSharp = true;
				break;
			}
		}
		if (previousButton != null && overflownButton == null)
			repaint();
	}

	/**
	 * Initialise les rectangles des touches et les ajoute en memoire
	 */
	private void initButtons() {

		// Hauteur des touches
		double hButton = 70;
		double hButtonSmall = 45;

		// Nombre d'octaves sur une séquence
		int nbO = 2;

		// Première séquence
		double w = getWidth() - 22;

		double x = (getWidth() - w) / 2;
		double y = 80;

		// Calculs
		double wButton = w / (7 * nbO);
		double wButtonSmall = (int) (wButton / 2);

		for (int n = 0; n < 2; ++n) {
			//Touches normales
			for (int i = 0; i < 7 * nbO; ++i) {
				Rectangle2D button = new Rectangle2D.Double(x + i * wButton, y,
						wButton, hButton);
				buttonsN.add(button);
			}

			//Touches dieses
			for (int i = 0; i < 7 * nbO; ++i) {
				if (i % 7 == 2 || i % 7 == 6)
					continue;
				Rectangle2D button = new Rectangle2D.Double(x + (i + 1)
						* wButton - wButtonSmall / 2, y, wButtonSmall,
						hButtonSmall);
				buttonsSharp.add(button);
			}
			
			//On passe a la deuxieme rangee
			y += hButton + 10;
		}

		neutralZone = new Rectangle2D.Double(x, y - 20 - hButton, w, 10);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Création graphique des touches standard

		for (Rectangle2D button : buttonsN) {
			g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND,
					BasicStroke.JOIN_ROUND));
			if (button != overflownButton)
				g.setColor(new Color(190, 190, 190));
			else
				g.setColor(new Color(255, 255, 255));
			g2d.fill(button);

			g.setColor(new Color(40, 40, 40));

			RoundRectangle2D buttonR = new RoundRectangle2D.Double(
					button.getX(), button.getY(), button.getWidth(),
					button.getHeight(), 3, 3);
			g2d.draw(buttonR);
		}
		
		//Creation des touches dieses
		g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		for (Rectangle2D button : buttonsSharp) {
			if (button != overflownButton)
				g.setColor(new Color(40, 40, 40));
			else
				g.setColor(new Color(70, 70, 70));
			g2d.fill(button);
			RoundRectangle2D buttonR = new RoundRectangle2D.Double(
					button.getX(), button.getY(), button.getWidth(),
					button.getHeight(), 4, 4);
			g2d.draw(buttonR);

		}

	}

	@Override
	public void updatePresentation() {
		super.updatePresentation();
		int position = 1;
		if(controller.getOctaveStart() == 4){
			position = 3;
		}
		else if(controller.getOctaveStart() == 2){
			position = 2;
		}
		this.octaveSlider.setValue(position);
	}
}
