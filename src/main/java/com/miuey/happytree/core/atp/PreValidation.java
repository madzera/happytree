package com.miuey.happytree.core.atp;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.miuey.happytree.annotation.Id;
import com.miuey.happytree.annotation.Parent;
import com.miuey.happytree.annotation.Tree;
import com.miuey.happytree.core.TreePipeline;
import com.miuey.happytree.exception.TreeException;

public class PreValidation extends ATPGenericPhase {

	@Override
	protected void run(TreePipeline pipeline) throws TreeException {
		Collection<?> objects = (Collection<?>) pipeline.getAttribute("objects");
		validateMandatorySource(objects);
		validateAnnotations(objects);
		try {
			validateIdentifiers(objects);
		} catch (ReflectiveOperationException exception) {
			throw this.throwTreeException(ATPRepositoryMessage.GENERAL);
		}
		doChain(pipeline);
	}

	private void validateMandatorySource(Collection<?> objects) {
		if (objects == null || objects.isEmpty()) {
			throw this.throwIllegalArgumentException(ATPRepositoryMessage.
					INVALID_INPUT);
		}
	}

	private void validateAnnotations(Collection<?> objects) throws TreeException {
		Object[] objectArray = objects.toArray(new Object[1]);
		Object object = objectArray[0];
		
		Class<?> treeClass = object.getClass();
		Tree treeAnnotation = treeClass.getAnnotation(Tree.class);
		
		Field fieldIdAnnotation = ATPReflectionUtil.getFieldAnnotation(
				object, Id.class);
		Field fieldParentAnnotation = ATPReflectionUtil.getFieldAnnotation(
				object, Parent.class);
		
		validateTreeAnnotation(treeAnnotation);
		validateFieldAnnotation(fieldIdAnnotation, ATPRepositoryMessage.NO_ID);
		validateFieldAnnotation(fieldParentAnnotation,
				ATPRepositoryMessage.NO_PARENT);
		
		if (!fieldIdAnnotation.getType().equals(fieldParentAnnotation.
				getType())) {
			throw this.throwTreeException(ATPRepositoryMessage.MISMATCH_TYPE_ID);
		}
	}
	
	private void validateIdentifiers(Collection<?> objects) 
			throws ReflectiveOperationException, TreeException {
		Set<Object> validIds = new HashSet<Object>(); 
		Iterator<?> iterator = objects.iterator();
		
		while (iterator.hasNext()) {
			Object object = iterator.next();
			Field fieldIdAnnotation = ATPReflectionUtil.getFieldAnnotation(
					object, Id.class);
			
			Object objId = ATPReflectionUtil.invokeGetter(
					fieldIdAnnotation.getName(), object);
			if (objId == null) {
				throw this.throwIllegalArgumentException(ATPRepositoryMessage.
						INVALID_INPUT);
			}
			if (validIds.contains(objId)) {
				throw this.throwTreeException(ATPRepositoryMessage.
						DUPLICATED_ID);
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
