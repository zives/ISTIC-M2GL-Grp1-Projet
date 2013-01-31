package group1.project.synthlab.control.port;

public interface IPortObservable {
	public void register(IPortObserver observer);
	public void unregister(IPortObserver observer);
	public void cableConnected();
	public void cableDisconnected();
}
