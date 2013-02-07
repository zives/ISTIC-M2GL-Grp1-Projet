package group1.project.synthlab.module.extra;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.FloatSample;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.jsyn.unitgen.VariableRateStereoReader;
import com.jsyn.util.SampleLoader;

public class ExtraModule extends Module implements IExtraModule{

	protected static int moduleCount = 0;


	/* Defintion des ports */
	protected IInPort leftPort;
	protected IInPort rightPort;

	/* jSyn module */
	//protected LineOut lineOut;

	private boolean isOn;

	public ExtraModule(Factory factory) {
		super("Extra-" + ++moduleCount, factory);


	}

	@Override
	public void start() {
		circuit.start();
		//lineOut.setSynthesisEngine(circuit.getSynthesisEngine());
		//lineOut.start();
		isOn = true;	
	}

	@Override
	public void stop() {
	//	lineOut.stop();
		circuit.stop();
		isOn = false;
	}

	@Override
	public boolean isStarted() {
		return isOn;
	}

	@Override
	public void destruct() {
		if (rightPort.isUsed())
			rightPort.getCable().disconnect();

		if (leftPort.isUsed())
			leftPort.getCable().disconnect();
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
	
		FloatSample sample;
		Synthesizer synth;
		VariableRateDataReader samplePlayer;
		LineOut lineOut;
		File sampleFile;
		
		sampleFile = new File("W:/S4/ISTIC-M2GL-Grp1-Projet/synthlab/resources/music.wav");
		
		synth = JSyn.createSynthesizer();
			// Add an output mixer.
			

			try {
				
				synth.add( lineOut = new LineOut() );

				SampleLoader.setJavaSoundPreferred(true);
				sample = SampleLoader.loadFloatSample(sampleFile);


				if( sample.getChannelsPerFrame() == 1 )
				{
					synth.add( samplePlayer = new VariableRateMonoReader() );
					samplePlayer.output.connect( 0, lineOut.input, 0 );
				}
				else if( sample.getChannelsPerFrame() == 2 )
				{
					synth.add( samplePlayer = new VariableRateStereoReader() );
					samplePlayer.output.connect( 0, lineOut.input, 0 );
					samplePlayer.output.connect( 1, lineOut.input, 1 );
				}
				else
				{
					throw new RuntimeException(
							"Can only play mono or stereo samples." );
				}

				// Start synthesizer using default stereo output at 44100 Hz.
				synth.start();

				samplePlayer.rate.set( sample.getFrameRate() );

				// We only need to start the LineOut. It will pull data from the
				// sample player.
				lineOut.start();

				// We can simply queue the entire file.
				// Or if it has a loop we can play the loop for a while.
				if( sample.getSustainBegin() < 0 )
				{
					System.out.println( "queue the sample" );
					samplePlayer.dataQueue.queue( sample );
				}
				else
				{
					System.out.println( "queueOn the sample" );
					samplePlayer.dataQueue.queueOn( sample );
					synth.sleepFor( 8.0 );
					System.out.println( "queueOff the sample" );
					samplePlayer.dataQueue.queueOff( sample );
				}

				// Wait until the sample has finished playing.
				do
				{
					synth.sleepFor( 1.0 );
				} while( samplePlayer.dataQueue.hasMore() );

			} catch( IOException e1 )
			{
				e1.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Stop everything.
			synth.stop();
		}
}
	


