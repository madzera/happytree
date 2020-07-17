package com.miuey.happytree.core;

import com.miuey.happytree.core.TreeFactory.ATPLifecycleFactory;
import com.miuey.happytree.exception.TreeException;

class ATPLifecycle<T> {

	private TreePipeline pipeline;
	
	
	ATPLifecycle(TreePipeline pipeline) {
		this.pipeline = pipeline;
	}
	
	
	void run() throws TreeException {
		ATPLifecycleFactory lifecycleFactory= TreeFactory.lifecycleFactory();
		
		ATPPhase<T> preValidation = lifecycleFactory.initPreValidation();
		ATPPhase<T> extraction = lifecycleFactory.initExtraction();
		ATPPhase<T> initialization = lifecycleFactory.initInitialization();
		ATPPhase<T> binding = lifecycleFactory.initBinding();
		
		preValidation.next(extraction);
		extraction.next(initialization);
		initialization.next(binding);
		
		preValidation.run(pipeline);
	}
}
