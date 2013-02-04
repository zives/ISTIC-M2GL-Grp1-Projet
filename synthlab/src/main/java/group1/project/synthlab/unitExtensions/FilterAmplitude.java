package group1.project.synthlab.unitExtensions;

import group1.project.synthlab.signal.Signal;

import com.jsyn.unitgen.UnitFilter;

public class FilterAmplitude extends UnitFilter{

	boolean isSatured = false;
	private double amax;
	
	public FilterAmplitude(double maxvolt){
		this.amax = maxvolt / Signal.AMAX;
	}
	
	@Override
	public void generate(int start, int limit) {
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();
		
		for (int i = start; i < limit; i++) {
			double x = inputs[i];
			if(x > amax){
				isSatured = true;
				x = amax;
			}
			if(x < -amax){
				isSatured = true;
				x = -amax;
			}
			outputs[i] = x;
		}
		if(isSatured)
			System.out.println("Attention : amplitude du signal modulant depasse la limite, ramene a " + Signal.AMAX + "V.");
	}
	
	public boolean isSatured() {
		return isSatured;
	}
	
	public double getMaxVolt() {
		return amax * Signal.AMAX;
	}
	
}
