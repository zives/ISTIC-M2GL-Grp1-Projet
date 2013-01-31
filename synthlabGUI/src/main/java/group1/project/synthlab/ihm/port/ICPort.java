package group1.project.synthlab.ihm.port;

import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.port.IPort;

public interface ICPort extends IPort {
	public IPPort getPresentation();

	public void actionCable()  throws BadConnection;

	public void checkPutCable();
}
