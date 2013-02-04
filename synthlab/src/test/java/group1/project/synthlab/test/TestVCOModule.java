package group1.project.synthlab.test;


import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.vco.IVCOModule;
import group1.project.synthlab.module.vco.VCOModule;
import junit.framework.TestCase;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.Circuit;

public class TestVCOModule extends TestCase {
	
	Circuit c;

	protected IVCOModule vco;
	protected Synthesizer synth;
	
	protected void setUp() throws Exception {
		Factory factory = new Factory();
		super.setUp();

		vco = factory.createVCOModule();
		
		synth = JSyn.createSynthesizer();
		synth.add(vco.getCircuit());

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link group1.project.synthlab.module.vco.VCOModule#VCOModule(group1.project.synthlab.factory.Factory)}.
	 */
	public void testVCOModule() {
		assert (vco.getSineOsc() != null);
		assert (vco.getSquareOsc() != null);
		assert (vco.getTriangleOsc() != null);
		assert (vco.getFm() != null);
		assert (vco.getCircuit() != null);
		assert (vco.getOutSine() != null);
		assert (vco.getOutSquare() != null);
		assert (vco.getOutTriangle() != null);
		assert (vco.getOutSine() != null);
		assert (vco.getFmConnected() == false);
		assert (vco.getSineOsc().amplitude.get() == VCOModule.a0);
		assert (vco.getSquareOsc().amplitude.get() == VCOModule.a0);
		assert (vco.getTriangleOsc().amplitude.get() == VCOModule.a0);
		assert (vco.getSineOsc().frequency.get() == vco.getf0());
		assert (vco.getSquareOsc().frequency.get() == vco.getf0());
		assert (vco.getTriangleOsc().frequency.get() == vco.getf0());
	}

	/**
	 * Test method for {@link group1.project.synthlab.module.vco.VCOModule#changeFrequency()}.
	 */
	public void testChangeFrequency() {
		vco.setCoarseAdjustment(1);
		vco.setFineAdjustment(0);
		vco.changeFrequency();
		assert (vco.getSineOsc().frequency.get() == (VCOModule.fmax - VCOModule.fmin)/50);
		assert (vco.getSquareOsc().frequency.get() == (VCOModule.fmax - VCOModule.fmin)/50);
		assert (vco.getTriangleOsc().frequency.get() == (VCOModule.fmax - VCOModule.fmin)/50);
	}

	/**
	 * Test method for {@link group1.project.synthlab.module.vco.VCOModule#cableConnected(group1.project.synthlab.port.IPort)}.
	 */
	public void testCableConnected() {
		vco.cableConnected(vco.getFm());
		assert (vco.getFmConnected() == true);
	}

	/**
	 * Test method for {@link group1.project.synthlab.module.vco.VCOModule#cableDisconnected(group1.project.synthlab.port.IPort)}.
	 */
	public void testCableDisconnected() {
		vco.cableDisconnected(vco.getFm());
		assert (vco.getFmConnected() == false);
	}

	/**
	 * Test method for {@link group1.project.synthlab.module.vco.VCOModule#start()}.
	 */
	public void testStart() {
		vco.start();
		assert(vco.isStarted());
		assert (vco.getSineOsc().amplitude.get() == VCOModule.a0);
		assert (vco.getSquareOsc().amplitude.get() == VCOModule.a0);
		assert (vco.getTriangleOsc().amplitude.get() == VCOModule.a0);
		System.err.println("Start...");
	}

	/**
	 * Test method for {@link group1.project.synthlab.module.vco.VCOModule#stop()}.
	 */
	public void testStop() {
		System.err.println("Preparation au stop...");
		vco.start();
		vco.stop();
		assert(!vco.isStarted());
		assert (vco.getSineOsc().amplitude.get() == VCOModule.amin);
		assert (vco.getSquareOsc().amplitude.get() == VCOModule.amin);
		assert (vco.getTriangleOsc().amplitude.get() == VCOModule.amin);
		System.err.println("Stop...");
		
		synth.stop();
	}

}
