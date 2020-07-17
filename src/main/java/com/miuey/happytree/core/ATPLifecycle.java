package com.miuey.happytree.core;

import com.miuey.happytree.core.TreeFactory.ATPLifecycleFactory;
import com.miuey.happytree.exception.TreeException;

class ATPLifecycle {

	private TreePipeline pipeline;
	
	
	ATPLifecycle(TreePipeline pipeline) {
		this.pipeline = pipeline;
	}
	
	
	void run() throws TreeException {
		ATPLifecycleFactory lifecycleFactory= TreeFactory.lifecycleFactory();
		
		ATPPhase preValidation = lifecycleFactory.initPreValidation();
		ATPPhase extraction = lifecycleFactory.initExtraction();
		
		preValidation.next(extraction);
		
		preValidation.run(pipeline);
	}
}
