package group1.project.synthlab.workspace;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.unitgen.UnitGenerator;
import com.softsynth.shared.time.ScheduledCommand;
import com.softsynth.shared.time.TimeStamp;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.module.OutModule;

public class Workspace implements IWorkspace {

	private Synthesizer synthesizer;
	
	public Workspace(){
		synthesizer = JSyn.createSynthesizer();
		synthesizer.start();
		addModule(new OutModule());
	}
	
	public void addModule(IModule module) {
		synthesizer.add(module.getCircuit());
	}

	public void removeModule(IModule module) {
		synthesizer.remove(module.getCircuit());
	}

}
