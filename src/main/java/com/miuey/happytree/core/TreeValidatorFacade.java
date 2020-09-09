package com.miuey.happytree.core;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.exception.TreeException;

class TreeValidatorFacade {
	
	private TreeManager manager;
	
	
	TreeValidatorFacade(TreeManager manager) {
		this.manager = manager;
	}

	
	void validateSessionInitialization(String identifier) throws TreeException {
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		
		pipeline.addAttribute(TreePipelineAttributes.SESSION_ID, identifier);
		
		TreeSessionValidator validator = TreeFactory.validatorFactory().
				createSessionValidator(manager);
		
		validator.validateMandatorySessionId(pipeline);
		validator.validateDuplicatedSessionId(pipeline);
	}
	
	void validateSessionInitialization(String identifier, Object typeSession)
			throws TreeException {
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		
		pipeline.addAttribute(TreePipelineAttributes.SESSION_ID, identifier);
		pipeline.addAttribute(TreePipelineAttributes.SESSION_TYPE, typeSession);
		
		TreeSessionValidator validator = TreeFactory.validatorFactory().
				createSessionValidator(manager);
		
		validator.validateMandatorySessionId(pipeline);
		validator.validateMandatoryTypeSession(pipeline);
		validator.validateDuplicatedSessionId(pipeline);
	}
	
	void validateSessionTransaction() throws TreeException {
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
	
	void validateCutOperation(Element<?> sourceElement,
			Element<?> targetElement) throws TreeException {
		final Operation operation = Operation.CUT;
		
		validateSessionTransaction();
		validateMandatory(sourceElement);
		
		TreeElementValidator validator = TreeFactory.validatorFactory().
				createCutValidator(manager);
		
		validateCutCopyOperation(sourceElement, manager.getTransaction().
				currentSession(), targetElement, operation, validator);
	}
	
	void validateCopyOperation(Element<?> sourceElement,
			Element<?> targetElement) throws TreeException {
		final Operation operation = Operation.COPY;
		
		validateSessionTransaction();
		validateMandatory(sourceElement);
		validateMandatory(targetElement);
		
		TreeElementValidator validator = TreeFactory.validatorFactory().
				createCopyValidator(manager);
		
		validateCutCopyOperation(sourceElement, manager.getTransaction().
				currentSession(), targetElement, operation, validator);
	}
	
	void validateRemoveOperation(Element<?> sourceElement) throws TreeException {
		final Operation operation = Operation.REMOVE;
		
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		
		TreeElementValidator validator = TreeFactory.validatorFactory().
				createRemoveValidator(manager);
		
		pipeline.addAttribute(TreePipelineAttributes.SOURCE_ELEMENT,
				sourceElement);
		pipeline.addAttribute(TreePipelineAttributes.OPERATION, operation);
		pipeline.addAttribute(TreePipelineAttributes.CURRENT_SESSION,
				manager.getTransaction().currentSession());
		
		validator.validateMismatchParameterizedType(pipeline);
		validator.validateSessionElement(pipeline);
		validator.validateHandleRootElement(pipeline);
		validator.validateDetachedElement(pipeline);
	}
	
	void validatePersistOperation(Element<?> sourceElement)
			throws TreeException {
		final Operation operation = Operation.PERSIST;
		
		validateSessionTransaction();
		validateMandatory(sourceElement);
		
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		
		TreePersistValidator validator = TreeFactory.validatorFactory().
				createPersistValidator(manager);
		
		pipeline.addAttribute(TreePipelineAttributes.SOURCE_ELEMENT,
				sourceElement);
		pipeline.addAttribute(TreePipelineAttributes.OPERATION, operation);
		pipeline.addAttribute(TreePipelineAttributes.CURRENT_SESSION,
				manager.getTransaction().currentSession());
		
		validator.validateMismatchParameterizedType(pipeline);
		validator.validateSessionElement(pipeline);
		validator.validateDetachedElement(pipeline);
		validator.validateDuplicatedIdElement(pipeline);
	}
	
	void validateUpdateOperation(Element<?> sourceElement) throws TreeException {
		final Operation operation = Operation.UPDATE;
		
		validateSessionTransaction();
		validateMandatory(sourceElement);
		
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		
		TreeUpdateValidator validator = TreeFactory.validatorFactory().
				createUpdateValidator(manager);
		
		pipeline.addAttribute(TreePipelineAttributes.SOURCE_ELEMENT,
				sourceElement);
		pipeline.addAttribute(TreePipelineAttributes.OPERATION, operation);
		pipeline.addAttribute(TreePipelineAttributes.CURRENT_SESSION,
				manager.getTransaction().currentSession());
		
		validator.validateMismatchParameterizedType(pipeline);
		validator.validateSessionElement(pipeline);
		validator.validateHandleRootElement(pipeline);
		validator.validateDetachedElement(pipeline);
		validator.validateDuplicatedIdElement(pipeline);
	}
	
	private void validateCutCopyOperation(Element<?> sourceElement,
			TreeSession session, Element<?> targetElement, Operation operation,
			TreeElementValidator validator) throws TreeException {
		
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		
		pipeline.addAttribute(TreePipelineAttributes.SOURCE_ELEMENT,
				sourceElement);
		pipeline.addAttribute(TreePipelineAttributes.TARGET_ELEMENT,
				targetElement);
		pipeline.addAttribute(TreePipelineAttributes.OPERATION, operation);
		pipeline.addAttribute(TreePipelineAttributes.CURRENT_SESSION, session);
		
		validator.validateMismatchParameterizedType(pipeline);
		validator.validateSessionElement(pipeline);
		validator.validateHandleRootElement(pipeline);
		validator.validateDetachedElement(pipeline);
		validator.validateDuplicatedIdElement(pipeline);
	}
}
