package group1.project.synthlab.module.vcf.hp;

import com.jsyn.unitgen.FilterHighPass;
import group1.project.synthlab.module.vcf.IVCFModule;

public interface IVCFHPModule extends IVCFModule {

    /**
	 * @return le filtre passe-bas de JSyn
	 */
    public FilterHighPass getFilter();

}
