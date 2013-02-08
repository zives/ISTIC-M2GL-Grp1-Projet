package group1.project.synthlab.unitExtensions.FilterFrequency;

import group1.project.synthlab.factory.Factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitFilter;

/**
 * Permet de visualiser les amplitudes par tranches de fréquences et ou de les
 * atténuer
 * 
 * @author Groupe 1
 * 
 */
public class FilterFrequencySupervisor extends UnitFilter {

	private double startFrequency; // Début de fréquence, toutes fréquence avant
									// n'est pas enregistrée
	private List<Double> intervals; // Liste des intervals des fréquences
	private int countAvg; // nombre de fois que l'amplitude a été calculée
	private int maxCountAvg; // Nombre de fois qu'il faut enregistrer les
								// amplitudes avant d'avertir les observateurs
	private List<List<Double>> avgIntervals; // Sauvegarde des amplitudes

	private double lastValue; // Dernière valeur
	private double lastMax; // Dernier max correspondant à un sommet
	private double lastMin; // Dernier min correspondant à un creux
	private double max; // dernière amplitude max trouvée
	private double min; // dernière amplitude min trouvée
	private double lastTime; // dernier temps trouvé au dernier lastMax enregistré
	private double freq; //real time frequency
	
	private Synthesizer synth; //le synthetiseur

	public FilterFrequencySupervisor(Synthesizer synth) {
		super();
		this.startFrequency = 0;
		this.intervals = new ArrayList<Double>();
		this.avgIntervals = new ArrayList<List<Double>>();
		this.maxCountAvg = 2;
		this.countAvg = 0;
		this.lastMax = 0;
		this.lastMin = 0;
		this.lastValue = 0;
		this.max = 0;
		this.min = 0;
		
		this.synth = synth;
		this.lastTime = synth.getCurrentTime();

	}

	public void clearIntervals() {
		intervals.clear();
	}

	public boolean addInterval(double frequency) {
		if (frequency > intervals.get(intervals.size() - 1)) {
			intervals.add(frequency);
			avgIntervals.add(new ArrayList<Double>());
			return true;
		}
		return false;
	}

	public List<Double> getIntervals() {
		return intervals;
	}

	public double getStartFrequency() {
		return startFrequency;
	}

	public void setStartFrequency(double startFrequency) {
		this.startFrequency = startFrequency;
	}

	public int getMaxCountAvg() {
		return maxCountAvg;
	}

	public void setMaxCountAvg(int maxCountAvg) {
		this.maxCountAvg = maxCountAvg;
	}
	
	public double getRealTimeFrequency() {
		return freq;		
	}

	@Override
	public void generate(int start, int limit) {

		double[] inputs = input.getValues();
		double[] outputs = output.getValues();

		if (countAvg > maxCountAvg) {
			countAvg = 0;
			avgIntervals.clear();
		}

		for (int i = start; i < limit; i++) {
			double x = inputs[i];
			System.err.println(x);
			if (x > max && lastValue < x)
				max = x;
			else if (x < max && lastValue > x) {
				// calc freq
				double time = synth.getCurrentTime() ;
				double timeElapsed = time - lastTime;
				lastTime = time;
				freq = 1.0 / (timeElapsed);
				//System.err.println(timeElapsed);
				if (freq > startFrequency) {
					// calc avg amplitutde
					double avg = (lastMax + -lastMin + max) / 3.0;
					// on trouve l'interval de fréquence où enregistrer la
					// moyenne de l'amplitude
					int f = 0;
					for (; f < intervals.size(); ++f) {
						if (freq <= intervals.get(f))
							break;
					}
					if (f < intervals.size())
						avgIntervals.get(f).add(avg);
				
				}

				lastMax = max;
				max = -99999999;	//out of bounds			
			}
			 if (x < min && lastValue > x) {
				min = x;
				
			} else if (x > min && lastValue < x) {
				lastMin = min;
				
				min = 99999999; //out of bounds
			}

			lastValue = x;
			outputs[i] = x;
		}
		++countAvg;
		if (countAvg > maxCountAvg) {
			// warn observers
			for(List<Double> list : avgIntervals)
				list.clear();
		}
		
		
		

	}

	public static void main(String[] args) {
		// Creeation de la factory
		Factory factory = new Factory();

		// Creation d'un module de sortie
		LineOut out = new LineOut();
		

		// Creation du synthetiseur
		Synthesizer synth = JSyn.createSynthesizer();

		synth.start();
		

		// Creation d'oscillateurs arbitraire
		SineOscillator oscS  = new SineOscillator();
		
		// Reglage des oscillateurs
		oscS.frequency.set(10000);
		oscS.amplitude.set(1);
	
		
		synth.add(oscS);
		oscS.start();

		//Création du filtre
		final FilterFrequencySupervisor filter = new FilterFrequencySupervisor(synth);
		
		
		// Ajout de notre module au synthetiseur et connexion aux oscillateur
		synth.add(out);
		out.start();
		oscS.output.connect(filter.input);
		
		filter.output.connect(out.input);
		
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				//System.err.println(filter.getRealTimeFrequency());
				
			}
		}, 1000, 1000);
		
		
	
	}

	
}
