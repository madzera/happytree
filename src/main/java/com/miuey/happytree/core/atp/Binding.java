package com.miuey.happytree.core.atp;

import java.util.Set;

import com.miuey.happytree.Element;
import com.miuey.happytree.core.TreePipeline;
import com.miuey.happytree.exception.TreeException;

public class Binding<T> extends ATPGenericPhase<T> {

	@Override
	protected void run(TreePipeline pipeline) throws TreeException {
		@SuppressWarnings("unchecked")
		Set<Element<T>> allElements = (Set<Element<T>>) pipeline.
				getAttribute("elements");
		
		Set<Element<T>> clonedElements = this.createHashSet();
		Set<Element<T>> tree = this.createHashSet();
		
		clonedElements.addAll(allElements);
		for (Element<T> element : allElements) {
			Object parentId = element.getParent();
			
			Element<T> parentElement = getParentElement(parentId,
					clonedElements);
			if (parentElement != null) {
				parentElement.addChild(element);
			} else {
				tree.add(element);
			}
		}
		pipeline.addAttribute("tree", tree);
		doChain(pipeline);
	}

	private Element<T> getParentElement(Object parentId,
			Set<Element<T>> clonedElements) {
		Object id = null;
		for (Element<T> element : clonedElements) {
			id = element.getId();
			if (parentId != null && parentId.equals(id)) {
				return element;
			}
		}
		return null;
	}
}
