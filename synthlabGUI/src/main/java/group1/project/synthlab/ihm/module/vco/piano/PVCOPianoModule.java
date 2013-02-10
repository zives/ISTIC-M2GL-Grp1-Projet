package group1.project.synthlab.ihm.module.vco.piano;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;
import group1.project.synthlab.unitExtensions.FilterAmplitude.IFilterAmplitudeObservable;

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

public class PVCOPianoModule extends PModule implements IPVCOPianoModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected ICVCOPianoModule controller;
	protected JLabel warnLabel;

	protected Rectangle2D neutralZone;
	protected List<Rectangle2D> buttonsN;
	protected List<Rectangle2D> buttonsSharp;
	protected Rectangle2D pressedButton;
	protected Rectangle2D overflownButton;
	protected boolean selectedButtonIsSharp;
	protected boolean interact;

	public PVCOPianoModule(final ICVCOPianoModule controller) {
		super(controller);
		this.controller = controller;
		// Taille et couleur définie dans la super classe

		// Label et onoff boutons déjà rajoutés dans la super classe
		this.onOffButton.setSelected(true);
		this.onOffButton.setEnabled(false);

		this.pressedButton = null;
		this.overflownButton = null;
		this.buttonsN = new ArrayList<Rectangle2D>();
		this.buttonsSharp = new ArrayList<Rectangle2D>();
		this.selectedButtonIsSharp = false;

		// Warn
		warnLabel = new JLabel("Amplitude out of bounds!");
		warnLabel.setForeground(Color.LIGHT_GRAY);
		warnLabel.setOpaque(false);
		warnLabel.setSize(180, 20);
		warnLabel.setForeground(Color.RED);
		warnLabel.setBorder(null);
		warnLabel.setLocation(13, 90);
		warnLabel.setFont(new Font("Arial", 0, 15));
		warnLabel.setVisible(false);

		// Ports
		PPort pportFM = (PPort) (((ICInPort) controller.getFm())
				.getPresentation());
		pportFM.setLocation(10, 240);
		PPort pportSin = (PPort) (((ICOutPort) controller.getOutSine())
				.getPresentation());
		pportSin.setLocation(70, 240);
		PPort pportTri = (PPort) (((ICOutPort) controller.getOutTriangle())
				.getPresentation());
		pportTri.setLocation(130, 240);
		PPort pportSqu = (PPort) (((ICOutPort) controller.getOutSquare())
				.getPresentation());
		pportSqu.setLocation(190, 240);

		// Sliders
		final JSlider octaveSlider = new JSlider();
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
		add(pportFM);
		add(pportSin);
		add(pportTri);
		add(pportSqu);
		add(warnLabel);
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
				controller.changeoctave(octaveSlider.getValue());

			}
		});

		repaint();

	}

	public void mouseEvent(MouseEvent me) {
		seekButton(me.getX(), me.getY());
		switch (me.getID()) {

		case MouseEvent.MOUSE_EXITED:
			canMove = true;
			break;
		case MouseEvent.MOUSE_DRAGGED:
		case MouseEvent.MOUSE_PRESSED:
			if (overflownButton != null) {
				canMove = false;
				pressedButton = overflownButton;
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				int pos = -1;
				if (!selectedButtonIsSharp) {
					pos = buttonsN.indexOf(pressedButton);
					controller.play(pos % 7, pos / 7, false);
				} else {
					pos = buttonsSharp.indexOf(pressedButton);
					controller.play(pos % 5, pos / 5, true);
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

			controller.stopPlay();
			canMove = true;
			break;
		case MouseEvent.MOUSE_MOVED:
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

	private void seekButton(int x, int y) {
		Rectangle2D previousButton = overflownButton;
		overflownButton = null;
		for (Rectangle2D button : buttonsN) {
			if (button.contains(x, y)) {
				overflownButton = button;
				selectedButtonIsSharp = false;
				break;
			}

		}
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
			for (int i = 0; i < 7 * nbO; ++i) {
				Rectangle2D button = new Rectangle2D.Double(x + i * wButton, y,
						wButton, hButton);
				buttonsN.add(button);
			}

			for (int i = 0; i < 7 * nbO; ++i) {
				if (i % 7 == 2 || i % 7 == 6)
					continue;
				Rectangle2D button = new Rectangle2D.Double(x + (i + 1)
						* wButton - wButtonSmall / 2, y, wButtonSmall,
						hButtonSmall);
				buttonsSharp.add(button);
			}
			y += hButton + 10;
		}

		neutralZone = new Rectangle2D.Double(x, y - 20 - hButton, w, 10);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
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

	public void setSlidersEnabled(boolean value) {

	}

	public void warn(IFilterAmplitudeObservable subject, boolean tooHigh) {
		warnLabel.setVisible(tooHigh);

	}

	public void hasSignal(IFilterAmplitudeObservable subject, boolean hasSignal) {

	}

}
