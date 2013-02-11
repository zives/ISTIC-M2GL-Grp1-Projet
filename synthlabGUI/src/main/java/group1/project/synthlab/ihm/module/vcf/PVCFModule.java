package group1.project.synthlab.ihm.module.vcf;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;
import group1.project.synthlab.ihm.tools.FloatTextField;
import group1.project.synthlab.ihm.tools.PTools;
import group1.project.synthlab.unitExtensions.filterSupervisor.IFilterAmplitudeObservable;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PVCFModule extends PModule implements IPVCFModule {

	protected ICVCFModule controller;

	protected JSlider coarseSlider;
	protected JSlider fineSlider;
	protected JLabel warnLabel;
	protected JSlider qualitySlider;

	public PVCFModule(final ICVCFModule controller) {
		super(controller);
		this.controller = controller;
		// Taille et couleur définie dans la super classe

		// Label et onoff boutons déjà rajoutés dans la super classe

		this.setSize(getWidth() + 30, getHeight() - 40);
		this.setPreferredSize(this.getSize());

		// Ports
		PPort pportIn = (PPort) (((ICInPort) controller.getIn()).getPresentation());
		pportIn.setLocation(10, getHeight() - pportIn.getHeight() -5);
		PPort pportFM = (PPort) (((ICInPort) controller.getFm()).getPresentation());
		pportFM.setLocation(pportIn.getX() + 60, pportIn.getY());
		PPort pportOut = (PPort) (((ICOutPort) controller.getOut()).getPresentation());
		pportOut.setLocation(pportIn.getX() + 120, pportIn.getY());

		// Label fréquence
		final JLabel freqLabel = new JLabel(controller.getf0() + " Hz");
		freqLabel.setForeground(Color.LIGHT_GRAY);
		freqLabel.setOpaque(false);
		freqLabel.setSize(230, 20);
		freqLabel.setHorizontalAlignment(JLabel.LEFT);
		freqLabel.setBorder(null);
		freqLabel.setPreferredSize(freqLabel.getSize());
		freqLabel.setLocation(getWidth() / 2 - freqLabel.getWidth() / 2, 50);
		freqLabel.setFont(new Font("Monospaced", Font.ITALIC, 26));

		// Sliders
		coarseSlider = new JSlider();
		coarseSlider.setMaximum(990);
		coarseSlider.setMinimum(0);
		coarseSlider.setOrientation(JSlider.HORIZONTAL);
		coarseSlider.setSize(230, 20);
		coarseSlider.setFont(new Font("Arial", 0, 8));
		coarseSlider.setForeground(Color.LIGHT_GRAY);
		coarseSlider.setPreferredSize(coarseSlider.getSize());
		coarseSlider.setOpaque(false);
		coarseSlider.setFocusable(false);
		coarseSlider.setLocation(20, 110);
		JLabel coarseLabel = new JLabel("coarse adjustement");
		coarseLabel.setForeground(Color.LIGHT_GRAY);
		coarseLabel.setOpaque(false);
		coarseLabel.setSize(100, 20);
		coarseLabel.setBorder(null);
		coarseLabel.setPreferredSize(freqLabel.getSize());
		coarseLabel.setLocation(coarseSlider.getX(), 90);
		coarseLabel.setFont(new Font("Arial", Font.ITALIC, 10));

		fineSlider = new JSlider();
		fineSlider.setMaximum(10000);
		fineSlider.setMinimum(0);
		fineSlider.setOrientation(JSlider.HORIZONTAL);
		fineSlider.setSize(180, 20);
		fineSlider.setFont(new Font("Arial", 0, 8));
		fineSlider.setForeground(Color.LIGHT_GRAY);
		fineSlider.setPreferredSize(fineSlider.getSize());
		fineSlider.setOpaque(false);
		fineSlider.setFocusable(false);
		fineSlider.setLocation(coarseSlider.getX(), 160);
		final JLabel fineLabel = new JLabel("fine adjustement");
		fineLabel.setForeground(Color.LIGHT_GRAY);
		fineLabel.setOpaque(false);
		fineLabel.setSize(100, 20);
		fineLabel.setBorder(null);
		fineLabel.setPreferredSize(freqLabel.getSize());
		fineLabel.setLocation(coarseSlider.getX(), 140);
		fineLabel.setFont(new Font("Arial", Font.ITALIC, 10));

		final JFormattedTextField textFreq = new FloatTextField(
				NumberFormat.getInstance());
		textFreq.setOpaque(false);
		textFreq.setSize(40, 20);
		textFreq.setFont(new Font("Arial", 0, 9));
		textFreq.setLocation(fineSlider.getX() + fineSlider.getWidth() + 10,
				160);
		final JLabel txtFreqLabel = new JLabel("custom");
		txtFreqLabel.setForeground(Color.LIGHT_GRAY);
		txtFreqLabel.setOpaque(false);
		txtFreqLabel.setSize(70, 20);
		txtFreqLabel.setBorder(null);
		txtFreqLabel.setPreferredSize(freqLabel.getSize());
		txtFreqLabel.setLocation(textFreq.getX(), 140);
		txtFreqLabel.setFont(new Font("Arial", Font.ITALIC, 10));

		// Sliders
		qualitySlider = new JSlider();
		qualitySlider.setMaximum(10);
		qualitySlider.setMinimum(0);
		qualitySlider.setOrientation(JSlider.VERTICAL);
		qualitySlider.setValue((int) controller.getq()*10);
		qualitySlider.setSize(40, 150);
		qualitySlider.setFont(new Font("Arial", 0, 8));
		qualitySlider.setForeground(Color.LIGHT_GRAY);
		qualitySlider.setPreferredSize(qualitySlider.getSize());
		qualitySlider.setBackground(getBackground());
		qualitySlider.setOpaque(false);
		qualitySlider.setFocusable(false);
		qualitySlider.setBorder(null);
		qualitySlider.setLocation(270, 80);
		qualitySlider.setMajorTickSpacing(5);
		qualitySlider.setMinorTickSpacing(1);
		qualitySlider.setPaintTicks(true);

		//Labels qualitySlider
		JLabel qualityLabel = new JLabel("Facteur Q");
		qualityLabel.setForeground(Color.LIGHT_GRAY);
		qualityLabel.setOpaque(false);
		qualityLabel.setSize(50, 20);
		qualityLabel.setBorder(null);
		qualityLabel.setPreferredSize(qualityLabel.getSize());
		qualityLabel.setLocation(qualitySlider.getX(), 50);
		qualityLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel qualityMaxValueLabel = new JLabel("1");
		qualityMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		qualityMaxValueLabel.setOpaque(false);
		qualityMaxValueLabel.setSize(30, 20);
		qualityMaxValueLabel.setBorder(null);
		qualityMaxValueLabel.setPreferredSize(qualityMaxValueLabel.getSize());
		qualityMaxValueLabel.setLocation(qualitySlider.getX() + 40, 70);
		qualityMaxValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));

		JLabel quality0ValueLabel = new JLabel("0");
		quality0ValueLabel.setForeground(Color.LIGHT_GRAY);
		quality0ValueLabel.setOpaque(false);
		quality0ValueLabel.setSize(30, 20);
		quality0ValueLabel.setBorder(null);
		quality0ValueLabel.setPreferredSize(quality0ValueLabel.getSize());
		quality0ValueLabel.setLocation(qualitySlider.getX() + 40, 210);
		quality0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		onOffButton.setLocation(getWidth() - onOffButton.getWidth() - 13, 13);
		removeButton.setLocation(getWidth() - removeButton.getWidth()
				- onOffButton.getWidth() - 15, 13);

		// Ajouts des composants
		add(freqLabel);
		add(pportIn);
		add(pportFM);
		add(pportOut);
		add(coarseLabel);
		add(coarseSlider);
		add(fineLabel);
		add(fineSlider);
		add(txtFreqLabel);
		add(textFreq);
		add(qualitySlider);
		add(qualityLabel);
		add(qualityMaxValueLabel);
		add(quality0ValueLabel);

		controller.redefAdjustments();
		coarseSlider.setValue(controller.getCoarseAdjustment() * 10);
		fineSlider.setValue((int) (controller.getFineAdjustment() * 10000.0));

		// Events
		coarseSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setCoarseAdjustment((int) coarseSlider.getValue() / 10);
				freqLabel.setText(PTools.freqToString(controller.getf0()) + " Hz");
				textFreq.setText(PTools.freqToString(controller.getf0()));
			}
		});

		fineSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setFineAdjustment(fineSlider.getValue() / 10000.0);
				freqLabel.setText(PTools.freqToString(controller.getf0()) + " Hz");
				textFreq.setText(PTools.freqToString(controller.getf0()));
			}
		});

		qualitySlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.changeQFactor();
				controller.setq((int) qualitySlider.getValue()/10.0);
			}
		});
		textFreq.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent ev) {

			}

			public void keyReleased(KeyEvent ev) {

			}

			public void keyPressed(KeyEvent ev) {
				if (ev.getKeyCode() == KeyEvent.VK_ENTER) {
					Double val = 0.0;
					try {
						String valText = textFreq.getText();
						valText = valText.replace(",", ".");
						valText = valText.trim();
						val = Double.valueOf(valText);
					} catch (Exception e) {
						return;
					}
					controller.setf0(val);
					controller.redefAdjustments();
					coarseSlider.setValue(controller.getCoarseAdjustment() * 10);
					fineSlider.setValue((int) (controller.getFineAdjustment() * 10000.0));
					freqLabel.setText(PTools.freqToString(val) + " Hz");
					textFreq.setText(PTools.freqToString(val));

				}
			}

		});

	}



	public void setSlidersEnabled(boolean value) {
		coarseSlider.setEnabled(value);
		fineSlider.setEnabled(value);
		qualitySlider.setEnabled(value);

	}

	public void warn(IFilterAmplitudeObservable subject, boolean tooHigh) {	
		warnLabel.setVisible(tooHigh);		

	}

	public void hasSignal(IFilterAmplitudeObservable subject, boolean hasSignal) {


	}



}
