package com.miuey.happytree.core.atp;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.miuey.happytree.annotation.Id;
import com.miuey.happytree.core.TreePipeline;
import com.miuey.happytree.exception.TreeException;

public class Extraction extends ATPGenericPhase {

	@Override
	protected void run(TreePipeline pipeline) throws TreeException {
		Map<Object, Object> sourceMap = new HashMap<Object, Object>();
		Collection<?> objects = (Collection<?>) pipeline.getAttribute("objects");
		
		for (Object object : objects) {
			Field fieldIdAnnotation = ATPReflectionUtil.getFieldAnnotation(
					object, Id.class);
			try {
				Object objId = ATPReflectionUtil.invokeGetter(
						fieldIdAnnotation.getName(), object);
				sourceMap.put(objId, object);
			} catch (ReflectiveOperationException e) {
				throw this.throwTreeException(ATPRepositoryMessage.GENERAL);
			}
		}
		doChain(pipeline);
	}
}
