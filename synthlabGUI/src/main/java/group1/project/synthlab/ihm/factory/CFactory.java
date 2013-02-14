package group1.project.synthlab.ihm.factory;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.ihm.cable.CCable;
import group1.project.synthlab.ihm.module.eg.CEGModule;
import group1.project.synthlab.ihm.module.eq.CEQModule;
import group1.project.synthlab.ihm.module.eqView.CEQViewModule;
import group1.project.synthlab.ihm.module.micro.CMicroModule;
import group1.project.synthlab.ihm.module.multiplexer.CMultiplexerModule;
import group1.project.synthlab.ihm.module.noise.CNoiseModule;
import group1.project.synthlab.ihm.module.osc.COSCModule;
import group1.project.synthlab.ihm.module.out.COutModule;
import group1.project.synthlab.ihm.module.piano.CPianoModule;
import group1.project.synthlab.ihm.module.sequencer.CSequencerModule;
import group1.project.synthlab.ihm.module.vca.CVCAModule;
import group1.project.synthlab.ihm.module.vcf.hp.CVCFHPModule;
import group1.project.synthlab.ihm.module.vcf.lp.CVCFLPModule;
import group1.project.synthlab.ihm.module.vco.CVCOModule;
import group1.project.synthlab.ihm.port.in.CInPort;
import group1.project.synthlab.ihm.port.out.COutPort;
import group1.project.synthlab.ihm.workspace.CWorkspace;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.module.eg.IEGModule;
import group1.project.synthlab.module.eq.IEQModule;
import group1.project.synthlab.module.eqView.IEQViewModule;
import group1.project.synthlab.module.micro.IMicroModule;
import group1.project.synthlab.module.multiplexer.IMultiplexerModule;
import group1.project.synthlab.module.noise.INoiseModule;
import group1.project.synthlab.module.osc.IOSCModule;
import group1.project.synthlab.module.out.IOutModule;
import group1.project.synthlab.module.piano.IPianoModule;
import group1.project.synthlab.module.sequencer.ISequencerModule;
import group1.project.synthlab.module.vca.IVCAModule;
import group1.project.synthlab.module.vcf.hp.IVCFHPModule;
import group1.project.synthlab.module.vcf.lp.IVCFLPModule;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 8837571126993846667L;

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
	public IPianoModule createPianoModule() {
		return new CPianoModule(this);
	}
	
	@Override
	public INoiseModule createNoiseModule() {
		return new CNoiseModule(this);
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
	public IVCFLPModule createVCFLPModule() {
		return new CVCFLPModule(this);
	}
	
	@Override
	public IVCFHPModule createVCFHPModule() {
		return new CVCFHPModule(this);
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
	public IEGModule createEGModule() {
		return new CEGModule(this);
	}
	
	@Override
	public IEQViewModule createEQViewModule() {
		return new CEQViewModule(this);
	}
	
	@Override
	public IMicroModule createMicroModule() {
		return new CMicroModule(this);
	}
	
	@Override
	public IEQModule createEQModule() {
		return new CEQModule(this);
	}
	
	@Override
	public IOSCModule createOSCModule() {
		return new COSCModule(this);
	}

	@Override
	public ISequencerModule createSequencerModule() {
		return new CSequencerModule(this);
	}
}
