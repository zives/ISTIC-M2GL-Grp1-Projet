package group1.project.synthlab.ihm.factory;

import group1.project.synthlab.cable.Cable;
import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.ihm.cable.CCable;
import group1.project.synthlab.ihm.port.in.CInPort;
import group1.project.synthlab.ihm.port.out.COutPort;
import group1.project.synthlab.module.OutModule;
import group1.project.synthlab.module.VCOModule;
import group1.project.synthlab.port.in.InPort;
import group1.project.synthlab.port.out.OutPort;

import com.jsyn.ports.ConnectableInput;
import com.jsyn.ports.ConnectableOutput;

public class ControllerFactory extends Factory {
	private static Factory instance;
	
	@Override
	public InPort createInPort(String label, ConnectableInput jSynPort)  {
		return new CInPort(label, jSynPort);
	}
	
	@Override
	public OutPort createOutPort(String label, ConnectableOutput jSynPort)  {
		return new COutPort(label, jSynPort);
	}
	
	@Override
	public Cable createCable() {
		 return new CCable();
	}

	@Override
	public VCOModule createVCOModule() {
		// TODO A décommenter
		// return new CVCOModule();
		return null;
	}

	@Override
	public OutModule createOutModule() {
		// TODO A décommenter
		// return new COutModule();
		return null;
	}
	
	public static Factory getInstance() {
		if (instance == null)
			instance = new ControllerFactory();
		return instance;
	}

}
