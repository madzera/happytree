package com.madzera.happytree.core;

import java.lang.reflect.Method;

/**
 * The class <code>TreeUnitTestHelper</code> is a helper class to provide
 * access to internal methods of the core package for JUnit tests purposes.
 * 
 * @author Diego Madson de Andrade Nóbrega
 */
public class TreeUnitTestHelper {
    private TreeUnitTestHelper() {}
    
    /**
     * <p>This method helps HappyTree API to cover code blocks that is not
     * accessible from outside for JUnit tests purposes.</p>
     * 
     * <p>The test must provide the fully qualified name of the class and the
     * method name to be tested. <b>This only works for methods with no
     * parameters.</b></p>
     * 
     * @param fqn fully qualified name of the class
     * @param method representing the method name
     * 
     * @throws ReflectiveOperationException if any reflection error occurs
     */
    public static boolean executeInternalMethod(final String fqn,
            final String method) throws ReflectiveOperationException {
        Class<?> className = Class.forName(fqn);
        
        Object obj = className.getDeclaredConstructor().newInstance();
        Method methodName = obj.getClass().getDeclaredMethod(method);

        return (boolean) methodName.invoke(obj);
    }
}
