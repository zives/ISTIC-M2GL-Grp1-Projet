package group1.project.synthlab.ihm.module.multiplexer;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;
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


public class PMultiplexerModule extends PModule implements IPMultiplexerModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected ICMultiplexerModule controller;
	protected JSlider attenuatorSlider;

	public PMultiplexerModule(final ICMultiplexerModule controller) {
		super(controller);
		this.controller = controller;
		this.setLayout(null);
		//Taille et couleur définie dans la super classe

		//Label et onoff boutons déjà rajoutés dans la super classe

		// Ports
		PPort pportIn1 = (PPort) (((ICInPort) controller.getInPort1())
				.getPresentation());
		pportIn1.setLocation(30, 120);
		PPort pportIn2 = (PPort) (((ICInPort) controller.getInPort2())
				.getPresentation());
		pportIn2.setLocation(90, 120);
		PPort pportIn3 = (PPort) (((ICInPort) controller.getInPort3())
				.getPresentation());
		pportIn3.setLocation(150, 120);
		PPort pportIn4 = (PPort) (((ICInPort) controller.getInPort4())
				.getPresentation());
		pportIn4.setLocation(210, 120);
		
		PPort pportOut1 = (PPort) (((ICOutPort) controller.getOutPort1())
				.getPresentation());
		pportOut1.setLocation(30, 50);
		PPort pportOut2 = (PPort) (((ICOutPort) controller.getOutPort2())
				.getPresentation());
		pportOut2.setLocation(90, 50);
		PPort pportOut3 = (PPort) (((ICOutPort) controller.getOutPort3())
				.getPresentation());
		pportOut3.setLocation(150, 50);
		PPort pportOut4 = (PPort) (((ICOutPort) controller.getOutPort4())
				.getPresentation());
		pportOut4.setLocation(210, 50);
		
		add(pportIn1);
		add(pportIn2);
		add(pportIn3);
		add(pportIn4);
		add(pportOut1);
		add(pportOut2);
		add(pportOut3);
		add(pportOut4);
		
		setSize(getWidth(), getHeight() - 100);
		setPreferredSize(getSize());
		onOffButton.setSelected(true);
		onOffButton.setText("Off");
	}

}
