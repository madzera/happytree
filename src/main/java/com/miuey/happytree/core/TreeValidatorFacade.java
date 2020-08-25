package com.miuey.happytree.core;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.exception.TreeException;

class TreeValidatorFacade {
	
	private static final String SOURCE_ELEMENT = "sourceElement";
	private static final String TARGET_ELEMENT = "targetElement";
	private static final String OPERATION = "operation";
	
	private TreeManager manager;
	
	
	TreeValidatorFacade(TreeManager manager) {
		this.manager = manager;
	}

	
	void validateTransaction() throws TreeException {
		TreeSessionValidator validator = TreeFactory.validatorFactory().
				createSessionValidator(manager);
		
		validator.validateNoDefinedSession();
		validator.validateNoActiveSession();
	}
	
	void validateMandatory(Object... args) {
		TreeMandatoryValidator validator = TreeFactory.validatorFactory().
				createMandatoryValidator();
		validator.validateMandatoryInput(args);
	}
	
	void validateCutCopyOperation(Element<?> sourceElement, TreeSession session,
			Element<?> targetElement, Operation operation) throws TreeException {
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		TreeElementValidator validator = null;
		
		if (operation.equals(Operation.CUT)) {
			validator = TreeFactory.validatorFactory().
					createCutValidator(manager);
		} else {
			validator = TreeFactory.validatorFactory().
					createCopyValidator(manager);
		}
		
		pipeline.addAttribute(SOURCE_ELEMENT, sourceElement);
		pipeline.addAttribute(TARGET_ELEMENT, targetElement);
		pipeline.addAttribute(OPERATION, operation);
		pipeline.addAttribute("session", session);
		
		validator.validateMismatchParameterizedType(pipeline);
		validator.validateHandleRootElement(pipeline);
		validator.validateDetachedElement(pipeline);
		validator.validateDuplicatedIdElement(pipeline);
	}
	
	void validateRemoveOperation(Element<?> sourceElement, TreeSession session,
			Operation operation) throws TreeException {
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		
		TreeElementValidator validator = TreeFactory.validatorFactory().
				createRemoveValidator(manager);
		pipeline.addAttribute(SOURCE_ELEMENT, sourceElement);
		pipeline.addAttribute(OPERATION, operation);
		pipeline.addAttribute("session", session);
		
		validator.validateMismatchParameterizedType(pipeline);
		validator.validateHandleRootElement(pipeline);
		validator.validateDetachedElement(pipeline);
	}
	
	void validatePersistOperation(Element<?> sourceElement, TreeSession session,
			Operation operation) throws TreeException {
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		
		TreePersistValidator validator = TreeFactory.validatorFactory().
				createPersistValidator(manager);
		
		pipeline.addAttribute(SOURCE_ELEMENT, sourceElement);
		pipeline.addAttribute(OPERATION, operation);
		pipeline.addAttribute("session", session);
		
		validator.validateMismatchParameterizedType(pipeline);
		validator.validateDetachedElement(pipeline);
		validator.validateDuplicatedIdElement(pipeline);
	}
	
	void validateUpdateOperation(Element<?> sourceElement, TreeSession session,
			Operation operation) throws TreeException {
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		
		TreeUpdateValidator validator = TreeFactory.validatorFactory().
				createUpdateValidator(manager);
		pipeline.addAttribute(SOURCE_ELEMENT, sourceElement);
		pipeline.addAttribute("session", session);
		pipeline.addAttribute(OPERATION, operation);
		
		validator.validateMismatchParameterizedType(pipeline);
		validator.validateHandleRootElement(pipeline);
		validator.validateDetachedElement(pipeline);
		validator.validateDuplicatedIdElement(pipeline);
	}
}
