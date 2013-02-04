package group1.project.synthlab.ihm.module.out;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.module.out.OutModule.Distribution;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class POutModule extends PModule implements IPOutModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected ICOutModule controller;
	protected JSlider attenuatorSlider;

	public POutModule(final ICOutModule controller) {
		super(controller);
		this.controller = controller;
		this.setLayout(null);
		//Taille et couleur définie dans la super classe

		//Label et onoff boutons déjà rajoutés dans la super classe

		// Ports
		PPort pportLeft = (PPort) (((ICInPort) controller.getLeftPort())
				.getPresentation());
		pportLeft.setLocation(10, 220);
		PPort pportRight = (PPort) (((ICInPort) controller.getRightPort())
				.getPresentation());
		pportRight.setLocation(70, 220);

		// Label volume
		final JLabel volumeLabel = new JLabel(controller.getAttenuation() + " dB");
		volumeLabel.setForeground(Color.LIGHT_GRAY);
		volumeLabel.setOpaque(false);
		volumeLabel.setSize(getWidth(), 20);
		volumeLabel.setHorizontalAlignment(JLabel.CENTER);
		volumeLabel.setBorder(null);
		volumeLabel.setPreferredSize(volumeLabel.getSize());
		volumeLabel.setLocation(getWidth() / 2 - volumeLabel.getWidth() / 2, 50);
		volumeLabel.setFont(new Font("Monospaced", Font.ITALIC, 26));

		// SSliders

		attenuatorSlider = new JSlider();
		attenuatorSlider.setMaximum(12);
		attenuatorSlider.setMinimum(-60);
		attenuatorSlider.setOrientation(JSlider.VERTICAL);
		attenuatorSlider.setValue((int) controller.getAttenuation());
		attenuatorSlider.setSize(40, 150);
		attenuatorSlider.setFont(new Font("Arial", 0, 8));
		attenuatorSlider.setForeground(Color.LIGHT_GRAY);
		attenuatorSlider.setPreferredSize(attenuatorSlider.getSize());
		attenuatorSlider.setOpaque(false);
		attenuatorSlider.setFocusable(false);
		attenuatorSlider.setSnapToTicks(true);
		attenuatorSlider.setLocation(getWidth()/5 - attenuatorSlider.getWidth()/5, 30);
		JLabel attenuatorLabel = new JLabel("dB");
		attenuatorLabel.setForeground(Color.LIGHT_GRAY);
		attenuatorLabel.setOpaque(false);
		attenuatorLabel.setSize(100, 20);
		attenuatorLabel.setBorder(null);
		attenuatorLabel.setPreferredSize(attenuatorLabel.getSize());
		attenuatorLabel.setLocation(attenuatorSlider.getX(), 90);
		attenuatorLabel.setFont(new Font("Arial", Font.ITALIC, 10));

		// Distribution
		final JToggleButton distributionButton = new JToggleButton("STEREO");
		distributionButton.setOpaque(false);
		distributionButton.setForeground(new Color(70, 70, 70));
		distributionButton.setSelected(false);
		distributionButton.setFont(new Font("Arial", 0, 10));
		distributionButton.setSize(50, 20);
		distributionButton.setBorder(null);
		distributionButton.setPreferredSize(distributionButton.getSize());
		distributionButton.setLocation(getWidth()/2 - distributionButton.getWidth()/2, 100);
		distributionButton.setFocusPainted(false);


		// Ajouts des composants
		add(pportLeft);
		add(pportRight);
		add(volumeLabel);
		add(attenuatorSlider);
		add(attenuatorLabel);
		add(distributionButton);

		// Events
		attenuatorSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setAttenuation(attenuatorSlider.getValue());
				volumeLabel.setText(Math.round(controller.getAttenuation()*4) + "dB");
			}
		});
		
		distributionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (distributionButton.isSelected()) {
					controller.setDistribution(Distribution.DISTRIBUTED);
					distributionButton.setText("NORMAL");
				} else {
					controller.setDistribution(Distribution.NORMAL);
					distributionButton.setText("STEREO");

				}

			}
		});



	}








}
