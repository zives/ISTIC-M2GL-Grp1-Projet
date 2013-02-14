package group1.project.synthlab.test;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.vcf.hp.IVCFHPModule;
import group1.project.synthlab.signal.Signal;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.Circuit;
import junit.framework.TestCase;

/**
 * Tests unitaires VCFHP
 * 
 * @author Groupe 1
 * 
 */

public class TestVCFHPModule extends TestCase {

	Circuit c;

	protected IVCFHPModule vcfhp;
	protected Synthesizer synth;
	
	protected void setUp() throws Exception {
		Factory factory = new Factory();
		super.setUp();

		vcfhp = factory.createVCFHPModule();
		
		synth = JSyn.createSynthesizer();
		synth.add(vcfhp.getCircuit());
		vcfhp.start();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testVCFModule() {
		assertNotNull(vcfhp.getFilter());
		assertNotNull(vcfhp.getFilterFrequencyModulation());
		assertNotNull(vcfhp.getFilterAmplitude());
		assertNotNull(vcfhp.getIn());
		assertNotNull(vcfhp.getFm());
		assertNotNull(vcfhp.getOut());
		assertEquals (vcfhp.getf0(), vcfhp.getFilterFrequencyModulation().getf0());
		assertEquals (vcfhp.getf0(), vcfhp.getFilter().frequency.getValue());
		assertTrue (!vcfhp.getFilter().input.isConnected());
		assertTrue (vcfhp.getFilter().output.isConnected());
		assertTrue (!vcfhp.getIn().isUsed());
		assertTrue (!vcfhp.getFm().isUsed());
		assertTrue (!vcfhp.getOut().isUsed());
	}

	public void testDestruct() {
		vcfhp.destruct();
		assertTrue (!vcfhp.getIn().isUsed());
		assertTrue (!vcfhp.getFm().isUsed());
		assertTrue (!vcfhp.getOut().isUsed());
	}

	public void testChangeFrequency() {
		vcfhp.setCoarseAdjustment(11);
		vcfhp.setFineAdjustment(0);
		vcfhp.changeFrequency();
		assertEquals ((Signal.FMAX/100)*11, vcfhp.getFilterFrequencyModulation().getf0());
	}

	public void testRedefAdjustments() {
		vcfhp.setf0(550);
		vcfhp.redefAdjustments();
		assertEquals ((int)(550/(Signal.FMAX/100)), vcfhp.getCoarseAdjustment());
		assertEquals ((550%(Signal.FMAX/100))/(Signal.FMAX/100), vcfhp.getFineAdjustment());
		vcfhp.setf0(660);
		vcfhp.redefAdjustments();
		assertEquals ((int)(660/(Signal.FMAX/100)), vcfhp.getCoarseAdjustment());
		assertEquals ((660%(Signal.FMAX/100))/(Signal.FMAX/100), vcfhp.getFineAdjustment());
	}

}
