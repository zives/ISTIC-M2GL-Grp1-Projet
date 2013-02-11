package group1.project.synthlab.ihm.module.eg;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;
import group1.project.synthlab.ihm.tools.FloatTextField;
import group1.project.synthlab.ihm.tools.PTools;
import group1.project.synthlab.signal.Tools;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSlider;
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
		
		// Taille et couleur définie dans la super classe

		// Label et onoff boutons déjà rajoutés dans la super classe

		// Label fréquence

		//Ports
		PPort pportGate = (PPort) (((ICInPort) controller.getGate()).getPresentation());
		pportGate.setLocation(10, 240);
		PPort pportOut = (PPort) (((ICOutPort) controller.getOut()).getPresentation());
		pportOut.setLocation(240, 240);
		
		// Sliders

		
		sustainSlider = new JSlider();
		sustainSlider.setMaximum(0);
		sustainSlider.setMinimum(-600);
		sustainSlider.setOrientation(JSlider.VERTICAL);
		sustainSlider.setSize(20, 180);
		sustainSlider.setFont(new Font("Arial", 0, 8));
		sustainSlider.setForeground(Color.LIGHT_GRAY);
		//sustainSlider.setPreferredSize(releaseSlider.getSize());
		sustainSlider.setOpaque(false);
		sustainSlider.setFocusable(false);
		//sustainSlider.setLocation(releaseSlider.getX(), 160);
		final JLabel sustainLabel = new JLabel("sustain adjustement");
		sustainSlider.setForeground(Color.LIGHT_GRAY);
		sustainSlider.setOpaque(false);
		sustainSlider.setSize(20, 100);
		sustainSlider.setBorder(null);
		//sustainSlider.setPreferredSize(freqLabel.getSize());
		sustainSlider.setLocation(20,100);
		sustainSlider.setFont(new Font("Arial", Font.ITALIC, 10));
		sustainSlider.setPaintTicks(true);
		sustainSlider.setMajorTickSpacing(100);
		sustainSlider.setMinorTickSpacing(100);
		
		
		final JFormattedTextField textFreq = new FloatTextField(
		NumberFormat.getInstance());
		textFreq.setOpaque(false);
		textFreq.setSize(40, 20);
		textFreq.setFont(new Font("Arial", 0, 9));
		textFreq.setLocation(sustainSlider.getX() + sustainSlider.getWidth() + 10,140);
		final JLabel txtFreqLabel = new JLabel("");
		txtFreqLabel.setForeground(Color.LIGHT_GRAY);
		txtFreqLabel.setOpaque(false);
		txtFreqLabel.setSize(70, 20);
		txtFreqLabel.setBorder(null);
		//txtFreqLabel.setPreferredSize(freqLabel.getSize());
		txtFreqLabel.setLocation(textFreq.getX(), 140);
		txtFreqLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		
		JLabel sustainMaxValueLabel = new JLabel("0");
		sustainMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		sustainMaxValueLabel.setOpaque(false);
		sustainMaxValueLabel.setSize(30, 20);
		sustainMaxValueLabel.setBorder(null);
		sustainMaxValueLabel.setPreferredSize(sustainMaxValueLabel.getSize());
		sustainMaxValueLabel.setLocation(sustainSlider.getX() + 20, 100);
		sustainMaxValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel sustain0ValueLabel = new JLabel("-60");
		sustain0ValueLabel.setForeground(Color.LIGHT_GRAY);
		sustain0ValueLabel.setOpaque(false);
		sustain0ValueLabel.setSize(30, 20);
		sustain0ValueLabel.setBorder(null);
		sustain0ValueLabel.setPreferredSize(sustain0ValueLabel.getSize());
		sustain0ValueLabel.setLocation(sustainSlider.getX() + 20, 185);
		sustain0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		attackSlider = new JSlider();
		attackSlider.setMaximum(50);
		attackSlider.setMinimum(0);
		attackSlider.setOrientation(JSlider.HORIZONTAL);
		attackSlider.setSize(100, 20);
		attackSlider.setFont(new Font("Arial", 0, 8));
		attackSlider.setForeground(Color.LIGHT_GRAY);
		attackSlider.setPreferredSize(attackSlider.getSize());
		attackSlider.setOpaque(false);
		attackSlider.setFocusable(false);
		attackSlider.setLocation(textFreq.getX() + 45, 110);
		JLabel attackLabel = new JLabel("attack adjustement");
		attackLabel.setForeground(Color.LIGHT_GRAY);
		attackLabel.setOpaque(false);
		attackLabel.setSize(100, 20);
		attackLabel.setBorder(null);
		attackLabel.setPreferredSize(textFreq.getSize());
		attackLabel.setLocation(attackLabel.getX() + 100, 80);
		attackLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		attackSlider.setPaintTicks(true);
		attackSlider.setMajorTickSpacing(10);
		attackSlider.setMinorTickSpacing(10);
		
		JLabel attackMaxValueLabel = new JLabel("5");
		attackMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		attackMaxValueLabel.setOpaque(false);
		attackMaxValueLabel.setSize(30, 20);
		attackMaxValueLabel.setBorder(null);
		attackMaxValueLabel.setPreferredSize(attackMaxValueLabel.getSize());
		attackMaxValueLabel.setLocation(attackSlider.getX() + 85, 95);
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
		decaySlider.setMaximum(990);
		decaySlider.setMinimum(0);
		decaySlider.setOrientation(JSlider.HORIZONTAL);
		decaySlider.setSize(100, 20);
		decaySlider.setFont(new Font("Arial", 0, 8));
		decaySlider.setForeground(Color.LIGHT_GRAY);
		decaySlider.setPreferredSize(attackSlider.getSize());
		decaySlider.setOpaque(false);
		decaySlider.setFocusable(false);
		decaySlider.setLocation(attackLabel.getX(), 190);
		final JLabel decayLabel = new JLabel("decay adjustement");
		decayLabel.setForeground(Color.LIGHT_GRAY);
		decayLabel.setOpaque(false);
		decayLabel.setSize(100, 20);
		decayLabel.setBorder(null);
		decayLabel.setPreferredSize(attackLabel.getSize());
		decayLabel.setLocation(attackLabel.getX(), 160);
		decayLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel decayMaxValueLabel = new JLabel("5");
		decayMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		decayMaxValueLabel.setOpaque(false);
		decayMaxValueLabel.setSize(30, 20);
		decayMaxValueLabel.setBorder(null);
		decayMaxValueLabel.setPreferredSize(decayMaxValueLabel.getSize());
		decayMaxValueLabel.setLocation(decaySlider.getX() + 180, 95);
		decayMaxValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel decay0ValueLabel = new JLabel("0");
		decay0ValueLabel.setForeground(Color.LIGHT_GRAY);
		decay0ValueLabel.setOpaque(false);
		decay0ValueLabel.setSize(30, 20);
		decay0ValueLabel.setBorder(null);
		decay0ValueLabel.setPreferredSize(decay0ValueLabel.getSize());
		decay0ValueLabel.setLocation(decaySlider.getX() + 100, 95);
		decay0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		releaseSlider = new JSlider();
		releaseSlider.setMaximum(990);
		releaseSlider.setMinimum(0);
		releaseSlider.setOrientation(JSlider.HORIZONTAL);
		releaseSlider.setSize(100, 20);
		releaseSlider.setFont(new Font("Arial", 0, 8));
		releaseSlider.setForeground(Color.LIGHT_GRAY);
		releaseSlider.setPreferredSize(attackSlider.getSize());
		releaseSlider.setOpaque(false);
		releaseSlider.setFocusable(false);
		releaseSlider.setLocation(attackSlider.getX() + 100, 110);
		final JLabel releaseLabel = new JLabel("release adjustement");
		releaseLabel.setForeground(Color.LIGHT_GRAY);
		releaseLabel.setOpaque(false);
		releaseLabel.setSize(100, 20);
		releaseLabel.setBorder(null);
		releaseLabel.setPreferredSize(attackLabel.getSize());
		releaseLabel.setLocation(attackLabel.getX() + 100, 80);
		releaseLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel releaseMaxValueLabel = new JLabel("5");
		releaseMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		releaseMaxValueLabel.setOpaque(false);
		releaseMaxValueLabel.setSize(30, 20);
		releaseMaxValueLabel.setBorder(null);
		releaseMaxValueLabel.setPreferredSize(releaseMaxValueLabel.getSize());
		releaseMaxValueLabel.setLocation(releaseSlider.getX() - 10, 175);
		releaseMaxValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel release0ValueLabel = new JLabel("0");
		release0ValueLabel.setForeground(Color.LIGHT_GRAY);
		release0ValueLabel.setOpaque(false);
		release0ValueLabel.setSize(30, 20);
		release0ValueLabel.setBorder(null);
		release0ValueLabel.setPreferredSize(release0ValueLabel.getSize());
		release0ValueLabel.setLocation(releaseSlider.getX() - 95, 175);
		release0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		holdSlider = new JSlider();
		holdSlider.setMaximum(990);
		holdSlider.setMinimum(0);
		holdSlider.setOrientation(JSlider.HORIZONTAL);
		holdSlider.setSize(100, 20);
		holdSlider.setFont(new Font("Arial", 0, 8));
		holdSlider.setForeground(Color.LIGHT_GRAY);
		holdSlider.setPreferredSize(attackSlider.getSize());
		holdSlider.setOpaque(false);
		holdSlider.setFocusable(false);
		holdSlider.setLocation(decaySlider.getX() + 100, 190);
		final JLabel holdLabel = new JLabel("hold adjustement");
		holdLabel.setForeground(Color.LIGHT_GRAY);
		holdLabel.setOpaque(false);
		holdLabel.setSize(100, 20);
		holdLabel.setBorder(null);
		holdLabel.setPreferredSize(attackLabel.getSize());
		holdLabel.setLocation(decayLabel.getX() + 100, 160);
		holdLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel holdMaxValueLabel = new JLabel("5");
		holdMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		holdMaxValueLabel.setOpaque(false);
		holdMaxValueLabel.setSize(30, 20);
		holdMaxValueLabel.setBorder(null);
		holdMaxValueLabel.setPreferredSize(holdMaxValueLabel.getSize());
		holdMaxValueLabel.setLocation(holdSlider.getX() + 80, 175);
		holdMaxValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel hold0ValueLabel = new JLabel("0");
		hold0ValueLabel.setForeground(Color.LIGHT_GRAY);
		hold0ValueLabel.setOpaque(false);
		hold0ValueLabel.setSize(30, 20);
		hold0ValueLabel.setBorder(null);
		hold0ValueLabel.setPreferredSize(hold0ValueLabel.getSize());
		hold0ValueLabel.setLocation(holdSlider.getX(), 175);
		hold0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
//		JLabel maxAttackValueLabel = new JLabel("5");
//		maxAttackValueLabel.setForeground(Color.LIGHT_GRAY);
//		maxAttackValueLabel.setOpaque(false);
//		maxAttackValueLabel.setSize(30, 20);
//		maxAttackValueLabel.setBorder(null);
//		//maxAttackValueLabel.setPreferredSize(sustainSlider.getSize());
//		maxAttackValueLabel.setLocation(sustainSlider.getX() + 5 , 100);
//		maxAttackValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		final JLabel maxAttackValueLabel = new JLabel(" dB");
		maxAttackValueLabel.setForeground(Color.LIGHT_GRAY);
		maxAttackValueLabel.setOpaque(false);
		maxAttackValueLabel.setSize(150, 20);
		maxAttackValueLabel.setHorizontalTextPosition(JLabel.LEFT);
		maxAttackValueLabel.setBorder(null);
		maxAttackValueLabel.setPreferredSize(sustainSlider.getSize());
		maxAttackValueLabel.setLocation(getWidth() / 2 - sustainSlider.getWidth() / 2 + 40, 50);
		maxAttackValueLabel.setFont(new Font("Monospaced", Font.ITALIC, 26));
		
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
		
		

		
		// Ports
//		PPort pportGate1 = (PPort) (((ICInPort) controller.getGate())
//				.getPresentation());
//		pportGate1.setLocation(10, 240);


		// Ajouts des composants
//		add(freqLabel);
//		add(pportGate);
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
		add(txtFreqLabel);
		add(textFreq);
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
		
//		controller.redefAdjustments();
//		coarseSlider.setValue(controller.getCoarseAdjustment() * 10);
//		fineSlider.setValue((int) (controller.getFineAdjustment() * 10000.0));
//
		// Events
		sustainSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setSustain((int) sustainSlider.getValue() / 6);
				//textFreq.setText(PTools.freqToString(controller.getf0()) + " Hz");
				//textFreq.setText(PTools.freqToString(controller.getf0()));
			}
		});

		attackSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setAttack((int) attackSlider.getValue() / 10);
			}
		});
		
		decaySlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setDecay((int) decaySlider.getValue() / 10);
			}
		});
		
		releaseSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setRelease((int) releaseSlider.getValue() / 10);
			}
		});
		
		holdSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setHold((int) holdSlider.getValue() / 10);
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
