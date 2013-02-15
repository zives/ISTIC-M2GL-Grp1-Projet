package group1.project.synthlab.unitExtension.filter.filterSupervisor;

import group1.project.synthlab.signal.Signal;

import java.util.ArrayList;
import java.util.List;

import com.jsyn.unitgen.UnitFilter;

/**
 * @author Groupe 1
 * Empeche la saturation du son en redefinissant les amplitudes hors des bornes
 * Sert aussi de superviseur des valeurs en amplitude
 * Permet de detecter la saturation d'un son
 * Permet de detecter un signal null
 * Permet de generer un signal null
 */
/**
 * @author Groupe 1
 *
 */
public class FilterAmplitude extends UnitFilter implements IFilterAmplitudeObservable{

	/**
	 * Le signal courant est t'il sature
	 */
	boolean isSatured = false; 
		
	/**
	 * L'amplitude maximale autorisee
	 */
	protected double amax; 
	
	/**
	 * Les observateurs
	 */
	protected List<IFilterAmplitudeObserver> observers; 
	
	
	/**
	 * Si les observateurs ont deja ete averti que le signal est sature
	 */
	protected boolean previousSaturatedWarned;
	
	/**
	 * Si les observateurs ont deja ete averti que le signal est nul
	 */
	protected boolean previousHasSignalWarned;
	
	/**
	 * Defini, si le signal est sature, s'il faut tronquer le signal a l'amplitude maximum autorisee
	 */
	protected boolean truncate; 
	
	/**
	 * Compteur interne avant de'accepter que le signal est bien nul
	 */
	protected int countNoSignal;
	
	/**
	 * Valeur maximale du compteur permettant de s'assurer que le signal est bien nul
	 */
	protected final int MAX_COUNT_NO_SIGNAL = 100;
	
	/** Le signal est t'il nul */
	protected boolean hasSignal = false;
	
	/** Defini si le filtre est en marche
	 * Si ce n'est pas le cas, envoie une tension constante nulle si celui-ci a été ajouté au
	 * synthetiseur et demarre aupres de celui-ci (sert par exemple pour les cable)
	 * Utile pour envoyer une tensuion nulle car les composants JSyn garde la derniere valeur courante apres extinction ce qui
	 * fausse les modules en marche connecte a ce composant alors que celui-ci est pourtant arrete
	 */
	protected boolean generateNullVoltage;
		
	/**
	 * @param maxvolt la tension maximale autorisee
	 * @param truncate s'il faut tronquer le signal
	 */
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
		
		//Sommes des amplitudes pour savoir si le signal est nul
		double sum = 0;

		//Si le filtre est a l'arret indiquer qu'il n'y a pas de signal
		if (generateNullVoltage) {
			hasSignal = false;
			previousSaturatedWarned = false;
			previousHasSignalWarned = false;
		}
		
		//Redefini que le signal n'ets pas sature pour la session
		isSatured = false;
		
		
		for (int i = start; i < limit; i++) {
			//Si le module est a l'arret et qu'il est demarre aupres du synthetioseur, envoyer une tension nulle
			if (generateNullVoltage) {
				outputs[i] = 0.0;
				continue;				
			}
			
			//Valeur courante
			double x = inputs[i];
			
			//Regarde si le signal est hors bornes et le redefini si truncate a vrai
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
			
			
			//Amplitude finale en sortie
			outputs[i] = x;
			
			//Somme absolue des amplitudes
			sum += Math.abs(x);
		}
		
		//Previens d'une saturation
		if (previousSaturatedWarned && !isSatured) {
			updateWarnAllSignalIsSaturated(false);
			previousSaturatedWarned = false;
		} else if( !previousSaturatedWarned && isSatured) {
			updateWarnAllSignalIsSaturated(true);
			previousSaturatedWarned = true;
		}
		
		//Determine s'il y a un signal
		if (sum > 0) {
			this.countNoSignal = 0;
			hasSignal = true;
		}
		else {
			this.countNoSignal++;
			
			if (this.countNoSignal >= MAX_COUNT_NO_SIGNAL) 
				hasSignal = false;
				
		}
	
		//Determine si on doit prevenir d'un signal ou non
		if (previousHasSignalWarned && !hasSignal) {
			updateHasSignalAll(false);
			previousHasSignalWarned = false;
		} else if( !previousHasSignalWarned && hasSignal) {
			updateHasSignalAll(true);
			previousHasSignalWarned = true;
		}
		
	}
	
	/**
	 * Ordonne au module de generer une tension nulle
	 */
	public void generateNullVoltage() {
		generateNullVoltage = true;
	}
	
	/**
	 * Etat normal du module, ne genere pas une tension nulle et surveille le signal
	 */
	public void setToNormalState() {
		generateNullVoltage = false;
	}
	
	/**
	 * @return si il y a un signal
	 */
	public boolean hasSignal() {
		return hasSignal;
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
		observer.warnSignalIsSatured(this, isSatured);
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
	public void updateWarnAllSignalIsSaturated(boolean tooHigh) {
		for(IFilterAmplitudeObserver observer: observers)
			observer.warnSignalIsSatured(this, tooHigh);
		
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.unitExtension.filter.filterSupervisor.IFilterAmplitudeObservable#updateHasSignalAll(boolean)
	 */
	public void updateHasSignalAll(boolean hasSignal) {
		for(IFilterAmplitudeObserver observer: observers)
			observer.hasSignal(this, hasSignal);
		
	}

	/**
	 * @param amplitudeMax redefini l'amplitude maximum
	 */
	public void setMax(double amplitudeMax) {
		this.amax = amplitudeMax;
		
	}


	
}
