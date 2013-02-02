package group1.project.synthlab.ihm.module.vco;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SliderUI;

public class PVCOModule extends PModule implements IPVCOModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected ICVCOModule controller;
	protected JSlider coarseSlider;
	protected JSlider fineSlider;
	public PVCOModule(final ICVCOModule controller) {
		super(controller);
		this.controller = controller;
		// Taille et couleur définie dans la super classe

		// Label et onoff boutons déjà rajoutés dans la super classe

		// Label fréquence
		final JLabel freqLabel = new JLabel(controller.getf0() + " Hz");
		freqLabel.setForeground(Color.LIGHT_GRAY);
		freqLabel.setOpaque(false);
		freqLabel.setSize(getWidth(), 20);
		freqLabel.setHorizontalAlignment(JLabel.CENTER);
		freqLabel.setBorder(null);
		freqLabel.setPreferredSize(freqLabel.getSize());
		freqLabel.setLocation(getWidth() / 2 - freqLabel.getWidth() / 2, 50);
		freqLabel.setFont(new Font("Monospaced", Font.ITALIC, 26));

		// Sliders

		coarseSlider = new JSlider();
		coarseSlider.setMaximum(900);
		coarseSlider.setMinimum(0);
		coarseSlider.setValue(controller.getCoarseAdjustment() * 100);
		coarseSlider.setOrientation(JSlider.HORIZONTAL);
		coarseSlider.setSize(200, 40);
		coarseSlider.setMajorTickSpacing(100);
		coarseSlider.setPaintTicks(true);
		coarseSlider.setFont(new Font("Arial", 0, 8));
		coarseSlider.setForeground(Color.LIGHT_GRAY);
		coarseSlider.setPreferredSize(coarseSlider.getSize());
		coarseSlider.setOpaque(false);
		coarseSlider.setFocusable(false);
		coarseSlider.setSnapToTicks(true);
		coarseSlider.setLocation(getWidth() / 2 - coarseSlider.getWidth() / 2,
				110);
		JLabel coarseLabel = new JLabel("coarse adjustement");
		coarseLabel.setForeground(Color.LIGHT_GRAY);
		coarseLabel.setOpaque(false);
		coarseLabel.setSize(100, 20);
		coarseLabel.setBorder(null);
		coarseLabel.setPreferredSize(freqLabel.getSize());
		coarseLabel.setLocation(coarseSlider.getX(), 90);
		coarseLabel.setFont(new Font("Arial", Font.ITALIC, 10));

		fineSlider  = new JSlider();
		fineSlider.setMaximum(1000);
		fineSlider.setMinimum(0);
		fineSlider.setValue((int) (controller.getFineAdjustment() * 1000));
		fineSlider.setOrientation(JSlider.HORIZONTAL);
		fineSlider.setSize(200, 40);
		fineSlider.setPaintTicks(true);
		fineSlider.setMajorTickSpacing(100);
		// fineSlider.setMinorTickSpacing(10);
		fineSlider.setFont(new Font("Arial", 0, 8));
		fineSlider.setForeground(Color.LIGHT_GRAY);
		fineSlider.setPreferredSize(fineSlider.getSize());
		fineSlider.setOpaque(false);
		fineSlider.setFocusable(false);
		fineSlider.setLocation(getWidth() / 2 - fineSlider.getWidth() / 2, 180);
		final JLabel fineLabel = new JLabel("fine adjustement");
		fineLabel.setForeground(Color.LIGHT_GRAY);
		fineLabel.setOpaque(false);
		fineLabel.setSize(100, 20);
		fineLabel.setBorder(null);
		fineLabel.setPreferredSize(freqLabel.getSize());
		fineLabel.setLocation(coarseSlider.getX(), 160);
		fineLabel.setFont(new Font("Arial", Font.ITALIC, 10));

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

		// Ajouts des composants
		add(freqLabel);
		add(pportFM);
		add(pportSin);
		add(pportTri);
		add(pportSqu);
		add(coarseLabel);
		add(coarseSlider);
		add(fineLabel);
		add(fineSlider);

		// Events
		coarseSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setCoarseAdjustment(coarseSlider.getValue() / 100);
				freqLabel.setText(Math.round(controller.getf0() * 100) / 100
						+ " Hz");
			}
		});

		fineSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setFineAdjustment(fineSlider.getValue() / 1000.0);
				freqLabel.setText(Math.round(controller.getf0() * 100) / 100
						+ " Hz");
			}
		});
	}

	public void setSlidersEnabled(boolean value) {
		coarseSlider.setEnabled(value);
		fineSlider.setEnabled(value);

	}

	public void reregisterCables() {
		// Enregitrement du module auprès des cables
		register((IPModuleObserver) controller.getFm().getCable());
		register((IPModuleObserver) controller.getOutSine().getCable());
		register((IPModuleObserver) controller.getOutTriangle().getCable());
		register((IPModuleObserver) controller.getOutSquare().getCable());

	}

}
