package group1.project.synthlab.factory;

import group1.project.synthlab.cable.Cable;
import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.module.out.IOutModule;
import group1.project.synthlab.module.out.OutModule;
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
	
	public IInPort createInPort(String label, ConnectableInput jSynPort)  {
		return new InPort(label, jSynPort, this);
	}
	
	public IOutPort createOutPort(String label, ConnectableOutput jSynPort)  {
		return new OutPort(label, jSynPort, this);
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
	
}
