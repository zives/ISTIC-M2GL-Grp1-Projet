package group1.project.synthlab.test;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.vco.IVCOModule;
import group1.project.synthlab.module.vco.VCOModule;
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
		assertNotNull (vco.getFm());
		assertNotNull (vco.getCircuit());
		assertNotNull (vco.getOutSine());
		assertNotNull (vco.getOutSquare());
		assertNotNull (vco.getOutTriangle());
		assertNotNull (vco.getOutSine());
		assertEquals (VCOModule.amin, vco.getSineOsc().amplitude.get());
		assertEquals (VCOModule.amin, vco.getSquareOsc().amplitude.get());
		assertEquals (VCOModule.amin, vco.getTriangleOsc().amplitude.get());
		assertEquals (vco.getf0(), vco.getSineOsc().frequency.get());
		assertEquals (vco.getf0(), vco.getSquareOsc().frequency.get());
		assertEquals (vco.getf0(), vco.getTriangleOsc().frequency.get());
		assertTrue (!vco.getSineOsc().output.isConnected());
		assertTrue (!vco.getSquareOsc().output.isConnected());
		assertTrue (!vco.getTriangleOsc().output.isConnected());
		assertTrue (!vco.getSineOsc().frequency.isConnected());
		assertTrue (!vco.getSquareOsc().frequency.isConnected());
		assertTrue (!vco.getTriangleOsc().frequency.isConnected());
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
	}
	
	/**
	 * Test method for {@link group1.project.synthlab.module.vco.VCOModule#destruct()}.
	 */
	public void testRedefAdjustments() {
		vco.setf0(630);
		vco.redefAdjustments();
		assertEquals (vco.getCoarseAdjustment(), 10);
		assertEquals (vco.getFineAdjustment(), 0.5);
		vco.setf0(660);
		vco.redefAdjustments();
		assertEquals (vco.getCoarseAdjustment(), 11);
		assertEquals (vco.getFineAdjustment(), 0.0);
	}
	
	/**
	 * Test method for {@link group1.project.synthlab.module.vco.VCOModule#changeFrequency()}.
	 */
	public void testChangeFrequency() {
		vco.setCoarseAdjustment(11);
		vco.setFineAdjustment(0);
		vco.changeFrequency();
		assertEquals (660.0, vco.getSineOsc().frequency.get());
		assertEquals (660.0, vco.getSquareOsc().frequency.get());
		assertEquals (660.0, vco.getTriangleOsc().frequency.get());
	}

	/**
	 * Test method for {@link group1.project.synthlab.module.vco.VCOModule#cableConnected(group1.project.synthlab.port.IPort)}.
	 */
	public void testCableConnected() {
		vco.cableConnected(vco.getFm());
		assertTrue (vco.getFmConnected());
		assertTrue (vco.getSineOsc().frequency.isConnected());
		assertTrue (vco.getSquareOsc().frequency.isConnected());
		assertTrue (vco.getTriangleOsc().frequency.isConnected());
	}

	/**
	 * Test method for {@link group1.project.synthlab.module.vco.VCOModule#cableDisconnected(group1.project.synthlab.port.IPort)}.
	 */
	public void testCableDisconnected() {
		vco.cableDisconnected(vco.getFm());
		assertTrue (!vco.getFmConnected());
		assertTrue (!vco.getSineOsc().frequency.isConnected());
		assertTrue (!vco.getSquareOsc().frequency.isConnected());
		assertTrue (!vco.getTriangleOsc().frequency.isConnected());
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
		System.err.println("Stop...");
		
		synth.stop();
	}

}
