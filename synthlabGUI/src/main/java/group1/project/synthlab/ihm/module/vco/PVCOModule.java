package group1.project.synthlab.ihm.module.vco;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;
import group1.project.synthlab.ihm.tools.FloatTextField;
import group1.project.synthlab.ihm.tools.PTools;
import group1.project.synthlab.unitExtension.filter.filterSupervisor.IFilterAmplitudeObservable;

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

/**
 * @author Groupe 1
 * Presentation du module VCO
 */
public class PVCOModule extends PModule implements IPVCOModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected transient ICVCOModule controller;
	protected JSlider coarseSlider;
	protected JSlider fineSlider;
	protected JLabel warnLabel;
	protected final JFormattedTextField textFreq ;
	protected final JLabel freqLabel ;

	public PVCOModule(final ICVCOModule controller) {
		super(controller);
		this.controller = controller;
		// Taille et couleur d�finie dans la super classe

		// Label et onoff boutons d�j� rajout�s dans la super classe
		
		this.setSize(getWidth(), getHeight() - 40);
		this.setPreferredSize(this.getSize());

		// Label fr�quence
		freqLabel = new JLabel(controller.getf0() + " Hz");
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
		coarseSlider.setLocation(freqLabel.getX(), 110);
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

		textFreq = new FloatTextField(
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
		
		//Warn
		warnLabel = new JLabel("Amplitude out of bounds!");
		warnLabel.setForeground(Color.LIGHT_GRAY);
		warnLabel.setOpaque(false);
		warnLabel.setSize(180, 20);
		warnLabel.setForeground(Color.RED);
		warnLabel.setBorder(null);
		warnLabel.setLocation(this.getWidth() / 2 - warnLabel.getWidth() / 2, 182);
		warnLabel.setFont(new Font("Arial", 0, 15));
		warnLabel.setVisible(false);

		// Ports
		PPort pportFM = (PPort) (((ICInPort) controller.getFm())
				.getPresentation());
		pportFM.setLocation(10, getHeight() - pportFM.getHeight() -5);
		PPort pportSin = (PPort) (((ICOutPort) controller.getOutSine())
				.getPresentation());
		pportSin.setLocation( pportFM.getX() + 55, pportFM.getY());
		PPort pportTri = (PPort) (((ICOutPort) controller.getOutTriangle())
				.getPresentation());
		pportTri.setLocation(pportFM.getX() + 110,  pportFM.getY());
		PPort pportSqu = (PPort) (((ICOutPort) controller.getOutSquare())
				.getPresentation());
		pportSqu.setLocation(pportFM.getX() + 165,  pportFM.getY());
		
		PPort pportST = (PPort) (((ICOutPort) controller.getOutSawTooth())
				.getPresentation());
		pportST.setLocation(pportFM.getX() + 220,  pportFM.getY());

		// Ajouts des composants
		add(freqLabel);
		add(pportFM);
		add(pportSin);
		add(pportTri);
		add(pportSqu);
		add(pportST);
		add(coarseLabel);
		add(coarseSlider);
		add(fineLabel);
		add(fineSlider);
		add(txtFreqLabel);
		add(textFreq);
		add(warnLabel);

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

	}

	@Override
	public void updatePresentation() {
		super.updatePresentation();
		coarseSlider.setValue(controller.getCoarseAdjustment() * 10);
		fineSlider.setValue((int) (controller.getFineAdjustment() * 10000.0));
		freqLabel.setText(PTools.freqToString(controller.getf0()) + " Hz");
		textFreq.setText(PTools.freqToString(controller.getf0()));
		
	}



	@Override
	public void warnSignalIsSatured(IFilterAmplitudeObservable subject,
			boolean tooHigh) {
		warnLabel.setVisible(tooHigh);
		
	}


	@Override
	public void hasSignal(IFilterAmplitudeObservable subject, boolean hasSignal) {
		
	}



}
