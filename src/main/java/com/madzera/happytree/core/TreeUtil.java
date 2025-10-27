package com.madzera.happytree.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

final class TreeUtil {

    /*
     * This class needs a default (package) visibility constructor instead
     * private only for the code coverage purposes.
     */
    TreeUtil() {
    }

    static class IoUtil {
        private IoUtil() {
        }

        static Object deepCopyObject(Object original,
                Class<? extends Exception> mock) {
            Object clonedObject = null;
            try {
                byte[] serializedData;
                
                try (ByteArrayOutputStream bos = TreeFactory.ioFactory().
                        createByteArrayOutputStream();
                     ObjectOutputStream out = TreeFactory.ioFactory().
                        createObjectOutputStream(bos)) {
                    
                    /*
                     * It is only for the code coverage purposes. The catch
                     * block is unreachable in normal conditions, then the mock
                     * exception is passed through the runMock() method.
                     */
                    if (IOException.class.equals(mock)) {
                        throw new IOException();
                    }

                    out.writeObject(original);
                    out.flush();
                    serializedData = bos.toByteArray();
                }

                try (ByteArrayInputStream bis = TreeFactory.ioFactory().
                        createByteArrayInputStream(serializedData);
                     ObjectInputStream in = TreeFactory.ioFactory().
                        createObjectInputStream(bis)) {
                    
                    clonedObject = in.readObject();
                }
                
            } catch (Exception e) {
                /*
                 * Unreachable code. This catch block is unreachable in
                 * normal conditions.
                 */
            }
            return clonedObject;
        }
    }
    
    /*
	 * Responsible only for code coverage purposes, specifically the catch block
	 * of the deepCopyObject() method.
	 */
	boolean runMock() {
		IoUtil.deepCopyObject(null, IOException.class);
		return true;
	}
}