package group1.project.synthlab.factory;

import group1.project.synthlab.cable.Cable;
import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.module.multiplexer.IMultiplexerModule;
import group1.project.synthlab.module.multiplexer.MultiplexerModule;
import group1.project.synthlab.module.out.IOutModule;
import group1.project.synthlab.module.out.OutModule;
import group1.project.synthlab.module.vcf.IVCFModule;
import group1.project.synthlab.module.vcf.VCFModule;
import group1.project.synthlab.module.vco.IVCOModule;
import group1.project.synthlab.module.vco.VCOModule;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.in.InPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.port.out.OutPort;
import group1.project.synthlab.workspace.IWorkspace;
import group1.project.synthlab.workspace.Workspace;

import com.jsyn.ports.ConnectableInput;
import com.jsyn.ports.ConnectableOutput;

public class Factory {

	public IInPort createInPort(String label, ConnectableInput jSynPort,
			IModule module) {
		return new InPort(label, jSynPort, module, this);
	}

	public IOutPort createOutPort(String label, ConnectableOutput jSynPort,
			IModule module) {
		return new OutPort(label, jSynPort, module, this);
	}

	public ICable createCable() {
		return new Cable(this);
	}

	public IVCOModule createVCOModule() {
		return new VCOModule(this);
	}

	public IOutModule createOutModule() {
		return new OutModule(this);
	}

	public IWorkspace createWorkSpace() {
		return new Workspace(this);
	}

	public IVCFModule createVCFModule() {
		return new VCFModule(this);
	}

	public IMultiplexerModule createMultiplexerModule() {
		return new MultiplexerModule(this);
	}

}
