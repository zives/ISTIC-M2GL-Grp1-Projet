package group1.project.synthlab.unitExtensions;

import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.UnitBinaryOperator;
import com.jsyn.unitgen.UnitFilter;
import com.jsyn.unitgen.UnitGenerator;

public class ShowFrequency extends UnitFilter {

	//en db
	private double attenuation;
	private SquareOscillator u;
	
	public ShowFrequency(SquareOscillator u) {
		super();
		this.u = u;
		attenuation = 0;
	}
	 
	
	@Override
	public void generate(int start, int limit) {
		System.out.println("fréquence : " + u.frequency.get());
	}


	public double getAttenuation() {
		return attenuation;
	}


	public void setAttenuation(double attenuation) {
		this.attenuation = attenuation;
	}

}
