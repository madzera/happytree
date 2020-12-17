package com.madzera.happytree.core;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeSession;
import com.madzera.happytree.exception.TreeException;

/*
 * Main class of the API HappyTree to validate all interface operations.
 */
class TreeValidatorFacade {
	
	private TreeManager manager;
	
	
	TreeValidatorFacade(TreeManager manager) {
		this.manager = manager;
	}

	
	/*
	 * Validates mandatory inputs.
	 */
	void validateMandatory(Object... args) {
		TreeMandatoryValidator validator = TreeFactory.validatorFactory().
				createMandatoryValidator();
		validator.validateMandatoryInput(args);
	}
	
	/*
	 * Validates TreeTransaction.initializeSession().
	 */
	void validateSessionInitialization(String identifier) throws TreeException {
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		
		pipeline.addAttribute(TreePipelineAttributes.SESSION_ID, identifier);
		
		TreeSessionValidator validator = TreeFactory.validatorFactory().
				createSessionValidator(manager);
		
		validator.validateMandatorySessionId(pipeline);
		validator.validateDuplicatedSessionId(pipeline);
	}
	
	/*
	 * Validates TreeTransaction.initializeSession().
	 */
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
	
	/*
	 * Validates the session invoking any TreeManager operations.
	 */
	void validateSessionTransaction() throws TreeException {
		TreeSessionValidator validator = TreeFactory.validatorFactory().
				createSessionValidator(manager);
		
		validator.validateNoDefinedSession();
		validator.validateNoActiveSession();
	}
	
	/*
	 * Validates TreeManager.cut().
	 */
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
	
	/*
	 * Validates TreeManager.copy().
	 */
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
	
	/*
	 * Validates TreeManager.removeElement().
	 */
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
	
	/*
	 * Validates TreeManager.persistElement().
	 */
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
	
	/*
	 * Validates TreeManager.updateElement().
	 */
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
