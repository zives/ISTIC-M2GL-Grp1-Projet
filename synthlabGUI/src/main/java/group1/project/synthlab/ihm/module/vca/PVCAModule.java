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
	protected JSlider gainSlider;


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
		gainSlider.setMaximum(300);
		gainSlider.setMinimum(-300);
		gainSlider.setOrientation(JSlider.HORIZONTAL);
		gainSlider.setValue((int) controller.geta0());
		gainSlider.setSize(230, 80);
		gainSlider.setFont(new Font("Arial", 0, 8));
		gainSlider.setForeground(Color.LIGHT_GRAY);
		gainSlider.setPreferredSize(gainSlider.getSize());
		gainSlider.setOpaque(false);
		gainSlider.setFocusable(false);
		gainSlider.setBorder(null);
		gainSlider.setLocation(35, 70);
		
		final JLabel a0Label = new JLabel("a0");
		a0Label.setForeground(Color.LIGHT_GRAY);
		a0Label.setOpaque(false);
		a0Label.setSize(50, 20);
		a0Label.setBorder(null);
		a0Label.setPreferredSize(a0Label.getSize());
		a0Label.setLocation(gainSlider.getX(), 80);
		a0Label.setFont(new Font("Arial", Font.ITALIC, 10));
	
				  
		// Ajouts des composants
		add(pportIn);
		add(pportAm);
		add(pportOut);
		add(gainLabel);
		add(a0Label);
		add(gainSlider);
		

		this.repaint();

		// Events
		gainSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.seta0(gainSlider.getValue()/10);
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
