package group1.project.synthlab.ihm.module.multiplexer;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.multiplexer.IMultiplexerModule;

public interface ICMultiplexerModule extends ICModule, IMultiplexerModule {
	public IPMultiplexerModule getPresentation();
}
