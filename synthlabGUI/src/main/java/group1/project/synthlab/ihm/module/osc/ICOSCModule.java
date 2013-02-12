package group1.project.synthlab.ihm.module.osc;

import java.util.List;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.osc.IOSCModule;

public interface ICOSCModule extends ICModule, IOSCModule {

	public List<Double> getValuesToDraw();

	public void setInterval(double interval);
	public double getInterval() ;
}
