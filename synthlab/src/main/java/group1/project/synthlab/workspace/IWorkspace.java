package group1.project.synthlab.workspace;

import group1.project.synthlab.module.IModule;

public interface IWorkspace {
	public void addModule(IModule module);
	public void removeModule(IModule module);
}
