package group1.project.synthlab.port.in;

import group1.project.synthlab.port.IPort;

import com.jsyn.ports.ConnectableInput;

/**
 * @author Groupe 1
 * Interface pour un port d'entree
 */
public interface IInPort extends IPort {

	/**
	 * @return le port JSyn
	 */
	public ConnectableInput getJSynPort();
		
}
