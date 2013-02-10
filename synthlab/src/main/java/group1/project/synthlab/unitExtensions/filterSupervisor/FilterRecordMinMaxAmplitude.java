package group1.project.synthlab.unitExtensions.filterSupervisor;

import com.jsyn.unitgen.UnitFilter;

public class FilterRecordMinMaxAmplitude extends UnitFilter {
	
	private double min;
	private double max;
	
	public FilterRecordMinMaxAmplitude() {
		super();
		this.min = 0;
		this.max = 0;
	}
	
	@Override
	public void generate(int start, int limit) {
		
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();

		for (int i = start; i < limit; i++) {
			double x = inputs[i];
			if(x > max)
				max = x;
			if(x < min)
				min = x;
			outputs[i] = x;
		}
	}

	/**
	 * Reinitialise min et max
	 */
	public void reset(){
		min = 0;
		max = 0;
	}
	
	/**
	 * 
	 * @return la valeur minimale du signal en sortie
	 */
	public double getMin() {
		return min;
	}
	
	/**
	 * 
	 * @return la valeur maximale du signal en sortie
	 */
	public double getMax() {
		return max;
	}
	
}
