package group1.project.synthlab.ihm.workspace;

import group1.project.synthlab.ihm.cable.IPCable;
import group1.project.synthlab.ihm.cable.PCable;
import group1.project.synthlab.ihm.module.IPModule;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PWorkspace extends JFrame implements IPWorkspace {

	private JMenuBar menuBar;
	private JMenu fichier;
	private JMenuItem quit;

	private static final long serialVersionUID = 1L;

	private ICWorkspace controller;
	private JPanel toolBar;
	private JLayeredPane workspacePanel;
	private JScrollPane centerPanel; 

	private Button vcoButton;

	public PWorkspace(ICWorkspace controller) {
		super("Synthetiseur");
		// this.controle = controle;

		this.controller = controller;

		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		setSize(800, 600);
		initialize();

		setVisible(true);
		setLocation(250, 100);

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
		workspacePanel.setPreferredSize(new Dimension(2000, 2000));
		workspacePanel.setSize(workspacePanel.getPreferredSize());
		centerPanel = new JScrollPane(workspacePanel);

		vcoButton = new Button("VCO");

		toolBar.add(vcoButton);
		add(toolBar, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);

		pack();
		setBackground(Color.BLACK);
		setVisible(true);
		setSize(800, 600);
		setResizable(true);
		setVisible(true);
		setLocation(250, 100);

		//Events
		workspacePanel.addMouseMotionListener(new MouseMotionListener() {

			public void mouseMoved(MouseEvent ev) {
				if (controller.isDrawingCable()) {
					PCable cable = (PCable) controller.getDrawingCable()
							.getPresentation();
					cable.setP2(ev.getX(), ev.getY());
					for (Component component : getComponents())
						component.dispatchEvent(ev);
				}
			}

			public void mouseDragged(MouseEvent arg0) {
			}
		});
		vcoButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent ev) {
				controller.addOneVCOModule();
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

	public void addModule(IPModule module) {
		Component cModule = (Component) module;
		int x = (int) (-centerPanel.getViewport().getView().getX() + Math.random() * (centerPanel.getWidth()) - cModule.getWidth());
		if (x < 0) x = 0;
		int  y = (int)(-centerPanel.getViewport().getView().getY() + Math.random() * (centerPanel.getHeight() - cModule.getHeight()));
		if (y < 0) y = 0;
		cModule.setLocation(x, y);
		
		workspacePanel.add(cModule);
		workspacePanel.moveToFront(cModule);
	}

	public void removeModule(IPModule module) {
		workspacePanel.remove((Component) module);

	}
	
	

	public static void main(String[] args) {
		CWorkspace ws = (CWorkspace) CWorkspace.getInstance();	

	}



}
