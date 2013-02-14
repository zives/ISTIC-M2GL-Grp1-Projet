package group1.project.synthlab.test;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.vco.IVCOModule;
import group1.project.synthlab.module.vco.VCOModule;
import group1.project.synthlab.signal.Signal;
import junit.framework.TestCase;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.Circuit;

/**
 * Tests unitaires VCO
 * 
 * @author Groupe 1
 * 
 */

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
		assertNotNull(vco.getSineOsc());
		assertNotNull (vco.getSquareOsc());
		assertNotNull (vco.getTriangleOsc());
		assertNotNull (vco.getSawToothOsc());
		assertNotNull (vco.getFm());
		assertNotNull (vco.getCircuit());
		assertNotNull (vco.getOutSine());
		assertNotNull (vco.getOutSquare());
		assertNotNull (vco.getOutTriangle());
		assertNotNull (vco.getOutSawTooth());
		assertNotNull (vco.getFilterFrequencyModulation());
		assertEquals (VCOModule.amin, vco.getSineOsc().amplitude.get());
		assertEquals (VCOModule.amin, vco.getSquareOsc().amplitude.get());
		assertEquals (VCOModule.amin, vco.getTriangleOsc().amplitude.get());
		assertEquals (VCOModule.amin, vco.getSawToothOsc().amplitude.get());
		assertEquals (vco.getf0(), vco.getSineOsc().frequency.get());
		assertEquals (vco.getf0(), vco.getSquareOsc().frequency.get());
		assertEquals (vco.getf0(), vco.getTriangleOsc().frequency.get());
		assertEquals (vco.getf0(), vco.getSawToothOsc().frequency.get());
		assertTrue (vco.getSineOsc().output.isConnected());
		assertTrue (vco.getSquareOsc().output.isConnected());
		assertTrue (vco.getTriangleOsc().output.isConnected());
		assertTrue (vco.getSawToothOsc().output.isConnected());
		assertTrue (!vco.getSineOsc().frequency.isConnected());
		assertTrue (!vco.getSquareOsc().frequency.isConnected());
		assertTrue (!vco.getTriangleOsc().frequency.isConnected());
		assertTrue (!vco.getSawToothOsc().frequency.isConnected());
		assertTrue (!vco.getFmConnected());
		assertTrue (vco.getFilterFrequencyModulation().input.isConnected());
		assertTrue (vco.getFilterFrequencyModulation().output.isConnected());
	}

	/**
	 * Test method for {@link group1.project.synthlab.module.vco.VCOModule#destruct()}.
	 */
	public void testDestruct() {
		vco.destruct();
		assertTrue (!vco.getFm().isUsed());
		assertTrue (!vco.getOutSine().isUsed());
		assertTrue (!vco.getOutSquare().isUsed());
		assertTrue (!vco.getOutTriangle().isUsed());
		assertTrue (!vco.getOutSawTooth().isUsed());
	}
	
	/**
	 * Test method for {@link group1.project.synthlab.module.vco.VCOModule#destruct()}.
	 */
	public void testRedefAdjustments() {
		vco.setf0(630);
		vco.redefAdjustments();
		assertEquals ((int)(630/(Signal.FMAXAUDIBLE/100)), vco.getCoarseAdjustment());
		assertEquals ((630%(Signal.FMAXAUDIBLE/100))/(Signal.FMAXAUDIBLE/100), vco.getFineAdjustment());
		vco.setf0(660);
		vco.redefAdjustments();
		assertEquals ((int)(660/(Signal.FMAXAUDIBLE/100)), vco.getCoarseAdjustment());
		assertEquals ((660%(Signal.FMAXAUDIBLE/100))/(Signal.FMAXAUDIBLE/100), vco.getFineAdjustment());
	}
	
	/**
	 * Test method for {@link group1.project.synthlab.module.vco.VCOModule#changeFrequency()}.
	 */
	public void testChangeFrequency() {
		vco.setCoarseAdjustment(11);
		vco.setFineAdjustment(0);
		vco.changeFrequency();
		assertEquals ((Signal.FMAXAUDIBLE/100)*11, vco.getSineOsc().frequency.get());
		assertEquals ((Signal.FMAXAUDIBLE/100)*11, vco.getSquareOsc().frequency.get());
		assertEquals ((Signal.FMAXAUDIBLE/100)*11, vco.getTriangleOsc().frequency.get());
		assertEquals ((Signal.FMAXAUDIBLE/100)*11, vco.getSawToothOsc().frequency.get());
	}

	/**
	 * Test method for {@link group1.project.synthlab.module.vco.VCOModule#cableConnected(group1.project.synthlab.port.IPort)}.
	 */
	public void testCableConnected() {
		vco.cableConnected(vco.getFm());
		assertTrue (vco.getFmConnected());
	}

	/**
	 * Test method for {@link group1.project.synthlab.module.vco.VCOModule#cableDisconnected(group1.project.synthlab.port.IPort)}.
	 */
	public void testCableDisconnected() {
		vco.cableDisconnected(vco.getFm());
		assertTrue (!vco.getFmConnected());
	}

	/**
	 * Test method for {@link group1.project.synthlab.module.vco.VCOModule#start()}.
	 */
	public void testStart() {
		vco.start();
		assertTrue (vco.isStarted());
		assertEquals (VCOModule.a0, vco.getSineOsc().amplitude.get());
		assertEquals (VCOModule.a0, vco.getSquareOsc().amplitude.get());
		assertEquals (VCOModule.a0, vco.getTriangleOsc().amplitude.get());
		assertEquals (VCOModule.a0, vco.getSawToothOsc().amplitude.get());
		System.err.println("Start...");
	}

	/**
	 * Test method for {@link group1.project.synthlab.module.vco.VCOModule#stop()}.
	 */
	public void testStop() {
		System.err.println("Preparation au stop...");
		vco.start();
		vco.stop();
		assertTrue (!vco.isStarted());
		assertEquals (VCOModule.amin, vco.getSineOsc().amplitude.get());
		assertEquals (VCOModule.amin, vco.getSquareOsc().amplitude.get());
		assertEquals (VCOModule.amin, vco.getTriangleOsc().amplitude.get());
		assertEquals (VCOModule.amin, vco.getSawToothOsc().amplitude.get());
		System.err.println("Stop...");
		
		synth.stop();
	}

}
