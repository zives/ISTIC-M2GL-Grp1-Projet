package group1.project.synthlab.ihm.module.fileIn;

import group1.project.synthlab.ihm.module.PModule;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
	protected TextArea sampleFile;
	protected DropTarget dropTarget;
	protected JLabel fileLabel;


	public PFileInModule(final ICFileInModule controller) {
		super(controller);
		this.controller = controller;
		this.setLayout(null);
		//Taille et couleur définie dans la super classe

		//Label et onoff boutons déjà rajoutés dans la super classe

		// Ports
		PPort pportOut = (PPort) (((ICOutPort) controller.getOutPort()).getPresentation());
		pportOut.setLocation(230, 220);

		// TextArea
		sampleFile = new TextArea();
		sampleFile.setLocation(30, 70);
		sampleFile.setSize(240, 50);
		sampleFile.setPreferredSize(sampleFile.getSize());
		sampleFile.setBackground(Color.lightGray);

		// Label
		fileLabel = new JLabel("Drop a WAV file here :");
		fileLabel.setForeground(Color.LIGHT_GRAY);
		fileLabel.setOpaque(false);
		fileLabel.setLocation(sampleFile.getX(), 50);
		fileLabel.setSize(130, 10);
		fileLabel.setPreferredSize(fileLabel.getSize());
		fileLabel.setBorder(null);
		fileLabel.setFont(new Font("Arial", Font.ITALIC, 10));

		dropTarget = new DropTarget(sampleFile, this);
		setVisible(true);

		// Ajouts des composants
		add(pportOut);
		add(fileLabel);
		add(sampleFile);

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
						sampleFile.append(list.get(j) + "\n");
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
