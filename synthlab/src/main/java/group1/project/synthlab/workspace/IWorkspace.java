package group1.project.synthlab.workspace;

import javax.swing.JPanel;

import group1.project.synthlab.control.module.IModule;

public interface IWorkspace {
	public void addModule(IModule module);
	public void removeModule(IModule module);
	public JPanel getPresentation();
}
