package group1.project.synthlab.unitExtension.producer.noise;

import group1.project.synthlab.workspace.Workspace;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.util.PseudoRandom;

public class BrownianNoise extends UnitGenerator {
	
	private PseudoRandom randomNum;
	private double brownAlpha; // Coefficient alpha
	private double brownCutoffFreq = 100.0; // Frequence de coupure
	private double brownAmpCorr = 6.2; // Correction d'amplitude
	private double lastOutput;
	public UnitInputPort amplitude;
	public UnitOutputPort output;
	
	public BrownianNoise()
	{
		this.randomNum = new PseudoRandom();
		double dt = 1.0/(Workspace.getInstance().getSynthetizer().getFrameRate());
		double RC = 1.0/(2.0*Math.PI*brownCutoffFreq);
		brownAlpha = dt/( RC + dt );
		lastOutput = 0.0;
		addPort(this.amplitude = new UnitInputPort("Amplitude", 0.999969482421875D));
		addPort(this.output = new UnitOutputPort("Output"));
	}
	
	public void generate(int paramInt1, int paramInt2)
	{
		double n;
		
		double[] arrayOfDouble1 = this.amplitude.getValues();
		double[] arrayOfDouble2 = this.output.getValues();
		for (int i = paramInt1; i < paramInt2; i++)
		{
			n = (this.randomNum.nextRandomDouble() * arrayOfDouble1[i]);
			n = brownAlpha * n + (1 - brownAlpha) * lastOutput;
			lastOutput = n;
			arrayOfDouble2[i] = n * brownAmpCorr;
		}
	}
}