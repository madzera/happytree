package com.miuey.happytree.core.atp;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ATPReflectionUtil {
	
	private ATPReflectionUtil() {}
	
	static boolean isAnnotation(Class<?> clazz) {
		return clazz.isAnnotation();
	}
	
	static boolean isArray(Class<?> clazz) {
		return clazz.isArray();
	}
	
	static boolean isPrimitive(Class<?> clazz) {
		return clazz.isPrimitive();
	}
	
	static Object instance(Class<?> clazz, Object[] constructorArgsClass) throws 
		NoSuchMethodException, InstantiationException, IllegalAccessException, 	
		InvocationTargetException {
		final int argsLength = constructorArgsClass.length;
		Class<?>[] objectsClass = new Class<?>[argsLength];
		for (int i = 0; i < argsLength; i++) {
			objectsClass[i] = constructorArgsClass[i].getClass();
		}
		Constructor<?> constructor = clazz.getConstructor(objectsClass);
		return instance(constructor, constructorArgsClass);
	}
	
	static Object instance(Constructor<?> constructor, Object... args) 
			throws InstantiationException, IllegalAccessException, 
			InvocationTargetException { 
		return constructor.newInstance(args);
	}
	
	static Object invokeMethod(Object object, String methodName) 
			throws IllegalAccessException, InvocationTargetException, 
			NoSuchMethodException {
		Method method = getMethod(object, methodName);
		return method.invoke(object);
	}
	
	static Object invokeMethod(Object object, Method method) 
			throws IllegalAccessException, InvocationTargetException {
		return method.invoke(object);
	}
	
	static Object invokeMethod(Object object, Method method, Object... args) 
			throws IllegalAccessException, InvocationTargetException {
		return method.invoke(object, args);
	}
	
	static Object invokeSetter(String attributeName, Object object, 
			Object...args) throws IllegalAccessException, 
			InvocationTargetException, NoSuchMethodException {
		Method setter = methodSetAttribute(attributeName, object.getClass());
		return invokeMethod(object, setter, args);
	}
	
	static Object invokeGetter(String attribute, Object object) 
			throws IllegalAccessException, InvocationTargetException, 
			NoSuchMethodException {
		Method getter = methodGetAttribute(attribute, object.getClass());
		return invokeMethod(object, getter);
	}
	
	static Method getMethod(Object object, String methodName) 
			throws NoSuchMethodException {
		Class<?> objClass = object.getClass();
		return getMethod(objClass, methodName);
	}
	
	static Method getMethod(Class<?> objClass, String methodName) 
			throws NoSuchMethodException {
		return objClass.getMethod(methodName);
	}
	
	static Method methodGetAttribute(String attributeName, Class<?> clazz) 
			throws NoSuchMethodException {
		return methodGetOrSetBody("get", attributeName, clazz);
	}
	
	static Method methodGetAttribute(Field attribute, Class<?> clazz) 
			throws NoSuchMethodException {
		return methodGetAttribute(attribute.getName(), clazz);
	}

	static Method methodSetAttribute(Field attribute, Class<?> clazz) 
			throws NoSuchMethodException {
		return methodGetOrSetBody("set", attribute.getName(), clazz);
	}
	
	static Method methodSetAttribute(String attributeName, Class<?> clazz) 
			throws NoSuchMethodException {
		return methodGetOrSetBody("set", attributeName, clazz);
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

	static void invokeVoidMethod(Object object, Method method, Object... args) 
			throws IllegalAccessException, InvocationTargetException {
		method.invoke(object, args);
	}

	static void invokeVoidMethod(Object object, String methodName, 
			Object... args) throws NoSuchMethodException, IllegalAccessException, 
			InvocationTargetException {
		Method method = getMethod(object.getClass(), methodName);
		method.invoke(object, args);
	}
	
	private static Method methodGetOrSetBody(String getOrSet, 
			String attributeName, Class<?> clazz) throws NoSuchMethodException {
		String firstLetter = attributeName.substring(0, 1);
		firstLetter = firstLetter.toUpperCase();
		attributeName = attributeName.substring(1);
		String methodName = getOrSet.concat(firstLetter).concat(attributeName);
		return clazz.getMethod(methodName);
	}
}
