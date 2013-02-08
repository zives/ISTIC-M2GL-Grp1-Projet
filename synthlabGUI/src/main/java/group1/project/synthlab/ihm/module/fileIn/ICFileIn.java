package group1.project.synthlab.ihm.module.fileIn;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.ihm.port.out.ICOutPort;
import group1.project.synthlab.module.fileIn.IFileInModule;

public interface ICFileIn extends ICModule, IFileInModule{
	public IPFileIn getPresentation();

}
