package group1.project.synthlab.unitExtensions.envelope;

import group1.project.synthlab.workspace.Workspace;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.UnitGate;

public class Envelope extends UnitGate {

	public final double EDGE_DETECTION_ACCURACY = 0.01;
	
	// En secondes
	public UnitInputPort attack;
	public UnitInputPort hold;
	public UnitInputPort decay;
	public UnitInputPort release;

	// En amplitude entre 0 et 1
	public UnitInputPort sustain;

	public enum ENVELOPE_STATE {
		NOTHING, ATTACK, HOLD, DECAY, SUSTAIN, RELEASE
	};

	protected ENVELOPE_STATE state;
	protected Synthesizer synth;

	protected double lastTime;
	protected double currentAmp;
	protected double stepAmp;
	protected int stepTime;
	protected int countTime;
	
	protected double previousGateAmp;

	public Envelope() {
		addPort(attack = new UnitInputPort("attack"));
		addPort(hold = new UnitInputPort("hold"));
		addPort(decay = new UnitInputPort("decay"));
		addPort(release = new UnitInputPort("release"));
		addPort(sustain = new UnitInputPort("sustain"));

		synth = Workspace.getInstance().getSynthetizer();
		lastTime = synth.getCurrentTime();
		currentAmp = 0;
		stepAmp = 0;
		stepTime = 0;
		countTime = 0;
		state = ENVELOPE_STATE.NOTHING;
		
	}

	@Override
	public void generate(int start, int limit) {
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();

		for (int i = start; i < limit; i++) {
			if (input.checkGate(i)) {
				state = ENVELOPE_STATE.ATTACK;
				lastTime = synth.getCurrentTime();
				//Calculer le step d'amplitude par rapport au temps
				stepTime = (int) (synth.getFrameRate() *  attack.get());
				stepAmp =  1.0 / stepTime;
				countTime = 0;
				currentAmp = 0;
				
			}
			
			//Calcul
			currentAmp += stepAmp;
			countTime++;
			
			
			
			//Verifications
			switch (state) {
				case NOTHING:
					currentAmp = 0;
					countTime = 0;
					stepAmp = 0;
					break;
				case ATTACK:
					if (currentAmp >= 1) {
						currentAmp = 1;
						state = ENVELOPE_STATE.HOLD;
						stepTime = (int) (synth.getFrameRate() *  hold.get());
						stepAmp = 0;
						countTime = 0;
					}
					break;
				case HOLD:
					if (countTime >= stepTime) {
						currentAmp = 1;
						state = ENVELOPE_STATE.DECAY;
						stepTime = (int) (synth.getFrameRate() *  decay.get());
						stepAmp = -(1 - sustain.get()) / stepTime;
						countTime = 0;
					}
					break;
				case DECAY:
					if (currentAmp <= sustain.get()) {
						currentAmp = sustain.get();
						state = ENVELOPE_STATE.SUSTAIN;
						stepTime = 0;
						stepAmp = 0;
						countTime = 0;
					}
					break;
				case SUSTAIN:
					if ((previousGateAmp - inputs[i]) > EDGE_DETECTION_ACCURACY  || inputs[i] < EDGE_DETECTION_ACCURACY ) {						
						currentAmp = sustain.get();
						state = ENVELOPE_STATE.RELEASE;
						stepTime = (int) (synth.getFrameRate() *  release.get());
						stepAmp =  -(sustain.get() / stepTime);
						countTime = 0;
					}
					break;
				case RELEASE:
					if (currentAmp <= EDGE_DETECTION_ACCURACY ) {						
						currentAmp = 0;
						state = ENVELOPE_STATE.NOTHING;
						stepTime = 0;
						stepAmp =  0;
						countTime = 0;
					}
					break;
					
			}
//			System.out.println("-----------------");
//			System.out.println("state:" + state);
//			System.out.println("current amp:" + currentAmp);
//			System.out.println("input:" + inputs[i]);
			
			previousGateAmp = inputs[i];
			outputs[i] = currentAmp;
		}
		

	}

	public UnitInputPort getAttack() {
		return attack;
	}

	public UnitInputPort getHold() {
		return hold;
	}

	public UnitInputPort getDecay() {
		return decay;
	}

	public UnitInputPort getRelease() {
		return release;
	}

	public UnitInputPort getSustain() {
		return sustain;
	}

	public ENVELOPE_STATE getState() {
		return state;
	}
	


}
