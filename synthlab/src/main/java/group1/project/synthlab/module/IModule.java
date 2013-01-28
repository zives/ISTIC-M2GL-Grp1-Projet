package group1.project.synthlab.module;

import group1.project.synthlab.exceptions.BadConnection;

public interface IModule {
	public void connectIn(IModule module) throws BadConnection;
	public void connectOut(IModule module) throws BadConnection;
}
