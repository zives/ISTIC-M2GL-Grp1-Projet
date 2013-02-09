package group1.project.synthlab.test;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.multiplexer.MultiplexerModule;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import junit.framework.TestCase;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;

public class TestMultiplexerModule extends TestCase {

	private MultiplexerModule module;

	public void testMultiplexerModule() {
		assert (module.getInPorts() != null);
		assert (module.getOutPorts() != null);
	}

	public void testDestruct() {
		for (int i = 0; i < 4; ++i) {
			assert (!module.getInPorts()[i].isUsed());
			assert (!module.getOutPorts()[i].isUsed());
		}

	}

	public void testGetInPort() {
		for (int i = 0; i < 4; ++i) {
			assert (module.getInPort(i) != null && module.getInPort(i) instanceof IInPort );
		}
	}

	public void testGetOutPort() {
		for (int i = 0; i < 4; ++i) {
			assert (module.getOutPort(i) != null && module.getOutPort(i) instanceof IOutPort );
		}
	}

	public void testGetInPorts() {
		assert (module.getInPorts() != null);
		assert (module.getInPorts().length == 4);
	}

	public void testGetOutPorts() {
		assert (module.getOutPorts() != null);
		assert (module.getOutPorts().length == 4);
	}

	public void testStart() {
		assert(module.isStarted());
	}

	public void testStop() {
		assert(!module.isStarted());
	}

	public void testIsStarted() {
		Synthesizer synth = JSyn.createSynthesizer();
		synth.add(module.getCircuit());
		module.start();
		assert(module.isStarted());
		module.stop();
		assert(!module.isStarted());
	}

	@Override
	protected void setUp() throws Exception {
		Factory fact = new Factory();
		module = (MultiplexerModule) fact.createMultiplexerModule();
		super.setUp();
	}

}
