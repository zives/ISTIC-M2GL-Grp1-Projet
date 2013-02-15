package group1.project.synthlab.port;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.module.IModuleObserver;

import java.io.Serializable;

import com.jsyn.unitgen.UnitGenerator;

/**
 * @author Groupe 1
 * Defini les methodes communes aux ports de sorties et d'entres
 */
public interface IPort extends IPortObservable, Serializable, IModuleObserver {
	/**
	 * @return le nom du port
	 */
	public String getLabel();
	/**
	 * @return obtient, s'il existe le cable connecte au port
	 */
	public ICable getCable();
	/**
	 * @param cable branche un cable au port
	 */
	public void setCable(ICable cable);
	/**
	 * @return si le port est connecte via un cable
	 */
	public boolean isUsed();
	/**
	 * @return le module rattache
	 */
	public IModule getModule();
	
	/**
	 * @param amplitude le maximum autorise
	 */
	public void setMaxForAmplitudeSupervisor(double amplitude);
	

	/**
	 * @return si le signal est sature au niveau du port
	 */
	public boolean detectSignalSaturated();

	/**
	 * @return si un signal circule
	 */
	public boolean detectSignal();
	
	/**
	 * @return le module interne supervisant la circulation du signal
	 */
	public UnitGenerator getSupervisor();
}
