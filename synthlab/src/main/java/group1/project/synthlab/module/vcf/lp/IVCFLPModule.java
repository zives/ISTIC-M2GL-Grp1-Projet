package group1.project.synthlab.module.vcf.lp;

import com.jsyn.unitgen.FilterLowPass;

import group1.project.synthlab.module.vcf.IVCFModule;

public interface IVCFLPModule extends IVCFModule {

	/**
	 * Met a jour le facteur de qualite q au niveau des filtres
	 */
    public void changeQFactor();
    
    /**
	 * @return le premier filtre passe-bas de JSyn
	 */
    public FilterLowPass getFilter1();
    
    /**
	 * @return le second filtre passe-bas de JSyn
	 */
    public FilterLowPass getFilter2();
    
    /**
	 * @return la valeur actuelle du facteur de qualite
	 */
    public double getq();
    
    /**
	 * Change la valeur du facteur de qualite
	 */
    public void setq(double q);
}
