package group1.project.synthlab.test;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.vcf.IVCFModule;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.Circuit;
import junit.framework.TestCase;

/**
 * Tests unitaires VCF
 * 
 * @author Groupe 1
 * 
 */

public class TestVCFModule extends TestCase {

	Circuit c;

	protected IVCFModule vcf;
	protected Synthesizer synth;
	
	protected void setUp() throws Exception {
		Factory factory = new Factory();
		super.setUp();

		vcf = factory.createVCFModule();
		
		synth = JSyn.createSynthesizer();
		synth.add(vcf.getCircuit());
		vcf.start();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testVCFModule() {
		assertNotNull(vcf.getFilter1());
		assertNotNull(vcf.getFilter2());
		assertNotNull(vcf.getFilterFrequencyModulation());
		assertNotNull(vcf.getFilterAmplitude());
		assertNotNull(vcf.getIn());
		assertNotNull(vcf.getFm());
		assertNotNull(vcf.getOut());
		assertEquals (vcf.getf0(), vcf.getFilterFrequencyModulation().getf0());
		assertEquals (vcf.getf0(), vcf.getFilter1().frequency.getValue());
		assertEquals (vcf.getf0(), vcf.getFilter2().frequency.getValue());
		assertEquals (vcf.getq(), vcf.getFilter1().Q.get());
		assertEquals (vcf.getq(), vcf.getFilter2().Q.get());
		assertTrue (!vcf.getFilter1().input.isConnected());
		assertTrue (vcf.getFilter1().output.isConnected());
		assertTrue (vcf.getFilter2().input.isConnected());
		assertTrue (vcf.getFilter2().output.isConnected());
		assertTrue (!vcf.getIn().isUsed());
		assertTrue (!vcf.getFm().isUsed());
		assertTrue (!vcf.getOut().isUsed());
	}

	public void testDestruct() {
		vcf.destruct();
		assertTrue (!vcf.getIn().isUsed());
		assertTrue (!vcf.getFm().isUsed());
		assertTrue (!vcf.getOut().isUsed());
	}

	public void testChangeFrequency() {
		vcf.setCoarseAdjustment(11);
		vcf.setFineAdjustment(0);
		vcf.changeFrequency();
		assertEquals (660.0, vcf.getFilterFrequencyModulation().getf0());
	}

	public void testRedefAdjustments() {
		vcf.setf0(630);
		vcf.redefAdjustments();
		assertEquals (vcf.getCoarseAdjustment(), 10);
		assertEquals (vcf.getFineAdjustment(), 0.5);
		vcf.setf0(660);
		vcf.redefAdjustments();
		assertEquals (vcf.getCoarseAdjustment(), 11);
		assertEquals (vcf.getFineAdjustment(), 0.0);
	}

	public void testChangeQFactor() {
		vcf.setq(0.5);
		vcf.changeQFactor();
		assertEquals (0.5, vcf.getFilter1().Q.get());
		assertEquals (0.5, vcf.getFilter2().Q.get());
	}
}
