package group1.project.synthlab.workspace;

import javax.swing.JPanel;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;

import group1.project.synthlab.control.module.IModule;
import group1.project.synthlab.control.module.OutModule;

public class Workspace implements IWorkspace {

	private Synthesizer synthesizer;
	private JPanel p;
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

	public JPanel getPresentation() {
		// TODO Auto-generated method stub
		return p;
	}

}
