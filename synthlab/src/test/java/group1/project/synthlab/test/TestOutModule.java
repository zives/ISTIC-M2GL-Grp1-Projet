package group1.project.synthlab.test;

import group1.project.synthlab.module.OutModule;
import group1.project.synthlab.module.OutModule.Distribution;
import junit.framework.TestCase;

import com.jsyn.unitgen.Circuit;

public class TestOutModule extends TestCase {
	
	OutModule outModule; 
	Circuit c;

	protected void setUp() throws Exception {
		super.setUp();
		outModule = new OutModule();
		c = new Circuit();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testOutModule() {
		fail("Not yet implemented");
	}

	public void testGetDistribution() {
		assertEquals(Distribution.MONO, outModule.getDistribution());
	}
	
	public void testSetDistribution() {
		outModule.setDistribution(Distribution.STEREO);
		assertEquals(Distribution.STEREO, outModule.getDistribution());
	}

	public void testMitigate() {
		outModule.mitigate(20);
		// TODO : ne faudrait-il pas un get pour dB ou pour attenuateur ??
	}

	public void testGetLineOut() {
		assertNotNull(outModule.getLineOut());
	}

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
		fail("Not yet implemented");
	}

	public void testStop() {
		fail("Not yet implemented");
	}

	public void testIsOnFalse() {
		assertFalse(outModule.isStarted());
	}
	
	public void testIsOnTrue() {		
		fail("Not yet implemented");
	}

	public void testMain() {
		fail("Not yet implemented");
	}

}
