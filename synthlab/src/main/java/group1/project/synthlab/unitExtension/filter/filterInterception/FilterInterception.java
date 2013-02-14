package group1.project.synthlab.unitExtension.filter.filterInterception;

import group1.project.synthlab.exceptions.BufferTooBig;
import group1.project.synthlab.unitExtension.filter.filterSupervisor.IFilterAmplitudeObserver;
import group1.project.synthlab.workspace.Workspace;

import java.util.ArrayList;
import java.util.List;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.UnitFilter;

/**
 * Prend un snapshot pendant une duree determinee et previens l'observeur
 * 
 * @author Groupe 1
 * 
 */
public class FilterInterception extends UnitFilter implements
		IFilterInterceptionObservable {
	protected Synthesizer synth;
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
		for (int i = start; i < limit; i++) {
			outputs[i] = inputs[i];
			buffer.add(inputs[i]);	
		}
		for (IFilterInterceptionObserver observer: observers)
			try {
				observer.interceptionResult(buffer, time);
			} catch (BufferTooBig e) {
				e.printStackTrace();
			}
	}

	public void register(IFilterInterceptionObserver observer) {
		observers.add(observer);

	}

	public void unregister(IFilterAmplitudeObserver observer) {
		observers.remove(observer);

	}

}