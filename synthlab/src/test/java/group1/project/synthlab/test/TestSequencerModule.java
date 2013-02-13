package group1.project.synthlab.test;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.sequencer.ISequencerModule;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.Circuit;
import junit.framework.TestCase;

public class TestSequencerModule extends TestCase {

	Circuit c;

	protected ISequencerModule sequencer;
	protected Synthesizer synth;
	
	protected void setUp() throws Exception {
		Factory factory = new Factory();
		super.setUp();

		sequencer = factory.createSequencerModule();
		
		synth = JSyn.createSynthesizer();
		synth.add(sequencer.getCircuit());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSequencerModule() {
		assertNotNull (sequencer.getFilterRisingEdge());
		assertTrue (!sequencer.getGate().isUsed());
		assertTrue (!sequencer.getOut().isUsed());
		assertTrue (!sequencer.getFilterRisingEdge().input.isConnected());
		assertTrue (!sequencer.getFilterRisingEdge().output.isConnected());
		assertTrue (!sequencer.isStarted());
		assertEquals (1, sequencer.getCurrentStep());
	}

	public void testResetSteps() {
		sequencer.resetSteps();
		assertEquals(1, sequencer.getCurrentStep());
	}

	public void testUpdate() {
		int previousStep = sequencer.getCurrentStep();
		sequencer.update();
		if(previousStep == 8)
			assertEquals(1, sequencer.getCurrentStep());
		else
			assertEquals(previousStep + 1, sequencer.getCurrentStep());
	}

	public void testStart() {
		sequencer.start();
		assertTrue (sequencer.isStarted());
		System.err.println("Start...");;
	}

	public void testStop() {
		sequencer.start();
		sequencer.stop();
		assertTrue (!sequencer.isStarted());
		System.err.println("Stop...");
	}

	public void testDestruct() {
		sequencer.destruct();
		assertTrue (!sequencer.getGate().isUsed());
		assertTrue (!sequencer.getOut().isUsed());
	}

}
