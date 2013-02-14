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
import group1.project.synthlab.ihm.port.PPort;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CWorkspace extends Workspace implements ICWorkspace {
	protected ICCable drawingCable;
	protected IPWorkspace presentation;
	private String save;

	public CWorkspace(CFactory factory) {
		super(factory);
		this.presentation = new PWorkspace(this);
		addModule(factory.createOutModule());
	}

	public IPWorkspace getPresentation() {
		return presentation;
	}

	public ICCable getDrawingCable() {
		return drawingCable;
	}

	public void setDrawingCable(ICCable drawingCable) {

		if (drawingCable != null && this.drawingCable == null) {
			this.drawingCable = drawingCable;
			presentation.addCable(drawingCable.getPresentation());
		} else
			this.drawingCable = null;

	}

	public boolean isDrawingCable() {
		return drawingCable != null;
	}

	public static ICWorkspace getInstance() {
		if (instance == null) {
			CFactory factory = new CFactory();
			instance = factory.createWorkSpace();
		}
		return (ICWorkspace) instance;
	}

	public void removeCable(ICable cCable) {
		presentation.removeCable(((ICCable) cCable).getPresentation());
	}

	@Override
	public void addModule(IModule module) {
		super.addModule(module);
		presentation.addModule(((ICModule) module).getPresentation());
	}

	@Override
	public void removeModule(IModule module) {
		if (isDrawingCable()) {
			removeCable(getDrawingCable());
			setDrawingCable(null);
		}
		presentation.removeModule(((ICModule) module).getPresentation());
		super.removeModule(module);
	}

	public void addOneVCOModule() {
		addModule(factory.createVCOModule());

	}

	public void addOneOutModule() {
		addModule(factory.createOutModule());

	}

	public void addOneMultiplexer() {
		addModule(factory.createMultiplexerModule());
	}

	@Override
	public void addOneSequencer() {
		addModule(factory.createSequencerModule());
	}

	public void addOneVCAModule() {
		addModule(factory.createVCAModule());
	}

	@Override
	public void addOnePianoModule() {
		addModule(factory.createPianoModule());

	}

	@Override
	public void addOneNoiseModule() {
		addModule(factory.createNoiseModule());

	}

	@Override
	public void addOneEGModule() {
		addModule(factory.createEGModule());

	}

	@Override
	public void addOneEQViewModule() {
		addModule(factory.createEQViewModule());

	}

	@Override
	public void addOneMicroModule() {
		addModule(factory.createMicroModule());

	}

	@Override
	public void addOneEQModule() {
		addModule(factory.createEQModule());

	}

	@Override
	public void addOneOSCModule() {
		addModule(factory.createOSCModule());

	}

	@Override
	public void addOneVCFLPModule() {
		addModule(factory.createVCFLPModule());

	}

	@Override
	public void addOneVCFHPModule() {
		addModule(factory.createVCFHPModule());
	}

	@Override
	public void quitApp() {
		System.exit(0);

	}

	@Override
	public void saveConfiguration() {
		File f = presentation.saveFileDialog();
		if (f == null)
			return;
		saveConfiguration(f.getName(), f.getAbsolutePath());

	}

	@Override
	public void loadConfiguration() {
		File f = presentation.openFileDialog();
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
					presentation.removeModule(cm.getPresentation());
				}
				modules.clear();
				parcourirModules(configuration);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Field findField(String nomAttribut, Class module) {
		Field field;
		try {
			field = module.getDeclaredField(nomAttribut);
			return field;
		} catch (java.lang.NoSuchFieldException e1) {
			if (module.getSuperclass() != null) {
				return findField(nomAttribut, module.getSuperclass());
			}
			return null;
		}

	}

	private void parcourirModules(Node configuration) {
		Map<String, ICModule> modulesInstances = new HashMap<>();
		Map<String, ICInPort> portsInInstances = new HashMap<>();
		Map<String, ICOutPort> portsOutInstances = new HashMap<>();
		Element e = (Element) configuration;
		NodeList modulesNodes = e.getElementsByTagName("Module");
		for (int i = 0; i < modulesNodes.getLength(); i++) {
			Element m = (Element) modulesNodes.item(i);
			String type = m.getAttribute("type");
			try {
				CFactory factory = new CFactory();
				Method method = CFactory.class.getMethod("create" + type);
				ICModule module = (ICModule) method.invoke(factory);
				System.out.println(module.getName() + " " + type);
				modulesInstances.put(module.getName(), module);

				// controller
				Node cont = m.getElementsByTagName("Controller").item(0);
				// attributs
				NodeList attr = ((Element) cont).getElementsByTagName("Attr");

				for (int j = 0; j < attr.getLength(); j++) {
					Element a = (Element) attr.item(j);
					Field field;
					field = findField(a.getAttribute("name"), module.getClass());
					if (field != null) {
						System.out.println("\n\n\nisArray : "
								+ a.getAttribute("struct"));
						if (a.getAttribute("struct").equals("array")) {
							field.setAccessible(true);
							// Object[] tab = (Object[]) field.get(module);
							String typeString = CTools.primitiveToObject(a
									.getAttribute("type"));
							System.out.println(typeString);
							int nbItem = a.getElementsByTagName("item")
									.getLength();
							// Class c = Class.forName(typeString);

							Class c = CTools.primitiveToClass(a
									.getAttribute("type"));
							Object array = Array.newInstance(c, nbItem);
							for (int ii = 0; ii < nbItem; ii++) {

								Class tmp = Class.forName(typeString);
								Object res = ValueOfString.valueOf(
										tmp,
										((Element) a.getElementsByTagName(
												"item").item(ii))
												.getAttribute("value"));
								Array.set(array, ii, res);
								// tab[ii] = res;
								// System.out.println(tab[ii]);
							}

							field.set(module, array);
							field.setAccessible(false);
							// System.out.println(nbItem);

						} else if (field.getType().isPrimitive()) {
							field.setAccessible(true);
							String typeString = CTools.primitiveToObject(a
									.getAttribute("type"));
							System.out.println(typeString + " "
									+ a.getAttribute("value"));
							Class c = Class.forName(typeString);

							Object res = ValueOfString.valueOf(c,
									a.getAttribute("value"));
							field.set(module, res);
							field.setAccessible(false);
						} else if (field.getType().isEnum()) {
							System.out.println("enum : " + field.getName());
							System.out.println(a.getAttribute("type") + " "
									+ a.getAttribute("value"));
							field.setAccessible(true);
							field.set(module, Enum.valueOf(
									(Class<Enum>) field.getType(),
									a.getAttribute("value")));
							field.setAccessible(false);
						}
					}

				}

				// ports et cables
				NodeList ports = m.getElementsByTagName("Port");
				for (int j = 0; j < ports.getLength(); j++) {
					Element portXML = (Element) ports.item(j);
					String typePort = portXML.getAttribute("type");
					ICOutPort portOut;
					ICInPort portIn;
					if (typePort.equals("in")) {
						Field field = findField(
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
						Field field = findField(
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

				// presentation
				Element pres = (Element) m.getElementsByTagName("Presentation")
						.item(0);
				Element location = (Element) pres.getElementsByTagName(
						"Location").item(0);
				int locationX = Integer.parseInt(location.getAttribute("x"));
				int locationY = Integer.parseInt(location.getAttribute("y"));

				// chargement
				CWorkspace.getInstance().addModule(module);
				module.refresh();
				module.getPresentation().updateLocation(locationX, locationY);

			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}

		for (int i = 0; i < modulesNodes.getLength(); i++) {
			Element m = (Element) modulesNodes.item(i);
			ICModule module = modulesInstances.get(m.getAttribute("name"));
			// ports et cables
			NodeList ports = m.getElementsByTagName("Port");
			for (int j = 0; j < ports.getLength(); j++) {
				Element portXML = (Element) ports.item(j);
				String typePort = portXML.getAttribute("type");
				ICCable cable = (ICCable) this.factory.createCable();
				ICOutPort portOut = null;
				ICInPort portIn = null;
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
				
				if (portOut == null || portIn == null
						|| portOut.getCable() != null
						|| portIn.getCable() != null) 
					continue;
				try {
					IPCable presentationCable = cable.getPresentation();
					presentation.addCable(presentationCable);					
					cable.setOutPort(portOut);
					cable.setInPort(portIn);
					
					//couleur du cable
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
		((Component) presentation).repaint();
	}

	public void saveConfiguration(String name, String path) {
		String save = "<Configuration name=\"" + name + "\">\n";
		Set<ICCable> cables = new HashSet<>();
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
		System.out.println(save);
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

	@Override
	public void clearAll() {
		for (IModule module : modules) {
			module.resetCounterInstance();
			removeModule(module);
		}
		Cable.resetCounterInstance();
	}

	@Override
	public void allModulesOn() {
		for (IModule module : modules) {
			ICModule cModule = (ICModule) module;
			cModule.start();
			cModule.getPresentation().start();
		}

	}

	@Override
	public void allModulesOff() {
		for (IModule module : modules) {
			ICModule cModule = (ICModule) module;
			cModule.stop();
			cModule.getPresentation().stop();
		}

	}

	public void giveFocus() {
		((Component) presentation).requestFocus();

	}

}
