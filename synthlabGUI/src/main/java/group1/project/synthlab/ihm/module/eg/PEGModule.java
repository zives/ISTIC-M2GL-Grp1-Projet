package group1.project.synthlab.ihm.module.eg;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.tools.FloatTextField;
import group1.project.synthlab.unitExtensions.filterSupervisor.IFilterAmplitudeObservable;
import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSlider;

public class PEGModule extends PModule implements IPEGModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected ICEGModule controller;
	protected JSlider attackSlider;
	protected JSlider decaySlider;
	protected JSlider releaseSlider;
	protected JSlider sustainSlider;
	protected JSlider holdSlider;
	
	
	
	protected JLabel warnLabel;
	
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
		final JLabel freqLabel = new JLabel(0 + " Hz");//TODO Test
		freqLabel.setForeground(Color.LIGHT_GRAY);
		freqLabel.setOpaque(false);
		freqLabel.setSize(230, 20);
		freqLabel.setHorizontalAlignment(JLabel.LEFT);
		freqLabel.setBorder(null);
		freqLabel.setPreferredSize(freqLabel.getSize());
		freqLabel.setLocation(getWidth() / 2 - freqLabel.getWidth() / 2, 50);
		freqLabel.setFont(new Font("Monospaced", Font.ITALIC, 26));

		// Sliders
		attackSlider = new JSlider();
		attackSlider.setMaximum(990);
		attackSlider.setMinimum(0);
		attackSlider.setOrientation(JSlider.HORIZONTAL);
		attackSlider.setSize(230, 20);
		attackSlider.setFont(new Font("Arial", 0, 8));
		attackSlider.setForeground(Color.LIGHT_GRAY);
		attackSlider.setPreferredSize(attackSlider.getSize());
		attackSlider.setOpaque(false);
		attackSlider.setFocusable(false);
		attackSlider.setLocation(freqLabel.getX(), 110);
		JLabel attackLabel = new JLabel("attack adjustement");
		attackLabel.setForeground(Color.LIGHT_GRAY);
		attackLabel.setOpaque(false);
		attackLabel.setSize(100, 20);
		attackLabel.setBorder(null);
		attackLabel.setPreferredSize(freqLabel.getSize());
		attackLabel.setLocation(attackLabel.getX(), 90);
		attackLabel.setFont(new Font("Arial", Font.ITALIC, 10));

		decaySlider = new JSlider();
		decaySlider.setMaximum(10000);
		decaySlider.setMinimum(0);
		decaySlider.setOrientation(JSlider.HORIZONTAL);
		decaySlider.setSize(180, 20);
		decaySlider.setFont(new Font("Arial", 0, 8));
		decaySlider.setForeground(Color.LIGHT_GRAY);
		decaySlider.setPreferredSize(attackSlider.getSize());
		decaySlider.setOpaque(false);
		decaySlider.setFocusable(false);
		decaySlider.setLocation(attackLabel.getX(), 160);
		final JLabel decayLabel = new JLabel("decay adjustement");
		decaySlider.setForeground(Color.LIGHT_GRAY);
		decaySlider.setOpaque(false);
		decaySlider.setSize(100, 20);
		decaySlider.setBorder(null);
		decaySlider.setPreferredSize(freqLabel.getSize());
		decaySlider.setLocation(attackLabel.getX(), 140);
		decaySlider.setFont(new Font("Arial", Font.ITALIC, 10));
		
		releaseSlider = new JSlider();
		releaseSlider.setMaximum(10000);
		releaseSlider.setMinimum(0);
		releaseSlider.setOrientation(JSlider.HORIZONTAL);
		releaseSlider.setSize(180, 20);
		releaseSlider.setFont(new Font("Arial", 0, 8));
		releaseSlider.setForeground(Color.LIGHT_GRAY);
		releaseSlider.setPreferredSize(decaySlider.getSize());
		releaseSlider.setOpaque(false);
		releaseSlider.setFocusable(false);
		releaseSlider.setLocation(decaySlider.getX(), 160);
		final JLabel releaseLabel = new JLabel("release adjustement");
		releaseSlider.setForeground(Color.LIGHT_GRAY);
		releaseSlider.setOpaque(false);
		releaseSlider.setSize(100, 20);
		releaseSlider.setBorder(null);
		releaseSlider.setPreferredSize(freqLabel.getSize());
		releaseSlider.setLocation(decaySlider.getX(), 140);
		releaseSlider.setFont(new Font("Arial", Font.ITALIC, 10));
		
		sustainSlider = new JSlider();
		sustainSlider.setMaximum(10000);
		sustainSlider.setMinimum(0);
		sustainSlider.setOrientation(JSlider.HORIZONTAL);
		sustainSlider.setSize(180, 20);
		sustainSlider.setFont(new Font("Arial", 0, 8));
		sustainSlider.setForeground(Color.LIGHT_GRAY);
		sustainSlider.setPreferredSize(releaseSlider.getSize());
		sustainSlider.setOpaque(false);
		sustainSlider.setFocusable(false);
		sustainSlider.setLocation(releaseSlider.getX(), 160);
		final JLabel sustainLabel = new JLabel("sustain adjustement");
		releaseSlider.setForeground(Color.LIGHT_GRAY);
		releaseSlider.setOpaque(false);
		releaseSlider.setSize(100, 20);
		releaseSlider.setBorder(null);
		releaseSlider.setPreferredSize(freqLabel.getSize());
		releaseSlider.setLocation(releaseSlider.getX(), 140);
		releaseSlider.setFont(new Font("Arial", Font.ITALIC, 10));

		final JFormattedTextField textFreq = new FloatTextField(
				NumberFormat.getInstance());
		textFreq.setOpaque(false);
		textFreq.setSize(40, 20);
		textFreq.setFont(new Font("Arial", 0, 9));
		textFreq.setLocation(attackSlider.getX() + attackSlider.getWidth() + 10,
				160);
		final JLabel txtFreqLabel = new JLabel("custom");
		txtFreqLabel.setForeground(Color.LIGHT_GRAY);
		txtFreqLabel.setOpaque(false);
		txtFreqLabel.setSize(70, 20);
		txtFreqLabel.setBorder(null);
		txtFreqLabel.setPreferredSize(freqLabel.getSize());
		txtFreqLabel.setLocation(textFreq.getX(), 140);
		txtFreqLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
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
		PPort pportGate = (PPort) (((ICInPort) controller.getGate())
				.getPresentation());
		pportGate.setLocation(10, 240);


		// Ajouts des composants
		add(freqLabel);
		add(pportGate);
		add(attackLabel);
		add(attackSlider);
		add(decayLabel);
		add(decaySlider);
		add(releaseLabel);
		add(releaseSlider);
		add(sustainLabel);
		add(sustainSlider);
		add(txtFreqLabel);
		add(textFreq);
		add(warnLabel);

//		controller.redefAdjustments();
//		coarseSlider.setValue(controller.getCoarseAdjustment() * 10);
//		fineSlider.setValue((int) (controller.getFineAdjustment() * 10000.0));
//
//		// Events
//		coarseSlider.addChangeListener(new ChangeListener() {
//			public void stateChanged(ChangeEvent arg0) {
//				controller.setCoarseAdjustment((int) coarseSlider.getValue() / 10);
//				freqLabel.setText(PTools.freqToString(controller.getf0()) + " Hz");
//				textFreq.setText(PTools.freqToString(controller.getf0()));
//			}
//		});
//
//		fineSlider.addChangeListener(new ChangeListener() {
//			public void stateChanged(ChangeEvent arg0) {
//				controller.setFineAdjustment(fineSlider.getValue() / 10000.0);
//				freqLabel.setText(PTools.freqToString(controller.getf0()) + " Hz");
//				textFreq.setText(PTools.freqToString(controller.getf0()));
//			}
//		});
//		textFreq.addKeyListener(new KeyListener() {
//
//			public void keyTyped(KeyEvent ev) {
//
//			}
//
//			public void keyReleased(KeyEvent ev) {
//
//			}
//
//			public void keyPressed(KeyEvent ev) {
//				if (ev.getKeyCode() == KeyEvent.VK_ENTER) {
//					Double val = 0.0;
//					try {
//						String valText = textFreq.getText();
//						valText = valText.replace(",", ".");
//						valText = valText.trim();
//						val = Double.valueOf(valText);
//					} catch (Exception e) {
//						return;
//					}
//					controller.setf0(val);
//					controller.redefAdjustments();
//					coarseSlider.setValue(controller.getCoarseAdjustment() * 10);
//					fineSlider.setValue((int) (controller.getFineAdjustment() * 10000.0));
//					freqLabel.setText(PTools.freqToString(val) + " Hz");
//					textFreq.setText(PTools.freqToString(val));
//					
//				}
//			}
//			
//		});
//
//	}
//
//
//
//	public void setSlidersEnabled(boolean value) {
//		coarseSlider.setEnabled(value);
//		fineSlider.setEnabled(value);
//
//	}
//
//	public void warn(IFilterAmplitudeObservable subject, boolean tooHigh) {	
//			warnLabel.setVisible(tooHigh);		
//		
//	}
//
//	public void hasSignal(IFilterAmplitudeObservable subject, boolean hasSignal) {
//		
//		
//	}
	}

	@Override
	public void warn(IFilterAmplitudeObservable subject, boolean tooHigh) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hasSignal(IFilterAmplitudeObservable subject, boolean hasSignal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSlidersEnabled(boolean value) {
		// TODO Auto-generated method stub
		
	}
	



}
