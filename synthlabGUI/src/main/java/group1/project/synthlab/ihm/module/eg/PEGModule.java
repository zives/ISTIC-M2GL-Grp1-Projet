package group1.project.synthlab.ihm.module.eg;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PEGModule extends PModule implements IPEGModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected transient ICEGModule controller;
	protected JSlider attackSlider;
	protected JSlider decaySlider;
	protected JSlider releaseSlider;
	protected JSlider sustainSlider;
	protected JSlider holdSlider;
	protected double attack;
	protected double decay;
	protected double release;
	protected double hold;
	protected double sustain;
	protected double decibel;
	
	
	
	protected JLabel warnLabel;

	protected Component pportGate;
	protected Component pportOut;


	public PEGModule(final ICEGModule controller) {
		super(controller);
		this.controller = controller;
		this.setLayout(null);
		final Font font = new Font("Arial",Font.BOLD,20);
		this.attack = controller.getAttack();
		this.release = controller.getRelease();
		this.decay = controller.getDecay();
		this.hold = controller.getHold();
		this.sustain = controller.getSustain();
		this.decibel = controller.getDecibel() ;
		
		// Taille et couleur définie dans la super classe

		// Label et onoff boutons déjà rajoutés dans la super classe

		this.setSize(getWidth(), getHeight() - 60);
		this.setPreferredSize(this.getSize());

		//Ports
		PPort pportGate = (PPort) (((ICInPort) controller.getGate()).getPresentation());
		pportGate.setLocation(10, getHeight() - pportGate.getHeight() - 5);
		PPort pportOut = (PPort) (((ICOutPort) controller.getOut()).getPresentation());
		pportOut.setLocation(240, getHeight() - pportGate.getHeight() - 5);
		
		
		
		// Sliders	
		sustainSlider = new JSlider();
		sustainSlider.setMaximum(0);
		sustainSlider.setMinimum(-60);
		sustainSlider.setOrientation(JSlider.VERTICAL);
		sustainSlider.setSize(15, 180);
		sustainSlider.setFont(new Font("Arial", 0, 8));
		sustainSlider.setForeground(Color.LIGHT_GRAY);
		sustainSlider.setOpaque(false);
		sustainSlider.setFocusable(false);
		final JLabel sustainLabel = new JLabel("sustain adj");
		sustainSlider.setForeground(Color.LIGHT_GRAY);
		sustainSlider.setOpaque(false);
		sustainSlider.setSize(30, 100);
		sustainSlider.setBorder(null);
		sustainSlider.setLocation(25,60);
		sustainSlider.setFont(new Font("Arial", Font.ITALIC, 10));
		sustainSlider.setPaintTicks(true);
		sustainSlider.setMajorTickSpacing(10);
		sustainSlider.setMinorTickSpacing(10);
		sustainSlider.setValue((int) (decibel * 10));
		

		JLabel sustainMaxValueLabel = new JLabel("0");
		sustainMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		sustainMaxValueLabel.setOpaque(false);
		sustainMaxValueLabel.setSize(30, 20);
		sustainMaxValueLabel.setBorder(null);
		sustainMaxValueLabel.setPreferredSize(sustainMaxValueLabel.getSize());
		sustainMaxValueLabel.setLocation(sustainSlider.getX() + 33, sustainSlider.getY() - 5);
		sustainMaxValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel sustain0ValueLabel = new JLabel(String.valueOf(sustainSlider.getMinimum() / 10));
		sustain0ValueLabel.setForeground(Color.LIGHT_GRAY);
		sustain0ValueLabel.setOpaque(false);
		sustain0ValueLabel.setSize(30, 20);
		sustain0ValueLabel.setBorder(null);
		sustain0ValueLabel.setPreferredSize(sustain0ValueLabel.getSize());
		sustain0ValueLabel.setLocation(sustainSlider.getX() + 33, sustainSlider.getY() + sustainSlider.getHeight() - 18);
		sustain0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		attackSlider = new JSlider();
		attackSlider.setMaximum(20);
		attackSlider.setMinimum(0);
		attackSlider.setOrientation(JSlider.HORIZONTAL);
		attackSlider.setSize(80, 25);
		attackSlider.setFont(new Font("Arial", 0, 8));
		attackSlider.setForeground(Color.LIGHT_GRAY);
		attackSlider.setPreferredSize(attackSlider.getSize());
		attackSlider.setOpaque(false);
		attackSlider.setFocusable(false);
		attackSlider.setLocation(sustainSlider.getX() + 65, 65);
		JLabel attackLabel = new JLabel("attack adj.");
		attackLabel.setForeground(Color.LIGHT_GRAY);
		attackLabel.setOpaque(false);
		attackLabel.setSize(100, 20);
		attackLabel.setBorder(null);
		attackLabel.setPreferredSize(attackSlider.getSize());
		attackLabel.setLocation(attackSlider.getX(), 45);
		attackLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		attackSlider.setPaintTicks(true);
		attackSlider.setMajorTickSpacing(5);
		attackSlider.setMinorTickSpacing(5);
		attackSlider.setValue((int) (attack * 10));
		
		JLabel attackMaxValueLabel = new JLabel(String.valueOf(attackSlider.getMaximum() /10));
		attackMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		attackMaxValueLabel.setOpaque(false);
		attackMaxValueLabel.setSize(20, 20);
		attackMaxValueLabel.setBorder(null);
		attackMaxValueLabel.setPreferredSize(attackMaxValueLabel.getSize());
		attackMaxValueLabel.setLocation(attackSlider.getX() + 70, 90);
		attackMaxValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel attack0ValueLabel = new JLabel("0");
		attack0ValueLabel.setForeground(Color.LIGHT_GRAY);
		attack0ValueLabel.setOpaque(false);
		attack0ValueLabel.setSize(30, 20);
		attack0ValueLabel.setBorder(null);
		attack0ValueLabel.setPreferredSize(attack0ValueLabel.getSize());
		attack0ValueLabel.setLocation(attackSlider.getX() + 5, 90);
		attack0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		decaySlider = new JSlider();
		decaySlider.setMaximum(50);
		decaySlider.setMinimum(0);
		decaySlider.setOrientation(JSlider.HORIZONTAL);
		decaySlider.setSize(80, 25);
		decaySlider.setFont(new Font("Arial", 0, 8));
		decaySlider.setForeground(Color.LIGHT_GRAY);
		decaySlider.setPreferredSize(attackSlider.getSize());
		decaySlider.setOpaque(false);
		decaySlider.setFocusable(false);
		decaySlider.setLocation(attackSlider.getX(), 130);
		final JLabel decayLabel = new JLabel("decay adj.");
		decayLabel.setForeground(Color.LIGHT_GRAY);
		decayLabel.setOpaque(false);
		decayLabel.setSize(100, 20);
		decayLabel.setBorder(null);
		decayLabel.setPreferredSize(decaySlider.getSize());
		decayLabel.setLocation(decaySlider.getX(), 110);
		decayLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		decaySlider.setPaintTicks(true);
		decaySlider.setMajorTickSpacing(5);
		decaySlider.setMinorTickSpacing(5);
		decaySlider.setValue((int) (decay * 10));
		
		JLabel decayMaxValueLabel = new JLabel(String.valueOf(decaySlider.getMaximum() /10));
		decayMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		decayMaxValueLabel.setOpaque(false);
		decayMaxValueLabel.setSize(30, 20);
		decayMaxValueLabel.setBorder(null);
		decayMaxValueLabel.setPreferredSize(decayMaxValueLabel.getSize());
		decayMaxValueLabel.setLocation(decaySlider.getX() + 70 , 155);
		decayMaxValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel decay0ValueLabel = new JLabel("0");
		decay0ValueLabel.setForeground(Color.LIGHT_GRAY);
		decay0ValueLabel.setOpaque(false);
		decay0ValueLabel.setSize(30, 20);
		decay0ValueLabel.setBorder(null);
		decay0ValueLabel.setPreferredSize(decay0ValueLabel.getSize());
		decay0ValueLabel.setLocation(decaySlider.getX() + 5, 155);
		decay0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		releaseSlider = new JSlider();
		releaseSlider.setMaximum(80);
		releaseSlider.setMinimum(0);
		releaseSlider.setOrientation(JSlider.HORIZONTAL);
		releaseSlider.setSize(80, 25);
		releaseSlider.setFont(new Font("Arial", 0, 8));
		releaseSlider.setForeground(Color.LIGHT_GRAY);
		releaseSlider.setPreferredSize(attackSlider.getSize());
		releaseSlider.setOpaque(false);
		releaseSlider.setFocusable(false);
		releaseSlider.setLocation(attackSlider.getX() + 100, 65);
		final JLabel releaseLabel = new JLabel("release adj.");
		releaseLabel.setForeground(Color.LIGHT_GRAY);
		releaseLabel.setOpaque(false);
		releaseLabel.setSize(100, 20);
		releaseLabel.setBorder(null);
		releaseLabel.setPreferredSize(attackLabel.getSize());
		releaseLabel.setLocation(releaseSlider.getX(), 45);
		releaseLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		releaseSlider.setPaintTicks(true);
		releaseSlider.setMajorTickSpacing(5);
		releaseSlider.setMinorTickSpacing(5);
		releaseSlider.setValue((int) (release * 10));
		
		JLabel releaseMaxValueLabel = new JLabel(String.valueOf(releaseSlider.getMaximum() /10));
		releaseMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		releaseMaxValueLabel.setOpaque(false);
		releaseMaxValueLabel.setSize(30, 20);
		releaseMaxValueLabel.setBorder(null);
		releaseMaxValueLabel.setPreferredSize(releaseMaxValueLabel.getSize());
		releaseMaxValueLabel.setLocation(releaseSlider.getX()+ 70, 90);
		releaseMaxValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel release0ValueLabel = new JLabel("0");
		release0ValueLabel.setForeground(Color.LIGHT_GRAY);
		release0ValueLabel.setOpaque(false);
		release0ValueLabel.setSize(30, 20);
		release0ValueLabel.setBorder(null);
		release0ValueLabel.setPreferredSize(release0ValueLabel.getSize());
		release0ValueLabel.setLocation(releaseSlider.getX() + 5 , 90);
		release0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		holdSlider = new JSlider();
		holdSlider.setMaximum(20);
		holdSlider.setMinimum(0);
		holdSlider.setOrientation(JSlider.HORIZONTAL);
		holdSlider.setSize(80, 25);
		holdSlider.setFont(new Font("Arial", 0, 8));
		holdSlider.setForeground(Color.LIGHT_GRAY);
		holdSlider.setPreferredSize(attackSlider.getSize());
		holdSlider.setOpaque(false);
		holdSlider.setFocusable(false);
		holdSlider.setLocation(decaySlider.getX() + 100, 130);
		final JLabel holdLabel = new JLabel("hold adj");
		holdLabel.setForeground(Color.LIGHT_GRAY);
		holdLabel.setOpaque(false);
		holdLabel.setSize(100, 10);
		holdLabel.setBorder(null);
		holdLabel.setPreferredSize(attackLabel.getSize());
		holdLabel.setLocation(holdSlider.getX(), 115);
		holdLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		holdSlider.setPaintTicks(true);
		holdSlider.setMajorTickSpacing(5);
		holdSlider.setMinorTickSpacing(5);
		holdSlider.setValue((int) (hold * 10));
		
		JLabel holdMaxValueLabel = new JLabel(String.valueOf(holdSlider.getMaximum() /10));
		holdMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		holdMaxValueLabel.setOpaque(false);
		holdMaxValueLabel.setSize(30, 20);
		holdMaxValueLabel.setBorder(null);
		holdMaxValueLabel.setPreferredSize(holdMaxValueLabel.getSize());
		holdMaxValueLabel.setLocation(holdSlider.getX() + 70, 155);
		holdMaxValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel hold0ValueLabel = new JLabel("0");
		hold0ValueLabel.setForeground(Color.LIGHT_GRAY);
		hold0ValueLabel.setOpaque(false);
		hold0ValueLabel.setSize(30, 20);
		hold0ValueLabel.setBorder(null);
		hold0ValueLabel.setPreferredSize(hold0ValueLabel.getSize());
		hold0ValueLabel.setLocation(holdSlider.getX() +5, 155);
		hold0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		
		final JLabel maxAttackValueLabel = new JLabel(" dB");
		maxAttackValueLabel.setForeground(Color.LIGHT_GRAY);
		maxAttackValueLabel.setOpaque(false);
		maxAttackValueLabel.setSize(150, 20);
		maxAttackValueLabel.setHorizontalTextPosition(JLabel.LEFT);
		maxAttackValueLabel.setBorder(null);
		maxAttackValueLabel.setPreferredSize(sustainSlider.getSize());
		maxAttackValueLabel.setLocation(getWidth() / 2 - sustainSlider.getWidth() / 2 + 40, 50);
		maxAttackValueLabel.setFont(new Font("Monospaced", Font.ITALIC, 26));
		
		//Affichage des valeurs
		final JLabel txtEgValueLabel = new JLabel("", JLabel.CENTER);
		txtEgValueLabel.setForeground(Color.LIGHT_GRAY);
		txtEgValueLabel.setOpaque(false);
		txtEgValueLabel.setSize(100, 20);
		txtEgValueLabel.setBorder(null);
		txtEgValueLabel.setLocation(getWidth() / 2 - txtEgValueLabel.getWidth() / 2, 180);
		txtEgValueLabel.setVisible(false);
		txtEgValueLabel.setFont(new Font("Monospaced", Font.ITALIC, 22));
		

		

		// Ajouts des composants
		add(pportGate);
		add(pportOut);
		add(attackLabel);
		add(attackSlider);
		add(decayLabel);
		add(decaySlider);
		add(releaseLabel);
		add(releaseSlider);
		add(sustainLabel);
		add(sustainSlider);
		add(holdLabel);
		add(holdSlider);

		add(sustainMaxValueLabel);
		add(sustain0ValueLabel);
		add(attackMaxValueLabel);
		add(attack0ValueLabel);
		add(decayMaxValueLabel);
		add(decay0ValueLabel);
		add(releaseMaxValueLabel);
		add(release0ValueLabel);
		add(hold0ValueLabel);
		add(holdMaxValueLabel);
		add(txtEgValueLabel);
		
		// Events
		sustainSlider.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				txtEgValueLabel.setVisible(true);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				txtEgValueLabel.setVisible(false);
			}
		});
		attackSlider.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				txtEgValueLabel.setVisible(true);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				txtEgValueLabel.setVisible(false);
			}
		});
		decaySlider.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				txtEgValueLabel.setVisible(true);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				txtEgValueLabel.setVisible(false);
			}
		});
		releaseSlider.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				txtEgValueLabel.setVisible(true);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				txtEgValueLabel.setVisible(false);
			}
		});
		holdSlider.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				txtEgValueLabel.setVisible(true);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				txtEgValueLabel.setVisible(false);
			}
		});
		
		sustainSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setSustain((int) sustainSlider.getValue() / 10.0);
				txtEgValueLabel.setText( sustainSlider.getValue()/ 10.0 + " dB");
			}
		});

		attackSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setAttack((double) attackSlider.getValue() / 10.0);
				txtEgValueLabel.setText( attackSlider.getValue() / 10.0 + " s");
			}
		});
		
		decaySlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setDecay((double) decaySlider.getValue() / 10.0);
				txtEgValueLabel.setText(decaySlider.getValue() / 10.0 + " s");
			}
		});
		
		releaseSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setRelease((double) releaseSlider.getValue() / 10.0);
				txtEgValueLabel.setText( releaseSlider.getValue() / 10.0 + " s");
			}
		});
		
		holdSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setHold((double) holdSlider.getValue() / 10.0);
				txtEgValueLabel.setText(  holdSlider.getValue() / 10.0 + " s");
			}
		});
		
	}

	@Override
	public void setSlidersEnabled(boolean value) {
		
	}



	@Override
	public void updatePresentation() {
		super.updatePresentation();
		attackSlider.setValue((int) (controller.getAttack() * 10));
		decaySlider.setValue((int) (controller.getDecay() * 10));
		releaseSlider.setValue((int) (controller.getRelease() * 10));
		holdSlider.setValue((int) (controller.getHold() * 10));
		sustainSlider.setValue((int) (controller.getSustain() * 10));
	}
}
