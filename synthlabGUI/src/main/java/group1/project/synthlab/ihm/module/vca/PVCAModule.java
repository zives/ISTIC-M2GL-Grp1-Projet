package group1.project.synthlab.ihm.module.vca;

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

public class PVCAModule extends PModule implements IPVCAModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected ICVCAModule controller;
	protected JSlider attenuatorSlider;


	public PVCAModule(final ICVCAModule controller) {
		super(controller);
		this.controller = controller;
		// Taille et couleur définie dans la super classe

		// Label et onoff boutons déjà rajoutés dans la super classe

		// Ports
		PPort pportIn = (PPort) (((ICInPort) controller.getIn()).getPresentation());
		pportIn.setLocation(10, 220);
		PPort pportAm = (PPort) (((ICInPort) controller.getAm()).getPresentation());
		pportAm.setLocation(70, 220);
		PPort pportOut = (PPort) (((ICOutPort) controller.getOut()).getPresentation());
		pportOut.setLocation(130, 220);

		// Label volume
		final JLabel volumeLabel = new JLabel(controller.geta0() + " dB");
		volumeLabel.setForeground(Color.LIGHT_GRAY);
		volumeLabel.setOpaque(false);
		volumeLabel.setSize(150, 20);
		volumeLabel.setHorizontalTextPosition(JLabel.CENTER);
		volumeLabel.setBorder(null);
		volumeLabel.setPreferredSize(volumeLabel.getSize());
		volumeLabel.setLocation(getWidth()/2 - volumeLabel.getWidth() / 2 + 40, 50);
		volumeLabel.setFont(new Font("Monospaced", Font.ITALIC, 26));

		// Sliders
		attenuatorSlider = new JSlider();
		attenuatorSlider.setMaximum(300);
		attenuatorSlider.setMinimum(-300);
		attenuatorSlider.setOrientation(JSlider.HORIZONTAL);
		attenuatorSlider.setValue((int) controller.geta0());
		attenuatorSlider.setSize(230, 80);
		attenuatorSlider.setFont(new Font("Arial", 0, 8));
		attenuatorSlider.setForeground(Color.LIGHT_GRAY);
		attenuatorSlider.setPreferredSize(attenuatorSlider.getSize());
		attenuatorSlider.setOpaque(false);
		attenuatorSlider.setFocusable(false);
		attenuatorSlider.setBorder(null);
		attenuatorSlider.setLocation(35, 50);
//		attenuatorSliDER.SETMAJORTICKSPACING(100);
//		ATTENUATORSLIDER.SETMINORTICKSPACING(25);
//		ATTENUATORSLIder.setPaintTicks(true);
		
		
		//Labels slider
//		JLabel attenuatorMaxValueLabel = new JLabel("+300");
//		attenuatorMaxValueLabel.setForeground(Color.LIGHT_GRAY);
//		attenuatorMaxValueLabel.setOpaque(false);
//		attenuatorMaxValueLabel.setSize(30, 20);
//		attenuatorMaxValueLabel.setBorder(null);
//		attenuatorMaxValueLabel.setPreferredSize(attenuatorMaxValueLabel.getSize());
//		attenuatorMaxValueLabel.setLocation(attenuatorSlider.getX() + 40, 50);
//		attenuatorMaxValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
//		
//		JLabel attenuator0ValueLabel = new JLabel("0");
//		attenuator0ValueLabel.setForeground(Color.LIGHT_GRAY);
//		attenuator0ValueLabel.setOpaque(false);
//		attenuator0ValueLabel.setSize(30, 20);
//		attenuator0ValueLabel.setBorder(null);
//		attenuator0ValueLabel.setPreferredSize(attenuator0ValueLabel.getSize());
//		attenuator0ValueLabel.setLocation(attenuatorSlider.getX() + 40, 85);
//		attenuator0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
//		
//		JLabel attenuatorMinValueLabel = new JLabel(String.valueOf(attenuatorSlider.getMinimum() / 10));
//		attenuatorMinValueLabel.setForeground(Color.LIGHT_GRAY);
//		attenuatorMinValueLabel.setOpaque(false);
//		attenuatorMinValueLabel.setSize(30, 20);
//		attenuatorMinValueLabel.setBorder(null);
//		attenuatorMinValueLabel.setPreferredSize(attenuatorMinValueLabel.getSize());
//		attenuatorMinValueLabel.setLocation(attenuatorSlider.getX() + 40, 185);
//		attenuatorMinValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));


//		PGradient gradient = new PGradient(new Color(100,60,60), new Color(160,160,160), 0.28f);
//		gradient.setSize(10, 140);
//		gradient.setLocation(25, 55);
				  
		// Ajouts des composants
		add(pportIn);
		add(pportAm);
		add(pportOut);
		add(volumeLabel);
		add(attenuatorSlider);
//		add(attenuatorMaxValueLabel);
//		add(attenuator0ValueLabel);
//		add(attenuatorMinValueLabel);
//		add(gradient);

		this.repaint();

		// Events
		attenuatorSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.seta0(attenuatorSlider.getValue());
				volumeLabel.setText(Math.round(controller.geta0() * 10) + " dB");
			}
		});

		
	}


	public void setSlidersEnabled(boolean value) {
		// TODO Auto-generated method stub
		
	}


	

}
