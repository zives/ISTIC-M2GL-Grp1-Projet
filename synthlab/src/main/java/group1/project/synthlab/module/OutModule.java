package group1.project.synthlab.module;

import group1.project.synthlab.port.in.InPort;

import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.PassThrough;

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

	/* Propriétés du module */
	Distribution distribution;

	public OutModule() {
		super("Out-" + moduleCount);
		lineOut = new LineOut();

		passThrough = new PassThrough();
		setDistribution(Distribution.STEREO);

		leftPort = new InPort("Source Left", passThrough.input);
		rightPort = new InPort("Source Right", passThrough.input);

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

	public Distribution getDistribution() {
		return distribution;
	}


	public void start() {
		lineOut.start();		
	}


	public void stop() {
		lineOut.stop();		
	}

}
