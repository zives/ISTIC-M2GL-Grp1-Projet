package group1.project.synthlab.module.fileIn;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.out.IOutPort;

import java.io.File;

import com.jsyn.unitgen.PassThrough;

public interface IFileInModule extends IModule {
	public void loadFile(File sampleFile);
	public IOutPort getLeftPort() ;
	public IOutPort getRightPort() ;
	public IOutPort getMonoPort() ;
	public PassThrough getPassThrough() ;
	
}
