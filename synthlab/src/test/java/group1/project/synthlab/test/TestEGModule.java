package group1.project.synthlab.test;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.eg.IEGModule;
import junit.framework.TestCase;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.Circuit;

public class TestEGModule extends TestCase {

	Circuit c;

	protected IEGModule eg;
	protected Synthesizer synth;
	
	protected void setUp() throws Exception {
		Factory factory = new Factory();
		super.setUp();

		eg = factory.createEGModule();
		
		synth = JSyn.createSynthesizer();
		synth.add(eg.getCircuit());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testEGModule() {
		assertNotNull (eg.getEnvelope());
		assertTrue (!eg.getGate().isUsed());
		assertTrue (!eg.getOut().isUsed());
		assertTrue (!eg.getEnvelope().input.isConnected());
		assertTrue (eg.getEnvelope().output.isConnected());
		assertTrue (!eg.isStarted());
	}

	public void testDestruct() {
		eg.destruct();
		assertTrue (!eg.getGate().isUsed());
		assertTrue (!eg.getOut().isUsed());
	}

	public void testStart() {
		eg.start();
		assertTrue (eg.isStarted());
		System.err.println("Start...");
	}

	public void testStop() {
		eg.start();
		eg.stop();
		assertTrue (!eg.isStarted());
		System.err.println("Stop...");
	}

}
