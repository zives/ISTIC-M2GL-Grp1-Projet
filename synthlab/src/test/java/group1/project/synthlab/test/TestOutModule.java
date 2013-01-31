package group1.project.synthlab.test;


import group1.project.synthlab.control.module.OutModule;
import group1.project.synthlab.control.module.OutModule.Distribution;
import junit.framework.TestCase;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.SineOscillator;

public class TestOutModule extends TestCase {
	
	OutModule outModule; 
	Circuit c;

	protected OutModule out;
	protected SineOscillator oscTest;
	protected Synthesizer synth;
	
	protected void setUp() throws Exception {
		super.setUp();

		out = new OutModule();
		oscTest = new SineOscillator();
		
		synth = JSyn.createSynthesizer();
		out.getLeftPort().getJSynPort().connect(oscTest.output);
		synth.add(oscTest);
		synth.add(out.getCircuit());
		oscTest.start();
		out.start();
		synth.start();

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
		
		try
		{
			double time = synth.getCurrentTime();
			synth.sleepUntil( time + 1.0 );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}
	}

	public void testSetDistribution() {

		out.setDistribution(Distribution.DISTRIBUTED);
		assert(out.getDistribution() == Distribution.DISTRIBUTED);
		System.err.println("Distribution 2channels");
		
		try
		{
			double time = synth.getCurrentTime();
			synth.sleepUntil( time + 3.0 );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}

		out.setDistribution(Distribution.NORMAL);
		assert(out.getDistribution() == Distribution.NORMAL);
		System.err.println("Distribution normale");
		try
		{
			double time = synth.getCurrentTime();
			synth.sleepUntil( time + 3.0 );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}

	}

	public void testSetAttenuation() {
		out.setAttenuation(13);
		assert(out.getAttenuation() == 12);
		System.err.println("Att�nuation +12db");
		
		try
		{
			double time = synth.getCurrentTime();
			synth.sleepUntil( time + 1.0 );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}
		
		out.setAttenuation(-20);
		assert(out.getAttenuation() == -20);
		System.err.println("Att�nuation -20db");
		
		try
		{
			double time = synth.getCurrentTime();
			synth.sleepUntil( time + 1.0 );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}
		
		out.setAttenuation(0);
		assert(out.getAttenuation() == 0);
		System.err.println("Att�nuation 0db");
		
		try
		{
			double time = synth.getCurrentTime();
			synth.sleepUntil( time + 1.0 );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}

	}
	
	public void testStart() {
		out.start();
		assert(out.isStarted());
		System.err.println("Start...");
		try
		{
			double time = synth.getCurrentTime();
			synth.sleepUntil( time + 4.0 );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}
	}

	public void testStop() {
		System.err.println("Préparation au stop...");
		out.start();
		try
		{
			double time = synth.getCurrentTime();
			synth.sleepUntil( time + 2.0 );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}
		
		out.stop();
		assert(!out.isStarted());
		System.err.println("Stop...");
		try
		{
			double time = synth.getCurrentTime();
			synth.sleepUntil( time + 4.0 );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}
	}

}
