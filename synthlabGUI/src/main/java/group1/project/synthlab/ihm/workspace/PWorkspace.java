package group1.project.synthlab.ihm.workspace;

import group1.project.synthlab.ihm.cable.IPCable;
import group1.project.synthlab.ihm.cable.PCable;
import group1.project.synthlab.ihm.factory.ControllerFactory;
import group1.project.synthlab.ihm.port.IPPort;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.in.CInPort;
import group1.project.synthlab.ihm.port.out.COutPort;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class PWorkspace extends JFrame implements IPWorkspace {


	private JMenuBar menuBar;
	private JMenu fichier;
	private JMenuItem quit;

	private static final long serialVersionUID = 1L;

	private ICWorkspace controller;
	private JPanel toolBar;
	private JLayeredPane workspacePanel;

	private Button vcoButton;
	private Button outputButton;


	public PWorkspace(ICWorkspace controller) {
		super("Synthetiseur");
		//this.controle = controle;

		this.controller = controller;
		
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		setSize(800,600);
		initialize();

		setVisible(true);
		setLocation(250,100);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void initialize() {		
		
		menuBar = new JMenuBar();
		fichier = new JMenu("Fichier");

		quit = new JMenuItem("Quitter l'application");

		setJMenuBar(menuBar);
		menuBar.add(fichier);
		fichier.add(quit);

		toolBar = new JPanel();
		toolBar.setBackground(Color.LIGHT_GRAY);
		toolBar.setLayout(new FlowLayout());

		workspacePanel = new JLayeredPane();
		workspacePanel.setOpaque(true);
		workspacePanel.setBackground(Color.BLACK);
		workspacePanel.setLayout(null);

		outputButton = new Button("OUT");
		vcoButton = new Button("VCO");

		toolBar.add(outputButton);
		toolBar.add(vcoButton);
		add(toolBar, BorderLayout.NORTH);
		add(workspacePanel, BorderLayout.CENTER);
		
		pack();
		setBackground(Color.BLACK);
		setVisible(true);
        setSize(800,600);
        setResizable(true);
        setVisible(true);
        setLocation(250,100);
        
        workspacePanel.addMouseMotionListener(new MouseMotionListener() {
			
			public void mouseMoved(MouseEvent ev) {
				if (controller.isDrawingCable()) {
					PCable cable = (PCable) controller.getDrawingCable().getPresentation();
					cable.setP2(ev.getX(), ev.getY());	
					for (Component component: getComponents())
						component.dispatchEvent(ev);
				}
			}
			
			public void mouseDragged(MouseEvent arg0) {
			}
		});
	}
	
	public JLayeredPane getWorkspacePanel() {
		return workspacePanel;
	}
	
	public void addCable(IPCable cable) {
		workspacePanel.add((Component) cable);
		workspacePanel.moveToFront((Component) cable);
	}

	public void removeCable(IPCable cable) {
		workspacePanel.remove((Component) cable);
		
	}

	public void addPort(IPPort port) {
		workspacePanel.add((Component) port);
		
	}

	public void removePort(IPPort port) {
		workspacePanel.remove((Component) port);
		
	}
	
	
	public static void main(String[] args) {
		
		CWorkspace ws = (CWorkspace) CWorkspace.getInstance();
		
		
		JPanel panel = new JPanel();
		COutPort portS = (COutPort)(ControllerFactory.getInstance().createOutPort("sModule", null));
		CInPort portE = (CInPort)(ControllerFactory.getInstance().createInPort("eModule", null));
		
		((PPort) portS.getPresentation()).setLocation(100, 100);
		((PPort) portE.getPresentation()).setLocation(200, 300);
		
		((PWorkspace) ws.getPresentation()).getWorkspacePanel().add(((PPort) portS.getPresentation()));
		((PWorkspace) ws.getPresentation()).getWorkspacePanel().add(((PPort) portE.getPresentation()));
		
		panel.setBackground(Color.BLACK);

	}




}

