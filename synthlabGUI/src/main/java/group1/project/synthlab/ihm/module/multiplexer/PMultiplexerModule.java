package group1.project.synthlab.ihm.module.multiplexer;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PMultiplexerModule extends PModule implements IPMultiplexerModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected ICMultiplexerModule controller;
	protected JSlider attenuatorSlider;

	public PMultiplexerModule(final ICMultiplexerModule controller) {
		super(controller);
		this.controller = controller;
		this.setLayout(null);
		// Taille et couleur définie dans la super classe

		// Label et onoff boutons déjà rajoutés dans la super classe

		// Ports
		PPort pportIn1 = (PPort) (((ICInPort) controller.getInPort(0))
				.getPresentation());
		pportIn1.setLocation(30, 120);
		PPort pportIn2 = (PPort) (((ICInPort) controller.getInPort(1))
				.getPresentation());
		pportIn2.setLocation(90, 120);
		PPort pportIn3 = (PPort) (((ICInPort) controller.getInPort(2))
				.getPresentation());
		pportIn3.setLocation(150, 120);
		PPort pportIn4 = (PPort) (((ICInPort) controller.getInPort(3))
				.getPresentation());
		pportIn4.setLocation(210, 120);

		PPort pportOut1 = (PPort) (((ICOutPort) controller.getOutPort(0))
				.getPresentation());
		pportOut1.setLocation(30, 50);
		PPort pportOut2 = (PPort) (((ICOutPort) controller.getOutPort(1))
				.getPresentation());
		pportOut2.setLocation(90, 50);
		PPort pportOut3 = (PPort) (((ICOutPort) controller.getOutPort(2))
				.getPresentation());
		pportOut3.setLocation(150, 50);
		PPort pportOut4 = (PPort) (((ICOutPort) controller.getOutPort(3))
				.getPresentation());
		pportOut4.setLocation(210, 50);

		// Sliders
		final JSlider pIn1Slider = new JSlider();
		pIn1Slider.setMaximum(120);
		pIn1Slider.setMinimum(-300);
		pIn1Slider.setOrientation(JSlider.HORIZONTAL);
		pIn1Slider.setValue(0);
		pIn1Slider.setSize(pportIn1.getWidth(), 30);
		pIn1Slider.setFont(new Font("Arial", 0, 8));
		pIn1Slider.setForeground(Color.LIGHT_GRAY);
		pIn1Slider.setPreferredSize(pIn1Slider.getSize());
		pIn1Slider.setOpaque(false);
		pIn1Slider.setFocusable(false);
		pIn1Slider.setBorder(null);
		pIn1Slider.setLocation(pportIn1.getX(), 170);
		pIn1Slider.setMajorTickSpacing(100);
		pIn1Slider.setMinorTickSpacing(100);
		pIn1Slider.setPaintTicks(true);

		final JSlider pIn2Slider = new JSlider();
		pIn2Slider.setMaximum(120);
		pIn2Slider.setMinimum(-300);
		pIn2Slider.setOrientation(JSlider.HORIZONTAL);
		pIn2Slider.setValue(0);
		pIn2Slider.setSize(pportIn1.getWidth(), 30);
		pIn2Slider.setFont(new Font("Arial", 0, 8));
		pIn2Slider.setForeground(Color.LIGHT_GRAY);
		pIn2Slider.setPreferredSize(pIn2Slider.getSize());
		pIn2Slider.setOpaque(false);
		pIn2Slider.setFocusable(false);
		pIn2Slider.setBorder(null);
		pIn2Slider.setLocation(pportIn2.getX(), 170);
		pIn2Slider.setMajorTickSpacing(100);
		pIn2Slider.setMinorTickSpacing(100);
		pIn2Slider.setPaintTicks(true);

		final JSlider pIn3Slider = new JSlider();
		pIn3Slider.setMaximum(120);
		pIn3Slider.setMinimum(-300);
		pIn3Slider.setOrientation(JSlider.HORIZONTAL);
		pIn3Slider.setValue(0);
		pIn3Slider.setSize(pportIn1.getWidth(), 30);
		pIn3Slider.setFont(new Font("Arial", 0, 8));
		pIn3Slider.setForeground(Color.LIGHT_GRAY);
		pIn3Slider.setPreferredSize(pIn3Slider.getSize());
		pIn3Slider.setOpaque(false);
		pIn3Slider.setFocusable(false);
		pIn3Slider.setBorder(null);
		pIn3Slider.setLocation(pportIn3.getX(), 170);
		pIn3Slider.setMajorTickSpacing(100);
		pIn3Slider.setMinorTickSpacing(100);
		pIn3Slider.setPaintTicks(true);

		final JSlider pIn4Slider = new JSlider();
		pIn4Slider.setMaximum(120);
		pIn4Slider.setMinimum(-300);
		pIn4Slider.setOrientation(JSlider.HORIZONTAL);
		pIn4Slider.setValue(0);
		pIn4Slider.setSize(pportIn1.getWidth(), 30);
		pIn4Slider.setFont(new Font("Arial", 0, 8));
		pIn4Slider.setForeground(Color.LIGHT_GRAY);
		pIn4Slider.setPreferredSize(pIn4Slider.getSize());
		pIn4Slider.setOpaque(false);
		pIn4Slider.setFocusable(false);
		pIn4Slider.setBorder(null);
		pIn4Slider.setLocation(pportIn4.getX(), 170);
		pIn4Slider.setMajorTickSpacing(100);
		pIn4Slider.setMinorTickSpacing(100);
		pIn4Slider.setPaintTicks(true);

		add(pportIn1);
		add(pportIn2);
		add(pportIn3);
		add(pportIn4);
		add(pportOut1);
		add(pportOut2);
		add(pportOut3);
		add(pportOut4);
		add(pIn1Slider);
		add(pIn2Slider);
		add(pIn3Slider);
		add(pIn4Slider);

		setSize(getWidth(), getHeight() - 90);
		setPreferredSize(getSize());
		onOffButton.setSelected(true);
		onOffButton.setText("Off");

		pIn1Slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				controller.setAttenuation(pIn1Slider.getValue() / 10.0, 0);

			}
		});
		pIn2Slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				controller.setAttenuation(pIn2Slider.getValue() / 10.0, 1);

			}
		});
		pIn3Slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				controller.setAttenuation(pIn3Slider.getValue() / 10.0, 2);

			}
		});
		pIn4Slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				controller.setAttenuation(pIn4Slider.getValue() / 10.0, 3);

			}
		});
	}

}
