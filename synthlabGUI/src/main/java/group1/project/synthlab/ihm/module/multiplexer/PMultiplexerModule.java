package group1.project.synthlab.ihm.module.multiplexer;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PMultiplexerModule extends PModule implements IPMultiplexerModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected transient ICMultiplexerModule controller;
	protected JSlider pIn1Slider;
	protected JSlider pIn2Slider;
	protected JSlider pIn3Slider;
	protected JSlider pIn4Slider;
	protected JLabel volumeLabel1;
	protected JLabel volumeLabel2;
	protected JLabel volumeLabel3;
	protected JLabel volumeLabel4;

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
		pIn1Slider = new JSlider();
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

		pIn2Slider = new JSlider();
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

		pIn3Slider = new JSlider();
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

		pIn4Slider = new JSlider();
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
		
		// Label volume
		volumeLabel1 = new JLabel("0 dB" ,JLabel.CENTER /*controller.getAttenuation() + " dB"*/);
		volumeLabel1.setForeground(Color.LIGHT_GRAY);
		volumeLabel1.setOpaque(false);
		volumeLabel1.setSize(60, 30);
		volumeLabel1.setBorder(null);
		volumeLabel1.setPreferredSize(volumeLabel1.getSize());
		volumeLabel1.setLocation(pIn1Slider.getX() + pIn1Slider.getWidth() / 2 - volumeLabel1.getWidth() / 2, pIn1Slider.getY() + 22);
		volumeLabel1.setFont(new Font("Monospaced", Font.ITALIC, 12));
		
		volumeLabel2 = new JLabel("0 dB" ,JLabel.CENTER /*controller.getAttenuation() + " dB"*/);
		volumeLabel2.setForeground(Color.LIGHT_GRAY);
		volumeLabel2.setOpaque(false);
		volumeLabel2.setSize(60, 30);
		volumeLabel2.setBorder(null);
		volumeLabel2.setPreferredSize(volumeLabel2.getSize());
		volumeLabel2.setLocation(pIn2Slider.getX() + pIn2Slider.getWidth() / 2 - volumeLabel2.getWidth() / 2, pIn2Slider.getY() + 22);
		volumeLabel2.setFont(new Font("Monospaced", Font.ITALIC, 12));
		
		
		volumeLabel3 = new JLabel("0 dB" ,JLabel.CENTER /*controller.getAttenuation() + " dB"*/);
		volumeLabel3.setForeground(Color.LIGHT_GRAY);
		volumeLabel3.setOpaque(false);
		volumeLabel3.setSize(60, 30);
		volumeLabel3.setBorder(null);
		volumeLabel3.setPreferredSize(volumeLabel3.getSize());
		volumeLabel3.setLocation(pIn3Slider.getX() + pIn3Slider.getWidth() / 2 - volumeLabel3.getWidth() / 2, pIn3Slider.getY() + 22);
		volumeLabel3.setFont(new Font("Monospaced", Font.ITALIC, 12));
		
		volumeLabel4 = new JLabel("0 dB" ,JLabel.CENTER /*controller.getAttenuation() + " dB"*/);
		volumeLabel4.setForeground(Color.LIGHT_GRAY);
		volumeLabel4.setOpaque(false);
		volumeLabel4.setSize(60, 30);
		volumeLabel4.setBorder(null);
		volumeLabel4.setPreferredSize(volumeLabel4.getSize());
		volumeLabel4.setLocation(pIn4Slider.getX() + pIn4Slider.getWidth() / 2 - volumeLabel4.getWidth() / 2, pIn4Slider.getY() + 22);
		volumeLabel4.setFont(new Font("Monospaced", Font.ITALIC, 12));
		
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
		add(volumeLabel1);
		add(volumeLabel2);
		add(volumeLabel3);
		add(volumeLabel4);

		setSize(getWidth(), getHeight() - 70);
		setPreferredSize(getSize());
		

		pIn1Slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				controller.setAttenuation(pIn1Slider.getValue() / 10.0, 0);
				volumeLabel1.setText(pIn1Slider.getValue() / 10.0 + " dB");
			}
		});
		pIn2Slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				controller.setAttenuation(pIn2Slider.getValue() / 10.0, 1);
				volumeLabel2.setText(pIn2Slider.getValue() / 10.0 + " dB");

			}
		});
		pIn3Slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				controller.setAttenuation(pIn3Slider.getValue() / 10.0, 2);
				volumeLabel3.setText(pIn3Slider.getValue() / 10.0 + " dB");

			}
		});
		pIn4Slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				controller.setAttenuation(pIn4Slider.getValue() / 10.0, 3);
				volumeLabel4.setText(pIn4Slider.getValue() / 10.0 + " dB");

			}
		});
	}
	
	@Override
	public void updatePresentation() {
		super.updatePresentation();
		pIn1Slider.setValue((int)(controller.getAttenuation(0) * 10));
		pIn2Slider.setValue((int)(controller.getAttenuation(1) * 10));
		pIn3Slider.setValue((int)(controller.getAttenuation(2) * 10));
		pIn4Slider.setValue((int)(controller.getAttenuation(3) * 10));
		volumeLabel1.setText(pIn1Slider.getValue() / 10.0 + " dB");
		volumeLabel2.setText(pIn2Slider.getValue() / 10.0 + " dB");
		volumeLabel3.setText(pIn3Slider.getValue() / 10.0 + " dB");
		volumeLabel4.setText(pIn4Slider.getValue() / 10.0 + " dB");

	}

}
