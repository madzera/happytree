package com.madzera.happytree.core.atp;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import com.madzera.happytree.annotation.Id;
import com.madzera.happytree.annotation.Parent;
import com.madzera.happytree.annotation.Tree;
import com.madzera.happytree.core.TreePipeline;
import com.madzera.happytree.exception.TreeException;

class PreValidation<T> extends ATPGenericPhase<T> {

	protected PreValidation() {}
	
	
	/*
	 * Checks that the node has a non-null or empty collection of the objects
	 * that will be transformed. It checks too that the class of those objects
	 * have annotations and getters and setters.
	 */
	@Override
	protected void run(TreePipeline pipeline) throws TreeException {
		@SuppressWarnings("unchecked")
		Collection<T> nodes = (Collection<T>) pipeline.getAttribute(
				ATPPipelineAttributes.NODES);
		
		validateMandatorySource(nodes);
		validateAnnotations(nodes);
		
		try {
			validateIdentifiers(nodes);
		} catch (ReflectiveOperationException exception) {
			throw this.throwTreeException(ATPRepositoryMessage.GENERAL);
		}
		
		doChain(pipeline);
	}

	private void validateMandatorySource(Collection<T> nodes) {
		if (nodes == null || nodes.isEmpty()) {
			throw this.throwIllegalArgumentException(ATPRepositoryMessage.
					INVALID_INPUT);
		}
	}

	private void validateAnnotations(Collection<T> nodes) throws TreeException {
		
		Object[] objectArray = nodes.toArray(new Object[0]);
		Object object = objectArray[0];
		
		Class<?> treeClass = object.getClass();
		Tree treeAnnotation = treeClass.getAnnotation(Tree.class);
		
		Field fieldIdAnnotation = ATPReflectionUtil.getFieldAnnotation(
				object, Id.class);
		Field fieldParentAnnotation = ATPReflectionUtil.getFieldAnnotation(
				object, Parent.class);
		
		validateTreeAnnotation(treeAnnotation);
		validateFieldAnnotation(fieldIdAnnotation, ATPRepositoryMessage.NO_ID);
		validateFieldAnnotation(fieldParentAnnotation, ATPRepositoryMessage.
				NO_PARENT);
		
		if (!fieldIdAnnotation.getType().equals(fieldParentAnnotation.
				getType())) {
			throw this.throwTreeException(ATPRepositoryMessage.MISMATCH_TYPE_ID);
		}
	}
	
	private void validateIdentifiers(Collection<T> nodes) 
			throws ReflectiveOperationException, TreeException {
		Set<Object> validIds = this.createHashSet();
		Iterator<T> iterator = nodes.iterator();
		
		while (iterator.hasNext()) {
			Object object = iterator.next();
			
			Field fieldIdAnnotation = ATPReflectionUtil.getFieldAnnotation(
					object, Id.class);
			
			Object objId = ATPReflectionUtil.invokeGetter(fieldIdAnnotation.
					getName(), object);
			
			if (objId == null) {
				throw this.throwIllegalArgumentException(ATPRepositoryMessage.
						INVALID_INPUT);
			}
			
			if (validIds.contains(objId)) {
				throw this.throwTreeException(ATPRepositoryMessage.DUPLICATE_ID);
			}
			
			validIds.add(objId);
		}
	}
	
	private void validateTreeAnnotation(Tree tree) throws TreeException {
		if (tree == null) {
			throw this.throwTreeException(ATPRepositoryMessage.NO_TREE);
		}
	}
	
	private void validateFieldAnnotation(Field field,
			ATPRepositoryMessage message) throws TreeException {
		if (field == null) {
			throw this.throwTreeException(message);
		}
	}
}
