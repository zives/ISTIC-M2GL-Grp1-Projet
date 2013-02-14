package group1.project.synthlab.factory;

import java.io.Serializable;

import group1.project.synthlab.cable.Cable;
import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.module.eg.EGModule;
import group1.project.synthlab.module.eg.IEGModule;
import group1.project.synthlab.module.eq.EQModule;
import group1.project.synthlab.module.eq.IEQModule;
import group1.project.synthlab.module.eqView.EQViewModule;
import group1.project.synthlab.module.eqView.IEQViewModule;
import group1.project.synthlab.module.fileIn.FileInModule;
import group1.project.synthlab.module.fileIn.IFileInModule;
import group1.project.synthlab.module.micro.IMicroModule;
import group1.project.synthlab.module.micro.MicroModule;
import group1.project.synthlab.module.multiplexer.IMultiplexerModule;
import group1.project.synthlab.module.multiplexer.MultiplexerModule;
import group1.project.synthlab.module.noise.INoiseModule;
import group1.project.synthlab.module.noise.NoiseModule;
import group1.project.synthlab.module.osc.IOSCModule;
import group1.project.synthlab.module.osc.OSCModule;
import group1.project.synthlab.module.out.IOutModule;
import group1.project.synthlab.module.out.OutModule;
import group1.project.synthlab.module.piano.IPianoModule;
import group1.project.synthlab.module.piano.PianoModule;
import group1.project.synthlab.module.sequencer.ISequencerModule;
import group1.project.synthlab.module.sequencer.SequencerModule;
import group1.project.synthlab.module.vca.IVCAModule;
import group1.project.synthlab.module.vca.VCAModule;
import group1.project.synthlab.module.vcf.hp.IVCFHPModule;
import group1.project.synthlab.module.vcf.hp.VCFHPModule;
import group1.project.synthlab.module.vcf.lp.IVCFLPModule;
import group1.project.synthlab.module.vcf.lp.VCFLPModule;
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

/**
 * @author Groupe 1
 * 
 */
public class Factory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8090664782304306522L;

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

	public IMultiplexerModule createMultiplexerModule() {
		return new MultiplexerModule(this);
	}

	public IVCAModule createVCAModule() {
		return new VCAModule(this);
	}
	
	public IFileInModule createFileInModule() {
		return new FileInModule(this);
	}

	public IEGModule createEGModule() {
		return new EGModule(this);
	}

	public IPianoModule createPianoModule() {
		return new PianoModule(this);
	}

	public IEQViewModule createEQViewModule() {
		return new EQViewModule(this);
	}

	public IMicroModule createMicroModule() {
		return new MicroModule(this);
	}

	public IEQModule createEQModule() {
		return new EQModule(this);
	}

	public IOSCModule createOSCModule() {
		return new OSCModule(this);
	}

	public ISequencerModule createSequencerModule() {
		return new SequencerModule(this);
	}

	public IVCFLPModule createVCFLPModule() {
		return new VCFLPModule(this);
	}

	public IVCFHPModule createVCFHPModule() {
		return new VCFHPModule(this);
	}

	public INoiseModule createNoiseModule() {
		return new NoiseModule(this);
	}
}
