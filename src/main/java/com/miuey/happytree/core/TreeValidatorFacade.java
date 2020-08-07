package com.miuey.happytree.core;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.exception.TreeException;

class TreeValidatorFacade {
	
	private static final String SOURCE_ELEMENT = "sourceElement";
	private static final String TARGET_ELEMENT = "targetElement";
	
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
	
	void validateCutCopyOperation(Element<?> sourceElement,
			Element<?> targetElement, boolean toCopy) throws TreeException {
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		TreeElementValidator validator = null;
		if (toCopy) {
			validator = TreeFactory.validatorFactory().
					createCopyValidator(manager);
		} else {
			validator = TreeFactory.validatorFactory().
					createCutValidator(manager);
		}
		pipeline.addAttribute(SOURCE_ELEMENT, sourceElement);
		pipeline.addAttribute(TARGET_ELEMENT, targetElement);
		
		validator.validateMandatoryElementId(pipeline);
		validator.validateHandleRootElement(pipeline);
		validator.validateDetachedElement(pipeline);
		validator.validateDuplicatedElement(pipeline);
	}
	
	void validateRemoveOperation(Element<?> sourceElement)
			throws TreeException {
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		
		TreeElementValidator validator = TreeFactory.validatorFactory().
				createRemoveValidator(manager);
		pipeline.addAttribute(SOURCE_ELEMENT, sourceElement);
		
		validator.validateHandleRootElement(pipeline);
		validator.validateDetachedElement(pipeline);
	}
	
	void validatePersistOperation(Element<?> sourceElement)
			throws TreeException {
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		
		TreePersistValidator validator = TreeFactory.validatorFactory().
				createPersistValidator(manager);
		pipeline.addAttribute(SOURCE_ELEMENT, sourceElement);
		
		validator.validateMandatoryElementId(pipeline);
		validator.validateTypeOfElement(pipeline);
		validator.validateDetachedElement(pipeline);
		validator.validateDuplicatedElement(pipeline);
	}
	
	void validateUpdateOperation(Element<?> sourceElement)
			throws TreeException {
		TreePipeline pipeline = TreeFactory.pipelineFactory().
				createPipelineValidator();
		
		TreeUpdateValidator validator = TreeFactory.validatorFactory().
				createUpdateValidator(manager);
		pipeline.addAttribute(SOURCE_ELEMENT, sourceElement);
		
		validator.validateMandatoryElementId(pipeline);
		validator.validateHandleRootElement(pipeline);
		validator.validateTypeOfElement(pipeline);
		validator.validateDetachedElement(pipeline);
		validator.validateDuplicatedElement(pipeline);
	}
}
