package group1.project.synthlab.ihm.workspace;

import group1.project.synthlab.cable.Cable;
import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.ihm.cable.ICCable;
import group1.project.synthlab.ihm.cable.IPCable;
import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.ihm.module.IPModule;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;
import group1.project.synthlab.ihm.tools.CTools;
import group1.project.synthlab.ihm.tools.ValueOfString;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.workspace.Workspace;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Groupe 1
 * Controleur du WS
 */
public class CWorkspace extends Workspace implements ICWorkspace {
	
	/** Le cable en cours de creation */
	protected ICCable drawingCable;
	
	/** La presentation */
	protected IPWorkspace presentationWS;


	public CWorkspace(CFactory factory) {
		super(factory);
		this.presentationWS = new PWorkspace(this);
		addModule(factory.createOutModule());
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#getPresentation()
	 */
	public IPWorkspace getPresentation() {
		return presentationWS;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#getDrawingCable()
	 */
	public ICCable getDrawingCable() {
		return drawingCable;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#setDrawingCable(group1.project.synthlab.ihm.cable.ICCable)
	 */
	public void setDrawingCable(ICCable drawingCable) {

		if (drawingCable != null && this.drawingCable == null) {
			this.drawingCable = drawingCable;
			presentationWS.addCable(drawingCable.getPresentation());
		} else
			this.drawingCable = null;

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#isDrawingCable()
	 */
	public boolean isDrawingCable() {
		return drawingCable != null;
	}

	/**
	 * @return l'instance du WS
	 */
	public static ICWorkspace getInstance() {
		if (instance == null) {
			CFactory factory = new CFactory();
			instance = factory.createWorkSpace();
		}
		return (ICWorkspace) instance;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#removeCable(group1.project.synthlab.cable.ICable)
	 */
	public void removeCable(ICable cCable) {
		presentationWS.removeCable(((ICCable) cCable).getPresentation());
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.workspace.Workspace#addModule(group1.project.synthlab.module.IModule)
	 */
	@Override
	public void addModule(IModule module) {
		super.addModule(module);
		presentationWS.addModule(((ICModule) module).getPresentation());
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.workspace.Workspace#removeModule(group1.project.synthlab.module.IModule)
	 */
	@Override
	public void removeModule(IModule module) {
		if (isDrawingCable()) {
			removeCable(getDrawingCable());
			setDrawingCable(null);
		}
		presentationWS.removeModule(((ICModule) module).getPresentation());
		super.removeModule(module);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#addOneVCOModule()
	 */
	public void addOneVCOModule() {
		addModule(factory.createVCOModule());

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#addOneOutModule()
	 */
	public void addOneOutModule() {
		addModule(factory.createOutModule());

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#addOneMultiplexer()
	 */
	public void addOneMultiplexer() {
		addModule(factory.createMultiplexerModule());
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#addOneSequencer()
	 */
	@Override
	public void addOneSequencer() {
		addModule(factory.createSequencerModule());
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#addOneVCAModule()
	 */
	public void addOneVCAModule() {
		addModule(factory.createVCAModule());
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#addOnePianoModule()
	 */
	@Override
	public void addOnePianoModule() {
		addModule(factory.createPianoModule());

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#addOneNoiseModule()
	 */
	@Override
	public void addOneNoiseModule() {
		addModule(factory.createNoiseModule());

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#addOneEGModule()
	 */
	@Override
	public void addOneEGModule() {
		addModule(factory.createEGModule());

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#addOneEQViewModule()
	 */
	@Override
	public void addOneEQViewModule() {
		addModule(factory.createEQViewModule());

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#addOneMicroModule()
	 */
	@Override
	public void addOneMicroModule() {
		addModule(factory.createMicroModule());

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#addOneEQModule()
	 */
	@Override
	public void addOneEQModule() {
		addModule(factory.createEQModule());

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#addOneOSCModule()
	 */
	@Override
	public void addOneOSCModule() {
		addModule(factory.createOSCModule());

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#addOneVCFLPModule()
	 */
	@Override
	public void addOneVCFLPModule() {
		addModule(factory.createVCFLPModule());

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#addOneVCFHPModule()
	 */
	@Override
	public void addOneVCFHPModule() {
		addModule(factory.createVCFHPModule());
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#quitApp()
	 */
	@Override
	public void quitApp() {
		System.exit(0);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#saveConfiguration()
	 */
	@Override
	public void saveConfiguration() {
		File f = presentationWS.saveFileDialog();
		if (f == null)
			return;
		saveConfiguration(f.getName(), f.getAbsolutePath());

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#loadConfiguration()
	 */
	@Override
	public void loadConfiguration() {
		File f = presentationWS.openFileDialog();
		if (f == null)
			return;
		clearAll();

		if (f != null) {
			try {
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = null;
				builder = builderFactory.newDocumentBuilder();
				Document document = builder.parse(new FileInputStream(f));
				Node configuration = document.getFirstChild();

				// on supprime tous les modules
				for (IModule m : modules) {
					ICModule cm = (ICModule) m;
					presentationWS.removeModule(cm.getPresentation());
				}
				modules.clear();
				parcourirModules(configuration);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	private void parcourirModules(Node configuration) {
		//Recence les instances trouvee a l'aide du parcours XML
		Map<String, ICModule> modulesInstances = new HashMap<>();
		Map<String, ICInPort> portsInInstances = new HashMap<>();
		Map<String, ICOutPort> portsOutInstances = new HashMap<>();
		
		//Le noeud parent 
		Element e = (Element) configuration;
		
		//Lecture des noeuds modules
		NodeList modulesNodes = e.getElementsByTagName("Module");
		for (int i = 0; i < modulesNodes.getLength(); i++) {
			Element moduleXMLElement = (Element) modulesNodes.item(i);
			String type = moduleXMLElement.getAttribute("type");
			try {
				//Cree une nouvelle instance de module a partir de son type
				CFactory factory = new CFactory();
				Method method = CFactory.class.getMethod("create" + type);
				ICModule module = (ICModule) method.invoke(factory);
				modulesInstances.put(module.getName(), module);

				//Le noeud controleur
				Node controlerXMLNode = moduleXMLElement.getElementsByTagName("Controller").item(0);
				//La liste des attributs du module
				NodeList attrsXMLNode = ((Element) controlerXMLNode).getElementsByTagName("Attr");

				//On boucle sur les attributs
				for (int j = 0; j < attrsXMLNode.getLength(); j++) {
					Element currentAttrXML = (Element) attrsXMLNode.item(j);
					
					//Cherche l'attribut du module correspondant a la balise XML
					Field field = CTools.findField(currentAttrXML.getAttribute("name"), module.getClass());
					//S'il existe
					if (field != null) {
						
						//Si c'est un tableau
						if (currentAttrXML.getAttribute("struct").equals("array")) {
							//Rend l'atribut accessible
							field.setAccessible(true);
							//Recupere le type du tableau
							String typeString = CTools.primitiveToObject(currentAttrXML
									.getAttribute("type"));
							//Trouve le nombre de valeurs dans le tableau
							int nbItem = currentAttrXML.getElementsByTagName("item")
									.getLength();
							//Obtient le type du tableau
							Class<?> itemTypeArray = CTools.primitiveToClass(currentAttrXML
									.getAttribute("type"));
							//Instancie un nouveau tableau
							Object array = Array.newInstance(itemTypeArray, nbItem);
							//Liste les items et les ajoute au tableau nouvellement instancie
							for (int ii = 0; ii < nbItem; ii++) {
								Class<?> tmp = Class.forName(typeString);
								Object res = ValueOfString.valueOf(
										tmp,
										((Element) currentAttrXML.getElementsByTagName(
												"item").item(ii))
												.getAttribute("value"));
								Array.set(array, ii, res);
							}
							//Set le tableau a l'attribut du module
							field.set(module, array);
							//Rend l'attribut plus accessible
							field.setAccessible(false);

						//Si c'est un type primitif
						} else if (field.getType().isPrimitive()) {
							//Rend l'attribut accessible
							field.setAccessible(true);
							//Trouve le type
							String typeString = CTools.primitiveToObject(currentAttrXML
									.getAttribute("type"));
							Class<?> typeAttrClass = Class.forName(typeString);
							//Recupere la valeur
							Object res = ValueOfString.valueOf(typeAttrClass,
									currentAttrXML.getAttribute("value"));
							//Assigne la valeur
							field.set(module, res);
							//Rend l'attribut non accessible
							field.setAccessible(false);
							
						//Si c'est un enum	
						} else if (field.getType().isEnum()) {
							//Rend l'attribut accessible
							field.setAccessible(true);
							//Set la valeur a l'attribut
							field.set(module, Enum.valueOf(
									(Class<Enum>) field.getType(),
									currentAttrXML.getAttribute("value")));
							//Rend l'attribut non accessible
							field.setAccessible(false);
						}
					}
					else
						System.err.println("Cet attribut n'existe pas");

				}

				//Boucle sur les ports et cable
				NodeList ports = moduleXMLElement.getElementsByTagName("Port");
				for (int j = 0; j < ports.getLength(); j++) {
					//le port XML courant
					Element portXML = (Element) ports.item(j);
					//Le type du port
					String typePort = portXML.getAttribute("type");
					//Declare deux ports et essaie de chercher les reference dans les modules correspondant au descriptif XML
					//Ajoute ensuite les references dans le HashSet definie en haut
					ICOutPort portOut;
					ICInPort portIn;
					if (typePort.equals("in")) {
						Field field = CTools.findField(
								portXML.getAttribute("nameAttr"),
								module.getClass());
						field.setAccessible(true);
						try {
							portIn = (ICInPort) field.get(module);
							portsInInstances.put(module.getName() + ","
									+ portIn.getLabel(), portIn);
						} catch (IllegalArgumentException e1) {
							e1.printStackTrace();
						} catch (IllegalAccessException e1) {
							e1.printStackTrace();
						}
					} else if (typePort.equals("out")) {
						Field field = CTools.findField(
								portXML.getAttribute("nameAttr"),
								module.getClass());
						field.setAccessible(true);
						try {
							portOut = (ICOutPort) field.get(module);
							portsOutInstances.put(module.getName() + ","
									+ portOut.getLabel(), portOut);
						} catch (IllegalArgumentException e1) {
							e1.printStackTrace();
						} catch (IllegalAccessException e1) {
							e1.printStackTrace();
						}
						field.setAccessible(false);
					}
					

				}

				//Presentation du module
				Element pres = (Element) moduleXMLElement.getElementsByTagName("Presentation")
						.item(0);
				//Recupère la location du module
				Element location = (Element) pres.getElementsByTagName(
						"Location").item(0);
				int locationX = Integer.parseInt(location.getAttribute("x"));
				int locationY = Integer.parseInt(location.getAttribute("y"));

				//Ajoute l'instance du module cree au WS
				CWorkspace.getInstance().addModule(module);
				//Refresh la presentation
				module.refresh();
				//Change la location du module
				module.getPresentation().updateLocation(locationX, locationY);

			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}

		//Deuxieme passage
		for (int i = 0; i < modulesNodes.getLength(); i++) {
			Element m = (Element) modulesNodes.item(i);
			ICModule module = modulesInstances.get(m.getAttribute("name"));
			
			//Boucles a nouveau sur les ports (les instances sont deja tous presentes dans le HashSet)
			NodeList ports = m.getElementsByTagName("Port");
			for (int j = 0; j < ports.getLength(); j++) {
				//Le port courant
				Element portXML = (Element) ports.item(j);
				String typePort = portXML.getAttribute("type");
				
				//Cree un cable
				ICCable cable = (ICCable) this.factory.createCable();
				ICOutPort portOut = null;
				ICInPort portIn = null;
				
				//Trouve les instances dans les hashSet
				if (typePort.equals("in")) {
					portIn = portsInInstances.get(module.getName() + ","
							+ portXML.getAttribute("name"));
					portOut = portsOutInstances.get(portXML
							.getAttribute("moduleTargetName")
							+ ","
							+ portXML.getAttribute("portTargetName"));
					
				} else if (typePort.equals("out")) {
					portOut = portsOutInstances.get(module.getName() + ","
							+ portXML.getAttribute("name"));
					portIn = portsInInstances.get(portXML
							.getAttribute("moduleTargetName")
							+ ","
							+ portXML.getAttribute("portTargetName"));
					
				}				
				
				//Si pas trouver on continue a charger (on essaie d'afficher un maximum de cables)
				if (portOut == null || portIn == null
						|| portOut.getCable() != null
						|| portIn.getCable() != null) 
					continue;
				
				//Sinon on continue
				try {
					
					//On assigne les ports aux cables et on l'ajoute au WS
					IPCable presentationCable = cable.getPresentation();
					presentationWS.addCable(presentationCable);					
					cable.setOutPort(portOut);
					cable.setInPort(portIn);
					
					//On trouve la couleur du cable et on l'assigne
					String idPresentationCableXML = portXML.getAttribute("refPresentation");
					NodeList listCables = e.getElementsByTagName("Cable");
					for (int c = 0; c < listCables.getLength(); ++c) {
						if (((Element) listCables.item(c)).getAttribute("id").equals(idPresentationCableXML)) {
							Element cableXML = (Element) listCables.item(c);
							Element colorCableXML = (Element) cableXML.getElementsByTagName("Color").item(0);
							presentationCable.setColorPosition(Integer.parseInt(colorCableXML.getAttribute("value")));
						}
					}
					
				} catch (BadConnection | PortAlreadyUsed e1) {
					e1.printStackTrace();
				}
				
				
				
			}
		}
		
		//On rafraichit le WS
		((Component) presentationWS).repaint();
	}


	//Sauve le WS en utilisant la reflection de Java
	private void saveConfiguration(String name, String path) {
		
		String save = "<Configuration name=\"" + name + "\">\n";
		List<ICCable> cables = new ArrayList<>();
		//on boucle sur tous les modules
		for (IModule m : modules) {
			String tmp = "";

			tmp = "	<Module name=\"" + m.getName() + "\" type=\""
					+ m.getClass().getSimpleName().substring(1) + "\">\n";

			tmp += "		<Controller>\n";
			// On boucle sur tout les attributs
			for (Field f : CTools.getAllFields(m.getClass())) {
				f.setAccessible(true);
				try {
					if (Modifier.isFinal(f.getModifiers())
							|| (Modifier.isStatic(f.getModifiers()))) {
						continue;
					}
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				}
				try {
					// Est un type primitif
					if (f.getType().isPrimitive()) {
						tmp += "			<Attr name=\"" + f.getName() + "\" type=\""
								+ f.getType() + "\"  value=\"" + f.get(m)
								+ "\" struct=\"primitive\"/>\n";

						// Est un tableau
					} else if (f.getType().isArray()) {
						f.setAccessible(true);
						Object array = f.get(m);

						Class<?> cType = f.getType().getComponentType();
						int length = Array.getLength(array);

						if (cType.isPrimitive()) {

							tmp += "			<Attr name=\"" + f.getName()
									+ "\" type=\"" + cType.getName()
									+ "\" struct=\"array\">\n";
							for (int i = 0; i < length; i++) {
								Object element = Array.get(array, i);

								tmp += "				<item value=\"" + element
										+ "\" />\n";
							}
							tmp += "			</Attr>\n";
						}
					}
					// Est un en enum
					else if (f.getType().isEnum()) {
						tmp += "			<Attr name=\"" + f.getName() + "\" type=\""
								+ f.getType().getName() + "\" value=\""
								+ f.get(m) + "\" struct=\"enum\" />\n";

					}
					// Est un port
					else if (f.getType().isAssignableFrom(IOutPort.class)
							|| f.getType().isAssignableFrom(IInPort.class)) {
						IPort port = (IPort) f.get(m);
						if (port.getCable() == null)
							continue;
						cables.add((ICCable) port.getCable());
						boolean outPort = f.getType().isAssignableFrom(IOutPort.class);
						tmp += "			<Port name=\"" + port.getLabel()
								+ "\" nameAttr=\"" + f.getName() + "\" type=\"";
						tmp += (outPort ? "out"
								: "in")
								+ "\" refPresentation=\""
								+ port.getCable().getNumCable()
								+ "\" moduleTargetName=\"";
						tmp += (outPort ? port.getCable().getInPort().getModule().getName()
								: port.getCable().getOutPort().getModule().getName());
						tmp += (outPort ? "\" portTargetName=\""
								+ port.getCable().getInPort().getLabel()
								+ "\" />\n"
								: "\" portTargetName=\""
										+ port.getCable().getOutPort()
												.getLabel() + "\" />\n");

					}

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			tmp += "		</Controller>\n";
			
			//On ajoute la presentation du module
			ICModule icm = (ICModule) m;
			IPModule ipm = icm.getPresentation();

			tmp += "		<Presentation>\n" + "			" + "<Location x=\""
					+ ((Component) ipm).getX() + "\" y=\""
					+ ((Component) ipm).getY() + "\" />"

					+ "\n		</Presentation>\n";

			tmp += "	</Module>\n";
			for (ICCable cable : cables) {
				tmp += "	<Cable id=\"" + cable.getNumCable() + "\">\n";
				tmp += "		<Color value=\""
						+ cable.getPresentation().getColorPosition()
						+ "\" />\n";
				tmp += "	</Cable>\n";

			}
			save += tmp;
		}

		save += "</Configuration>";
		try {
			if (!path.endsWith(".synth"))
				path += ".synth";
			FileWriter fw = new FileWriter(path);
			fw.write(save);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#clearAll()
	 */
	@Override
	public void clearAll() {
		for (IModule module : modules) {
			module.resetCounterInstance();
			removeModule(module);
		}
		Cable.resetCounterInstance();
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#allModulesOn()
	 */
	@Override
	public void allModulesOn() {
		for (IModule module : modules) {
			ICModule cModule = (ICModule) module;
			cModule.start();
			cModule.getPresentation().start();
		}

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#allModulesOff()
	 */
	@Override
	public void allModulesOff() {
		for (IModule module : modules) {
			ICModule cModule = (ICModule) module;
			cModule.stop();
			cModule.getPresentation().stop();
		}

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.workspace.ICWorkspace#giveFocus()
	 */
	public void giveFocus() {
		((Component) presentationWS).requestFocus();

	}

}
