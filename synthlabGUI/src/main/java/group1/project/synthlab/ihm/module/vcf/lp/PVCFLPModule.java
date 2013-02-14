package group1.project.synthlab.ihm.module.vcf.lp;

import group1.project.synthlab.ihm.module.vcf.PVCFModule;
import group1.project.synthlab.ihm.tools.PTools;
import group1.project.synthlab.unitExtension.filter.filterSupervisor.IFilterAmplitudeObservable;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PVCFLPModule extends PVCFModule implements IPVCFLPModule {

	protected transient ICVCFLPModule controller;

	protected JSlider coarseSlider;
	protected JSlider fineSlider;
	protected JLabel warnLabel;
	protected JSlider qualitySlider;

	protected JLabel qualityValue;

	public PVCFLPModule(final ICVCFLPModule controller) {
		super(controller);
		this.controller = controller;
		// Taille et couleur définie dans la super classe

		// Label et onoff boutons déjà rajoutés dans la super classe
		
		// On recupere l'ihm du VCF-HP et on rajoute les composants pour la resonnance.

		this.setSize(getWidth() + 30, getHeight());
		this.setPreferredSize(this.getSize());

		// Sliders
		qualitySlider = new JSlider();
		qualitySlider.setMaximum(100);
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
		qualitySlider.setLocation(260, 75);
		qualitySlider.setMajorTickSpacing(50);
		qualitySlider.setMinorTickSpacing(10);
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
		
		qualityValue = new JLabel(controller.getq() + "");
		qualityValue.setForeground(Color.LIGHT_GRAY);
		qualityValue.setOpaque(false);
		qualityValue.setSize(qualitySlider.getX(), qualitySlider.getY());
		qualityValue.setHorizontalAlignment(JLabel.LEFT);
		qualityValue.setBorder(null);
		qualityValue.setPreferredSize(qualityValue.getSize());
		qualityValue.setLocation(qualitySlider.getX()+10, 200);
		qualityValue.setFont(new Font("Monospaced", Font.ITALIC, 12));
		
		JLabel qualityMaxValueLabel = new JLabel("10");
		qualityMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		qualityMaxValueLabel.setOpaque(false);
		qualityMaxValueLabel.setSize(30, 20);
		qualityMaxValueLabel.setBorder(null);
		qualityMaxValueLabel.setPreferredSize(qualityMaxValueLabel.getSize());
		qualityMaxValueLabel.setLocation(qualitySlider.getX() + 40, 65);
		qualityMaxValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));

		JLabel quality0ValueLabel = new JLabel("0");
		quality0ValueLabel.setForeground(Color.LIGHT_GRAY);
		quality0ValueLabel.setOpaque(false);
		quality0ValueLabel.setSize(30, 20);
		quality0ValueLabel.setBorder(null);
		quality0ValueLabel.setPreferredSize(quality0ValueLabel.getSize());
		quality0ValueLabel.setLocation(qualitySlider.getX() + 40, 205);
		quality0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		onOffButton.setLocation(getWidth() - onOffButton.getWidth() - 13, 13);
		removeButton.setLocation(getWidth() - removeButton.getWidth()
				- onOffButton.getWidth() - 15, 13);

		// Ajouts des composants
		add(qualitySlider);
		add(qualityValue);
		add(qualityLabel);
		add(qualityMaxValueLabel);
		add(quality0ValueLabel);

		qualitySlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.changeQFactor();
				controller.setq((int) qualitySlider.getValue()/10.0);
				qualityValue.setText(PTools.freqToString(controller.getq()));
			}
		});

	}

	public void setSlidersEnabled(boolean value) {
		qualitySlider.setEnabled(value);

	}

	public void warn(IFilterAmplitudeObservable subject, boolean tooHigh) {	
		warnLabel.setVisible(tooHigh);		

	}

	public void hasSignal(IFilterAmplitudeObservable subject, boolean hasSignal) {


	}


	@Override
	public void updatePresentation() {
		super.updatePresentation();
		qualitySlider.setValue((int) (controller.getq() * 10));
		qualityValue.setText(PTools.freqToString(controller.getq()));
	}

}
