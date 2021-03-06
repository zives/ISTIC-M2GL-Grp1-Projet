package group1.project.synthlab.unitExtension.filter.filterEnvelope;

import group1.project.synthlab.workspace.Workspace;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.UnitGate;

/**
 * @author Groupe 1
 * Une enveloppe AHDSR
 */
/**
 * @author Groupe 1
 *
 */
public class FilterEnvelopeAHDSR extends UnitGate {

	/**
	 * Precision de detection des flux montants et descendants
	 */
	public final double EDGE_DETECTION_ACCURACY = 0.01;
	
	// En secondes
	/**
	 * Le port JSyn attack en secondes
	 */
	public UnitInputPort attack;
	
	/**
	 * Le port JSyn hold en secondes
	 */
	public UnitInputPort hold;
		
	/**
	 * Le port JSyn decay en secondes
	 */
	public UnitInputPort decay;
	
	/**
	 * Le port JSyn release en secondes
	 */
	public UnitInputPort release;

	// En amplitude entre 0 et 1
	/**
	 * Le port JSyn sustain en amplitude
	 */
	public UnitInputPort sustain;

	/**
	 * @author Groupe 1
	 * Les differents etats d'une enveloppe
	 */
	public enum ENVELOPE_STATE {
		NOTHING, ATTACK, HOLD, DECAY, SUSTAIN, RELEASE, STOP
	};

	/**
	 * Etat courant de l'enveloppe
	 */
	protected ENVELOPE_STATE state;
	
	
	/**
	 * Le synthtiseur rattache
	 */
	protected Synthesizer synth;

	/**
	 * Compteur interne de temps
	 */
	protected double lastTime;
		
	/**
	 * Amplitude courante en sortie entre 0 et 1
	 */
	protected double currentAmp;
	

	/**
	 * Pas interne pour abaisser ou augmenter la tension dans le temps
	 */
	protected double stepAmp;
	
	/**
	 * Pas interne de temps a soustraire au temps courant avant nouvel etat
	 */
	protected int stepTime;	
	
	/**
	 * Compteur interne de temps
	 */
	protected int countTime;
	
	/**
	 * Precendente amplitude detectee au port d'entree
	 */
	protected double previousGateAmp;

	public FilterEnvelopeAHDSR() {
		
		//Ajout des ports
		addPort(attack = new UnitInputPort("attack"));
		addPort(hold = new UnitInputPort("hold"));
		addPort(decay = new UnitInputPort("decay"));
		addPort(release = new UnitInputPort("release"));
		addPort(sustain = new UnitInputPort("sustain"));

		//Initialisation des variables
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
			//Si l'etat est a stop on envoie toujours une tension de 0
			if (state == ENVELOPE_STATE.STOP) {
				outputs[i] = 0;
				continue;
			}
			
			//Si on detecte un front montant on arrete le cycle et on redemarre dans l'etat attack
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
			
			//Calcul de la tension selon les etats et le temps ecoule
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
				default:
					break;
					
			}
//			System.out.println("-----------------");
//			System.out.println("state:" + state);
//			System.out.println("current amp:" + currentAmp);
//			System.out.println("input:" + inputs[i]);
			
			//La tension d'entree courante est stockee
			previousGateAmp = inputs[i];
			
			//La valeur de sortie
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

	public void startProcessus() {
		state = ENVELOPE_STATE.NOTHING;
		
	}
	
	public void stopProcessus() {
		state = ENVELOPE_STATE.STOP;
		
	}
	


}
