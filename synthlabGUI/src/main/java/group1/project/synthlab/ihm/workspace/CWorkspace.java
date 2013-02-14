package group1.project.synthlab.ihm.workspace;

import group1.project.synthlab.cable.Cable;
import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.ihm.cable.ICCable;
import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.ihm.module.IPModule;
import group1.project.synthlab.ihm.module.out.COutModule;
import group1.project.synthlab.ihm.tools.CTools;
import group1.project.synthlab.ihm.tools.ValueOfString;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.module.out.OutModule;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.workspace.Workspace;

<<<<<<< HEAD
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
=======
import java.awt.Component;
>>>>>>> 3413653a1cd8c04c66646c875ed3cfe92fe06730
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
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
	public void saveConfiguration() {
		saveConfiguration("toto");
		// saveConfiguration("toto");
		// TODO Auto-generated method stub
		// on demande le nom de fichier
		// String name = presentation.askFileName();
		// if(name!=null){
		// //bouton cancel non appuyé
		// if(name.length()>0){
		// //on sauvegarde
		// save="";
		// save+="<Configuration>\n";
		// for(IModule m : modules){
		// ICModule cm = (ICModule) m;
		// save+=cm.saveConfiguration();
		// }
		// save+="</Configuration>";
		// System.out.println(save);
		// try {
		// FileWriter f = new FileWriter(name+".synt");
		// f.write(save);
		// f.close();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		// else{
		// presentation.showError("Erreur lors de la sauvegarde : le nom de fichier doit contenir au moins un caractère");
		// }
		// }
	}

	@Override
	public void loadConfiguration() {
<<<<<<< HEAD
		 clearAll();
		 File f = presentation.askFileChooser();
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
		 
//		// on demande le fichier à l'utilisateur
//		File f = presentation.askFileChooser();
//		if (f != null) {
//			try {
//				DocumentBuilderFactory builderFactory = DocumentBuilderFactory
//						.newInstance();
//				DocumentBuilder builder = null;
//				builder = builderFactory.newDocumentBuilder();
//				Document document = builder.parse(new FileInputStream(f));
//				configuration = document.getFirstChild();
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			// on supprime tous les modules
//			for (IModule m : modules) {
//				ICModule cm = (ICModule) m;
//				presentation.removeModule(cm.getPresentation());
//			}
//			modules.clear();
//			COutModule.resetModuleCount();
//			CVCOModule.resetModuleCount();
//
//			parcourirTous();
//		}

	}
//
//	// variables pour le chargement
//	private Node configuration;
//	private double tmp_x, tmp_y;
//
//	public void parcourirTous() {
//		NodeList listModules = configuration.getChildNodes();
//		for (int i = 0; i < listModules.getLength(); i++) {
//			Node node = listModules.item(i);
//			if (node.getNodeName().equals("OutModule")) {
//				COutModule com = parcourirOutModule(node);
//				addModule(com);
//				com.updateLocation(tmp_x, tmp_y);
//			} else if (node.getNodeName().equals("VCOModule")) {
//				CVCOModule com = parcourirVCOModule(node);
//				addModule(com);
//				com.updateLocation(tmp_x, tmp_y);
//			} else if (node.getNodeName().equals("CPianoModule")) {
//				CPianoModule com = parcourirPianoModule(node);
//				addModule(com);
//				com.updateLocation(tmp_x, tmp_y);
//			} else if (node.getNodeName().equals("EGModule")) {
//				CEGModule com = parcourirEGModule(node);
//				addModule(com);
//				com.updateLocation(tmp_x, tmp_y);
//			} else if (node.getNodeName().equals("VCAModule")) {
//				CVCAModule com = parcourirVCAModule(node);
//				addModule(com);
//				com.updateLocation(tmp_x, tmp_y);
//			}
//		}
//	}
//
//	private CVCAModule parcourirVCAModule(Node node) {
//		// TODO Auto-generated method stub
//		CVCAModule cvca = (CVCAModule) factory.createVCAModule();
//		NodeList l = node.getChildNodes();
//		for (int i = 0; i < l.getLength(); i++) {
//			if (l.item(i).getNodeName().equals("A0")) {
//				cvca.updateA0(Double.parseDouble(l.item(i).getFirstChild()
//						.getNodeValue()));
//			} else if (l.item(i).getNodeName().equals("Location")) {
//				Element e = (Element) l.item(i);
//				double x = Double.parseDouble(e.getAttribute("x"));
//				double y = Double.parseDouble(e.getAttribute("y"));
//				cvca.updateLocation(x, y);
//				this.tmp_x = x;
//				this.tmp_y = y;
//			}
//		}
//		return cvca;
//	}
//
//	private CEGModule parcourirEGModule(Node node) {
//		// TODO Auto-generated method stub
//		CEGModule ceg = (CEGModule) factory.createEGModule();
//		NodeList l = node.getChildNodes();
//		for (int i = 0; i < l.getLength(); i++) {
//			if (l.item(i).getNodeName().equals("Attack")) {
//				ceg.updateAttack(Double.parseDouble(l.item(i).getFirstChild()
//						.getNodeValue()));
//			} else if (l.item(i).getNodeName().equals("Decay")) {
//				ceg.updateDecay(Double.parseDouble(l.item(i).getFirstChild()
//						.getNodeValue()));
//			} else if (l.item(i).getNodeName().equals("Release")) {
//				ceg.updateRelease(Double.parseDouble(l.item(i).getFirstChild()
//						.getNodeValue()));
//			} else if (l.item(i).getNodeName().equals("Hold")) {
//				ceg.updateHold(Double.parseDouble(l.item(i).getFirstChild()
//						.getNodeValue()));
//			} else if (l.item(i).getNodeName().equals("Decibel")) {
//				ceg.updateDecibel(Double.parseDouble(l.item(i).getFirstChild()
//						.getNodeValue()));
//			} else if (l.item(i).getNodeName().equals("Location")) {
//				Element e = (Element) l.item(i);
//				double x = Double.parseDouble(e.getAttribute("x"));
//				double y = Double.parseDouble(e.getAttribute("y"));
//				ceg.updateLocation(x, y);
//				this.tmp_x = x;
//				this.tmp_y = y;
//			}
//		}
//		return ceg;
//	}
//
//	private CPianoModule parcourirPianoModule(Node node) {
//
//		// TODO Auto-generated method stub
//		CPianoModule cvcop = (CPianoModule) factory.createPianoModule();
//		NodeList l = node.getChildNodes();
//		for (int i = 0; i < l.getLength(); i++) {
//			if (l.item(i).getNodeName().equals("OctaveStart")) {
//				cvcop.updateOctaveStart(Integer.parseInt(l.item(i)
//						.getFirstChild().getNodeValue()));
//			} else if (l.item(i).getNodeName().equals("Location")) {
//				Element e = (Element) l.item(i);
//				double x = Double.parseDouble(e.getAttribute("x"));
//				double y = Double.parseDouble(e.getAttribute("y"));
//				cvcop.updateLocation(x, y);
//				this.tmp_x = x;
//				this.tmp_y = y;
//			}
//		}
//		return cvcop;
//	}
//
//	private CVCOModule parcourirVCOModule(Node node) {
//		// TODO Auto-generated method stub
//		CVCOModule cvco = (CVCOModule) factory.createVCOModule();
//		NodeList l = node.getChildNodes();
//		for (int i = 0; i < l.getLength(); i++) {
//			if (l.item(i).getNodeName().equals("CoarseAdjustment")) {
//				cvco.updateCoarseAdjustment(Integer.parseInt(l.item(i)
//						.getFirstChild().getNodeValue()));
//			} else if (l.item(i).getNodeName().equals("FineAdjustment")) {
//				// System.out.println("db="+l.item(i).getFirstChild().getNodeValue());
//				Double d = Double.parseDouble(l.item(i).getFirstChild()
//						.getNodeValue());
//				cvco.updateFineAdjustment(d);
//			} else if (l.item(i).getNodeName().equals("Location")) {
//				Element e = (Element) l.item(i);
//				double x = Double.parseDouble(e.getAttribute("x"));
//				double y = Double.parseDouble(e.getAttribute("y"));
//				cvco.updateLocation(x, y);
//				this.tmp_x = x;
//				this.tmp_y = y;
//			}
//		}
//		return cvco;
//	}
//
//	private COutModule parcourirOutModule(Node node) {
//		// TODO Auto-generated method stub
//		COutModule com = (COutModule) factory.createOutModule();
//		NodeList l = node.getChildNodes();
//		for (int i = 0; i < l.getLength(); i++) {
//			if (l.item(i).getNodeName().equals("Distribution")) {
//				// System.out.println(l.item(i).getFirstChild().getNodeValue());
//				if (l.item(i).getFirstChild().getNodeValue().equals("NORMAL")) {
//					com.updateDistribution(OutModule.Distribution.NORMAL);
//				} else {
//					com.updateDistribution(OutModule.Distribution.DISTRIBUTED);
//				}
//			} else if (l.item(i).getNodeName().equals("AttenuationDB")) {
//				// System.out.println("db="+l.item(i).getFirstChild().getNodeValue());
//				Double db = Double.parseDouble(l.item(i).getFirstChild()
//						.getNodeValue());
//				System.out.println(db);
//				com.updateAttenuation(db);
//				System.out.println(com.getAttenuation());
//			} else if (l.item(i).getNodeName().equals("Location")) {
//				Element e = (Element) l.item(i);
//				double x = Double.parseDouble(e.getAttribute("x"));
//				double y = Double.parseDouble(e.getAttribute("y"));
//				com.updateLocation(x, y);
//				this.tmp_x = x;
//				this.tmp_y = y;
//			}
//		}
//		return com;
//	}
	
	public Field findField(String nomAttribut, Class module){
		Field field;
		try{
			field = module.getDeclaredField(nomAttribut);
			return field;
		}catch(java.lang.NoSuchFieldException e1){
			if(module.getSuperclass() != null){
				return findField(nomAttribut, module.getSuperclass());
			}
			return null;
		}
		
	}

	private void parcourirModules(Node configuration) {
		// TODO Auto-generated method stub
		Element e = (Element) configuration;
		System.out.println(configuration);
		NodeList modulesNodes = e.getElementsByTagName("Module");
		for(int i = 0 ;i<modulesNodes.getLength();i++){
			Element m = (Element) modulesNodes.item(i);
			String type = m.getAttribute("type");
			try {
				CFactory factory = new CFactory();
				Method method = CFactory.class.getMethod("create" + type);
				ICModule module = (ICModule) method.invoke(factory);
				System.out.println(module.getName()+" "+type);
				
				
				//controller
				Node cont = m.getElementsByTagName("Controller").item(0);
				//attributs
				NodeList attr = ((Element)cont).getElementsByTagName("Attr");
				
				for(int j = 0; j<attr.getLength();j++){
					Element a = (Element) attr.item(j);
					Field field;
					field = findField(a.getAttribute("name"), module.getClass());
					if(field!=null){
						System.out.println("\n\n\nisArray : "+a.getAttribute("struct"));
						if(a.getAttribute("struct").equals("array")){
							field.setAccessible(true);
							//Object[] tab = (Object[]) field.get(module);
							String typeString = CTools.primitiveToObject(a.getAttribute("type"));
							System.out.println(typeString);
							int nbItem = a.getElementsByTagName("item").getLength();
							//Class c = Class.forName(typeString);

							Class c = CTools.primitiveToClass(a.getAttribute("type"));
							Object array = Array.newInstance(c, nbItem);
							for(int ii=0;ii<nbItem;ii++){
								
								Class tmp = Class.forName(typeString);
								Object res = ValueOfString.valueOf(tmp,((Element)a.getElementsByTagName("item").item(ii)).getAttribute("value"));
								Array.set(array, ii, res);
								//tab[ii] = res;
								//System.out.println(tab[ii]);
							}
							
							
							field.set(module,array);
					
							
							//System.out.println(nbItem);
							
						}else if(field.getType().isPrimitive()){
							field.setAccessible(true);
							String typeString = CTools.primitiveToObject(a.getAttribute("type"));
							System.out.println(typeString+" "+a.getAttribute("value"));
							Class c = Class.forName(typeString);

							Object res = ValueOfString.valueOf(c,a.getAttribute("value"));
							field.set(module, res);
						}
						else if(field.getType().isEnum()){
							System.out.println("enum : "+field.getName());
							System.out.println(a.getAttribute("type")+" "+a.getAttribute("value"));
							field.setAccessible(true);							
							field.set(module, Enum.valueOf((Class<Enum>) field.getType(), a.getAttribute("value")));						
						}
					}

				}
				
				//presentation
//				Node pres = m.getElementsByTagName("Presentation").item(0);
//				
//				String presSer = pres.getFirstChild().getNodeValue().replace("	", "");
//				
//				Object p = CTools.fromString(presSer);
//				System.out.println(p);
//				Field field;
//				field = findField("presentation", module.getClass());
//				System.out.println(field);
//				field.setAccessible(true);
//				field.set(module, p);			
//				System.out.println(((COutModule)module).getDistribution());
				module.initPresentation(null);
				
				
				CWorkspace.getInstance().addModule(module);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
=======
		clearAll();

>>>>>>> 3413653a1cd8c04c66646c875ed3cfe92fe06730
		// // on demande le fichier à l'utilisateur
		// File f = presentation.askFileChooser();
		// if (f != null) {
		// try {
		// DocumentBuilderFactory builderFactory = DocumentBuilderFactory
		// .newInstance();
		// DocumentBuilder builder = null;
		// builder = builderFactory.newDocumentBuilder();
		// Document document = builder.parse(new FileInputStream(f));
		// configuration = document.getFirstChild();
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// // on supprime tous les modules
		// for (IModule m : modules) {
		// ICModule cm = (ICModule) m;
		// presentation.removeModule(cm.getPresentation());
		// }
		// modules.clear();
		// COutModule.resetModuleCount();
		// CVCOModule.resetModuleCount();
		//
		// parcourirTous();
		// }

	

	//
	// // variables pour le chargement
	// private Node configuration;
	// private double tmp_x, tmp_y;
	//
	// public void parcourirTous() {
	// NodeList listModules = configuration.getChildNodes();
	// for (int i = 0; i < listModules.getLength(); i++) {
	// Node node = listModules.item(i);
	// if (node.getNodeName().equals("OutModule")) {
	// COutModule com = parcourirOutModule(node);
	// addModule(com);
	// com.updateLocation(tmp_x, tmp_y);
	// } else if (node.getNodeName().equals("VCOModule")) {
	// CVCOModule com = parcourirVCOModule(node);
	// addModule(com);
	// com.updateLocation(tmp_x, tmp_y);
	// } else if (node.getNodeName().equals("CPianoModule")) {
	// CPianoModule com = parcourirPianoModule(node);
	// addModule(com);
	// com.updateLocation(tmp_x, tmp_y);
	// } else if (node.getNodeName().equals("EGModule")) {
	// CEGModule com = parcourirEGModule(node);
	// addModule(com);
	// com.updateLocation(tmp_x, tmp_y);
	// } else if (node.getNodeName().equals("VCAModule")) {
	// CVCAModule com = parcourirVCAModule(node);
	// addModule(com);
	// com.updateLocation(tmp_x, tmp_y);
	// }
	// }
	// }
	//
	// private CVCAModule parcourirVCAModule(Node node) {
	// // TODO Auto-generated method stub
	// CVCAModule cvca = (CVCAModule) factory.createVCAModule();
	// NodeList l = node.getChildNodes();
	// for (int i = 0; i < l.getLength(); i++) {
	// if (l.item(i).getNodeName().equals("A0")) {
	// cvca.updateA0(Double.parseDouble(l.item(i).getFirstChild()
	// .getNodeValue()));
	// } else if (l.item(i).getNodeName().equals("Location")) {
	// Element e = (Element) l.item(i);
	// double x = Double.parseDouble(e.getAttribute("x"));
	// double y = Double.parseDouble(e.getAttribute("y"));
	// cvca.updateLocation(x, y);
	// this.tmp_x = x;
	// this.tmp_y = y;
	// }
	// }
	// return cvca;
	// }
	//
	// private CEGModule parcourirEGModule(Node node) {
	// // TODO Auto-generated method stub
	// CEGModule ceg = (CEGModule) factory.createEGModule();
	// NodeList l = node.getChildNodes();
	// for (int i = 0; i < l.getLength(); i++) {
	// if (l.item(i).getNodeName().equals("Attack")) {
	// ceg.updateAttack(Double.parseDouble(l.item(i).getFirstChild()
	// .getNodeValue()));
	// } else if (l.item(i).getNodeName().equals("Decay")) {
	// ceg.updateDecay(Double.parseDouble(l.item(i).getFirstChild()
	// .getNodeValue()));
	// } else if (l.item(i).getNodeName().equals("Release")) {
	// ceg.updateRelease(Double.parseDouble(l.item(i).getFirstChild()
	// .getNodeValue()));
	// } else if (l.item(i).getNodeName().equals("Hold")) {
	// ceg.updateHold(Double.parseDouble(l.item(i).getFirstChild()
	// .getNodeValue()));
	// } else if (l.item(i).getNodeName().equals("Decibel")) {
	// ceg.updateDecibel(Double.parseDouble(l.item(i).getFirstChild()
	// .getNodeValue()));
	// } else if (l.item(i).getNodeName().equals("Location")) {
	// Element e = (Element) l.item(i);
	// double x = Double.parseDouble(e.getAttribute("x"));
	// double y = Double.parseDouble(e.getAttribute("y"));
	// ceg.updateLocation(x, y);
	// this.tmp_x = x;
	// this.tmp_y = y;
	// }
	// }
	// return ceg;
	// }
	//
	// private CPianoModule parcourirPianoModule(Node node) {
	//
	// // TODO Auto-generated method stub
	// CPianoModule cvcop = (CPianoModule) factory.createPianoModule();
	// NodeList l = node.getChildNodes();
	// for (int i = 0; i < l.getLength(); i++) {
	// if (l.item(i).getNodeName().equals("OctaveStart")) {
	// cvcop.updateOctaveStart(Integer.parseInt(l.item(i)
	// .getFirstChild().getNodeValue()));
	// } else if (l.item(i).getNodeName().equals("Location")) {
	// Element e = (Element) l.item(i);
	// double x = Double.parseDouble(e.getAttribute("x"));
	// double y = Double.parseDouble(e.getAttribute("y"));
	// cvcop.updateLocation(x, y);
	// this.tmp_x = x;
	// this.tmp_y = y;
	// }
	// }
	// return cvcop;
	// }
	//
	// private CVCOModule parcourirVCOModule(Node node) {
	// // TODO Auto-generated method stub
	// CVCOModule cvco = (CVCOModule) factory.createVCOModule();
	// NodeList l = node.getChildNodes();
	// for (int i = 0; i < l.getLength(); i++) {
	// if (l.item(i).getNodeName().equals("CoarseAdjustment")) {
	// cvco.updateCoarseAdjustment(Integer.parseInt(l.item(i)
	// .getFirstChild().getNodeValue()));
	// } else if (l.item(i).getNodeName().equals("FineAdjustment")) {
	// // System.out.println("db="+l.item(i).getFirstChild().getNodeValue());
	// Double d = Double.parseDouble(l.item(i).getFirstChild()
	// .getNodeValue());
	// cvco.updateFineAdjustment(d);
	// } else if (l.item(i).getNodeName().equals("Location")) {
	// Element e = (Element) l.item(i);
	// double x = Double.parseDouble(e.getAttribute("x"));
	// double y = Double.parseDouble(e.getAttribute("y"));
	// cvco.updateLocation(x, y);
	// this.tmp_x = x;
	// this.tmp_y = y;
	// }
	// }
	// return cvco;
	// }
	//
	// private COutModule parcourirOutModule(Node node) {
	// // TODO Auto-generated method stub
	// COutModule com = (COutModule) factory.createOutModule();
	// NodeList l = node.getChildNodes();
	// for (int i = 0; i < l.getLength(); i++) {
	// if (l.item(i).getNodeName().equals("Distribution")) {
	// // System.out.println(l.item(i).getFirstChild().getNodeValue());
	// if (l.item(i).getFirstChild().getNodeValue().equals("NORMAL")) {
	// com.updateDistribution(OutModule.Distribution.NORMAL);
	// } else {
	// com.updateDistribution(OutModule.Distribution.DISTRIBUTED);
	// }
	// } else if (l.item(i).getNodeName().equals("AttenuationDB")) {
	// // System.out.println("db="+l.item(i).getFirstChild().getNodeValue());
	// Double db = Double.parseDouble(l.item(i).getFirstChild()
	// .getNodeValue());
	// System.out.println(db);
	// com.updateAttenuation(db);
	// System.out.println(com.getAttenuation());
	// } else if (l.item(i).getNodeName().equals("Location")) {
	// Element e = (Element) l.item(i);
	// double x = Double.parseDouble(e.getAttribute("x"));
	// double y = Double.parseDouble(e.getAttribute("y"));
	// com.updateLocation(x, y);
	// this.tmp_x = x;
	// this.tmp_y = y;
	// }
	// }
	// return com;
	// }

	public void addOneFileInModule() {
		addModule(factory.createFileInModule());

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

	public void saveConfiguration(String name) {
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
<<<<<<< HEAD
					if (Modifier.isFinal(f.getModifiers()) || (Modifier.isStatic(f.getModifiers()))) {
=======
					if (Modifier.isFinal(f.getModifiers())
							|| (Modifier.isStatic(f.getModifiers()) && f
									.getType().isArray())) {
>>>>>>> 3413653a1cd8c04c66646c875ed3cfe92fe06730
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
						tmp += "			<Port name=\"" + port.getLabel()
								+ "\" nameAttr=\"" + f.getName() + "\" type=\""
								+ f.getType().getName()
								+ "\" refPresentation=\""
								+ port.getCable().getNumCable()
								+ "\" moduleTargetName=\""
								+ port.getModule().getName();
						tmp += (f.getType().isAssignableFrom(IOutPort.class) ? "\" portTargetNameAttr=\""
								+ port.getCable().getInPort().getLabel()
								+ "\" />\n"
								: "\" portTargetNameAttr=\""
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
<<<<<<< HEAD
//			try {
//				String presentationString = CTools.toString(ipm);
//				tmp+="		<Presentation>\n"+
//						"			"+presentationString+
//						"\n		</Presentation>\n";
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
=======
			tmp += "		<Presentation>\n" + "			" + "<Location x=\""
					+ ((Component) ipm).getX() + "\" y=\""
					+ ((Component) ipm).getY() + "\" />"

					+ "\n		</Presentation>\n";

>>>>>>> 3413653a1cd8c04c66646c875ed3cfe92fe06730
			tmp += "	</Module>\n";
			for(ICCable cable: cables) {
				tmp += "	<Cable id=\"" +  cable.getNumCable() + "\">\n";
				tmp += "		<Color value=\"" +  cable.getPresentation().getColorPosition() + "\" />\n";
				tmp += "	</Cable>\n";
			
			}
			save += tmp;
		}

		save += "</Configuration>";
		System.out.println(save);
		try {
			FileWriter f = new FileWriter(new File("essai.conf"));
			f.write(save);
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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

}
