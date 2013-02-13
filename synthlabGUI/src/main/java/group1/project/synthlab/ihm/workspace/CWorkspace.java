package group1.project.synthlab.ihm.workspace;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.ihm.cable.ICCable;
import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.CModule;
import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.ihm.module.eg.CEGModule;
import group1.project.synthlab.ihm.module.out.COutModule;
import group1.project.synthlab.ihm.module.piano.CPianoModule;
import group1.project.synthlab.ihm.module.vca.CVCAModule;
import group1.project.synthlab.ihm.module.vco.CVCOModule;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.module.out.OutModule;
import group1.project.synthlab.workspace.Workspace;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	public void addOneVCFModule() {
		addModule(factory.createVCFModule());
	}

	@Override
	public void addOnePianoModule() {
		addModule(factory.createPianoModule());

	}

	@Override
	public void saveConfiguration() {
		saveReflexion("toto");
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
		// on demande le fichier à l'utilisateur
		File f = presentation.askFileChooser();
		if (f != null) {
			try {
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = null;
				builder = builderFactory.newDocumentBuilder();
				Document document = builder.parse(new FileInputStream(f));
				configuration = document.getFirstChild();

			} catch (Exception e) {
				e.printStackTrace();
			}
			// on supprime tous les modules
			for (IModule m : modules) {
				ICModule cm = (ICModule) m;
				presentation.removeModule(cm.getPresentation());
			}
			modules.clear();
			COutModule.resetModuleCount();
			CVCOModule.resetModuleCount();

			parcourirTous();
		}
	}

	// variables pour le chargement
	private Node configuration;
	private double tmp_x, tmp_y;

	public void parcourirTous() {
		NodeList listModules = configuration.getChildNodes();
		for (int i = 0; i < listModules.getLength(); i++) {
			Node node = listModules.item(i);
			if (node.getNodeName().equals("OutModule")) {
				COutModule com = parcourirOutModule(node);
				addModule(com);
				com.updateLocation(tmp_x, tmp_y);
			} else if (node.getNodeName().equals("VCOModule")) {
				CVCOModule com = parcourirVCOModule(node);
				addModule(com);
				com.updateLocation(tmp_x, tmp_y);
			} else if (node.getNodeName().equals("CPianoModule")) {
				CPianoModule com = parcourirPianoModule(node);
				addModule(com);
				com.updateLocation(tmp_x, tmp_y);
			} else if (node.getNodeName().equals("EGModule")) {
				CEGModule com = parcourirEGModule(node);
				addModule(com);
				com.updateLocation(tmp_x, tmp_y);
			} else if (node.getNodeName().equals("VCAModule")) {
				CVCAModule com = parcourirVCAModule(node);
				addModule(com);
				com.updateLocation(tmp_x, tmp_y);
			}
		}
	}

	private CVCAModule parcourirVCAModule(Node node) {
		// TODO Auto-generated method stub
		CVCAModule cvca = (CVCAModule) factory.createVCAModule();
		NodeList l = node.getChildNodes();
		for (int i = 0; i < l.getLength(); i++) {
			if (l.item(i).getNodeName().equals("A0")) {
				cvca.updateA0(Double.parseDouble(l.item(i).getFirstChild()
						.getNodeValue()));
			} else if (l.item(i).getNodeName().equals("Location")) {
				Element e = (Element) l.item(i);
				double x = Double.parseDouble(e.getAttribute("x"));
				double y = Double.parseDouble(e.getAttribute("y"));
				cvca.updateLocation(x, y);
				this.tmp_x = x;
				this.tmp_y = y;
			}
		}
		return cvca;
	}

	private CEGModule parcourirEGModule(Node node) {
		// TODO Auto-generated method stub
		CEGModule ceg = (CEGModule) factory.createEGModule();
		NodeList l = node.getChildNodes();
		for (int i = 0; i < l.getLength(); i++) {
			if (l.item(i).getNodeName().equals("Attack")) {
				ceg.updateAttack(Double.parseDouble(l.item(i).getFirstChild()
						.getNodeValue()));
			} else if (l.item(i).getNodeName().equals("Decay")) {
				ceg.updateDecay(Double.parseDouble(l.item(i).getFirstChild()
						.getNodeValue()));
			} else if (l.item(i).getNodeName().equals("Release")) {
				ceg.updateRelease(Double.parseDouble(l.item(i).getFirstChild()
						.getNodeValue()));
			} else if (l.item(i).getNodeName().equals("Hold")) {
				ceg.updateHold(Double.parseDouble(l.item(i).getFirstChild()
						.getNodeValue()));
			} else if (l.item(i).getNodeName().equals("Decibel")) {
				ceg.updateDecibel(Double.parseDouble(l.item(i).getFirstChild()
						.getNodeValue()));
			} else if (l.item(i).getNodeName().equals("Location")) {
				Element e = (Element) l.item(i);
				double x = Double.parseDouble(e.getAttribute("x"));
				double y = Double.parseDouble(e.getAttribute("y"));
				ceg.updateLocation(x, y);
				this.tmp_x = x;
				this.tmp_y = y;
			}
		}
		return ceg;
	}

	private CPianoModule parcourirPianoModule(Node node) {

		// TODO Auto-generated method stub
		CPianoModule cvcop = (CPianoModule) factory.createPianoModule();
		NodeList l = node.getChildNodes();
		for (int i = 0; i < l.getLength(); i++) {
			if (l.item(i).getNodeName().equals("OctaveStart")) {
				cvcop.updateOctaveStart(Integer.parseInt(l.item(i)
						.getFirstChild().getNodeValue()));
			} else if (l.item(i).getNodeName().equals("Location")) {
				Element e = (Element) l.item(i);
				double x = Double.parseDouble(e.getAttribute("x"));
				double y = Double.parseDouble(e.getAttribute("y"));
				cvcop.updateLocation(x, y);
				this.tmp_x = x;
				this.tmp_y = y;
			}
		}
		return cvcop;
	}

	private CVCOModule parcourirVCOModule(Node node) {
		// TODO Auto-generated method stub
		CVCOModule cvco = (CVCOModule) factory.createVCOModule();
		NodeList l = node.getChildNodes();
		for (int i = 0; i < l.getLength(); i++) {
			if (l.item(i).getNodeName().equals("CoarseAdjustment")) {
				cvco.updateCoarseAdjustment(Integer.parseInt(l.item(i)
						.getFirstChild().getNodeValue()));
			} else if (l.item(i).getNodeName().equals("FineAdjustment")) {
				// System.out.println("db="+l.item(i).getFirstChild().getNodeValue());
				Double d = Double.parseDouble(l.item(i).getFirstChild()
						.getNodeValue());
				cvco.updateFineAdjustment(d);
			} else if (l.item(i).getNodeName().equals("Location")) {
				Element e = (Element) l.item(i);
				double x = Double.parseDouble(e.getAttribute("x"));
				double y = Double.parseDouble(e.getAttribute("y"));
				cvco.updateLocation(x, y);
				this.tmp_x = x;
				this.tmp_y = y;
			}
		}
		return cvco;
	}

	private COutModule parcourirOutModule(Node node) {
		// TODO Auto-generated method stub
		COutModule com = (COutModule) factory.createOutModule();
		NodeList l = node.getChildNodes();
		for (int i = 0; i < l.getLength(); i++) {
			if (l.item(i).getNodeName().equals("Distribution")) {
				// System.out.println(l.item(i).getFirstChild().getNodeValue());
				if (l.item(i).getFirstChild().getNodeValue().equals("NORMAL")) {
					com.updateDistribution(OutModule.Distribution.NORMAL);
				} else {
					com.updateDistribution(OutModule.Distribution.DISTRIBUTED);
				}
			} else if (l.item(i).getNodeName().equals("AttenuationDB")) {
				// System.out.println("db="+l.item(i).getFirstChild().getNodeValue());
				Double db = Double.parseDouble(l.item(i).getFirstChild()
						.getNodeValue());
				System.out.println(db);
				com.updateAttenuation(db);
				System.out.println(com.getAttenuation());
			} else if (l.item(i).getNodeName().equals("Location")) {
				Element e = (Element) l.item(i);
				double x = Double.parseDouble(e.getAttribute("x"));
				double y = Double.parseDouble(e.getAttribute("y"));
				com.updateLocation(x, y);
				this.tmp_x = x;
				this.tmp_y = y;
			}
		}
		return com;
	}

	public void addFileInModule() {
		addModule(factory.createFileInModule());

	}

	@Override
	public void addOneEGModule() {
		addModule(factory.createEGModule());

	}

	@Override
	public void addEQViewModule() {
		addModule(factory.createEQViewModule());

	}

	@Override
	public void addMicroModule() {
		addModule(factory.createMicroModule());

	}

	@Override
	public void addEQModule() {
		addModule(factory.createEQModule());

	}

	@Override
	public void addOSCModule() {
		addModule(factory.createOSCModule());

	}

	@Override
	public void quitApp() {
		System.exit(0);

	}

	public static Field[] getAllFields(Class klass) {
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(klass.getDeclaredFields()));
		if (klass.getSuperclass() != null) {
			fields.addAll(Arrays.asList(getAllFields(klass.getSuperclass())));
		}
		return fields.toArray(new Field[] {});
	}

	public static boolean isEnumType(Class<?> clazz) { 
	    return ( Enum.class.isAssignableFrom(clazz) && Enum.class!=clazz ); 
	 }
	
	 public static boolean isEnumType2(Class<?> clazz) { 
		    return ( clazz.isEnum() || (clazz.getSuperclass()!=null && clazz.getSuperclass().isEnum()) ); 
		  }
	 
	 @SuppressWarnings("unchecked") // cast vers (Class<? extends Enum>) 
	  public static Class<? extends Enum> getEnumDeclaringClass(Class<?> clazz) { 
	    if (clazz.isEnum()) { 
	      return (Class<? extends Enum>) clazz; 
	    } 
	    clazz = clazz.getSuperclass(); 
	    if (clazz!=null && clazz.isEnum()) { 
	      return (Class<? extends Enum>) clazz; 
	    } 
	    return null; // ce n'est pas une enum 
	  }
	
	public void saveReflexion(String filename) {

		String save = "<Configuration>\n";
		for (IModule m : modules) {
			String tmp = "";
			tmp = "	<Module name=\"" + m.getName() + "\" type=\""
					+ m.getClass().getSimpleName() + "\">\n";

			tmp += "		<Controller>\n";
			for (Field f : getAllFields(m.getClass())) {
				f.setAccessible(true);
				//System.out.println();
				try {
					System.err.println(f.getName());
//					isEnumType(f.getDeclaringClass()) || isEnumType2(f.getDeclaringClass()) || getEnumDeclaringClass(f.getDeclaringClass())!=null
//					
//					
//					for(Class c: m.getClass().getDeclaringClass().get)
//						System.err.println(c.getName());
					if (Modifier.isFinal(f.getModifiers()) || f.getType().isEnum()) {

						continue;
					}
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} 
				try {
					if (f.getType().isPrimitive()) {

						// System.out.println("		"+f.getName() + ":" +
						// f.get(m));
						tmp += "			<Attr name=\"" + f.getName() + "\" type=\""
								+ f.getType() + "\"  value=\"" + f.get(m)
								+ "\" isArray=\"false\"/>\n";

					} else if (f.getType().isArray()) {
						f.setAccessible(true);
						Object array = f.get(m);
						
						Class cType = f.getType().getComponentType();
						int length = Array.getLength(array);
						System.out.println(f.getClass().getEnumConstants() != null);
						
						if (cType.isPrimitive()) {
							tmp += "			<Attr name=\"" + f.getName()
									+ "\" type=\"" + cType.getName()
									+ "\" isArray=\"true\">\n";
							for (int i = 0; i < length; i++) {
								Object element = Array.get(array, i);
							
								tmp += "				<item value=\"" + element
										+ "\" />\n";
							}
							tmp += "			</Attr>\n";
						}
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			tmp += "		</Controller>\n";
			// /!\ajouter presentation/!\
			ICModule icm = (ICModule) m;
			icm.getPresentation();

			tmp += "	</Module>\n";
			save += tmp;
		}

		save += "</Configuration>";
		System.out.println(save);

	}

}
