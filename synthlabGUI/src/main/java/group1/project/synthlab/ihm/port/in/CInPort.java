package group1.project.synthlab.ihm.port.in;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.ihm.cable.CCable;
import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.workspace.CWorkspace;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.in.InPort;

import com.jsyn.ports.ConnectableInput;

/**
 * @author Groupe 1
 * Controleur pour port d'entree
 */
public class CInPort extends InPort implements ICInPort {

	private static final long serialVersionUID = 4313365767212678923L;
	
	protected IPInPort presentation;

	public CInPort(String label, ConnectableInput jSynPort, IModule module,  CFactory factory) {
		super(label, jSynPort, module,  factory);
		presentation = new PInPort(this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.port.ICPort#getPresentation()
	 */
	public IPInPort getPresentation() {
		return presentation;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.port.ICPort#actionCable()
	 */
	public void actionCable() throws BadConnection {
		//Si le port n'est pas deja utilise
		if (!isUsed()) {
			//Et si on dessine actuellement un cable
			if (CWorkspace.getInstance().isDrawingCable()) {
				//On retrouve l'instance de ce cable
				CCable cable = (CCable) CWorkspace.getInstance()
						.getDrawingCable();
				
				try {
					//On attribue l'extremite restante a ce port, le cable est pose
					cable.setInPort(this);
					//On ne dessine plus de cable
					CWorkspace.getInstance().setDrawingCable(null); 
					
				} catch (PortAlreadyUsed e) {
					e.printStackTrace();
				}
				
			}
		} else {
			
		}

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.Port#setCable(group1.project.synthlab.cable.ICable)
	 */
	@Override
	public void setCable(ICable cable) {
		super.setCable(cable);
		presentation.refresh();
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.port.ICPort#checkPutCable()
	 */
	public void checkPutCable() {
		if (CWorkspace.getInstance().isDrawingCable() && isUsed())
			presentation.setForbidden();
		else if (isUsed())
			presentation.setAllowedToDelete();
		else if (!CWorkspace.getInstance().isDrawingCable())
			presentation.setForbidden();
		else
			presentation.setAllowed();
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.port.ICPort#removeCable()
	 */
	public void removeCable()  throws BadConnection {
		if (isUsed() && cable.isConnected())
			cable.disconnect();
		presentation.setForbidden();
	}

	
	
	
}
