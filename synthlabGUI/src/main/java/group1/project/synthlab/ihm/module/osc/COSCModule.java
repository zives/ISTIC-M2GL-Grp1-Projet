package group1.project.synthlab.ihm.module.osc;

import group1.project.synthlab.exceptions.BufferTooBig;
import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.osc.OSCModule;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.workspace.Workspace;

import java.awt.Component;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import com.jsyn.Synthesizer;

public class COSCModule extends OSCModule implements ICOSCModule {

	protected IPOSCModule presentation;
	protected Queue<Double> valuesToDrawBuffer;
	protected List<Double> valuesToDraw;
	protected double previousLastTime;
	protected Thread internalThread;
	protected Timer timer;
	protected Synthesizer synth = Workspace.getInstance().getSynthetizer();

	public COSCModule(CFactory factory) {
		super(factory);
		this.presentation = new POSCModule(this);
		self = this;
		valuesToDraw = new CopyOnWriteArrayList<>();
		valuesToDrawBuffer = new ConcurrentLinkedQueue<Double>();
		((Component) presentation).repaint();

	}

//	private void refreshValues() throws InterruptedException {
//		while (true) {
//			this.buffer.clear();
//			synth.sleepFor(0.05);
//			previousLastTime = synth.getCurrentTime();
//
//			valuesToDraw.clear();
//			Iterator<Double> it = this.buffer.iterator();
//			int cmpt = 0 ;
//			int max = (int) (synth.getFrameRate() * 0.05);
//			while (it.hasNext()&& cmpt < max) {
//				++cmpt;
//				//valuesToDrawBuffer.add(it.next());
//				valuesToDraw.add(it.next()	);
//			}
//			System.err.println(this.buffer.size());
//			
//			//valuesToDraw.addAll(this.buffer);
//			((Component) presentation).repaint();
//
//		}
//	}
protected int cmptPaint = 0;
	@Override
	public void interceptionResult(List<Double> buffer, double time)
			throws BufferTooBig {
		super.interceptionResult(buffer, time);
		
		int maxCmptPaint = (int) (0.05/ 0.05); // nombre d'iterationsavant de parvenir à 20ms pour un affiche fluide (pas trop rapide pr l'ecran)
		if (this.buffer.size() >= synth.getFrameRate() * 0.05) { //augmenter ce nombre pour zoomer (moins de bande à afficher d'un coup)
			
			valuesToDraw.clear();
			int cmpt = 0 ;
			int max = (int) (synth.getFrameRate() * 0.05);
			while (cmpt < max) {
				++cmpt;
				//valuesToDrawBuffer.add(this.buffer.poll());
				System.err.println(this.buffer.peek());
				valuesToDraw.add(this.buffer.poll());
				
			}
			if (++cmptPaint>= maxCmptPaint) {
			((Component) presentation).repaint();
				cmptPaint = 0;
			}
		}
	}

	@Override
	public void start() {
		super.start();
		internalThread = new Thread(new Runnable() {

			@Override
			public void run() {
//				try {
//					previousLastTime = synth.getCurrentTime();
//				//	refreshValues();
//				} catch (InterruptedException e) {
//
//				}
			}
		});
		internalThread.start();

		timer = new Timer();
//		timer.schedule(new TimerTask() {
//
//			@Override
//			public void run() {
//				// TODO diviser pour plus de details, pour l'instant tout
//				// afficher
//				if (!valuesToDrawBuffer.isEmpty()) {
//					valuesToDraw.clear();
//					int size = valuesToDrawBuffer.size();
//					for (int i = 0; i < size; ++i) {						
//						valuesToDraw.add(valuesToDrawBuffer.poll());
//					}
//					((Component) presentation).repaint();
//				}
//			}
//		}, 0, 20);
	}

	@Override
	public void stop() {
		super.stop();
		if (internalThread != null) {
			try {
				internalThread.interrupt();
			} catch (Exception e) {
			}
		}
		internalThread = null;
		valuesToDrawBuffer.clear();
		valuesToDraw.clear();
		((Component) presentation).repaint();
	}

	public IPOSCModule getPresentation() {
		return presentation;
	}

	@Override
	public void cableConnected(IPort port) {
		super.cableConnected(port);
		presentation.register((IPModuleObserver) port.getCable());

	}

	@Override
	public void cableDisconnected(IPort port) {
		presentation.unregister((IPModuleObserver) port.getCable());
		super.cableDisconnected(port);
	}

	@Override
	public List<Double> getValuesToDraw() {
		return valuesToDraw;
	}

}
