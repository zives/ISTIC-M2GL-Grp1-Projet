package group1.project.synthlab.presentation.synthesizer;

import group1.project.synthlab.cable.Cable;
import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.control.port.in.InPort;
import group1.project.synthlab.presentation.port.PPortIn;
import group1.project.synthlab.workspace.IWorkspace;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.jsyn.ports.ConnectableInput;

public class PSynthesizer extends JFrame {


	private JMenuBar menuBar;
	private JMenu fichier;
	private JMenuItem quit;

	private static final long serialVersionUID = 1L;

	private IWorkspace controle;
	private JPanel toolBar;
	private JPanel workspacePanel;

	private Button vcoButton;
	private Button outputButton;

	public PSynthesizer() {
		super("Synthétiseur");
		//this.controle = controle;

		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		setSize(800,600);
		initialiser();

		setVisible(true);
		setLocation(250,100);

		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
	public void initialiser() {
		menuBar = new JMenuBar();
		fichier = new JMenu("Fichier");

		quit = new JMenuItem("Quitter l'application");

		setJMenuBar(menuBar);
		menuBar.add(fichier);
		fichier.add(quit);

		toolBar = new JPanel();
		toolBar.setBackground(Color.LIGHT_GRAY);
		toolBar.setLayout(new FlowLayout());

		workspacePanel = new JPanel();
		workspacePanel.setBackground(Color.white);
		workspacePanel.setLayout(null);

		outputButton = new Button("OUT");
		vcoButton = new Button("VCO");

		toolBar.add(outputButton);
		toolBar.add(vcoButton);
		add(toolBar, BorderLayout.NORTH);
		add(workspacePanel, BorderLayout.CENTER);
	}
	
	
	public static void main(String[] args) {
		ConnectableInput jSynPort = null;

		PPortIn port1 = new PPortIn(new InPort("portIn", jSynPort));
		PPortIn port2 = new PPortIn(new InPort("portIn", jSynPort));
		ICable cable = new Cable();
		
        JFrame mainFrame = new JFrame("Synthetiseur");
       // IWorkspace controle = new Workspace();
        mainFrame.getContentPane().add(port1);
        mainFrame.getContentPane().add(port2);
        
        mainFrame.setSize(800,600);
        mainFrame.setResizable(true);
        mainFrame.setVisible(true);
        mainFrame.setLocation(250,100);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}
}

