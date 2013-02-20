package group1.project.synthlab.ihm.module.sequencer;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Groupe 1
 * Presnetation du module sequenceur
 */
public class PSequencerModule extends PModule implements IPSequencerModule{

	protected transient ICSequencerModule controller;

	private static final long serialVersionUID = 4031580764495351076L;

	protected JSlider[] tensionSlider;
	protected JSlider tensionSlider1;
	protected JSlider tensionSlider2;
	protected JSlider tensionSlider3;
	protected JSlider tensionSlider4;
	protected JSlider tensionSlider5;
	protected JSlider tensionSlider6;
	protected JSlider tensionSlider7;
	protected JSlider tensionSlider8;
	

	public PSequencerModule(final ICSequencerModule controller) {
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
		int y = 50;
		for(int i = 0; i < 8; ++i) {
			// Sliders
			tensionSlider[i] = new JSlider();
			tensionSlider[i].setMaximum(1000);
			tensionSlider[i].setMinimum(-1000);
			tensionSlider[i].setOrientation(JSlider.VERTICAL);
			tensionSlider[i].setValue((int) (controller.getStepValue(i+1)*1000));
			tensionSlider[i].setSize(45, 121);
			tensionSlider[i].setFont(new Font("Arial", 0, 8));
			tensionSlider[i].setForeground(Color.LIGHT_GRAY);
			tensionSlider[i].setPreferredSize(tensionSlider[i].getSize());
			tensionSlider[i].setBackground(getBackground());
			tensionSlider[i].setOpaque(false);
			tensionSlider[i].setFocusable(false);
			tensionSlider[i].setBorder(null);
			tensionSlider[i].setLocation(i%8 * (tensionSlider[i].getWidth() + 5) + 10, y);
			tensionSlider[i].setMajorTickSpacing(500);
			tensionSlider[i].setMinorTickSpacing(100);
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
			tension0ValueLabel.setLocation(tensionSlider[i].getWidth() + tensionSlider[i].getX(), tensionSlider[i].getY()+tensionSlider[i].getHeight()/2);
						
			JLabel tensionMinValueLabel = new JLabel("-1");
			tensionMinValueLabel.setForeground(Color.LIGHT_GRAY);
			tensionMinValueLabel.setOpaque(false);
			tensionMinValueLabel.setSize(20, 10);
			tensionMinValueLabel.setBorder(null);
			tensionMinValueLabel.setFont(font);
			tensionMinValueLabel.setPreferredSize(tensionLabel.getSize());
			tensionMinValueLabel.setLocation(tensionSlider[i].getWidth() + tensionSlider[i].getX()-1, tensionSlider[i].getY()+tensionSlider[i].getHeight()-10);
			
			add(tensionLabel);
			add(tensionSlider[i]);
			add(tensionMaxValueLabel);
			add(tension0ValueLabel);
			add(tensionMinValueLabel);

		}
		// Bouton Reset
		JButton resetButton = new JButton("RESET");
		resetButton.setLocation(180, tensionSlider[1].getHeight()+70);
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
				controller.setStepValue(ii, tensionSlider[ii-1].getValue()/1000.0);
			}
		
		});
		}
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				controller.resetSteps();
			}
		});
	
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.PModule#updatePresentation()
	 */
	@Override
	public void updatePresentation() {
		super.updatePresentation();
		for(int i = 0; i< this.tensionSlider.length;i++){
			tensionSlider[i].setValue((int) (controller.getStepValue(i+1)*1000));
		}
		
	}

}
