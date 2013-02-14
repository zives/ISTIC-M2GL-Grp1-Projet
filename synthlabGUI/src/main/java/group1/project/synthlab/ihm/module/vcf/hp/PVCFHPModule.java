package group1.project.synthlab.ihm.module.vcf.hp;

import group1.project.synthlab.ihm.module.vcf.PVCFModule;

import javax.swing.JLabel;
import javax.swing.JSlider;

public class PVCFHPModule extends PVCFModule implements IPVCFHPModule {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7498802672670972503L;

	protected transient ICVCFHPModule controller;

	protected JSlider coarseSlider;
	protected JSlider fineSlider;
	protected JLabel warnLabel;

	public PVCFHPModule(final ICVCFHPModule controller) {
		super(controller);
		this.controller = controller;
		// Taille et couleur définie dans la super classe

		// Label et onoff boutons déjà rajoutés dans la super classe
	}
}
