package group1.project.synthlab.test;

<<<<<<< .mine
import javax.sql.rowset.spi.SyncFactory;

import group1.project.synthlab.module.OutModule;
import group1.project.synthlab.module.OutModule.Distribution;
=======
import group1.project.synthlab.module.OutModule;
import group1.project.synthlab.module.OutModule.Distribution;


>>>>>>> .theirs
import junit.framework.TestCase;

<<<<<<< .mine
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SineOscillator;

=======
import com.jsyn.unitgen.Circuit;



>>>>>>> .theirs
public class TestOutModule extends TestCase {
	
	OutModule outModule; 
	Circuit c;

	protected OutModule out;
	protected SineOscillator oscTest;
	protected Synthesizer synth;
	
	protected void setUp() throws Exception {
		super.setUp();
<<<<<<< .mine
		out = new OutModule();
		oscTest = new SineOscillator();
		
		synth = JSyn.createSynthesizer();
		out.getLeftPort().getJSynPort().connect(oscTest.output);
		synth.add(oscTest);
		synth.add(out.getCircuit());
		oscTest.start();
		out.start();
		synth.start();
=======
		outModule = new OutModule();
		c = new Circuit();








>>>>>>> .theirs
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

	public void testGetDistribution() {
		assertEquals(Distribution.MONO, outModule.getDistribution());
	}
	
	public void testSetDistribution() {
<<<<<<< .mine
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
=======
		outModule.setDistribution(Distribution.STEREO);
		assertEquals(Distribution.STEREO, outModule.getDistribution());
	}









>>>>>>> .theirs

<<<<<<< .mine
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
=======
	public void testMitigate() {
		outModule.mitigate(20);
		// TODO : ne faudrait-il pas un get pour dB ou pour attenuateur ??








>>>>>>> .theirs
	}

<<<<<<< .mine
	public void testSetAttenuation() {
		out.setAttenuation(13);
		assert(out.getAttenuation() == 12);
		System.err.println("Atténuation +12db");
		
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
		System.err.println("Atténuation -20db");
		
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
		System.err.println("Atténuation 0db");
		
		try
		{
			double time = synth.getCurrentTime();
			synth.sleepUntil( time + 1.0 );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}
=======
	public void testGetLineOut() {
		assertNotNull(outModule.getLineOut());





































>>>>>>> .theirs
	}
<<<<<<< .mine
	
	public void testStart() {












=======

	public void testGetLeftPort() {
		assertNotNull(outModule.getLeftPort());
	}

	public void testGetRightPort() {
		assertNotNull(outModule.getRightPort());
	}

	public void testStartFalse() {
		assertFalse(outModule.isStarted());
	}
	
	public void testStartTrue() {
>>>>>>> .theirs
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

<<<<<<< .mine












=======
	public void testIsOnFalse() {
		assertFalse(outModule.isStarted());
	}
	
	public void testIsOnTrue() {		
		fail("Not yet implemented");
	}

	public void testMain() {
		fail("Not yet implemented");
	}

>>>>>>> .theirs
}
