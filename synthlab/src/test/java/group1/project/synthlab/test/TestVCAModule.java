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

	/**
	 * Test method for {@link group1.project.synthlab.module.vca.VCAModule#VCAModule(group1.project.synthlab.factory.Factory)}.
	 */
	public void testVCAModule() {
		assertNotNull (vca.getFiltera0());
		assertNotNull (vca.getFilteram());
		assertTrue (!vca.getIn().isUsed());
		assertTrue (!vca.getAm().isUsed());
		assertTrue (!vca.getOut().isUsed());
		assertTrue (vca.getFiltera0().inputA.isConnected());
		assertEquals (vca.geta0()/60, vca.getFiltera0().inputB.get());
		assertTrue (vca.getFiltera0().output.isConnected());
		assertTrue (vca.getFilteram().inputA.isConnected());
		assertTrue (vca.getFilteram().inputB.isConnected());
		assertTrue (vca.getFilteram().output.isConnected());
		assertTrue (!vca.isStarted());
	}

	/**
	 * Test method for {@link group1.project.synthlab.module.vca.VCAModule#destruct()}.
	 */
	public void testDestruct() {
		vca.destruct();
		assertTrue (!vca.getAm().isUsed());
		assertTrue (!vca.getIn().isUsed());
		assertTrue (!vca.getOut().isUsed());
	}

	/**
	 * Test method for {@link group1.project.synthlab.module.vca.VCAModule#changeGain()}.
	 */
	public void testChangeGain() {
		vca.seta0(6);
		vca.changeGain();
		assertEquals (vca.geta0()/60, vca.getFiltera0().inputB.get());
	}
	
	/**
	 * Test method for {@link group1.project.synthlab.module.vca.VCAModule#start()}.
	 */
	public void testStart() {
		vca.start();
		assertTrue (vca.isStarted());
		System.err.println("Start...");
	}
	
	/**
	 * Test method for {@link group1.project.synthlab.module.vca.VCAModule#stop()}.
	 */
	public void testStop() {
		vca.stop();
		assertTrue (!vca.isStarted());
		System.err.println("Stop...");
	}
	
}
