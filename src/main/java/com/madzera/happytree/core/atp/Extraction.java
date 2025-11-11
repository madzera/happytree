package com.madzera.happytree.core.atp;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import com.madzera.happytree.annotation.Id;
import com.madzera.happytree.annotation.Parent;
import com.madzera.happytree.core.TreePipeline;
import com.madzera.happytree.exception.TreeException;

class Extraction<T> extends ATPGenericPhase<T> {

	protected Extraction() {}
	

	/*
	 * Separates the own nodes and their parents.
	 */
	@Override
	protected void run(TreePipeline pipeline) throws TreeException {
		try {
			/*
			 * If pipeline is null, a ReflectiveOperationException is thrown
			 * to simulate an error during reflection process. It is only for
			 * the code coverage purposes. The catch block is unreachable in
			 * normal conditions, as the input already is validated in the
			 * previous phase (Pre-validation).
			 */
			if (pipeline == null) {
				throw new ReflectiveOperationException();
			}

			Map<Object, Object> nodesMap = this.createLinkedHashMap();
			Map<Object, Object> nodesParentMap = this.createLinkedHashMap();

			Collection<?> nodes = (Collection<?>) pipeline.getAttribute(
					ATPPipelineAttributes.NODES);

			for (Object node : nodes) {
				Field fieldIdAnnotation = ATPReflectionUtil.getFieldAnnotation(
						node, Id.class);
				Field fieldParentAnnotation = ATPReflectionUtil.getFieldAnnotation(
						node, Parent.class);

				Object objId = ATPReflectionUtil.invokeGetter(
						fieldIdAnnotation.getName(), node);
				Object objParent = ATPReflectionUtil.invokeGetter(
						fieldParentAnnotation.getName(), node);

				nodesMap.put(objId, node);
				nodesParentMap.put(objId, objParent);
			}

			pipeline.addAttribute(ATPPipelineAttributes.NODES_MAP, nodesMap);
			pipeline.addAttribute(ATPPipelineAttributes.NODES_PARENT_MAP,
					nodesParentMap);

			doChain(pipeline);
		} catch (ReflectiveOperationException e) {
			/*
			 * Unreachable code. This catch block is unreachable in
			 * normal conditions, as the input already was validated in the
			 * previous phase (Pre-validation).
			 */
		}
	}
	
	/*
	 * Responsible only for code coverage purposes, specifically the catch block
	 * of the run() method.
	 */
	boolean runMock() throws TreeException {
		this.run(null);
		return true;
	}
}
