package group1.project.synthlab.module;

import javax.swing.JFrame;

import group1.project.synthlab.port.in.InPort;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.SineOscillator;

public class OutModule extends Module implements IModule {

	public enum Distribution {
		MONOLEFT, MONORIGHT, STEREO
	}

	/* jSyn module */
	protected LineOut lineOut;

	/* Défintion des ports */
	protected InPort leftPort;
	protected InPort rightPort;

	/* Variables internes */
	private PassThrough passThrough;
	private boolean isOn;

	/* Propriétés du module */
	Distribution distribution;

	public OutModule() {
		super("Out-" + moduleCount);
		lineOut = new LineOut();
		
		//Ne pas ajouter LineOut au ciruit!
		//Bugs!
		
		passThrough = new PassThrough();
		setDistribution(Distribution.STEREO);

		lineOut.input.setup(-5, -4, 5);
		lineOut.input.setMaximum(5);
		lineOut.input.setMinimum(-5);
	

		leftPort = new InPort("Source Left", passThrough.input);
		rightPort = new InPort("Source Right", passThrough.input);

		isOn = false;
	}

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;

		passThrough.output.disconnectAll();
		switch (this.distribution) {
		case MONOLEFT:
			passThrough.output.connect(lineOut.input.getConnectablePart(0));
			break;
		case MONORIGHT:
			passThrough.output.connect(lineOut.input.getConnectablePart(1));
			break;
		default:
			passThrough.output.connect(lineOut.input.getConnectablePart(0));
			passThrough.output.connect(lineOut.input.getConnectablePart(1));
		}

	}

	public LineOut getLineOut() {
		return lineOut;
	}

	public InPort getLeftPort() {
		return leftPort;
	}
	
	public InPort getRightPort() {
		return rightPort;
	}

	public Distribution getDistribution() {
		return distribution;
	}

	public void start() {
		circuit.start();
		lineOut.setSynthesisEngine(circuit.getSynthesisEngine());
		lineOut.start();		
		isOn = true;
	}

	public void stop() {
		circuit.stop();
		lineOut.stop();
		isOn = false;
	}

	public boolean isOn() {
		return isOn;
	}

	public static void main(String[] args) {
		OutModule out = new OutModule();
		// Create a context for the synthesizer.
		Synthesizer synth = JSyn.createSynthesizer();

		synth.start();
		
		SineOscillator osc;
		// Add a tone generator.
		synth.add( osc = new SineOscillator() );
		// Add a stereo audio output unit.
		synth.add(out.getCircuit());
		osc.output.connect(out.getLeftPort().getJSynPort());
		// Set the frequency and amplitude for the sine wave.
		osc.frequency.set(345.0);		
		osc.amplitude.set(0.6);
			
		
		out.start();
		
	
		
		 AudioScope scope= new AudioScope( synth );
		scope.addProbe( osc.output );
		scope.setTriggerMode( AudioScope.TriggerMode.NORMAL );
		
		scope.getModel().getTriggerModel().getLevelModel().setDoubleValue( 0.0001 );
	
		scope.getView().setShowControls( true );
		scope.start();
		
				
		
		JFrame frame = new JFrame();
		frame.add(scope.getView());
		frame.pack();
		frame.setVisible(true);
				
				
	}
}
