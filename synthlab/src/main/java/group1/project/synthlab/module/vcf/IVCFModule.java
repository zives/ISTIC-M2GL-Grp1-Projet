package group1.project.synthlab.module.vcf;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.unitExtensions.filterModulation.FilterFrequencyModulation;
import group1.project.synthlab.unitExtensions.filterSupervisor.FilterAmplitude;

import com.jsyn.unitgen.FilterLowPass;

public interface IVCFModule extends IModule {

	/**
	 * Met a jour la frequence de coupure f0 au niveau des filtres
	 */
    public void changeFrequency();
    
	/**
	 * Met a  jour les valeurs d'ajustements en fonction de la frequence de coupure
	 */
	public void redefAdjustments();
	
	/**
	 * Met a jour le facteur de qualite q au niveau des filtres
	 */
    public void changeQFactor();
    
    /**
	 * @return le filtre pour le calcul de la modulation de frequence
	 */
    public FilterFrequencyModulation getFilterFrequencyModulation();
    
    /**
	 * @return le filtre pour la detection d'amplitudes trop elevees
	 */
    public FilterAmplitude getFilterAmplitude();
    
    /**
	 * @return le premier filtre passe-bas
	 */
    public FilterLowPass getFilter1();
    
    /**
	 * @return le second filtre passe-bas
	 */
    public FilterLowPass getFilter2();
    
	/**
	 * @return la valeur actuelle de la frequence de coupure
	 */
    public double getf0();
    
	/**
	 * Change la valeur de la frequence de coupure
	 */
    public void setf0(double f0);
    
    /**
	 * @return la valeur actuelle du facteur de qualite
	 */
    public double getq();
    
    /**
	 * Change la valeur du facteur de qualite
	 */
    public void setq(double q);
    
	/**
	 * @return le port d'entree In
	 */
    public IInPort getIn();
    
	/**
	 * @return le port d'entree Fm
	 */
    public IInPort getFm();
    
	/**
	 * @return le port de sortie Out
	 */
    public IOutPort getOut();
    
	/**
	 * Si un cable est connecte dans le port d'entree fm, le VCF doit activer la modulation de la frequence de coupure
	 * @param port : le port du VCF auquel on vient de connecter un cable
	 */
    public void cableConnected(IPort port);
    
	/**
	 * Si un cable est deconnecte du port d'entree fm, le VCF doit desactiver la modulation de la frequence de coupure
	 * @param port : le port du VCF duquel on vient de deconnecter un cable
	 */
    public void cableDisconnected(IPort port);
    
	/**
	 * Reglage grossier
	 * @return la valeur du reglage grossier de la frequence de coupure f0
	 */
	public int getCoarseAdjustment();
	
	/**
	 * @param coarseadjustment : la nouvelle valeur du reglage grossier de la frequence de coupure f0
	 */
	public void setCoarseAdjustment(int coarseadjustment);
	
	/**
	 * Reglage fin
	 * @return la valeur du reglage fin de la frequence de coupure f0
	 */
	public double getFineAdjustment();
	
	/**
	 * @param fineAdjustment : la nouvelle valeur du reglage fin de la frequence de coupure f0
	 */
	public void setFineAdjustment(double fineadjustment);
}
