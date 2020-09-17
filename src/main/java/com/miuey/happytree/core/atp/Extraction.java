package com.miuey.happytree.core.atp;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import com.miuey.happytree.annotation.Id;
import com.miuey.happytree.annotation.Parent;
import com.miuey.happytree.core.TreePipeline;
import com.miuey.happytree.exception.TreeException;

class Extraction<T> extends ATPGenericPhase<T> {

	protected Extraction() {}
	

	/*
	 * Separates the own nodes and their parents.
	 */
	@Override
	protected void run(TreePipeline pipeline) throws TreeException {
		Map<Object, Object> nodesMap = this.createHashMap();
		Map<Object, Object> nodesParentMap = this.createHashMap();
		
		Collection<?> nodes = (Collection<?>) pipeline.getAttribute(
				ATPPipelineAttributes.NODES);
		
		for (Object node : nodes) {
			Field fieldIdAnnotation = ATPReflectionUtil.getFieldAnnotation(
					node, Id.class);
			Field fieldParentAnnotation = ATPReflectionUtil.getFieldAnnotation(
					node, Parent.class);
			
			try {
				Object objId = ATPReflectionUtil.invokeGetter(
						fieldIdAnnotation.getName(), node);
				Object objParent = ATPReflectionUtil.invokeGetter(
						fieldParentAnnotation.getName(), node);
				
				nodesMap.put(objId, node);
				nodesParentMap.put(objId, objParent);
			} catch (ReflectiveOperationException e) {
				throw this.throwTreeException(ATPRepositoryMessage.GENERAL);
			}
		}
		
		pipeline.addAttribute(ATPPipelineAttributes.NODES_MAP, nodesMap);
		pipeline.addAttribute(ATPPipelineAttributes.NODES_PARENT_MAP,
				nodesParentMap);
		
		doChain(pipeline);
	}
}
