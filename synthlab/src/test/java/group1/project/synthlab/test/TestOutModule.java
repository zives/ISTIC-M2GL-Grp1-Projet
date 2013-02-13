package group1.project.synthlab.test;


import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.out.IOutModule;
import group1.project.synthlab.module.out.IOutModule.Distribution;
import group1.project.synthlab.module.out.OutModule;
import junit.framework.TestCase;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.SineOscillator;

public class TestOutModule extends TestCase {
	
	OutModule outModule; 
	Circuit c;

	protected IOutModule out;
	protected SineOscillator oscTest;
	protected Synthesizer synth;
	
	protected void setUp() throws Exception {
		Factory factory = new Factory();
		super.setUp();

		out = factory.createOutModule();
		oscTest = new SineOscillator();
		
		synth = JSyn.createSynthesizer();
		out.getLeftPort().getJSynPort().connect(oscTest.output);
		synth.add(oscTest);
		synth.add(out.getCircuit());
		oscTest.start();
		out.start();

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testOutModule() {
		assert(out.getCircuit() != null);
		assert(out.getLineOut() != null);
		assert(out.getRightPort() != null);
		assert(out.getCircuit() != null);
		assert(out.getDistribution() == Distribution.NORMAL);
		assert(!out.isStarted());
		assert(out.getAttenuation() == 0);
				
	}

	public void testSetDistribution() {

		out.setDistribution(Distribution.DISTRIBUTED);
		assert(out.getDistribution() == Distribution.DISTRIBUTED);
		System.err.println("Distribution 2 channels");
	
		out.setDistribution(Distribution.NORMAL);
		assert(out.getDistribution() == Distribution.NORMAL);
		System.err.println("Distribution normale");
	

	}

	public void testSetAttenuation() {
		out.setAttenuation(13);
		assert(out.getAttenuation() == 12);
		System.err.println("Attenuation +12db");
		
		out.setAttenuation(-20);
		assert(out.getAttenuation() == -20);
		System.err.println("Attenuation -20db");
		
		
		out.setAttenuation(0);
		assert(out.getAttenuation() == 0);
		System.err.println("Attenuation 0db");
		

	}
	
	public void testStart() {
		out.start();
		assert(out.isStarted());
		System.err.println("Start...");
	
	}

	public void testStop() {
		System.err.println("Preparation au stop...");
		out.start();
		
		out.stop();
		assert(!out.isStarted());
		System.err.println("Stop...");
		
		synth.stop();
		
	}

}
