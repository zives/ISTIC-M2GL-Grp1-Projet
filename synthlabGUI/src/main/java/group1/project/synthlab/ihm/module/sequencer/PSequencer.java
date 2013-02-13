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

		this.setSize(getWidth()+130, getHeight()-50);
		this.setPreferredSize(this.getSize());
		
		//Ports
		PPort pportGate = (PPort) (((ICInPort) controller.getGate()).getPresentation());
		pportGate.setLocation(10, getHeight() - pportGate.getHeight() - 5);
		PPort pportOut = (PPort) (((ICOutPort) controller.getOut()).getPresentation());
		pportOut.setLocation(360, getHeight() - pportGate.getHeight() - 5);


		
		// 8 sliders de réglages de tension entre -1 et +1V
		tensionSlider = new JSlider[8];	
		int y = 60;
		int y1 = 0;
		for(int i = 0; i < 8; ++i) {
//			if (i == 4 )
//				y1 =  tensionSlider[i - 1].getHeight() + 10;
			// Sliders
			tensionSlider[i] = new JSlider();
			tensionSlider[i].setMaximum(10);
			tensionSlider[i].setMinimum(-10);
			tensionSlider[i].setOrientation(JSlider.VERTICAL);
			tensionSlider[i].setValue((int) controller.getStepValue(i+1)*10);
			tensionSlider[i].setSize(45, 90);
			tensionSlider[i].setFont(new Font("Arial", 0, 8));
			tensionSlider[i].setForeground(Color.LIGHT_GRAY);
			tensionSlider[i].setPreferredSize(tensionSlider[i].getSize());
			tensionSlider[i].setBackground(getBackground());
			tensionSlider[i].setOpaque(false);
			tensionSlider[i].setFocusable(false);
			tensionSlider[i].setBorder(null);
			tensionSlider[i].setLocation((i%8 * tensionSlider[i].getWidth() + 20) + 10, y);
			tensionSlider[i].setMajorTickSpacing(5);
			tensionSlider[i].setMinorTickSpacing(1);
			tensionSlider[i].setPaintTicks(true);
		
			//Labels slider
			Font font = new Font("Arial", Font.ITALIC, 9);		
			String text = "";
			text = String.valueOf(i+1);
			JLabel tensionLabel = new JLabel(text);
			tensionLabel.setForeground(Color.LIGHT_GRAY);
			tensionLabel.setOpaque(false);
			tensionLabel.setSize(20, 10);
			tensionLabel.setBorder(null);
			tensionLabel.setFont(font);
			tensionLabel.setPreferredSize(tensionLabel.getSize());
			tensionLabel.setLocation(tensionSlider[i].getX() + 30 - tensionLabel.getWidth() / 2, tensionSlider[i].getY() + tensionSlider[i].getHeight());
	
			JLabel tensionMaxValueLabel = new JLabel("+1");
			tensionMaxValueLabel.setForeground(Color.LIGHT_GRAY);
			tensionMaxValueLabel.setOpaque(false);
			tensionMaxValueLabel.setSize(20, 10);
			tensionMaxValueLabel.setBorder(null);
			tensionMaxValueLabel.setFont(font);
			tensionMaxValueLabel.setPreferredSize(tensionLabel.getSize());
			tensionMaxValueLabel.setLocation(tensionSlider[i].getWidth() + tensionSlider[i].getX()-1, tensionSlider[i].getY());
			
			JLabel tension0ValueLabel = new JLabel("0");
			tension0ValueLabel.setForeground(Color.LIGHT_GRAY);
			tension0ValueLabel.setOpaque(false);
			tension0ValueLabel.setSize(10, 10);
			tension0ValueLabel.setBorder(null);
			tension0ValueLabel.setFont(font);
			tension0ValueLabel.setPreferredSize(tensionLabel.getSize());
			tension0ValueLabel.setLocation(tensionSlider[i].getWidth() + tensionSlider[i].getX(), tensionSlider[i].getY()+40);
			System.out.println(tensionSlider[i].getY());
			
			JLabel tensionMinValueLabel = new JLabel("-1");
			tensionMinValueLabel.setForeground(Color.LIGHT_GRAY);
			tensionMinValueLabel.setOpaque(false);
			tensionMinValueLabel.setSize(20, 10);
			tensionMinValueLabel.setBorder(null);
			tensionMinValueLabel.setFont(font);
			tensionMinValueLabel.setPreferredSize(tensionLabel.getSize());
			tensionMinValueLabel.setLocation(tensionSlider[i].getWidth() + tensionSlider[i].getX()-1, tensionSlider[i].getY()+80);
			System.out.println(tensionSlider[i].getY());
			
	
			
			add(tensionLabel);
			add(tensionSlider[i]);
			add(tensionMaxValueLabel);
			add(tension0ValueLabel);
			add(tensionMinValueLabel);

		}
		// Bouton Reset
		JButton resetButton = new JButton("RESET");
		resetButton.setLocation(180, 180);
		resetButton.setOpaque(false);
		resetButton.setForeground(new Color(70, 70, 70));
		resetButton.setSelected(false);
		resetButton.setFont(new Font("Arial", 0, 10));
		resetButton.setSize(70, 20);
		resetButton.setBorder(null);
		resetButton.setPreferredSize(resetButton.getSize());
		resetButton.setFocusPainted(false);
		
		
		// Ajouts des composants
		add(pportGate);
		add(pportOut);
		add(resetButton);
		
		onOffButton.setLocation(getWidth() - onOffButton.getWidth() - 13, 13);
		removeButton.setLocation(getWidth() - removeButton.getWidth()
				- onOffButton.getWidth() - 15, 13);
		
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
