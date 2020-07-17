package com.miuey.happytree.core.atp;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.TreePipeline;
import com.miuey.happytree.exception.TreeException;

public class Initialization<T> extends ATPGenericPhase<T> {

	@SuppressWarnings("unchecked")
	@Override
	protected void run(TreePipeline pipeline) throws TreeException {
		Map<Object, T> mapObjects = (Map<Object, T>) 
				pipeline.getAttribute("mapObjects");
		Map<Object, Object> mapParents = (Map<Object, Object>) 
				pipeline.getAttribute("mapParents");
		
		TreeManager manager = (TreeManager) pipeline.getAttribute("manager");
		String sessionId = (String) pipeline.getAttribute("sessionId");
		
		Object[] objectsArray = mapObjects.values().toArray(new Object[0]);
		Class<?> clazz = objectsArray[0].getClass();
		
		TreeTransaction transaction = manager.getTransaction();
		transaction.initializeSession(sessionId, clazz);
		
		Set<Element<?>> elements = new HashSet<Element<?>>();
		Set<Entry<Object, T>> entrySet = mapObjects.entrySet();
		
		for (Entry<Object, T> entry : entrySet) {
			Object id = entry.getKey();
			Object parentId = mapParents.get(id);
			T object = entry.getValue();
			
			Element<T> element = manager.createElement(id, parentId);
			element.wrap(object);
			elements.add(element);
		}
		
		pipeline.addAttribute("type", clazz);
		pipeline.addAttribute("elements", elements);
		doChain(pipeline);
	}
}
