package group1.project.synthlab.ihm.cable;

import group1.project.synthlab.ihm.port.IPPort;
import group1.project.synthlab.ihm.port.PPort;

public interface IPCable {
	public void setP1(int x, int y);
	public void setP1(IPPort presentation);
	public void setP2(int x, int y);
	public void setP2(IPPort presentation);
}
