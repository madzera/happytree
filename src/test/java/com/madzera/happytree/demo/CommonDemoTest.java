package com.madzera.happytree.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.madzera.happytree.exception.TreeException;

abstract class CommonDemoTest {
	
	protected CommonDemoTest() {}
	
	
	protected void isEquals(Object expected, Object actual) {
		assertEquals(expected, actual);
	}
	
	protected void isNotEquals(Object expected, Object actual) {
		assertNotEquals(expected, actual);
	}
	
	protected void isNull(Object object) {
		assertNull(object);
	}
	
	protected void isNotNull(Object object) {
		assertNotNull(object);
	}
	
	protected void isTrue(boolean condition) {
		assertTrue(condition);
	}
	
	protected void isFalse(boolean condition) {
		assertFalse(condition);
	}
	
	@Test
	protected abstract void execute() throws TreeException;
}
