package group1.project.synthlab.ihm.module.fileIn;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.TextArea;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;

import javax.swing.JLabel;

public class PFileInModule extends PModule implements IPFileInModule, DropTargetListener{

	protected ICFileInModule controller;
	protected TextArea file;
	protected DropTarget dropTarget;


	public PFileInModule(final ICFileInModule controller) {
		super(controller);
		this.controller = controller;
		this.setLayout(null);
		//Taille et couleur définie dans la super classe

		//Label et onoff boutons déjà rajoutés dans la super classe

		// Ports
		PPort pportOut = (PPort) (((ICOutPort) controller.getOutPort()).getPresentation());
		pportOut.setLocation(10, 220);

		// textArea
		file = new TextArea();
		file.setLocation(30, 50);
		file.setSize(250, 100);
		file.setPreferredSize(file.getSize());
		file.setBackground(Color.lightGray);

		add(new JLabel("Drop a list from your file chooser here:"), BorderLayout.NORTH);
		
		dropTarget = new DropTarget(file, this);
		setVisible(true);

		// Ajouts des composants
		add(pportOut);
		add(file);

		this.repaint();

	}

	public void dragEnter(DropTargetDragEvent dtde) {
	    System.out.println("Drag Enter");
	  }

	  public void dragExit(DropTargetEvent dte) {
	    System.out.println("Source: " + dte.getSource());
	    System.out.println("Drag Exit");
	  }

	  public void dragOver(DropTargetDragEvent dtde) {
	    System.out.println("Drag Over");
	  }

	  public void dropActionChanged(DropTargetDragEvent dtde) {
	    System.out.println("Drop Action Changed");
	  }

	  public void drop(DropTargetDropEvent dtde) {
	    try {
	      Transferable tr = dtde.getTransferable();
	      DataFlavor[] flavors = tr.getTransferDataFlavors();
	      for (int i = 0; i < flavors.length; i++) {
	        if (flavors[i].isFlavorJavaFileListType()) {
	          dtde.acceptDrop(DnDConstants.ACTION_COPY);
	          List list = (List) tr.getTransferData(flavors[i]);
	          for (int j = 0; j < list.size(); j++) {
	            file.append(list.get(j) + "\n");
	            System.err.println(list.get(j));
	            controller.loadFile(new File(list.get(j).toString()));
	          }
	          dtde.dropComplete(true);
	          return;
	        }
	      }
	      dtde.rejectDrop();
	    } catch (Exception e) {
	      e.printStackTrace();
	      dtde.rejectDrop();
	    }
	  }
}
