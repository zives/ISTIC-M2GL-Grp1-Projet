package group1.project.synthlab.port;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.module.IModule;

import java.io.Serializable;

import com.jsyn.unitgen.UnitGenerator;

/**
 * @author Groupe 1
 * Defini les methodes communes aux ports de sorties et d'entres
 */
public interface IPort extends IPortObservable, Serializable {
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
	 * @return le module rattaché
	 */
	public IModule getModule();
	
	/**
	 * @param amplitude le maximum autorisé
	 */
	public void setMaxForAmplitudeSupervisor(double amplitude);
	

	public boolean detectSignalSaturated();

	public boolean detectSignal();
	
	public UnitGenerator getSupervisor();
}
