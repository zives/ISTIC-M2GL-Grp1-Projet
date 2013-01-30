package group1.project.synthlab.port;

public interface IPortObserver {
	public void cableConnected(IPort port);
	public void cableDisconnected(IPort port);
}
