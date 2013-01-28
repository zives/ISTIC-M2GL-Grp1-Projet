package group1.project.synthlab.port;

import group1.project.synthlab.exceptions.BadConnection;

public interface IPort {
	public enum PortType {IN, OUT};
	public void connectTo(Port p) throws BadConnection;
	public PortType getType();
}
