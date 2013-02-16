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

/**
 * @author Groupe 1
 * Controleur pour le module OSC
 */
public class COSCModule extends OSCModule implements ICOSCModule {

	private static final long serialVersionUID = 3332918051064544540L;
	
	/**
	 * la presentation
	 */
	protected IPOSCModule presentation;
		
	/**
	 * Le buffer correspondant aux valeurs a afficher
	 */
	protected List<Double> valuesToDraw;
	
	/**
	 * Derniere date a laquelle les donnees ont ete recu de l'abstraction
	 */
	protected double previousLastTime;
	
	/**
	 * Interval minimum de mise a jour pour la presentation
	 */
	protected double intervalS;
	
	/**
	 * Le synthetiseur
	 */
	protected Synthesizer synth = Workspace.getInstance().getSynthetizer();
		
	/**
	 * Est ce que je peux repaindre la presentation, si non, je me base sur l'ancien buffer
	 */
	protected boolean canRepaint;
		
	/**
	 * Compteur avant rafraichissement de la presentation
	 */
	protected int cmptPaint = 0;
		
	/**
	 * Buffer clone du buffer valuesToDraw, sert si les valeurs ne sont pas pretes a afficher, contient les anciennes valeurs
	 */
	protected List<Double> valuesToDrawBuffered;
	
	
	public COSCModule(CFactory factory) {
		super(factory);
		//Intialise les valeurs		
		this.intervalS = 0.05;
		this.presentation = new POSCModule(this);
		self = this;
		valuesToDraw = new CopyOnWriteArrayList<>();
		valuesToDrawBuffered = new CopyOnWriteArrayList<>();
		((Component) presentation).repaint();
		canRepaint = false;
	}


	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.osc.OSCModule#interceptionResult(java.util.List, double)
	 */
	@Override
	public void interceptionResult(List<Double> buffer, double time)
			throws BufferTooBig {
		//Abstraction
		super.interceptionResult(buffer, time);
		
		//Defini le macimum d'iteration avant de repeindre la presentation
		int maxCmptPaint = (int) (0.2 / intervalS); // nombre d'iterationsavant de parvenir à 20ms pour un affiche fluide (pas trop rapide pr l'ecran)
		
		//Si la quantite de donnees est recu dans l'interval defini, remplir le buffer
		if (this.buffer.size() >= synth.getFrameRate() * intervalS) { //augmenter ce nombre pour zoomer (moins de bande à afficher d'un coup)
			canRepaint = false;
			valuesToDraw.clear();
			int cmpt = 0 ;
			int max = (int) (synth.getFrameRate() * intervalS);
			//Rempli le buffer pile poil a la quantite de donnees desiree
			while (cmpt < max) {
				++cmpt;
				valuesToDraw.add(this.buffer.poll());
			}
			//Si le compteur d'iteration est depasse, dessiner
			if (++cmptPaint>= maxCmptPaint) {				
				presentation.refresh();
				cmptPaint = 0;
			}
			//Ca y est on est autoriser a peindre (ne pas oublier que la peinture Swing se passe dans un thread a part)
			canRepaint = true;
			////On rempacer l'ancien buffer par le nouveau
			valuesToDrawBuffered.clear();
			valuesToDrawBuffered.addAll(valuesToDraw);
		}
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.osc.ICOSCModule#getInterval()
	 */
	public double getInterval() {
		return  intervalS * 1000.0;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.osc.ICOSCModule#setInterval(double)
	 */
	public void setInterval(double interval) {
		this.intervalS =  interval /1000.0;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.Module#start()
	 */
	@Override
	public void start() {
		super.start();	
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.osc.OSCModule#stop()
	 */
	@Override
	public void stop() {
		super.stop();
		valuesToDraw.clear();
		((Component) presentation).repaint();
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.ICModule#getPresentation()
	 */
	public IPOSCModule getPresentation() {
		return presentation;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.osc.OSCModule#cableConnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableConnected(IPort port) {
		super.cableConnected(port);
		presentation.register((IPModuleObserver) port.getCable());

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.osc.OSCModule#cableDisconnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableDisconnected(IPort port) {
		presentation.unregister((IPModuleObserver) port.getCable());
		super.cableDisconnected(port);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.osc.ICOSCModule#getValuesToDraw()
	 */
	@Override
	public List<Double> getValuesToDraw() {
		if (canRepaint) 
			return valuesToDraw;
		else
			return valuesToDrawBuffered;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.osc.OSCModule#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		presentation.updatePresentation();
	}


}
