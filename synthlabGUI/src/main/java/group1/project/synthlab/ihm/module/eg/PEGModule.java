package group1.project.synthlab.ihm.module.eg;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PEGModule extends PModule implements IPEGModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected ICEGModule controller;
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
	
//	this.attack = 1000;
//	this.decay = 1000;
//	this.release = 1000;
//	this.sustain = 0.3;
//	this.hold = 0;

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
		this.decibel = controller.getDecibel() * 10;
		
		// Taille et couleur définie dans la super classe

		// Label et onoff boutons déjà rajoutés dans la super classe

		// Label fréquence

		//Ports
		PPort pportGate = (PPort) (((ICInPort) controller.getGate()).getPresentation());
		pportGate.setLocation(10, 220);
		PPort pportOut = (PPort) (((ICOutPort) controller.getOut()).getPresentation());
		pportOut.setLocation(240, 220);
		
		// Sliders

		
		sustainSlider = new JSlider();
		sustainSlider.setMaximum(0);
		sustainSlider.setMinimum(-600);
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
		sustainSlider.setLocation(20,80);
		sustainSlider.setFont(new Font("Arial", Font.ITALIC, 10));
		sustainSlider.setPaintTicks(true);
		sustainSlider.setMajorTickSpacing(100);
		sustainSlider.setMinorTickSpacing(100);
		sustainSlider.setValue((int) (decibel));
		

		JLabel sustainMaxValueLabel = new JLabel("0");
		sustainMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		sustainMaxValueLabel.setOpaque(false);
		sustainMaxValueLabel.setSize(30, 20);
		sustainMaxValueLabel.setBorder(null);
		sustainMaxValueLabel.setPreferredSize(sustainMaxValueLabel.getSize());
		sustainMaxValueLabel.setLocation(sustainSlider.getX() + 30, 80);
		sustainMaxValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel sustain0ValueLabel = new JLabel("-60");
		sustain0ValueLabel.setForeground(Color.LIGHT_GRAY);
		sustain0ValueLabel.setOpaque(false);
		sustain0ValueLabel.setSize(30, 20);
		sustain0ValueLabel.setBorder(null);
		sustain0ValueLabel.setPreferredSize(sustain0ValueLabel.getSize());
		sustain0ValueLabel.setLocation(sustainSlider.getX() + 30, 165);
		sustain0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		attackSlider = new JSlider();
		attackSlider.setMaximum(50);
		attackSlider.setMinimum(0);
		attackSlider.setOrientation(JSlider.HORIZONTAL);
		attackSlider.setSize(80, 20);
		attackSlider.setFont(new Font("Arial", 0, 8));
		attackSlider.setForeground(Color.LIGHT_GRAY);
		attackSlider.setPreferredSize(attackSlider.getSize());
		attackSlider.setOpaque(false);
		attackSlider.setFocusable(false);
		attackSlider.setLocation(sustainSlider.getX() + 65, 80);
		JLabel attackLabel = new JLabel("attack adj");
		attackLabel.setForeground(Color.LIGHT_GRAY);
		attackLabel.setOpaque(false);
		attackLabel.setSize(100, 20);
		attackLabel.setBorder(null);
		attackLabel.setPreferredSize(attackSlider.getSize());
		attackLabel.setLocation(sustainSlider.getX() + 80, 60);
		attackLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		attackSlider.setPaintTicks(true);
		attackSlider.setMajorTickSpacing(10);
		attackSlider.setMinorTickSpacing(10);
		attackSlider.setValue((int) (attack * 10));
		
		JLabel attackMaxValueLabel = new JLabel("5");
		attackMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		attackMaxValueLabel.setOpaque(false);
		attackMaxValueLabel.setSize(30, 20);
		attackMaxValueLabel.setBorder(null);
		attackMaxValueLabel.setPreferredSize(attackMaxValueLabel.getSize());
		attackMaxValueLabel.setLocation(attackSlider.getX() + 70, 95);
		attackMaxValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel attack0ValueLabel = new JLabel("0");
		attack0ValueLabel.setForeground(Color.LIGHT_GRAY);
		attack0ValueLabel.setOpaque(false);
		attack0ValueLabel.setSize(30, 20);
		attack0ValueLabel.setBorder(null);
		attack0ValueLabel.setPreferredSize(attack0ValueLabel.getSize());
		attack0ValueLabel.setLocation(attackSlider.getX() + 5, 95);
		attack0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		decaySlider = new JSlider();
		decaySlider.setMaximum(50);
		decaySlider.setMinimum(0);
		decaySlider.setOrientation(JSlider.HORIZONTAL);
		decaySlider.setSize(80, 20);
		decaySlider.setFont(new Font("Arial", 0, 8));
		decaySlider.setForeground(Color.LIGHT_GRAY);
		decaySlider.setPreferredSize(attackSlider.getSize());
		decaySlider.setOpaque(false);
		decaySlider.setFocusable(false);
		decaySlider.setLocation(attackSlider.getX(), 140);
		final JLabel decayLabel = new JLabel("decay adj");
		decayLabel.setForeground(Color.LIGHT_GRAY);
		decayLabel.setOpaque(false);
		decayLabel.setSize(100, 20);
		decayLabel.setBorder(null);
		decayLabel.setPreferredSize(attackLabel.getSize());
		decayLabel.setLocation(attackLabel.getX(), 120);
		decayLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		decaySlider.setPaintTicks(true);
		decaySlider.setMajorTickSpacing(10);
		decaySlider.setMinorTickSpacing(10);
		decaySlider.setValue((int) (decay * 10));
		
		JLabel decayMaxValueLabel = new JLabel("5");
		decayMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		decayMaxValueLabel.setOpaque(false);
		decayMaxValueLabel.setSize(30, 20);
		decayMaxValueLabel.setBorder(null);
		decayMaxValueLabel.setPreferredSize(decayMaxValueLabel.getSize());
		decayMaxValueLabel.setLocation(decaySlider.getX() + 70, 155);
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
		releaseSlider.setMaximum(60);
		releaseSlider.setMinimum(0);
		releaseSlider.setOrientation(JSlider.HORIZONTAL);
		releaseSlider.setSize(80, 20);
		releaseSlider.setFont(new Font("Arial", 0, 8));
		releaseSlider.setForeground(Color.LIGHT_GRAY);
		releaseSlider.setPreferredSize(attackSlider.getSize());
		releaseSlider.setOpaque(false);
		releaseSlider.setFocusable(false);
		releaseSlider.setLocation(attackSlider.getX() + 100, 80);
		final JLabel releaseLabel = new JLabel("release adj");
		releaseLabel.setForeground(Color.LIGHT_GRAY);
		releaseLabel.setOpaque(false);
		releaseLabel.setSize(100, 20);
		releaseLabel.setBorder(null);
		releaseLabel.setPreferredSize(attackLabel.getSize());
		releaseLabel.setLocation(attackLabel.getX() + 110, 60);
		releaseLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		releaseSlider.setPaintTicks(true);
		releaseSlider.setMajorTickSpacing(10);
		releaseSlider.setMinorTickSpacing(10);
		releaseSlider.setValue((int) (release * 10));
		
		JLabel releaseMaxValueLabel = new JLabel("5");
		releaseMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		releaseMaxValueLabel.setOpaque(false);
		releaseMaxValueLabel.setSize(30, 20);
		releaseMaxValueLabel.setBorder(null);
		releaseMaxValueLabel.setPreferredSize(releaseMaxValueLabel.getSize());
		releaseMaxValueLabel.setLocation(releaseSlider.getX()+ 70, 95);
		releaseMaxValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel release0ValueLabel = new JLabel("0");
		release0ValueLabel.setForeground(Color.LIGHT_GRAY);
		release0ValueLabel.setOpaque(false);
		release0ValueLabel.setSize(30, 20);
		release0ValueLabel.setBorder(null);
		release0ValueLabel.setPreferredSize(release0ValueLabel.getSize());
		release0ValueLabel.setLocation(releaseSlider.getX() + 5 , 95);
		release0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		holdSlider = new JSlider();
		holdSlider.setMaximum(50);
		holdSlider.setMinimum(0);
		holdSlider.setOrientation(JSlider.HORIZONTAL);
		holdSlider.setSize(80, 20);
		holdSlider.setFont(new Font("Arial", 0, 8));
		holdSlider.setForeground(Color.LIGHT_GRAY);
		holdSlider.setPreferredSize(attackSlider.getSize());
		holdSlider.setOpaque(false);
		holdSlider.setFocusable(false);
		holdSlider.setLocation(decaySlider.getX() + 100, 140);
		final JLabel holdLabel = new JLabel("hold adj");
		holdLabel.setForeground(Color.LIGHT_GRAY);
		holdLabel.setOpaque(false);
		holdLabel.setSize(100, 20);
		holdLabel.setBorder(null);
		holdLabel.setPreferredSize(attackLabel.getSize());
		holdLabel.setLocation(decayLabel.getX() + 100, 120);
		holdLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		holdSlider.setPaintTicks(true);
		holdSlider.setMajorTickSpacing(10);
		holdSlider.setMinorTickSpacing(10);
		holdSlider.setValue((int) (hold * 10));
		
		JLabel holdMaxValueLabel = new JLabel("5");
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
		final JLabel txtEgValueLabel = new JLabel("");
		txtEgValueLabel.setForeground(Color.LIGHT_GRAY);
		txtEgValueLabel.setOpaque(false);
		txtEgValueLabel.setSize(70, 20);
		txtEgValueLabel.setBorder(null);
		txtEgValueLabel.setLocation(130, 190);
		txtEgValueLabel.setVisible(false);
		txtEgValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		//Warn
		warnLabel = new JLabel("Amplitude out of bounds!");
		warnLabel.setForeground(Color.LIGHT_GRAY);
		warnLabel.setOpaque(false);
		warnLabel.setSize(180, 20);
		warnLabel.setForeground(Color.RED);
		warnLabel.setBorder(null);
		warnLabel.setLocation(this.getWidth() / 2 - warnLabel.getWidth() / 2, 190);
		warnLabel.setFont(new Font("Arial", 0, 15));
		warnLabel.setVisible(false);
		

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

		add(warnLabel);
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
		sustainSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setSustain((int) sustainSlider.getValue() / 10.0);
				txtEgValueLabel.setVisible(true);
				txtEgValueLabel.setFont(font);
				txtEgValueLabel.setText( sustainSlider.getValue()/ 10.0 + " dB");
				//textFreq.setText(PTools.freqToString(controller.getf0()) + " Hz");
				//textFreq.setText(PTools.freqToString(controller.getf0()));
			}
		});

		attackSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setAttack((double) attackSlider.getValue() / 10.0);
				txtEgValueLabel.setVisible(true);
				txtEgValueLabel.setFont(font);
				txtEgValueLabel.setText( attackSlider.getValue() / 10.0 + "");
			}
		});
		
		decaySlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setDecay((double) decaySlider.getValue() / 10.0);
				txtEgValueLabel.setVisible(true);
				txtEgValueLabel.setFont(font);
				txtEgValueLabel.setText(decaySlider.getValue() / 10.0 + "");
			}
		});
		
		releaseSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setRelease((double) releaseSlider.getValue() / 10.0);
				txtEgValueLabel.setVisible(true);
				txtEgValueLabel.setText( releaseSlider.getValue() / 10.0 + "");
			}
		});
		
		holdSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setHold((double) holdSlider.getValue() / 10.0);
				txtEgValueLabel.setVisible(true);
				txtEgValueLabel.setText(  holdSlider.getValue() / 10.0 + "");
			}
		});
		
	}

	@Override
	public void setSlidersEnabled(boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(
			group1.project.synthlab.unitExtensions.filterSupervisor.IFilterAmplitudeObservable subject,
			boolean tooHigh) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hasSignal(
			group1.project.synthlab.unitExtensions.filterSupervisor.IFilterAmplitudeObservable subject,
			boolean hasSignal) {
		// TODO Auto-generated method stub
		
	}
	
}
