package com.madzera.happytree.core.atp;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.madzera.happytree.Element;
import com.madzera.happytree.core.TreePipeline;
import com.madzera.happytree.exception.TreeException;

class PostValidation<T> extends ATPGenericPhase<T> {

	protected PostValidation() {}
	
	
	/*
	 * Verify if the resulting element list corresponds to the input list used
	 * in this transformation
	 */
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
				POST_VALID_INCONSISTENCY);
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
			
			/*
			 * The same wrapped node inside of this element must be equals to
			 * the node used as input of this transformation.
			 */
			if (!object.equals(wrappedNode) || (parentIdSource != null &&
					!parentIdSource.equals(parentId))) {
				throw this.throwTreeException(ATPRepositoryMessage.
					POST_VALID_INCONSISTENCY);
			}
		}
		doChain(pipeline);
	}
}
