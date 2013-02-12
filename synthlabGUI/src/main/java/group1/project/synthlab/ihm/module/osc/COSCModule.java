package group1.project.synthlab.ihm.module.osc;

import group1.project.synthlab.exceptions.BufferTooBig;
import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.osc.OSCModule;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.workspace.Workspace;

import java.awt.Component;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.jsyn.Synthesizer;

public class COSCModule extends OSCModule implements ICOSCModule {

	protected IPOSCModule presentation;
	protected List<Double> valuesToDraw;
	protected double previousLastTime;
	protected double intervalS;
	protected Synthesizer synth = Workspace.getInstance().getSynthetizer();

	public COSCModule(CFactory factory) {
		super(factory);
		this.intervalS = 0.05;
		this.presentation = new POSCModule(this);
		self = this;
		valuesToDraw = new CopyOnWriteArrayList<>();
		((Component) presentation).repaint();

	}

protected int cmptPaint = 0;
	@Override
	public void interceptionResult(List<Double> buffer, double time)
			throws BufferTooBig {
		super.interceptionResult(buffer, time);
		
		int maxCmptPaint = (int) (0.1 / intervalS); // nombre d'iterationsavant de parvenir à 20ms pour un affiche fluide (pas trop rapide pr l'ecran)
		if (this.buffer.size() >= synth.getFrameRate() * intervalS) { //augmenter ce nombre pour zoomer (moins de bande à afficher d'un coup)
			
			valuesToDraw.clear();
			int cmpt = 0 ;
			int max = (int) (synth.getFrameRate() * intervalS);
			while (cmpt < max) {
				++cmpt;
				valuesToDraw.add(this.buffer.poll());
			}
			if (++cmptPaint>= maxCmptPaint) {
			((Component) presentation).repaint();
				cmptPaint = 0;
			}
		}
	}

	public double getInterval() {
		return  intervalS * 1000.0;
	}

	public void setInterval(double interval) {
		this.intervalS =  interval /1000.0;
	}

	@Override
	public void start() {
		super.start();
	
	}

	@Override
	public void stop() {
		super.stop();
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
