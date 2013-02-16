package group1.project.synthlab.ihm.port;

import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.workspace.CWorkspace;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.Port;

/**
 * @author Groupe 1
 * Controleur generale de ports
 */
public abstract class CPort extends Port implements ICPort {

	private static final long serialVersionUID = 1157253840746260196L;
	
	//La presentation
	protected IPPort presentation;
		
	public CPort(String label, CWorkspace workspace, IModule module,  CFactory factory) {
		super(label, module,  factory);
		presentation = new PPort(this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.port.ICPort#getPresentation()
	 */
	public IPPort getPresentation() {
		return presentation;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.port.ICPort#actionCable()
	 */
	public void actionCable() throws BadConnection {
		throw new BadConnection("Impossible de générer un cable avec un port de type indéfini, utilisez InPort ou OuPort !");
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.port.ICPort#checkPutCable()
	 */
	public void checkPutCable() {
		presentation.setForbidden();
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.port.ICPort#removeCable()
	 */
	public void removeCable() throws BadConnection {
		throw new BadConnection("Impossible de supprimer un cable avec un port de type indéfini, utilisez InPort ou OuPort !");
	}
}
