package group1.project.synthlab.ihm.module.micro;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;
import group1.project.synthlab.ihm.tools.PGradient;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class PMicroModule extends PModule implements IPMicroModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected ICMicroModule controller;
	protected JSlider attenuatorSlider;

	public PMicroModule(final ICMicroModule controller) {
		super(controller);
		this.controller = controller;
		this.setLayout(null);
		//Taille et couleur définie dans la super classe

		//Label et onoff boutons déjà rajoutés dans la super classe

		// Ports
		PPort pportOut = (PPort) (((ICOutPort) controller.getOutPort())
				.getPresentation());
		pportOut.setLocation(getWidth() - pportOut.getWidth() - 10, 220);

		// Label volume
		final JLabel volumeLabel = new JLabel(controller.getAttenuation() + " dB");
		volumeLabel.setForeground(Color.LIGHT_GRAY);
		volumeLabel.setOpaque(false);
		volumeLabel.setSize(150, 20);
		volumeLabel.setHorizontalTextPosition(JLabel.LEFT);
		volumeLabel.setBorder(null);
		volumeLabel.setPreferredSize(volumeLabel.getSize());
		volumeLabel.setLocation(getWidth() / 2 - volumeLabel.getWidth() / 2 + 40, 50);
		volumeLabel.setFont(new Font("Monospaced", Font.ITALIC, 26));

		// Sliders
		attenuatorSlider = new JSlider();
		attenuatorSlider.setMaximum(360);
		attenuatorSlider.setMinimum(-300);
		attenuatorSlider.setOrientation(JSlider.VERTICAL);
		attenuatorSlider.setValue((int) controller.getAttenuation()*10);
		attenuatorSlider.setSize(40, 150);
		attenuatorSlider.setFont(new Font("Arial", 0, 8));
		attenuatorSlider.setForeground(Color.LIGHT_GRAY);
		attenuatorSlider.setPreferredSize(attenuatorSlider.getSize());
		attenuatorSlider.setBackground(getBackground());
		attenuatorSlider.setOpaque(false);
		attenuatorSlider.setFocusable(false);
		attenuatorSlider.setBorder(null);
		attenuatorSlider.setLocation(35, 50);
		attenuatorSlider.setMajorTickSpacing(100);
		attenuatorSlider.setMinorTickSpacing(25);
		attenuatorSlider.setPaintTicks(true);
		
		
		//Labels slider
		JLabel attenuatorMaxValueLabel = new JLabel("+24");
		attenuatorMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		attenuatorMaxValueLabel.setOpaque(false);
		attenuatorMaxValueLabel.setSize(30, 20);
		attenuatorMaxValueLabel.setBorder(null);
		attenuatorMaxValueLabel.setPreferredSize(attenuatorMaxValueLabel.getSize());
		attenuatorMaxValueLabel.setLocation(attenuatorSlider.getX() + 40, 50);
		attenuatorMaxValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel attenuator0ValueLabel = new JLabel("0");
		attenuator0ValueLabel.setForeground(Color.LIGHT_GRAY);
		attenuator0ValueLabel.setOpaque(false);
		attenuator0ValueLabel.setSize(30, 20);
		attenuator0ValueLabel.setBorder(null);
		attenuator0ValueLabel.setPreferredSize(attenuator0ValueLabel.getSize());
		attenuator0ValueLabel.setLocation(attenuatorSlider.getX() + 40, 120);
		attenuator0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel attenuatorMinValueLabel = new JLabel(String.valueOf(attenuatorSlider.getMinimum() / 10));
		attenuatorMinValueLabel.setForeground(Color.LIGHT_GRAY);
		attenuatorMinValueLabel.setOpaque(false);
		attenuatorMinValueLabel.setSize(30, 20);
		attenuatorMinValueLabel.setBorder(null);
		attenuatorMinValueLabel.setPreferredSize(attenuatorMinValueLabel.getSize());
		attenuatorMinValueLabel.setLocation(attenuatorSlider.getX() + 40, 185);
		attenuatorMinValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));


		PGradient gradient = new PGradient("VERTICAL", new Color(90,60,60), new Color(100,100,100), 0.28f);
		gradient.setSize(10, 140);
		gradient.setLocation(25, 55);
				  
		// Ajouts des composants
		add(pportOut);
		add(volumeLabel);
		add(attenuatorSlider);
		add(attenuatorMaxValueLabel);
		add(attenuator0ValueLabel);
		add(attenuatorMinValueLabel);
		add(gradient);

		this.repaint();

		// Events
		attenuatorSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setAttenuation(attenuatorSlider.getValue()/10.0);
				volumeLabel.setText(Math.round(controller.getAttenuation() * 10) / 10.0 + " dB");
			}
		});

	
	}

}
