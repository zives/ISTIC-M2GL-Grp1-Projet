package group1.project.synthlab.ihm.module.vcf.lp;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.vcf.lp.VCFLPModule;
import group1.project.synthlab.port.IPort;

/**
 * @author Groupe 1
 * Controleur du module VCF LP
 */
public class CVCFLPModule extends VCFLPModule implements ICVCFLPModule {

	private static final long serialVersionUID = 5474594253227440370L;

	protected IPVCFLPModule presentation;
	
	public CVCFLPModule(Factory factory) {
		super(factory);
		this.presentation = new PVCFLPModule(this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.vcf.lp.ICVCFLPModule#getPresentation()
	 */
	public IPVCFLPModule getPresentation() {
		return presentation;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.vcf.lp.VCFLPModule#cableConnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableConnected(IPort port) {		
		super.cableConnected(port);
		presentation.register((IPModuleObserver) port.getCable());
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.vcf.lp.VCFLPModule#cableDisconnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableDisconnected(IPort port) {		
		presentation.unregister((IPModuleObserver) port.getCable());
		super.cableDisconnected(port);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.vcf.lp.VCFLPModule#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		presentation.updatePresentation();
	}
	 
}
