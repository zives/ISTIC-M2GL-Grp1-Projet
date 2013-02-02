package group1.project.synthlab.ihm.workspace;

import group1.project.synthlab.ihm.cable.IPCable;
import group1.project.synthlab.ihm.module.IPModule;
import group1.project.synthlab.ihm.module.IPModuleObserver;

import javax.swing.JLayeredPane;

public interface IPWorkspace {
	public void initialize();
	public void addCable(IPCable cable);
	public void removeCable (IPCable cable);
	public void addModule(IPModule module); 
	public void removeModule (IPModule module); 
	public JLayeredPane getWorkspacePanel();
}
