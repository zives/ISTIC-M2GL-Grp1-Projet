package group1.project.synthlab.ihm.cable;

import group1.project.synthlab.cable.Cable;
import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObservable;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;
import group1.project.synthlab.ihm.workspace.CWorkspace;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;

public class CCable extends Cable implements ICCable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6221567842176606010L;
	protected IPCable presentation;
	public CCable(CFactory factory) {
		super(factory);
		presentation = new PCable(this);
	}
	/* (non-Javadoc)
	 * @see group1.project.synthlab.cable.Cable#setOutPort(group1.project.synthlab.port.out.IOutPort)
	 */
	@Override
	public void setOutPort(IOutPort outPort) throws BadConnection,
			PortAlreadyUsed {
		super.setOutPort(outPort);
		presentation.setP1(((ICOutPort)outPort).getPresentation());
		presentation.setP2(((ICOutPort)outPort).getPresentation());
		
	}
	/* (non-Javadoc)
	 * @see group1.project.synthlab.cable.Cable#setInPort(group1.project.synthlab.port.in.IInPort)
	 */
	@Override
	public void setInPort(IInPort inPort) throws BadConnection, PortAlreadyUsed {		
		super.setInPort(inPort);
		presentation.setP1(((ICOutPort)outPort).getPresentation());
		presentation.setP2(((ICInPort)inPort).getPresentation()); 
		
	}
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.cable.ICCable#getPresentation()
	 */
	public IPCable getPresentation() {
		return presentation;
	}
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.IPModuleObserver#moduleMove(group1.project.synthlab.ihm.module.IPModuleObservable)
	 */
	public void moduleMove(IPModuleObservable subject) {
		presentation.setP1(((ICOutPort)outPort).getPresentation());
		presentation.setP2(((ICInPort)inPort).getPresentation());
	
	}
	/* (non-Javadoc)
	 * @see group1.project.synthlab.cable.Cable#disconnect()
	 */
	@Override
	public void disconnect() {
		presentation.destruct();
		CWorkspace.getInstance().removeCable(this);		
		super.disconnect();
	}


	
	
	
	
}
