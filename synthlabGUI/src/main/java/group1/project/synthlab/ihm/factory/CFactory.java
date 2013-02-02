package group1.project.synthlab.ihm.factory;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.ihm.cable.CCable;
import group1.project.synthlab.ihm.module.out.COutModule;
import group1.project.synthlab.ihm.module.vco.CVCOModule;
import group1.project.synthlab.ihm.port.in.CInPort;
import group1.project.synthlab.ihm.port.out.COutPort;
import group1.project.synthlab.ihm.workspace.CWorkspace;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.module.out.IOutModule;
import group1.project.synthlab.module.vco.IVCOModule;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.workspace.IWorkspace;

import com.jsyn.ports.ConnectableInput;
import com.jsyn.ports.ConnectableOutput;

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
	public IOutModule createOutModule() {
		return new COutModule(this);
	}

	@Override
	public IWorkspace createWorkSpace() {
		return new CWorkspace(this);
	}
	

}
