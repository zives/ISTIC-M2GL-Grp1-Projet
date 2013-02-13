package group1.project.synthlab.ihm.module.sequencer;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

public class PSequencer extends PModule implements IPSequencer{

	protected ICSequencer controller;
	protected JSlider[] tensionSlider;
	protected JSlider tensionSlider1;
	protected JSlider tensionSlider2;
	protected JSlider tensionSlider3;
	protected JSlider tensionSlider4;
	protected JSlider tensionSlider5;
	protected JSlider tensionSlider6;
	protected JSlider tensionSlider7;
	protected JSlider tensionSlider8;
	

	public PSequencer(final ICSequencer controller) {
		super(controller);
		this.controller = controller;
		this.setLayout(null);
		// Taille et couleur définie dans la super classe

		// Label et onoff boutons déjà rajoutés dans la super classe

		this.setSize(getWidth(), getHeight());
		this.setPreferredSize(this.getSize());
		
		//Ports
		PPort pportGate = (PPort) (((ICInPort) controller.getGate()).getPresentation());
		pportGate.setLocation(10, getHeight() - pportGate.getHeight() - 5);
		PPort pportOut = (PPort) (((ICOutPort) controller.getOut()).getPresentation());
		pportOut.setLocation(240, getHeight() - pportGate.getHeight() - 5);

		// Bouton Reset
		JButton resetButton = new JButton("RESET");
		resetButton.setBackground(new Color(150, 150, 150));
		resetButton.setForeground(Color.BLACK);
		
		// 8 sliders de réglages de tension entre -1 et +1V
		tensionSlider = new JSlider[8];	
		int y = 40;
		int y1 = 0;
		for(int i = 0; i < 8; ++i) {
			if (i == 4 )
				y1 =  tensionSlider[i - 1].getHeight() + 10;
			// Sliders
			tensionSlider[i] = new JSlider();
			tensionSlider[i].setMaximum(10);
			tensionSlider[i].setMinimum(-10);
			tensionSlider[i].setOrientation(JSlider.VERTICAL);
//			tensionSlider[i].setValue((int) controller.getAttenuation(i)*10);
			tensionSlider[i].setSize(40, 90);
			tensionSlider[i].setFont(new Font("Arial", 0, 8));
			tensionSlider[i].setForeground(Color.LIGHT_GRAY);
			tensionSlider[i].setPreferredSize(tensionSlider[i].getSize());
			tensionSlider[i].setBackground(getBackground());
			tensionSlider[i].setOpaque(false);
			tensionSlider[i].setFocusable(false);
			tensionSlider[i].setBorder(null);
			tensionSlider[i].setLocation(i%5 * (tensionSlider[i].getWidth() + 20) + 10, y + y1);
			tensionSlider[i].setMajorTickSpacing(5);
			tensionSlider[i].setMinorTickSpacing(1);
			tensionSlider[i].setPaintTicks(true);
		
			//Labels slider
			Font font = new Font("Arial", Font.ITALIC, 9);		
			String text = "";
			text = String.valueOf(i+1);
			if (text.length() > 8)
				text = text.substring(0, 8);
			JLabel tensionLabel = new JLabel(text);
			tensionLabel.setForeground(Color.LIGHT_GRAY);
			tensionLabel.setOpaque(false);
			tensionLabel.setSize(20, 10);
			tensionLabel.setBorder(null);
			tensionLabel.setFont(font);
			tensionLabel.setPreferredSize(tensionLabel.getSize());
			tensionLabel.setLocation(tensionSlider[i].getX() + 20 - tensionLabel.getWidth() / 2, tensionSlider[i].getY() + tensionSlider[i].getHeight() - 4);
	
			String maxValue = "+1";
			if (text.length() > 8)
				text = text.substring(0, 8);
			JLabel tensionMaxValueLabel = new JLabel(maxValue);
			tensionMaxValueLabel.setForeground(Color.LIGHT_GRAY);
			tensionMaxValueLabel.setOpaque(false);
			tensionMaxValueLabel.setSize(20, 10);
			tensionMaxValueLabel.setBorder(null);
			tensionMaxValueLabel.setFont(font);
			tensionMaxValueLabel.setPreferredSize(tensionLabel.getSize());
			tensionMaxValueLabel.setLocation(tensionSlider[i].getX() + 40, tensionSlider[i].getY());
			
			String value = "0";
			if (text.length() > 8)
				text = text.substring(0, 8);
			JLabel tension0ValueLabel = new JLabel(value);
			tension0ValueLabel.setForeground(Color.LIGHT_GRAY);
			tension0ValueLabel.setOpaque(false);
			tension0ValueLabel.setSize(20, 10);
			tension0ValueLabel.setBorder(null);
			tension0ValueLabel.setFont(font);
			tension0ValueLabel.setPreferredSize(tensionLabel.getSize());
			tension0ValueLabel.setLocation(tensionSlider[i].getX() + 40, tensionSlider[i].getY()+40);
			System.out.println(tensionSlider[i].getY());
			
			String minValue = "-1";
			if (text.length() > 8)
				text = text.substring(0, 8);
			JLabel tensionMinValueLabel = new JLabel(minValue);
			tensionMinValueLabel.setForeground(Color.LIGHT_GRAY);
			tensionMinValueLabel.setOpaque(false);
			tensionMinValueLabel.setSize(20, 10);
			tensionMinValueLabel.setBorder(null);
			tensionMinValueLabel.setFont(font);
			tensionMinValueLabel.setPreferredSize(tensionLabel.getSize());
			tensionMinValueLabel.setLocation(tensionSlider[i].getX() + 40, tensionSlider[i].getY()+80);
			System.out.println(tensionSlider[i].getY());
			
			add(tensionLabel);
			add(tensionSlider[i]);
			add(tensionMaxValueLabel);
			add(tension0ValueLabel);
			add(tensionMinValueLabel);
		}

		
		// Ajouts des composants
		add(pportGate);
		add(pportOut);
		add(resetButton);
		
		this.repaint();

		
		// Events
		for(int i = 0; i < 8; ++i) {
			final int ii = i+1;
		tensionSlider[i].addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setStepValue(ii, tensionSlider[ii-1].getValue()/10.0);
			}
		
		});
		}
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				controller.resetSteps();
			}
		});
	
	}

	@Override
	public void setSlidersEnabled(boolean value) {
		// TODO Auto-generated method stub

	}

}
