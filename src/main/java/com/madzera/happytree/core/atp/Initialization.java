package com.madzera.happytree.core.atp;

import java.util.Map;
import java.util.Map.Entry;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeTransaction;
import com.madzera.happytree.core.TreePipeline;
import com.madzera.happytree.exception.TreeException;

import java.util.Set;

class Initialization<T> extends ATPGenericPhase<T> {

	protected Initialization() {}
	

	/*
	 * Instantiate each element with the id and their respective parents
	 * informed in the previous phase.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void run(TreePipeline pipeline) throws TreeException {
		Map<Object, T> nodesMap = (Map<Object, T>) 
				pipeline.getAttribute(ATPPipelineAttributes.NODES_MAP);
		Map<Object, Object> nodesParentMap = (Map<Object, Object>) 
				pipeline.getAttribute(ATPPipelineAttributes.NODES_PARENT_MAP);
		
		TreeManager manager = (TreeManager) pipeline.getAttribute(
				ATPPipelineAttributes.MANAGER);
		String sessionId = (String) pipeline.getAttribute(
				ATPPipelineAttributes.SESSION_ID);
		
		Object[] objectsArray = nodesMap.values().toArray(new Object[0]);
		Class<?> clazz = objectsArray[0].getClass();
		
		TreeTransaction transaction = manager.getTransaction();
		transaction.initializeSession(sessionId, clazz);
		
		Set<Element<?>> elements = this.createHashSet();
		Set<Entry<Object, T>> entrySet = nodesMap.entrySet();
		
		for (Entry<Object, T> entry : entrySet) {
			Object id = entry.getKey();
			Object parentId = nodesParentMap.get(id);
			T object = entry.getValue();
			
			Element<T> element = manager.createElement(id, parentId, object);
			elements.add(element);
		}
		
		pipeline.addAttribute(ATPPipelineAttributes.NODE_TYPE, clazz);
		pipeline.addAttribute(ATPPipelineAttributes.ELEMENTS, elements);
		
		doChain(pipeline);
	}
}
