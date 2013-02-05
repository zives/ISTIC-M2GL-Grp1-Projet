package group1.project.synthlab.port.out;

import group1.project.synthlab.port.IPort;

import com.jsyn.ports.ConnectableOutput;

/**
 * @author Groupe 1
 * Interface pour un port de sortie
 */
public interface IOutPort extends IPort {

	/**
	 * @return le port JSyn
	 */
	public ConnectableOutput getJSynPort();
}
