package group1.project.synthlab.ihm.port.out;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.ihm.cable.ICCable;
import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.workspace.CWorkspace;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.out.OutPort;

import com.jsyn.ports.ConnectableOutput;

/**
 * @author Groupe 1
 * Controleur de port de sortie
 */
public class COutPort extends OutPort implements ICOutPort {

	private static final long serialVersionUID = 5664870563807386725L;
	
	//La presentation
	protected IPOutPort presentation;

	public COutPort(String label, ConnectableOutput jSynPort, IModule module, CFactory factory) {
		super(label, jSynPort, module,  factory);
		presentation = new POutPort(this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.port.ICPort#getPresentation()
	 */
	public IPOutPort getPresentation() {
		return presentation;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.port.ICPort#actionCable()
	 */
	public void actionCable() throws BadConnection {
		//Si le port n'est pas deja utilise par un autre cable
		if (!isUsed()) {
			//Et si on n'est pas entrain de dessiner un cable
			if (!CWorkspace.getInstance().isDrawingCable()) {
				ICCable cable = (ICCable)factory.createCable();
				try {
					//On commence la creation d'un cable
					CWorkspace.getInstance().setDrawingCable(cable);
					//Et on attribue une de ces extremites a ce port
					cable.setOutPort(this);
									
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
		if (CWorkspace.getInstance().isDrawingCable())
			presentation.setForbidden();
		else if (isUsed())
			presentation.setAllowedToDelete();	
		else
			presentation.setAllowed();
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.port.ICPort#removeCable()
	 */
	public void removeCable()  throws BadConnection {
		if (isUsed() && cable.isConnected())
			cable.disconnect();		
		presentation.setAllowed();
	}

}
