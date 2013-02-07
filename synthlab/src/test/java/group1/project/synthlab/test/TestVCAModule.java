/**
 * 
 */
package group1.project.synthlab.test;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.vca.IVCAModule;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.Circuit;
import junit.framework.TestCase;

/**
 * Tests unitaires VCA
 * 
 * @author Groupe 1
 * 
 */
public class TestVCAModule extends TestCase {

	Circuit c;

	protected IVCAModule vca;
	protected Synthesizer synth;
	
	protected void setUp() throws Exception {
		Factory factory = new Factory();
		super.setUp();

		vca = factory.createVCAModule();
		
		synth = JSyn.createSynthesizer();
		synth.add(vca.getCircuit());

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testVCAModule() {
		assertNotNull (vca.getFiltera0());
		assertNotNull (vca.getFilteram());
	}

	public void testDestruct() {
		fail("Not yet implemented");
	}

	public void testChangeGain() {
		fail("Not yet implemented");
	}
	
	public void testStart() {
		fail("Not yet implemented");
	}

	public void testStop() {
		fail("Not yet implemented");
	}

	public void testIsStarted() {
		fail("Not yet implemented");
	}

	public void testCableConnected() {
		fail("Not yet implemented");
	}

	public void testCableDisconnected() {
		fail("Not yet implemented");
	}
	
}
