package group1.project.synthlab.ihm.workspace;

import group1.project.synthlab.ihm.cable.IPCable;
import group1.project.synthlab.ihm.cable.PCable;
import group1.project.synthlab.ihm.module.IPModule;
import group1.project.synthlab.ihm.module.IPModuleObservable;
import group1.project.synthlab.ihm.module.piano.IPPianoModule;
import group1.project.synthlab.ihm.port.IPPort;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
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
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jtattoo.plaf.noire.NoireLookAndFeel;

public class PWorkspace extends JFrame implements IPWorkspace {

	protected JMenuBar menuBar;
	protected JMenu fichierMenu;
	protected JMenuItem quit;
	protected JMenuItem save;
	protected JMenuItem load;

	private static final long serialVersionUID = 1L;

	protected transient ICWorkspace controller;
	protected JPanel toolBar;
	protected JLayeredPane workspacePanel;
	protected JScrollPane centerPanel;

	protected JButton vcoButton;
	protected JButton outButton;
	protected JButton vcaButton;
	protected JButton vcflpButton;
	protected JButton vcfhpButton;
	protected JButton pianoButton;
	protected JButton noiseButton;
	protected JButton multiplexerButton;
	protected JButton egButton;
	protected JButton eqViewButton;
	protected JButton microButton;
	protected JButton eqButton;
	protected JButton oscButton;
	protected JButton sequencerButton;
	protected JMenu workspaceMenu;
	protected JMenuItem clear;
	protected JMenuItem allOn;
	protected JMenuItem allOff;

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

		props.put("logoString", "Synth");
		props.put("selectionBackgroundColor", "100 100 100");
		props.put("menuSelectionBackgroundColor", "110 110 110");

		props.put("controlColor", "70 70 70");
		props.put("controlColorLight", "70 70 70");
		props.put("controlColorDark", "70 70 70");

		props.put("buttonColor", "200 200 200");
		props.put("buttonColorLight", "220 220 220");
		props.put("buttonColorDark", "200 200 200");

		props.put("rolloverColor", "100 100 100");
		props.put("rolloverColorLight", "180 180 180");
		props.put("rolloverColorDark", "190 190 190");

		props.put("windowTitleForegroundColor", "0 0 0");
		props.put("windowTitleBackgroundColor", "100 100 100");
		props.put("windowTitleColorLight", "180 180 180");
		props.put("windowTitleColorDark", "180 180 180");
		props.put("windowBorderColor", "40 40 40");
		
		NoireLookAndFeel.setCurrentTheme(props);
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
		fichierMenu = new JMenu("Fichier");
		workspaceMenu = new JMenu("Workspace");
		
		//Workspace
		clear = new JMenuItem("Tout effacer");
		clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
		workspaceMenu.add(clear);	
	
		allOn = new JMenuItem("Tout demarrer");
		allOn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
		workspaceMenu.add(allOn);
		
		allOff = new JMenuItem("Tout arreter");
		allOff.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,InputEvent.CTRL_MASK));
		workspaceMenu.add(allOff);

		//bouton sauvegarde pour la configuration
		save = new JMenuItem("Sauver");	
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		fichierMenu.add(save);
		//bouton charger
		load = new JMenuItem("Charger");	
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		fichierMenu.add(load);		
		
		quit = new JMenuItem("Quitter");
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));		
		fichierMenu.add(quit);
		
		setJMenuBar(menuBar);
		menuBar.add(fichierMenu);
		menuBar.add(workspaceMenu);
		

		toolBar = new JPanel();
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));

		workspacePanel = new JLayeredPane();
		workspacePanel.setOpaque(true);
		workspacePanel.setBackground(new Color(30, 30, 30));
		workspacePanel.setLayout(null);
		workspacePanel.setPreferredSize(new Dimension(3000, 3000));
		workspacePanel.setSize(workspacePanel.getPreferredSize());
		centerPanel = new JScrollPane(workspacePanel);
		centerPanel.setDoubleBuffered(true);
		centerPanel.setDoubleBuffered(true);
		centerPanel.setWheelScrollingEnabled(false);
		centerPanel.getViewport().setIgnoreRepaint(false);
		centerPanel.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
				
		//---------------------------------------------------------------------
				JSeparator separatorOut = new JSeparator(JSeparator.VERTICAL);
				separatorOut.setSize(2, 20);
				separatorOut.setBackground(new Color(70,70,70));
				separatorOut.setPreferredSize(separatorOut.getSize());
				toolBar.add(separatorOut);
		//---------------------------------------------------------------------
		Color colorButton = new Color(60, 60, 90);		
				
		outButton = new JButton("OUT");
		outButton.setBackground(colorButton);
		outButton.setForeground(Color.LIGHT_GRAY);
		outButton.setFocusable(false);
		toolBar.add(outButton);
		
		eqViewButton= new JButton("EQ VIEW");
		eqViewButton.setBackground(colorButton);
		eqViewButton.setForeground(Color.LIGHT_GRAY);
		eqViewButton.setFocusable(false);
		toolBar.add(eqViewButton);
		
		oscButton= new JButton("OSC");
		oscButton.setBackground(colorButton);
		oscButton.setForeground(Color.LIGHT_GRAY);
		oscButton.setFocusable(false);
		toolBar.add(oscButton);		
		
		//---------------------------------------------------------------------
				JSeparator separatorGenerators = new JSeparator(JSeparator.VERTICAL);
				separatorGenerators.setSize(2, 20);
				separatorGenerators.setBackground(new Color(70,70,70));
				separatorGenerators.setPreferredSize(separatorOut.getSize());
				toolBar.add(separatorGenerators);
		//---------------------------------------------------------------------
		colorButton = new Color(60, 90, 60);	
		
		pianoButton = new JButton("PIANO");
		pianoButton.setBackground(colorButton);
		pianoButton.setForeground(Color.LIGHT_GRAY);
		pianoButton.setFocusable(false);
		toolBar.add(pianoButton);

		noiseButton = new JButton("NOISE");
		noiseButton.setBackground(colorButton);
		noiseButton.setForeground(Color.LIGHT_GRAY);
		noiseButton.setFocusable(false);
		toolBar.add(noiseButton);
		
		vcoButton = new JButton("VCO");
		vcoButton.setBackground(colorButton);
		vcoButton.setForeground(Color.LIGHT_GRAY);
		vcoButton.setFocusable(false);
		toolBar.add(vcoButton);
		
		microButton= new JButton("MICRO");
		microButton.setBackground(colorButton);
		microButton.setForeground(Color.LIGHT_GRAY);
		microButton.setFocusable(false);
		if (controller.isMicrophoneSupported())
			toolBar.add(microButton);
		
		//---------------------------------------------------------------------
				JSeparator separatorFilters= new JSeparator(JSeparator.VERTICAL);
				separatorFilters.setSize(2, 20);
				separatorFilters.setBackground(new Color(70,70,70));
				separatorFilters.setPreferredSize(separatorOut.getSize());
				toolBar.add(separatorFilters);
		//---------------------------------------------------------------------
		colorButton = new Color(90, 90, 90);	
				
		egButton = new JButton("EG");
		egButton.setBackground(colorButton);
		egButton.setForeground(Color.LIGHT_GRAY);
		egButton.setFocusable(false);
		toolBar.add(egButton);

		vcaButton = new JButton("VCA");
		vcaButton.setBackground(colorButton);
		vcaButton.setForeground(Color.LIGHT_GRAY);
		vcaButton.setFocusable(false);
		toolBar.add(vcaButton);

		vcflpButton = new JButton("VCF-LP");
		vcflpButton.setBackground(colorButton);
		vcflpButton.setForeground(Color.LIGHT_GRAY);
		vcflpButton.setFocusable(false);
		toolBar.add(vcflpButton);
		
		vcfhpButton = new JButton("VCF-HP");
		vcfhpButton.setBackground(colorButton);
		vcfhpButton.setForeground(Color.LIGHT_GRAY);
		vcfhpButton.setFocusable(false);
		toolBar.add(vcfhpButton);
		
		eqButton= new JButton("EQ");
		eqButton.setBackground(colorButton);
		eqButton.setForeground(Color.LIGHT_GRAY);
		eqButton.setFocusable(false);
		toolBar.add(eqButton);
		
		sequencerButton= new JButton("SEQ");
		sequencerButton.setBackground(colorButton);
		sequencerButton.setForeground(Color.LIGHT_GRAY);
		sequencerButton.setFocusable(false);
		toolBar.add(sequencerButton);
		
		//---------------------------------------------------------------------
				JSeparator separatorOthers = new JSeparator(JSeparator.VERTICAL);
				separatorOthers.setSize(2, 20);
				separatorOthers.setBackground(new Color(70,70,70));
				separatorOthers.setPreferredSize(separatorOut.getSize());
				toolBar.add(separatorOthers);
		//---------------------------------------------------------------------	
		colorButton = new Color(90, 50, 30);	

		multiplexerButton = new JButton("MULTIPLEXER");
		multiplexerButton.setBackground(colorButton);
		multiplexerButton.setForeground(Color.LIGHT_GRAY);
		multiplexerButton.setFocusable(false);
		toolBar.add(multiplexerButton);			
	
		add(toolBar, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);

		pack();
		setBackground(Color.BLACK);
		setVisible(true);
		setSize(1224, 650);
		setResizable(true);
		setVisible(true);
		setLocation(250, 100);

		// Gestion des evenements du WS et de son contenu (evenements accepté
		// par les controles dessous, ex sous le rectangle du cable on peut
		// deplacer un module)
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {

			public void eventDispatched(AWTEvent e) {
				if (e instanceof MouseEvent) {
					if (SwingUtilities.isDescendingFrom(
							(Component) e.getSource(), centerPanel)) {
						MouseEvent m = (MouseEvent) e;
						if (m.getID() == MouseEvent.MOUSE_MOVED || m.getID() == MouseEvent.MOUSE_DRAGGED ) {
							// On dessine le cable (meme si on mouse move sur le
							// module en plus du ws)
							if (controller.isDrawingCable()) {
								PCable cable = (PCable) controller
										.getDrawingCable().getPresentation();
								Point finalPoint = SwingUtilities.convertPoint(
										(Component) e.getSource(),
										m.getPoint(), centerPanel);
								cable.setP2((int) finalPoint.getX(),
										(int) finalPoint.getY());
							}
							// Les curseurs
							if (e.getSource() instanceof IPPort)
								workspacePanel.setCursor(new Cursor(
										Cursor.HAND_CURSOR));
							else if (e.getSource() instanceof IPModule 
									&& !controller.isDrawingCable()){							
								if (e.getSource() instanceof IPPianoModule) 
									workspacePanel.setCursor(((Component) e
										.getSource()).getCursor());
								else {
									workspacePanel.setCursor(new Cursor(
										Cursor.MOVE_CURSOR));
								}
							} else
								workspacePanel.setCursor(new Cursor(
										Cursor.DEFAULT_CURSOR));
						}
					}
				}
			}
		}, AWTEvent.MOUSE_MOTION_EVENT_MASK);
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {

			public void eventDispatched(AWTEvent e) {
				if (e instanceof MouseEvent) {
					if (SwingUtilities.isDescendingFrom(
							(Component) e.getSource(), centerPanel)) {
						MouseEvent m = (MouseEvent) e;
						if (m.getID() == MouseEvent.MOUSE_WHEEL) {
							// On dessine le cable (meme si on mouse move sur le
							// module en plus du ws)
							if (controller.isDrawingCable()) {
								PCable cable = (PCable) controller
										.getDrawingCable().getPresentation();
								cable.nextColor();
								m.consume();
							}
						}
					}
				}
			}
		}, AWTEvent.MOUSE_WHEEL_EVENT_MASK);
		// La touche echap (accessible à l'application) pour retirer un cable
		// encours de creation
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
					}else if (m.getID() == KeyEvent.KEY_PRESSED) {
//						if (m.getKeyCode() == KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK).getKeyCode()) 
//							controller.saveConfiguration();
//						clear = new JMenuItem("Tout effacer");
//						clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
//						workspaceMenu.add(clear);	
//					
//						allOn = new JMenuItem("Tout demarrer");
//						allOn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
//						workspaceMenu.add(allOn);
//						
//						allOff = new JMenuItem("Tout arreter");
//						allOff.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,InputEvent.CTRL_MASK));
//						workspaceMenu.add(allOff);
//
//						//bouton sauvegarde pour la configuration
//						save = new JMenuItem("Sauver");	
//						save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
//						fichierMenu.add(save);
//						//bouton charger
//						load = new JMenuItem("Charger");	
//						load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
//						fichierMenu.add(load);		
//						
//						quit = new JMenuItem("Quitter");
//						quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));		
//						fichierMenu.add(quit);
					}
				}
			}
		}, AWTEvent.KEY_EVENT_MASK);


		vcoButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				controller.addOneVCOModule();
			}
		});

		pianoButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				controller.addOnePianoModule();
			}
		});
		
		noiseButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				controller.addOneNoiseModule();
			}
		});

		vcaButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				controller.addOneVCAModule();
			}
		});
		
		vcflpButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				controller.addOneVCFLPModule();
			}
		});

		vcfhpButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				controller.addOneVCFHPModule();
			}
		});
		egButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				controller.addOneEGModule();
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
		
		sequencerButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				controller.addOneSequencer();
			}
		});
		
		eqViewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				controller.addOneEQViewModule();
			}
		});
		microButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				controller.addOneMicroModule();
			}
		});
		eqButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				controller.addOneEQModule();
			}
		});
		oscButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				controller.addOneOSCModule();
			}
		});
		
		
		
		//Menu bar

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.quitApp();

			}
		});
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveConfiguration();
			}
		});
		
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadConfiguration();
			}
		});
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.clearAll();
			}
		});
		allOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.allModulesOn();
			}
		});
		allOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.allModulesOff();
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
		module.register(this);
		workspacePanel.add(cModule);
		workspacePanel.moveToFront(cModule);
		repaint();
	}

	public void removeModule(IPModule module) {
		module.unregister(this);
		workspacePanel.remove((Component) module);
		repaint();
	}

	public void moduleMove(IPModuleObservable subject) {
		workspacePanel.moveToFront((Component) subject);

	}

	public static void main(String[] args) {
		CWorkspace ws = (CWorkspace) CWorkspace.getInstance();

	}

	@Override
	public File saveFileDialog() {
		UIManager.put("FileChooser.openButtonText", "Sauver");
		UIManager.put("FileChooser.openButtonToolTipText", "Sauver");
		JFileChooser j = new JFileChooser();
		j.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter( "Fichier Synth (*.synth)","synth");
		j.addChoosableFileFilter(filter);
		j.setMultiSelectionEnabled(false);
		j.setFileFilter(filter);
		j.setDialogTitle("Sauver...");
		j.setDialogType(JFileChooser.SAVE_DIALOG); 		
		j.showOpenDialog(null);
		return j.getSelectedFile();
	}

	@Override
	public void showError(String s) {
		JOptionPane.showMessageDialog(null, s);
	}

	@Override
	public File openFileDialog() {
		UIManager.put("FileChooser.openButtonText", "Open");
		JFileChooser j = new JFileChooser();
		j.setDialogTitle("Charger...");
		j.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier Synth (*.synth)", "synth" );
		j.addChoosableFileFilter(filter);
		j.setMultiSelectionEnabled(false);	
		j.setFileFilter(filter);
		j.setDialogType(JFileChooser.OPEN_DIALOG); 
		j.showOpenDialog(null);
		return j.getSelectedFile();
		
	}
	
	


}
