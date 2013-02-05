package group1.project.synthlab.ihm.workspace;

import group1.project.synthlab.ihm.cable.IPCable;
import group1.project.synthlab.ihm.cable.PCable;
import group1.project.synthlab.ihm.module.IPModule;
import group1.project.synthlab.ihm.port.IPPort;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jtattoo.plaf.smart.SmartLookAndFeel;

public class PWorkspace extends JFrame implements IPWorkspace {

	protected JMenuBar menuBar;
	protected JMenu fichier;
	protected JMenuItem quit;

	private static final long serialVersionUID = 1L;

	protected ICWorkspace controller;
	protected JPanel toolBar;
	protected JLayeredPane workspacePanel;
	protected JScrollPane centerPanel;

	protected Button vcoButton;
	protected Button outButton;
	protected Button multiplexerButton;

	public PWorkspace(ICWorkspace controller) {
		super("Synthetiseur");
		initLnF();

		this.controller = controller;

		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		setSize(800, 600);
		initialize();

		setVisible(true);
		setLocation(250, 100);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initLnF() {
		// setup the look and feel properties
		Properties props = new Properties();

		props.put("selectionBackgroundColor", "180 240 197");
		props.put("menuSelectionBackgroundColor", "180 240 197");

		props.put("controlColor", "218 254 230");
		props.put("controlColorLight", "218 254 230");
		props.put("controlColorDark", "180 240 197");

		props.put("buttonColor", "218 230 254");
		props.put("buttonColorLight", "255 255 255");
		props.put("buttonColorDark", "244 242 232");

		props.put("rolloverColor", "218 254 230");
		props.put("rolloverColorLight", "218 254 230");
		props.put("rolloverColorDark", "180 240 197");

		props.put("windowTitleForegroundColor", "0 0 0");
		props.put("windowTitleBackgroundColor", "180 240 197");
		props.put("windowTitleColorLight", "218 254 230");
		props.put("windowTitleColorDark", "180 240 197");
		props.put("windowBorderColor", "218 254 230");

		SmartLookAndFeel.setCurrentTheme(props);
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public void initialize() {

		menuBar = new JMenuBar();
		fichier = new JMenu("Fichier");

		quit = new JMenuItem("Quitter l'application");

		setJMenuBar(menuBar);		  
		menuBar.add(fichier);
		fichier.add(quit);

		toolBar = new JPanel();
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));

		workspacePanel = new JLayeredPane();
		workspacePanel.setOpaque(true);
		workspacePanel.setBackground(new Color(20, 20, 20));
		workspacePanel.setLayout(null);
		workspacePanel.setPreferredSize(new Dimension(3000, 3000));
		workspacePanel.setSize(workspacePanel.getPreferredSize());
		centerPanel = new JScrollPane(workspacePanel);

		vcoButton = new Button("VCO");
		vcoButton.setBackground(new Color(150,150,150));
		vcoButton.setForeground(Color.BLACK);
		toolBar.add(vcoButton);

		outButton = new Button("OUT");
		outButton.setBackground(new Color(150,150,150));
		outButton.setForeground(Color.BLACK);
		toolBar.add(outButton);
		
		multiplexerButton = new Button("MULTIPLEXER");
		multiplexerButton.setBackground(new Color(150,150,150));
		multiplexerButton.setForeground(Color.BLACK);
		toolBar.add(multiplexerButton);

		add(toolBar, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);

		pack();
		setBackground(Color.BLACK);
		setVisible(true);
		setSize(1024, 600);
		setResizable(true);
		setVisible(true);
		setLocation(250, 100);

		// Events
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {

			public void eventDispatched(AWTEvent e) {
				if (e instanceof MouseEvent) {
					if (SwingUtilities.isDescendingFrom(
							(Component) e.getSource(), centerPanel)) {
						MouseEvent m = (MouseEvent) e;
						if (m.getID() == MouseEvent.MOUSE_MOVED) {
							if (controller.isDrawingCable()) {
								PCable cable = (PCable) controller
										.getDrawingCable().getPresentation();
								Point finalPoint = SwingUtilities.convertPoint(
										(Component) e.getSource(),
										m.getPoint(), centerPanel);
								cable.setP2((int) finalPoint.getX(),
										(int) finalPoint.getY());
							}
							//Les curseurs
							if (e.getSource() instanceof IPPort)
								System.err.println('j');
								//workspacePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
							else if (e.getSource() instanceof IPModule) 
								workspacePanel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
							
							else
								workspacePanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						}
					}
				}
			}
		}, AWTEvent.MOUSE_MOTION_EVENT_MASK);
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			public void eventDispatched(AWTEvent e) {
				if (e instanceof KeyEvent) {
					KeyEvent m = (KeyEvent) e;
					if (m.getID() == KeyEvent.KEY_RELEASED) {
						if (m.getKeyCode() == KeyEvent.VK_ESCAPE
								&& controller.isDrawingCable()) {
							controller.removeCable(controller.getDrawingCable());
							controller.setDrawingCable(null);
							repaint();
						}
					}
				}
			}
		}, AWTEvent.KEY_EVENT_MASK);

		// workspacePanel.addMouseMotionListener(new MouseMotionListener() {
		//
		// public void mouseMoved(MouseEvent ev) {
		// if (controller.isDrawingCable()) {
		// PCable cable = (PCable) controller.getDrawingCable()
		// .getPresentation();
		// cable.setP2(ev.getX(), ev.getY());
		// for (Component component : getComponents())
		// component.repaint();
		// }
		// }
		//
		// public void mouseDragged(MouseEvent arg0) {
		// }
		// });

		vcoButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				controller.addOneVCOModule();
			}
		});

		outButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				controller.addOneOutModule();
			}
		});
		
		multiplexerButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				controller.addOneMultiplexer();
			}
		});
		
		quit.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
	}

	public JLayeredPane getWorkspacePanel() {
		return workspacePanel;
	}

	public void addCable(IPCable cable) {
		workspacePanel.add((Component) cable);
		workspacePanel.moveToFront((Component) cable);
		repaint();
	}

	public void removeCable(IPCable cable) {
		workspacePanel.remove((Component) cable);
		repaint();

	}

	public void addModule(IPModule module) {
		Component cModule = (Component) module;
		int x = (int) (-centerPanel.getViewport().getView().getX()
				+ Math.random() * (centerPanel.getWidth()) - cModule.getWidth());
		if (x < 0)
			x = 0;
		int y = (int) (-centerPanel.getViewport().getView().getY() + Math
				.random() * (centerPanel.getHeight() - cModule.getHeight()));
		if (y < 0)
			y = 0;
		cModule.setLocation(x, y);

		workspacePanel.add(cModule);
		workspacePanel.moveToFront(cModule);
		repaint();
	}

	public void removeModule(IPModule module) {
		workspacePanel.remove((Component) module);
		repaint();
	}

	public static void main(String[] args) {
		CWorkspace ws = (CWorkspace) CWorkspace.getInstance();

	}

}
