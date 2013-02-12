package group1.project.synthlab.ihm.module.out;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.tools.PGradient;
import group1.project.synthlab.module.out.OutModule.Distribution;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SliderUI;


public class POutModule extends PModule implements IPOutModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected ICOutModule controller;
	protected JSlider attenuatorSlider;
	protected final JToggleButton distributionButton;

	public POutModule(final ICOutModule controller) {
		super(controller);
		this.controller = controller;
		this.setLayout(null);
		//Taille et couleur définie dans la super classe

		//Label et onoff boutons déjà rajoutés dans la super classe
		
		this.setSize(getWidth(), getHeight() - 70);
		this.setPreferredSize(this.getSize());

		// Ports
		PPort pportLeft = (PPort) (((ICInPort) controller.getLeftPort())
				.getPresentation());
		pportLeft.setLocation(getWidth() - pportLeft.getWidth() - 10, getHeight() - pportLeft.getHeight() -5);
		PPort pportRight = (PPort) (((ICInPort) controller.getRightPort())
				.getPresentation());
		pportRight.setLocation(pportLeft.getX() - 60, pportLeft.getY());

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
		attenuatorSlider.setMaximum(120);
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
		JLabel attenuatorMaxValueLabel = new JLabel("+12");
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
		attenuator0ValueLabel.setLocation(attenuatorSlider.getX() + 40, 85);
		attenuator0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel attenuatorMinValueLabel = new JLabel(String.valueOf(attenuatorSlider.getMinimum() / 10));
		attenuatorMinValueLabel.setForeground(Color.LIGHT_GRAY);
		attenuatorMinValueLabel.setOpaque(false);
		attenuatorMinValueLabel.setSize(30, 20);
		attenuatorMinValueLabel.setBorder(null);
		attenuatorMinValueLabel.setPreferredSize(attenuatorMinValueLabel.getSize());
		attenuatorMinValueLabel.setLocation(attenuatorSlider.getX() + 40, 185);
		attenuatorMinValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));

		// Distribution
		distributionButton = new JToggleButton("DISTRIBUTED");
		distributionButton.setOpaque(false);
		distributionButton.setForeground(new Color(70, 70, 70));
		distributionButton.setSelected(false);
		distributionButton.setFont(new Font("Arial", 0, 10));
		distributionButton.setSize(100, 20);
		distributionButton.setBorder(null);
		distributionButton.setPreferredSize(distributionButton.getSize());
		distributionButton.setLocation(volumeLabel.getX(), 100);
		distributionButton.setFocusPainted(false);

		PGradient gradient = new PGradient("VERTICAL", new Color(90,60,60), new Color(100,100,100), 0.28f);
		gradient.setSize(10, 140);
		gradient.setLocation(25, 55);
				  
		// Ajouts des composants
		add(pportLeft);
		add(pportRight);
		add(volumeLabel);
		add(attenuatorSlider);
		add(attenuatorMaxValueLabel);
		add(attenuator0ValueLabel);
		add(attenuatorMinValueLabel);
		add(distributionButton);
		add(gradient);
		
		

		this.repaint();

		// Events
		attenuatorSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setAttenuation(attenuatorSlider.getValue()/10.0);
				volumeLabel.setText(Math.round(controller.getAttenuation() * 10) / 10.0 + " dB");
			}
		});

		distributionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				if (distributionButton.isSelected()) {
					controller.setDistribution(Distribution.DISTRIBUTED);
					distributionButton.setText("NORMAL");
				} else {
					controller.setDistribution(Distribution.NORMAL);
					distributionButton.setText("DISTRIBUTED");
				}

			}
		});
	}

	@Override
	public void updateSlider() {
		// TODO Auto-generated method stub

		attenuatorSlider.setValue((int) controller.getAttenuation()*10);
	}

	@Override
	public void updateDistribution(){
		// TODO Auto-generated method stub
		if(controller.getDistribution() == Distribution.NORMAL){
			distributionButton.setText("DISTRIBUTED");
			distributionButton.setSelected(false);
		}else{
			distributionButton.setText("NORMAL");
			distributionButton.setSelected(true);
		}
	}


}
