package group1.project.synthlab.factory;

import group1.project.synthlab.cable.Cable;
import group1.project.synthlab.module.out.OutModule;
import group1.project.synthlab.module.vco.VCOModule;
import group1.project.synthlab.port.in.InPort;
import group1.project.synthlab.port.out.OutPort;

import com.jsyn.ports.ConnectableInput;
import com.jsyn.ports.ConnectableOutput;

public class Factory {
	private static Factory instance;
	
	public InPort createInPort(String label, ConnectableInput jSynPort)  {
		return new InPort(label, jSynPort);
	}
	
	public OutPort createOutPort(String label, ConnectableOutput jSynPort)  {
		return new OutPort(label, jSynPort);
	}
	
	public Cable createCable() {
		return new Cable();
	}
	
	public VCOModule createVCOModule() {
		return new VCOModule();
	}
	
	public OutModule createOutModule() {
		return new OutModule();
	}
	
	public static Factory getInstance() {
		if (instance == null)
			instance = new Factory();
		return instance;
	}
	
	
}
