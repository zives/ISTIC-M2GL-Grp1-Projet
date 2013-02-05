package group1.project.synthlab.unitExtensions.FilterAmplitude;

import group1.project.synthlab.signal.Signal;

import java.util.ArrayList;
import java.util.List;

import com.jsyn.unitgen.UnitFilter;

public class FilterAmplitude extends UnitFilter implements IFilterAmplitudeObservable{

	boolean isSatured = false;
	protected double amax;
	protected List<IFilterAmplitudeObserver> observers;
	protected boolean previouswarned;
	
	public FilterAmplitude(double maxvolt){
		this.amax = maxvolt / Signal.AMAX;
		this.observers = new ArrayList<IFilterAmplitudeObserver>();
		this.previouswarned = false;
	}
	
	@Override
	public void generate(int start, int limit) {
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();
		

			isSatured = false;
		for (int i = start; i < limit; i++) {
			double x = inputs[i];
			if(x > amax){
				isSatured = true;
				x = amax;				
			}
			if(x < -amax ){
				isSatured = true;
				x = -amax;			
			}
			else 
			outputs[i] = x;
		}
		if (previouswarned && !isSatured) {
			updateAll(false);
			previouswarned = false;
		} else if( !previouswarned && isSatured) {
			updateAll(true);
			previouswarned = true;
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
