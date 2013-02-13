package group1.project.synthlab.ihm.module.osc;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.osc.IOSCModule;

import java.util.List;

public interface ICOSCModule extends ICModule, IOSCModule {

	public List<Double> getValuesToDrawAndEraseIts();

	public void setInterval(double interval);
	public double getInterval() ;
}
