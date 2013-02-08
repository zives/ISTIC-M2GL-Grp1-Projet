package group1.project.synthlab.unitExtensions.FilterFrequency;

import java.util.ArrayList;
import java.util.List;

import com.jsyn.unitgen.FilterLowPass;
import com.jsyn.unitgen.UnitFilter;

/**
 * Permet de visualiser les amplitudes par tranches de fréquences et ou de les atténuer
 * @author Groupe 1
 * 
 */
public class FilterFrequencySupervisor extends UnitFilter {
	
	private double startFrequency;
	private List<Double> intervals;
	private int countAvg;
	private int maxCountAvg;
	private List<Double> avgIntervals;
	private double lastMax;
	private double max;
	private long lastTime;

	
	public FilterFrequencySupervisor() {
		super();
		this.startFrequency = 0;
		this.intervals = new ArrayList<Double>();
		this.maxCountAvg = 2;
		this.countAvg = 0;
		this.lastMax = 0;
		this.max = 0;
		this.lastTime = System.nanoTime();

	}

	
	public void clearIntervals() {
		intervals.clear();
	}
	
	public boolean addInterval(double frequency) {
		if (frequency > intervals.get(intervals.size() - 1)) {
			intervals.add(frequency);
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
			if (x > max)
				max = x;
			else if (x < max) {
				long timeElapsed = System.nanoTime() - lastTime;
				double freq = 1.0 / (timeElapsed * 1.0E-9);
				
				//something
				
				lastTime = System.nanoTime();
				lastMax = x;
				
				
			}
		}
		++countAvg;
		if (countAvg > maxCountAvg) {					
			//warn observers
			avgIntervals.clear();
		}
		
	}

	
}
