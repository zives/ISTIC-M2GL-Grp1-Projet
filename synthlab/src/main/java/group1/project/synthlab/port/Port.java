package group1.project.synthlab.port;

import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.module.IModule;

public class Port implements IPort {
	
	private PortType portType;
	private String label;
	private IModule module;
	
	
	public Port(PortType portType, String label, IModule module) {
		this.portType = portType;
		this.label = label;
		this.module = module;
	}
	
	public void connectTo(Port port) throws BadConnection {
		if (portType == port.getType())
			throw new BadConnection("It's impossible to connect ports to the same type.");
		if (portType == PortType.IN) 
			module.connectIn(port.getModule());
		else
			module.connectOut(port.getModule());
		
	}

	public PortType getType() {
		return portType;
	}

	public String getLabel() {
		return label;
	}
	
	public IModule getModule() {
		return module;
	}
}
