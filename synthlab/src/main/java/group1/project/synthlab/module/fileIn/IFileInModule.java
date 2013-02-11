package group1.project.synthlab.module.fileIn;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.out.IOutPort;

import java.io.File;

import com.jsyn.data.FloatSample;
import com.jsyn.unitgen.PassThrough;

public interface IFileInModule extends IModule {
	public void loadFile(File sampleFile);
	public IOutPort getOutPort() ;
	public IOutPort getMonoPort() ;
	public PassThrough getPassThrough() ;
	public FloatSample getSample();
	
}
