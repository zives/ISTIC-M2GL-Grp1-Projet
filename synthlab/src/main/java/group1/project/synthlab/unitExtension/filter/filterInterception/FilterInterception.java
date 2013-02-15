package group1.project.synthlab.unitExtension.filter.filterInterception;

import group1.project.synthlab.exceptions.BufferTooBig;
import group1.project.synthlab.unitExtension.filter.filterSupervisor.IFilterAmplitudeObserver;
import group1.project.synthlab.workspace.Workspace;

import java.util.ArrayList;
import java.util.List;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.UnitFilter;

/**
 * Previens un observateur de nouvelles donnees entrantes et lui donne
 * /!\ L'evenement levee pour les observateur  avec les donnes ne doit pas etre complexe sous peine de ralentir la lecture
 * sur le synthetiseur du flux de donnees. Utilisez un autre thread pour les traitements lourds
 * @author Groupe 1
 * 
 */
public class FilterInterception extends UnitFilter implements
		IFilterInterceptionObservable {
	/**
	 * Le synthetiseur rattache au filtre
	 */
	protected Synthesizer synth;
	
	
	/**
	 * La liste des observateurs
	 */
	protected List<IFilterInterceptionObserver> observers;

	public FilterInterception() {
		super();
		observers = new ArrayList<IFilterInterceptionObserver>();
		synth = Workspace.getInstance().getSynthetizer();
	}

	@Override
	public void generate(int start, int limit) {
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();
		
		final List<Double> buffer = new ArrayList<Double>();
		double time = synth.getCurrentTime();
		
		//AJoute au buffer les donnees
		for (int i = start; i < limit; i++) {			
			outputs[i] = inputs[i];
			buffer.add(inputs[i]);	
		}
		
		//Envoie les donnees a tous les observateur /!\  si traitement lourd derriere, utilisez un thread
		for (IFilterInterceptionObserver observer: observers)
			try {
				observer.interceptionResult(buffer, time);
			} catch (BufferTooBig e) {
				e.printStackTrace();
			}
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.unitExtension.filter.filterInterception.IFilterInterceptionObservable#register(group1.project.synthlab.unitExtension.filter.filterInterception.IFilterInterceptionObserver)
	 */
	public void register(IFilterInterceptionObserver observer) {
		observers.add(observer);

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.unitExtension.filter.filterInterception.IFilterInterceptionObservable#unregister(group1.project.synthlab.unitExtension.filter.filterSupervisor.IFilterAmplitudeObserver)
	 */
	public void unregister(IFilterAmplitudeObserver observer) {
		observers.remove(observer);

	}

}
