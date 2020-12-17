package com.madzera.happytree.core;

import com.madzera.happytree.exception.TreeException;

public abstract class ATPPhase<T> {
	
	private ATPPhase<T> phase;
	
	
	protected ATPPhase() {}
	
	
	protected void next(ATPPhase<T> phase) {
		this.phase = phase;
	}
	
	protected void doChain(TreePipeline pipeline) throws TreeException {
		if (phase != null) {
			phase.run(pipeline);
		}
	}

	protected abstract void run(TreePipeline pipeline) throws TreeException;
}
