package group1.project.synthlab.ihm.module.eq;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class PEQModule extends PModule implements IPEQModule {

	private static final long serialVersionUID = 9202805048987933945L;

	protected ICEQModule controller;
	protected JSlider[] attenuatorSlider;

	public PEQModule(final ICEQModule controller) {
		super(controller);
		this.controller = controller;
		this.setLayout(null);
		//Taille et couleur définie dans la super classe

		//Label et onoff boutons déjà rajoutés dans la super classe

		// Ports
		PPort pportIn = (PPort) (((ICInPort) controller.getInPort())
				.getPresentation());
		pportIn.setLocation(10, 245);
		PPort pportOut = (PPort)(((ICOutPort) controller.getOutPort())
				.getPresentation());
		pportOut.setLocation(getWidth() - pportOut.getWidth() - 10, 245);

		double[] frequencies = controller.getFrequencies();
		attenuatorSlider = new JSlider[frequencies.length];
		
		int y = 40;
		int y1 = 0;
		for(int i = 0; i < frequencies.length; ++i) {
			if (i ==5 )
				y1 =  attenuatorSlider[i - 1].getHeight() + 10;
			// Sliders
			attenuatorSlider[i] = new JSlider();
			attenuatorSlider[i].setMaximum(120);
			attenuatorSlider[i].setMinimum(-300);
			attenuatorSlider[i].setOrientation(JSlider.VERTICAL);
			attenuatorSlider[i].setValue((int) controller.getAttenuation(i)*10);
			attenuatorSlider[i].setSize(40, 90);
			attenuatorSlider[i].setFont(new Font("Arial", 0, 8));
			attenuatorSlider[i].setForeground(Color.LIGHT_GRAY);
			attenuatorSlider[i].setPreferredSize(attenuatorSlider[i].getSize());
			attenuatorSlider[i].setBackground(getBackground());
			attenuatorSlider[i].setOpaque(false);
			attenuatorSlider[i].setFocusable(false);
			attenuatorSlider[i].setBorder(null);
			attenuatorSlider[i].setLocation(i%5 * (attenuatorSlider[i].getWidth() + 20) + 10, y + y1);
			attenuatorSlider[i].setMajorTickSpacing(100);
			attenuatorSlider[i].setMinorTickSpacing(20);
			attenuatorSlider[i].setPaintTicks(true);
			
			
			//Labels slider
	
			Font font = new Font("Arial", Font.ITALIC, 9);		
			String text = "";
			if (frequencies[i] > 999.0)
				text = String.valueOf(Math.round(frequencies[i] * 10 / 1000.0) / 10.0) + "k";
			else
				text = String.valueOf(Math.round(frequencies[i]));
			if (text.length() > 8)
				text = text.substring(0, 8);
			JLabel freqLabel = new JLabel(text);
			freqLabel.setForeground(Color.LIGHT_GRAY);
			freqLabel.setOpaque(false);
			freqLabel.setSize(20, 10);
			freqLabel.setBorder(null);
			freqLabel.setFont(font);
			freqLabel.setPreferredSize(freqLabel.getSize());
			freqLabel.setLocation(attenuatorSlider[i].getX() + 20 - freqLabel.getWidth() / 2, attenuatorSlider[i].getY() + attenuatorSlider[i].getHeight() - 4);
	
			add(freqLabel);
			add(attenuatorSlider[i]);
		}
		
		// Label volume
		final JLabel volumeLabel = new JLabel("" /*controller.getAttenuation() + " dB"*/);
		volumeLabel.setForeground(Color.LIGHT_GRAY);
		volumeLabel.setOpaque(false);
		volumeLabel.setSize(100, 20);
		volumeLabel.setHorizontalTextPosition(JLabel.LEFT);
		volumeLabel.setBorder(null);
		volumeLabel.setPreferredSize(volumeLabel.getSize());
		volumeLabel.setLocation(getWidth() / 2 - volumeLabel.getWidth() / 2 + 17, 230);
		volumeLabel.setFont(new Font("Monospaced", Font.ITALIC, 18));
		volumeLabel.setVisible(false);
				  
		// Ajouts des composants
		add(pportIn);
		add(pportOut);
		add(volumeLabel);

		this.repaint();

		// Events
		for(int i = 0; i < frequencies.length; ++i) {
			final int ii = i;
		attenuatorSlider[i].addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				controller.setAttenuation(ii, attenuatorSlider[ii].getValue()/10.0);
				volumeLabel.setText(controller.getAttenuation(ii) + " dB");
			}
		});
		attenuatorSlider[i].addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				volumeLabel.setVisible(false);
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				volumeLabel.setVisible(true);
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {				
			}
		});

		}
	}
	


}
