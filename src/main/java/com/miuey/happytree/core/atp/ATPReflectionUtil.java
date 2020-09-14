package com.miuey.happytree.core.atp;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

class ATPReflectionUtil {
	
	private ATPReflectionUtil() {}
	
	
	static Object invokeMethod(Object object, Method method) 
			throws ReflectiveOperationException {
		return method.invoke(object);
	}
	
	static Object invokeGetter(String attribute, Object object) 
			throws ReflectiveOperationException {
		Method getter = methodGetAttribute(attribute, object.getClass());
		return invokeMethod(object, getter);
	}
	
	static Method methodGetAttribute(String attributeName, Class<?> clazz) 
			throws ReflectiveOperationException {
		return methodGetOrSetBody("get", attributeName, clazz);
	}
	
	static Field getFieldAnnotation(Object object, 
			Class<? extends Annotation> annotationClass) {
		Class<?> objClass = object.getClass();
		Field[] fields = objClass.getDeclaredFields();
		
		for (Field field : fields) {
			Annotation annotation = field.getAnnotation(annotationClass);
			if (annotation != null) {
				return field;
			}
		}
		return null;
	}

	private static Method methodGetOrSetBody(String getOrSet,
			String attributeName, Class<?> clazz)
					throws ReflectiveOperationException {
		
		String firstLetter = attributeName.substring(0, 1);
		firstLetter = firstLetter.toUpperCase();
		attributeName = attributeName.substring(1);
		String methodName = getOrSet.concat(firstLetter).concat(attributeName);
		return clazz.getMethod(methodName);
	}
}
