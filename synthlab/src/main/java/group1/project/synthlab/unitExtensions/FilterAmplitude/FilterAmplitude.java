package group1.project.synthlab.unitExtensions.FilterAmplitude;

import group1.project.synthlab.signal.Signal;

import java.util.ArrayList;
import java.util.List;

import com.jsyn.unitgen.UnitFilter;

public class FilterAmplitude extends UnitFilter implements IFilterAmplitudeObservable{

	boolean isSatured = false;
	protected double amax;
	protected List<IFilterAmplitudeObserver> observers;
	protected boolean warned;
	
	public FilterAmplitude(double maxvolt){
		this.amax = maxvolt / Signal.AMAX;
		this.observers = new ArrayList<IFilterAmplitudeObserver>();
		this.warned = false;
	}
	
	@Override
	public void generate(int start, int limit) {
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();
		
		double max = 0;
			double min = 0;
		for (int i = start; i < limit; i++) {
			double x = inputs[i];
			if(x > amax){
				isSatured = true;
				x = amax;
				if (!warned && x > max) {
					updateAll(true);
					warned = true;
					max = x;
				}
			}
			if(x < -amax && x < min){
				isSatured = true;
				x = -amax;
				if (!warned) {
					updateAll(true);
					warned = true;
					min = x;
				}
			}
			else {
				if (warned && !isSatured) {
					updateAll(false);
					warned = false;
				}
			}
			outputs[i] = x;
		}
	}
	
	public boolean isSatured() {
		return isSatured;
	}
	
	public double getMaxVolt() {
		return amax * Signal.AMAX;
	}

	public void register(IFilterAmplitudeObserver observer) {
		observers.add(observer);
		
	}

	public void unregister(IFilterAmplitudeObserver observer) {
		observers.remove(observer);
		
	}

	public void updateAll(boolean tooHigh) {
		for(IFilterAmplitudeObserver observer: observers)
			observer.warn(this, tooHigh);
		
	}
	
}
