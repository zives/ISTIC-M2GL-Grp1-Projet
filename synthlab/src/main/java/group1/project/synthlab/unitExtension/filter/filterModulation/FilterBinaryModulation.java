package group1.project.synthlab.unitExtension.filter.filterModulation;

import com.jsyn.unitgen.UnitFilter;

/**
 * Renvoie 1 si il y a un signal ou 0 sinon
 * @author Groupe 1
 * 
 */
public class FilterBinaryModulation extends UnitFilter {
	
	/**
	 * La sensibilite a laquelle determiner si un signal est nul en amplitude
	 * Exemple si la sensibilite est a 0.01, toutes valeurs en entree entre 0 et 0.001 est considere comme une tension nulle et le filtre renverra 0
	 */
	protected double sensibility;
		
	
	public FilterBinaryModulation() {
		super();
		this.sensibility = 0;
	}
	
	public FilterBinaryModulation(double sensibility) {
		super();
		this.sensibility = sensibility;
	}

	@Override
	public void generate(int start, int limit) {
		
		double[] inputs = input.getValues();	
		double[] outputs = output.getValues();
		
		for (int i = start; i < limit; i++) {
			if (inputs[i] <= sensibility)
				outputs[i] = 0;
			else 
				outputs[i] = 1;
			

		}
	}

}
