package com.miuey.happytree.core;

import com.miuey.happytree.exception.TreeException;

public abstract class TreeServiceValidator {
	
	private TreeServiceValidator handler;
	
	
	protected TreeServiceValidator() {}
	
	
	protected void next(TreeServiceValidator handler) {
		this.handler = handler;
	}
	
	protected void doChain(TreePipeline pipeline) throws TreeException {
		if (handler != null) {
			handler.validate(pipeline);
		}
	}

	protected abstract void validate(TreePipeline pipeline) throws TreeException;
}
