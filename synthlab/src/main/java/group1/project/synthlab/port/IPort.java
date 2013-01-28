package group1.project.synthlab.port;

import group1.project.synthlab.exceptions.BadConnection;

import com.jsyn.ports.UnitPort;

public interface IPort {
	public String getLabel();
	public boolean isUsed();
}
