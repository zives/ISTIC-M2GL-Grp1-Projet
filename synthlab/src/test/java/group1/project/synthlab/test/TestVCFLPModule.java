package group1.project.synthlab.test;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.vcf.lp.IVCFLPModule;
import group1.project.synthlab.signal.Signal;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.Circuit;
import junit.framework.TestCase;

/**
 * Tests unitaires VCFLP
 * 
 * @author Groupe 1
 * 
 */

public class TestVCFLPModule extends TestCase {

	Circuit c;

	protected IVCFLPModule vcflp;
	protected Synthesizer synth;
	
	protected void setUp() throws Exception {
		Factory factory = new Factory();
		super.setUp();

		vcflp = factory.createVCFLPModule();
		
		synth = JSyn.createSynthesizer();
		synth.add(vcflp.getCircuit());
		vcflp.start();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testVCFModule() {
		assertNotNull(vcflp.getFilter1());
		assertNotNull(vcflp.getFilter2());
		assertNotNull(vcflp.getFilterFrequencyModulation());
		assertNotNull(vcflp.getFilterAmplitude());
		assertNotNull(vcflp.getIn());
		assertNotNull(vcflp.getFm());
		assertNotNull(vcflp.getOut());
		assertEquals (vcflp.getf0(), vcflp.getFilterFrequencyModulation().getf0());
		assertEquals (vcflp.getf0(), vcflp.getFilter1().frequency.getValue());
		assertEquals (vcflp.getf0(), vcflp.getFilter2().frequency.getValue());
		assertEquals (vcflp.getq(), vcflp.getFilter1().Q.get());
		assertEquals (vcflp.getq(), vcflp.getFilter2().Q.get());
		assertTrue (vcflp.getFilter1().input.isConnected());
		assertTrue (vcflp.getFilter1().output.isConnected());
		assertTrue (vcflp.getFilter2().input.isConnected());
		assertTrue (vcflp.getFilter2().output.isConnected());
		assertTrue (!vcflp.getIn().isUsed());
		assertTrue (!vcflp.getFm().isUsed());
		assertTrue (!vcflp.getOut().isUsed());
	}

	public void testDestruct() {
		vcflp.destruct();
		assertTrue (!vcflp.getIn().isUsed());
		assertTrue (!vcflp.getFm().isUsed());
		assertTrue (!vcflp.getOut().isUsed());
	}

	public void testChangeFrequency() {
		vcflp.setCoarseAdjustment(11);
		vcflp.setFineAdjustment(0);
		vcflp.changeFrequency();
		assertEquals ((Signal.FMAX/100)*11, vcflp.getFilterFrequencyModulation().getf0());
	}

	public void testRedefAdjustments() {
		vcflp.setf0(550);
		vcflp.redefAdjustments();
		assertEquals ((int)(550/(Signal.FMAX/100)), vcflp.getCoarseAdjustment());
		assertEquals ((550%(Signal.FMAX/100))/(Signal.FMAX/100), vcflp.getFineAdjustment());
		vcflp.setf0(660);
		vcflp.redefAdjustments();
		assertEquals ((int)(660/(Signal.FMAX/100)), vcflp.getCoarseAdjustment());
		assertEquals ((660%(Signal.FMAX/100))/(Signal.FMAX/100), vcflp.getFineAdjustment());
	}

	public void testChangeQFactor() {
		vcflp.setq(0.5);
		vcflp.changeQFactor();
		assertEquals (0.5, vcflp.getFilter1().Q.get());
		assertEquals (0.5, vcflp.getFilter2().Q.get());
	}
}
