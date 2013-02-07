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

	boolean isSatured = false; //Défini si le son est saturé (c.a.d qui dépasse amax)
	protected double amax; //L'amplitude maximale autorisée
	protected List<IFilterAmplitudeObserver> observers; 
	protected boolean previousSaturatedWarned; //Défini si on a déjà prévenu d'une saturation ou non
	protected boolean previousHasSignalWarned; //Défini si on a déjà prévenu d'un signal null ou non
	protected boolean truncate; //Défini s'il font tronquer l'amplitude saturé à amax
	protected int countNoSignal; //Nombre de fois où le signal était null
	protected final int MAX_COUNT_NO_SIGNAL = 100; //Nombre maximum de fois ou on autorise un signal null avant d'avertir les observers
	protected boolean hasSignal;
	
	public FilterAmplitude (double maxvolt, boolean truncate){
		this.amax = maxvolt / Signal.AMAX;
		this.observers = new ArrayList<IFilterAmplitudeObserver>();
		this.previousSaturatedWarned = false;
		this.previousHasSignalWarned = false;
		this.truncate = false;
		this.countNoSignal = 0;
	}
	
	@Override
	public void generate(int start, int limit) {
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();

		double sum = 0;

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
			sum += Math.abs(x);
		}
		//Préviens d'une saturation
		if (previousSaturatedWarned && !isSatured) {
			updateWarnAll(false);
			previousSaturatedWarned = false;
		} else if( !previousSaturatedWarned && isSatured) {
			updateWarnAll(true);
			previousSaturatedWarned = true;
		}
		
		//Détermine si on doit prévenir d'un signal ou non
		if (sum > 0) {
			this.countNoSignal = 0;
			hasSignal = true;
		}
		else {
			this.countNoSignal++;
			
			if (this.countNoSignal >= MAX_COUNT_NO_SIGNAL) 
				hasSignal = false;
				
		}
	
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
		observer.warn(this, isSatured);
		observer.hasSignal(this, hasSignal);
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
