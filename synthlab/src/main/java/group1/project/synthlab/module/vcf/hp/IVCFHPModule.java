package group1.project.synthlab.module.vcf.hp;

import group1.project.synthlab.module.vcf.IVCFModule;

import com.jsyn.unitgen.FilterHighPass;

public interface IVCFHPModule extends IVCFModule {

    /**
	 * @return le filtre passe-bas de JSyn
	 */
    public FilterHighPass getFilter();

}
