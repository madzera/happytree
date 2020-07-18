package com.miuey.happytree.core.atp;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.miuey.happytree.Element;
import com.miuey.happytree.core.TreePipeline;
import com.miuey.happytree.exception.TreeException;

public class PostValidation<T> extends ATPGenericPhase<T> {

	@SuppressWarnings("unchecked")
	@Override
	protected void run(TreePipeline pipeline) throws TreeException {
		Set<Element<T>> allElements = (Set<Element<T>>) pipeline.
				getAttribute("elements");
		Map<Object, Object> mapObjects = (Map<Object, Object>) 
				pipeline.getAttribute("mapObjects");
		Map<Object, Object> mapParents = (Map<Object, Object>) 
				pipeline.getAttribute("mapParents");
		Collection<Object> objects = mapObjects.values();
		
		if (allElements.size() != objects.size()) {
			throw this.throwTreeException(ATPRepositoryMessage.
					POST_VALID_INCONS);
		}
		
		Object id = null;
		Object parentId = null;
		T wrappedObject = null;
		for (Element<T> element : allElements) {
			id = element.getId();
			parentId = element.getParent();
			wrappedObject = element.unwrap();
			
			Object parentIdSource = mapParents.get(id);
			Object object = mapObjects.get(id);
			
			if (!object.equals(wrappedObject) || 
					parentIdSource.equals(parentId)) {
				throw this.throwTreeException(ATPRepositoryMessage.
						POST_VALID_INCONS);
			}
		}
		doChain(pipeline);
	}
}
