package group1.project.synthlab.ihm.cable;

import group1.project.synthlab.cable.Cable;
import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObservable;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.ihm.port.in.ICInPort;
import group1.project.synthlab.ihm.port.out.ICOutPort;
import group1.project.synthlab.ihm.workspace.CWorkspace;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;

public class CCable extends Cable implements ICCable{
	protected IPCable presentation;
	public CCable(CFactory factory) {
		super(factory);
		presentation = new PCable(this);
	}
	@Override
	public void setOutPort(IOutPort outPort) throws BadConnection,
			PortAlreadyUsed {
		super.setOutPort(outPort);
		presentation.setP1(((ICOutPort)outPort).getPresentation());
		presentation.setP2(((ICOutPort)outPort).getPresentation());
		
	}
	@Override
	public void setInPort(IInPort inPort) throws BadConnection, PortAlreadyUsed {		
		super.setInPort(inPort);
		presentation.setP2(((ICInPort)inPort).getPresentation()); 
		presentation.addMouseClickEvents();
		
	}
	public IPCable getPresentation() {
		return presentation;
	}
	public void update(IPModuleObservable subject) {
		presentation.setP1(((ICOutPort)outPort).getPresentation());
		presentation.setP2(((ICInPort)inPort).getPresentation());
	
	}
	@Override
	public void disconnect() {
		CWorkspace.getInstance().removeCable(this);		
		super.disconnect();
	}
	
	
	
	
}
