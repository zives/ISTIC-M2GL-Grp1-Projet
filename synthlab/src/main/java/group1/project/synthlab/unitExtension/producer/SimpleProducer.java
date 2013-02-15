package group1.project.synthlab.unitExtension.producer;

import com.jsyn.unitgen.UnitFilter;

/**
 * @author Groupe 1
 * Produit une tension donnee en entree
 * Note : il suffit de donner une valeur au port d'entree par la methode set pour produire uune tension continue
 */
public class SimpleProducer extends UnitFilter {

	
	@Override
	public void generate(int start, int limit) {
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();

		for (int i = start; i < limit; i++) {
			double x = inputs[i];
			outputs[i] = x ;
		}
		
	}

}
