package group1.project.synthlab.ihm.factory;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.ihm.cable.CCable;
import group1.project.synthlab.ihm.module.eg.CEGModule;
import group1.project.synthlab.ihm.module.eq.CEQModule;
import group1.project.synthlab.ihm.module.eqView.CEQViewModule;
import group1.project.synthlab.ihm.module.fileIn.CFileInModule;
import group1.project.synthlab.ihm.module.micro.CMicroModule;
import group1.project.synthlab.ihm.module.multiplexer.CMultiplexerModule;
import group1.project.synthlab.ihm.module.out.COutModule;
import group1.project.synthlab.ihm.module.vca.CVCAModule;
import group1.project.synthlab.ihm.module.vco.CVCOModule;
import group1.project.synthlab.ihm.module.vco.piano.CVCOPianoModule;
import group1.project.synthlab.ihm.port.in.CInPort;
import group1.project.synthlab.ihm.port.out.COutPort;
import group1.project.synthlab.ihm.workspace.CWorkspace;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.module.eg.IEGModule;
import group1.project.synthlab.module.eqView.IEQViewModule;
import group1.project.synthlab.module.fileIn.IFileInModule;
import group1.project.synthlab.module.multiplexer.IMultiplexerModule;
import group1.project.synthlab.module.out.IOutModule;
import group1.project.synthlab.module.vca.IVCAModule;
import group1.project.synthlab.module.vcf.IVCFModule;
import group1.project.synthlab.module.vco.IVCOModule;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.workspace.IWorkspace;

import com.jsyn.ports.ConnectableInput;
import com.jsyn.ports.ConnectableOutput;

/**
 * @author Groupe 1
 *	La factory des controles
 */
public class CFactory extends Factory {

	@Override
	public IInPort createInPort(String label, ConnectableInput jSynPort, IModule module) {
		return new CInPort(label, jSynPort,  module, this);
	}

	@Override
	public IOutPort createOutPort(String label, ConnectableOutput jSynPort, IModule module) {
		return new COutPort(label, jSynPort,  module,  this);
	}

	@Override
	public ICable createCable() {
		return new CCable(this);
	}

	@Override
	public IVCOModule createVCOModule() {
		return new CVCOModule(this);
	}
	
	@Override
	public IVCOModule createVCOPianoModule() {
		return new CVCOPianoModule(this);
	}
	
	@Override
	public IOutModule createOutModule() {
		return new COutModule(this);
	}
	
	@Override
	public IVCAModule createVCAModule() {
		return new CVCAModule(this); 
	}
	
	@Override
	public IVCFModule createVCFModule() {
		//return new CVCFModule(this); // TODO
		return null;
	}
	
	@Override
	public IMultiplexerModule createMultiplexerModule() {
		return new CMultiplexerModule(this);
	}

	@Override
	public IWorkspace createWorkSpace() {
		return new CWorkspace(this);
	}
	
	@Override
	public IFileInModule createFileInModule() {
		return new CFileInModule(this);
	}
	
	@Override
	public IEGModule createEGModule() {
		return new CEGModule(this);
	}
	
	@Override
	public IEQViewModule createEQViewModule() {
		return new CEQViewModule(this);
	}
	
	@Override
	public IModule createMicroModule() {
		return new CMicroModule(this);
	}
	
	@Override
	public IModule createEQModule() {
		return new CEQModule(this);
	}

}
