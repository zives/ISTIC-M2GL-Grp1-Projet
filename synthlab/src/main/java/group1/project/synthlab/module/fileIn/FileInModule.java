


package group1.project.synthlab.module.fileIn;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.module.vco.VCOModule;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.workspace.Workspace;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import com.jsyn.Synthesizer;
import com.jsyn.data.FloatSample;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.LineIn;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.jsyn.unitgen.VariableRateStereoReader;
import com.jsyn.util.SampleLoader;

public class FileInModule extends Module implements IFileInModule{

	protected static int moduleCount = 0;


	/* Defintion des ports */
	protected IOutPort outPort;
	protected IOutPort rightPort;
	protected IOutPort monoPort;

	/* jSyn module */
	//protected LineOut lineOut;
	protected transient PassThrough passThrough;


	protected  FloatSample sample;
	protected  Synthesizer synth;
	protected  VariableRateDataReader samplePlayer;
	protected  File sampleFile;

	public FileInModule(Factory factory) {
		super("Extra-" + ++moduleCount, factory);
		passThrough = new PassThrough();
		outPort = factory.createOutPort("out", passThrough.output, this);
		circuit.add(passThrough);
	}

	public void loadFile(File sampleFile) {
		SampleLoader.setJavaSoundPreferred(false);
		try {
			sample = SampleLoader.loadFloatSample(sampleFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println( "Sample has: channels  = " + sample.getChannelsPerFrame() );
		System.out.println( "            frames    = " + sample.getNumFrames() );
		System.out.println( "            rate      = " + sample.getFrameRate() );
		System.out.println( "            loopStart = " + sample.getSustainBegin() );
		System.out.println( "            loopEnd   = " + sample.getSustainEnd() );

		if (samplePlayer != null) {
			Workspace.getInstance().getSynthetizer().remove(samplePlayer);
		}

		if( sample.getChannelsPerFrame() == 1 )
		{
			samplePlayer = new VariableRateMonoReader() ;
			samplePlayer.output.connect( 0, passThrough.input, 0 );
		}
		else if( sample.getChannelsPerFrame() == 2 )
		{
			samplePlayer = new VariableRateStereoReader();
			samplePlayer.output.connect(passThrough.input);
		}
		else
		{
			throw new RuntimeException(
					"Can only play mono or stereo samples." );
		}

		Workspace.getInstance().getSynthetizer().add(samplePlayer);
		samplePlayer.rate.set(sample.getFrameRate());
		
		//Pour repeter en boucle
		samplePlayer.dataQueue.queueLoop(sample, 0, sample.getNumFrames ());
		
	}
	
	@Override
	public void resetCounterInstance() {
		FileInModule.moduleCount = 0;		
	}

	public FloatSample getSample() {
		return sample;
	}



	public IOutPort getOutPort() {
		return outPort;
	}

	public IOutPort getMonoPort() {
		return monoPort;
	}

	public PassThrough getPassThrough() {
		return passThrough;
	}

	@Override
	public void destruct() {
		if (outPort.isUsed())
			outPort.getCable().disconnect();
	}

	@Override
	public void cableConnected(IPort port) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cableDisconnected(IPort port) {
		// TODO Auto-generated method stub

	}

	// Test fonctionnel
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		Factory factory = new Factory();
		IFileInModule fileIn = factory.createFileInModule();
		fileIn.loadFile(new File(ClassLoader.getSystemResource("music.wav").getFile()));
		
	

	}

}