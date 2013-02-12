package group1.project.synthlab.ihm.module.sequencer;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

public class PSequencer extends PModule implements IPSequencer{

	protected ICSequencer controller;
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

		this.setSize(getWidth(), getHeight() - 40);
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
		tensionSlider1 = new JSlider();
		tensionSlider1.setMaximum(10);
		tensionSlider1.setMinimum(-10);
		tensionSlider1.setOrientation(JSlider.VERTICAL);
		tensionSlider1.setValue(10);
		tensionSlider1.setSize(40, 130);
		tensionSlider1.setFont(new Font("Arial", 0, 8));
		tensionSlider1.setForeground(Color.LIGHT_GRAY);
		tensionSlider1.setPreferredSize(tensionSlider1.getSize());
		tensionSlider1.setOpaque(false);
		tensionSlider1.setFocusable(false);
		tensionSlider1.setLocation(20, 40);
		tensionSlider1.setMajorTickSpacing(5);
		tensionSlider1.setMinorTickSpacing(1);
		tensionSlider1.setPaintTicks(true);
		
		tensionSlider2 = new JSlider();
		tensionSlider2.setMaximum(10);
		tensionSlider2.setMinimum(-10);
		tensionSlider2.setOrientation(JSlider.VERTICAL);
		tensionSlider2.setSize(40, 130);
		tensionSlider2.setFont(new Font("Arial", 0, 8));
		tensionSlider2.setForeground(Color.LIGHT_GRAY);
		tensionSlider2.setPreferredSize(tensionSlider2.getSize());
		tensionSlider2.setOpaque(false);
		tensionSlider2.setFocusable(false);
		tensionSlider2.setLocation(50, 40);
		tensionSlider2.setMajorTickSpacing(5);
		tensionSlider2.setMinorTickSpacing(1);
		tensionSlider2.setPaintTicks(true);
		
		tensionSlider3 = new JSlider();
		tensionSlider3.setMaximum(10);
		tensionSlider3.setMinimum(-10);
		tensionSlider3.setOrientation(JSlider.VERTICAL);
		tensionSlider3.setSize(40, 130);
		tensionSlider3.setFont(new Font("Arial", 0, 8));
		tensionSlider3.setForeground(Color.LIGHT_GRAY);
		tensionSlider3.setPreferredSize(tensionSlider3.getSize());
		tensionSlider3.setOpaque(false);
		tensionSlider3.setFocusable(false);
		tensionSlider3.setLocation(80, 40);
		tensionSlider3.setMajorTickSpacing(5);
		tensionSlider3.setMinorTickSpacing(1);
		tensionSlider3.setPaintTicks(true);
		
		tensionSlider4 = new JSlider();
		tensionSlider4.setMaximum(10);
		tensionSlider4.setMinimum(-10);
		tensionSlider4.setOrientation(JSlider.VERTICAL);
		tensionSlider4.setSize(40, 130);
		tensionSlider4.setFont(new Font("Arial", 0, 8));
		tensionSlider4.setForeground(Color.LIGHT_GRAY);
		tensionSlider4.setPreferredSize(tensionSlider4.getSize());
		tensionSlider4.setOpaque(false);
		tensionSlider4.setFocusable(false);
		tensionSlider4.setLocation(110, 40);
		tensionSlider4.setMajorTickSpacing(5);
		tensionSlider4.setMinorTickSpacing(1);
		tensionSlider4.setPaintTicks(true);
		
		tensionSlider5 = new JSlider();
		tensionSlider5.setMaximum(10);
		tensionSlider5.setMinimum(-10);
		tensionSlider5.setOrientation(JSlider.VERTICAL);
		tensionSlider5.setSize(40, 130);
		tensionSlider5.setFont(new Font("Arial", 0, 8));
		tensionSlider5.setForeground(Color.LIGHT_GRAY);
		tensionSlider5.setPreferredSize(tensionSlider5.getSize());
		tensionSlider5.setOpaque(false);
		tensionSlider5.setFocusable(false);
		tensionSlider5.setLocation(140, 40);
		tensionSlider5.setMajorTickSpacing(5);
		tensionSlider5.setMinorTickSpacing(1);
		tensionSlider5.setPaintTicks(true);
		
		tensionSlider6 = new JSlider();
		tensionSlider6.setMaximum(10);
		tensionSlider6.setMinimum(-10);
		tensionSlider6.setOrientation(JSlider.VERTICAL);
		tensionSlider6.setSize(40, 130);
		tensionSlider6.setFont(new Font("Arial", 0, 8));
		tensionSlider6.setForeground(Color.LIGHT_GRAY);
		tensionSlider6.setPreferredSize(tensionSlider6.getSize());
		tensionSlider6.setOpaque(false);
		tensionSlider6.setFocusable(false);
		tensionSlider6.setLocation(170, 40);
		tensionSlider6.setMajorTickSpacing(5);
		tensionSlider6.setMinorTickSpacing(1);
		tensionSlider6.setPaintTicks(true);
		
		tensionSlider7 = new JSlider();
		tensionSlider7.setMaximum(10);
		tensionSlider7.setMinimum(-10);
		tensionSlider7.setOrientation(JSlider.VERTICAL);
		tensionSlider7.setSize(40, 130);
		tensionSlider7.setFont(new Font("Arial", 0, 8));
		tensionSlider7.setForeground(Color.LIGHT_GRAY);
		tensionSlider7.setPreferredSize(tensionSlider7.getSize());
		tensionSlider7.setOpaque(false);
		tensionSlider7.setFocusable(false);
		tensionSlider7.setLocation(200, 40);
		tensionSlider7.setMajorTickSpacing(5);
		tensionSlider7.setMinorTickSpacing(1);
		tensionSlider7.setPaintTicks(true);
		
		tensionSlider8 = new JSlider();
		tensionSlider8.setMaximum(10);
		tensionSlider8.setMinimum(-10);
		tensionSlider8.setOrientation(JSlider.VERTICAL);
		tensionSlider8.setSize(40, 130);
		tensionSlider8.setFont(new Font("Arial", 0, 8));
		tensionSlider8.setForeground(Color.LIGHT_GRAY);
		tensionSlider8.setPreferredSize(tensionSlider8.getSize());
		tensionSlider8.setOpaque(false);
		tensionSlider8.setFocusable(false);
		tensionSlider8.setLocation(230, 40);
		tensionSlider8.setMajorTickSpacing(5);
		tensionSlider8.setMinorTickSpacing(1);
		tensionSlider8.setPaintTicks(true);

		add(pportGate);
		add(pportOut);
		add(resetButton);
		add(tensionSlider1);
		add(tensionSlider2);
		add(tensionSlider3);
		add(tensionSlider4);
		add(tensionSlider5);
		add(tensionSlider6);
		add(tensionSlider7);
		add(tensionSlider8);
		
		tensionSlider1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.update();
//				controller.set((int) tensionSlider1.getValue()/10.0);
			}
		});
		
		tensionSlider2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.update();
//				controller.setq((int) tensionSlider2.getValue()/10.0);
			}
		});
		
		tensionSlider3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.update();
//				controller.setq((int) tensionSlider3.getValue()/10.0);
			}
		});
		
		tensionSlider4.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.update();
//				controller.setq((int) tensionSlider4.getValue()/10.0);
			}
		});
		
		tensionSlider5.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.update();
//				controller.setq((int) tensionSlider5.getValue()/10.0);
			}
		});
		
		tensionSlider6.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.update();
//				controller.setq((int) tensionSlider6.getValue()/10.0);
			}
		});
		
		tensionSlider7.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.update();
//				controller.setq((int) tensionSlider7.getValue()/10.0);
			}
		});
		
		tensionSlider8.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.update();
//				controller.setq((int) tensionSlider8.getValue()/10.0);
			}
		});
		
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
