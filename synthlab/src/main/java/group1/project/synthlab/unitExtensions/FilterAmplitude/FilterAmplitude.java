package group1.project.synthlab.unitExtensions.FilterAmplitude;

import group1.project.synthlab.signal.Signal;

import java.util.ArrayList;
import java.util.List;

import com.jsyn.unitgen.UnitFilter;

/**
 * @author Groupe 1
 * Empeche la saturation du son en redefinissant les amplitudes hors des bornes
 */
public class FilterAmplitude extends UnitFilter implements IFilterAmplitudeObservable{

	boolean isSatured = false;
	protected double amax;
	protected List<IFilterAmplitudeObserver> observers;
	protected boolean previousSaturatedWarned;
	protected boolean previousHasSignalWarned;
	protected boolean truncate;
	
	public FilterAmplitude (double maxvolt, boolean truncate){
		this.amax = maxvolt / Signal.AMAX;
		this.observers = new ArrayList<IFilterAmplitudeObserver>();
		this.previousSaturatedWarned = false;
		this.previousHasSignalWarned = false;
		this.truncate = false;
	}
	
	@Override
	public void generate(int start, int limit) {
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();

		double moyenne = 0;

			isSatured = false;
		for (int i = start; i < limit; i++) {
			double x = inputs[i];
			if(x > amax){
				isSatured = true;
				if (truncate)
					x = amax;				
			}
			if(x < -amax ){
				isSatured = true;
				if (truncate)
					x = -amax;			
			}
			else 
				outputs[i] = x;
			moyenne += x;
			
		}
		
		moyenne /= (limit - start);
		
		if (previousSaturatedWarned && !isSatured) {
			updateWarnAll(false);
			previousSaturatedWarned = false;
		} else if( !previousSaturatedWarned && isSatured) {
			updateWarnAll(true);
			previousSaturatedWarned = true;
		}
		
		boolean hasSignal = false;
		if (Math.round(moyenne * 1000) / 1000 > 0)
			hasSignal = true;
		if (previousHasSignalWarned && !hasSignal) {
			updateHasSignalAll(false);
			previousHasSignalWarned = false;
		} else if( !previousHasSignalWarned && hasSignal) {
			updateHasSignalAll(true);
			previousHasSignalWarned = true;
		}
	}
	
	/**
	 * @return si le son est sature
	 */
	public boolean isSatured() {
		return isSatured;
	}
	
	/**
	 * @return la borne dont l'amplitude ne doit pas etre depassee
	 */
	public double getMaxVolt() {
		return amax * Signal.AMAX;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.unitExtensions.FilterAmplitude.IFilterAmplitudeObservable#register(group1.project.synthlab.unitExtensions.FilterAmplitude.IFilterAmplitudeObserver)
	 */
	public void register(IFilterAmplitudeObserver observer) {
		observers.add(observer);
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.unitExtensions.FilterAmplitude.IFilterAmplitudeObservable#unregister(group1.project.synthlab.unitExtensions.FilterAmplitude.IFilterAmplitudeObserver)
	 */
	public void unregister(IFilterAmplitudeObserver observer) {
		observers.remove(observer);
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.unitExtensions.FilterAmplitude.IFilterAmplitudeObservable#updateAll(boolean)
	 */
	public void updateWarnAll(boolean tooHigh) {
		for(IFilterAmplitudeObserver observer: observers)
			observer.warn(this, tooHigh);
		
	}
	
	public void updateHasSignalAll(boolean hasSignal) {
		for(IFilterAmplitudeObserver observer: observers)
			observer.hasSignal(this, hasSignal);
		
	}

	public void setMax(double amplitudeMax) {
		this.amax = amplitudeMax;
		
	}


	
}
