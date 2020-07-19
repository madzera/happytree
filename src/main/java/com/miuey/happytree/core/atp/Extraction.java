package com.miuey.happytree.core.atp;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.miuey.happytree.annotation.Id;
import com.miuey.happytree.annotation.Parent;
import com.miuey.happytree.core.TreePipeline;
import com.miuey.happytree.exception.TreeException;

public class Extraction<T> extends ATPGenericPhase<T> {

	@Override
	protected void run(TreePipeline pipeline) throws TreeException {
		Map<Object, Object> mapObjects = new HashMap<>();
		Map<Object, Object> mapParents = new HashMap<>();
		
		Collection<?> objects = (Collection<?>) pipeline.getAttribute("objects");
		
		for (Object object : objects) {
			Field fieldIdAnnotation = ATPReflectionUtil.getFieldAnnotation(
					object, Id.class);
			Field fieldParentAnnotation = ATPReflectionUtil.getFieldAnnotation(
					object, Parent.class);
			try {
				Object objId = ATPReflectionUtil.invokeGetter(
						fieldIdAnnotation.getName(), object);
				Object objParent = ATPReflectionUtil.invokeGetter(
						fieldParentAnnotation.getName(), object);
				
				mapObjects.put(objId, object);
				mapParents.put(objId, objParent);
			} catch (ReflectiveOperationException e) {
				throw this.throwTreeException(ATPRepositoryMessage.GENERAL);
			}
		}
		pipeline.addAttribute("mapObjects", mapObjects);
		pipeline.addAttribute("mapParents", mapParents);
		doChain(pipeline);
	}
}
