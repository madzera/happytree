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
				getAttribute(ATPPipelineAttributes.ELEMENTS);
		
		Map<Object, Object> nodesMap = (Map<Object, Object>) 
				pipeline.getAttribute(ATPPipelineAttributes.NODES_MAP);
		Map<Object, Object> nodesParentMap = (Map<Object, Object>) 
				pipeline.getAttribute(ATPPipelineAttributes.NODES_PARENT_MAP);
		
		Collection<Object> nodes = nodesMap.values();
		
		if (allElements.size() != nodes.size()) {
			throw this.throwTreeException(ATPRepositoryMessage.
					POST_VALID_INCONS);
		}
		
		Object id = null;
		Object parentId = null;
		T wrappedNode = null;
		
		for (Element<T> element : allElements) {
			id = element.getId();
			parentId = element.getParent();
			wrappedNode = element.unwrap();
			
			Object parentIdSource = nodesParentMap.get(id);
			Object object = nodesMap.get(id);
			
			if (!object.equals(wrappedNode) || 
					parentIdSource.equals(parentId)) {
				throw this.throwTreeException(ATPRepositoryMessage.
						POST_VALID_INCONS);
			}
		}
		
		doChain(pipeline);
	}
}
