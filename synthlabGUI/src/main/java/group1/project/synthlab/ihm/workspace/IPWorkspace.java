package group1.project.synthlab.ihm.workspace;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import group1.project.synthlab.ihm.cable.IPCable;
import group1.project.synthlab.ihm.port.IPPort;

public interface IPWorkspace {
	public void initialize();
	public void addCable(IPCable cable);
	public void removeCable (IPCable cable);
	public void addPort(IPPort port); //TODO à remplacer par module par la suite
	public void removePort (IPPort port); //TODO à remplacer par module par la suite
	public JLayeredPane getWorkspacePanel();
}
