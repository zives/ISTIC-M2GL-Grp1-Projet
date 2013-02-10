package group1.project.synthlab.ihm.module.vca;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;
import group1.project.synthlab.ihm.tools.PGradient;
import group1.project.synthlab.module.vca.VCAModule;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PVCAModule extends PModule implements IPVCAModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected ICVCAModule controller;
	protected JSlider gainSlider;


	public PVCAModule(final ICVCAModule controller) {
		super(controller);
		this.controller = controller;
		// Taille et couleur définie dans la super classe

		// Label et onoff boutons déjà rajoutés dans la super classe

		// Ports
		PPort pportIn = (PPort) (((ICInPort) controller.getIn()).getPresentation());
		pportIn.setLocation(10, 245);
		PPort pportAm = (PPort) (((ICInPort) controller.getAm()).getPresentation());
		pportAm.setLocation(70, 245);
		PPort pportOut = (PPort) (((ICOutPort) controller.getOut()).getPresentation());
		pportOut.setLocation(130, 245);

		// Label gain
		final JLabel gainLabel = new JLabel(controller.geta0() + " dB");
		gainLabel.setForeground(Color.LIGHT_GRAY);
		gainLabel.setOpaque(false);
		gainLabel.setSize(150, 20);
		gainLabel.setHorizontalTextPosition(JLabel.CENTER);
		gainLabel.setBorder(null);
		gainLabel.setPreferredSize(gainLabel.getSize());
		gainLabel.setLocation(getWidth()/2 - gainLabel.getWidth() / 2 + 30, 50);
		gainLabel.setFont(new Font("Monospaced", Font.ITALIC, 26));

		// Sliders
		gainSlider = new JSlider();
		gainSlider.setMaximum((int) VCAModule.amax);
		gainSlider.setMinimum((int) VCAModule.amin);
		gainSlider.setOrientation(JSlider.HORIZONTAL);
		gainSlider.setValue((int) controller.geta0());
		gainSlider.setSize(230, 80);
		gainSlider.setFont(new Font("Arial", 0, 8));
		gainSlider.setForeground(Color.LIGHT_GRAY);
		gainSlider.setPreferredSize(gainSlider.getSize());
		gainSlider.setOpaque(false);
		gainSlider.setFocusable(false);
		gainSlider.setBorder(null);
		gainSlider.setLocation(35, 105);
		gainSlider.setMajorTickSpacing(10);
		gainSlider.setMinorTickSpacing(2);
		gainSlider.setPaintTicks(true);
		
		final JLabel gainMinValueLabel = new JLabel(String.valueOf(VCAModule.amin));
		gainMinValueLabel.setForeground(Color.LIGHT_GRAY);
		gainMinValueLabel.setOpaque(false);
		gainMinValueLabel.setSize(50, 20);
		gainMinValueLabel.setBorder(null);
		gainMinValueLabel.setPreferredSize(gainMinValueLabel.getSize());
		gainMinValueLabel.setLocation(gainSlider.getX(), 160);
		gainMinValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel gainA0ValueLabel = new JLabel("a0");
		gainA0ValueLabel.setForeground(Color.LIGHT_GRAY);
		gainA0ValueLabel.setOpaque(false);
		gainA0ValueLabel.setSize(30, 20);
		gainA0ValueLabel.setBorder(null);
		gainA0ValueLabel.setPreferredSize(gainA0ValueLabel.getSize());
		gainA0ValueLabel.setLocation(gainSlider.getX() + 149, 160);
		gainA0ValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		JLabel gainMaxValueLabel = new JLabel(String.valueOf(VCAModule.amax));
		gainMaxValueLabel.setForeground(Color.LIGHT_GRAY);
		gainMaxValueLabel.setOpaque(false);
		gainMaxValueLabel.setSize(30, 20);
		gainMaxValueLabel.setBorder(null);
		gainMaxValueLabel.setPreferredSize(gainMaxValueLabel.getSize());
		gainMaxValueLabel.setLocation(gainSlider.getX() + 220, 160);
		gainMaxValueLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		
		PGradient gradient = new PGradient("HORIZONTAL", new Color(90,60,60), new Color(100,100,100), 0.75f);
		gradient.setSize(230, 10);
		gradient.setLocation(35, 115);
	
				  
		// Ajouts des composants
		add(pportIn);
		add(pportAm);
		add(pportOut);
		add(gainLabel);
		add(gradient);
		add(gainMinValueLabel);
		add(gainA0ValueLabel);
		add(gainMaxValueLabel);
		add(gainSlider);
		

		this.repaint();

		// Events
		gainSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.seta0(gainSlider.getValue());
				controller.changeGain();
				gainLabel.setText(Math.round(controller.geta0()) + " dB");
			}
		});	
	}


	public void setSlidersEnabled(boolean value) {
		// TODO Auto-generated method stub
		gainSlider.setEnabled(value);
	}

}
