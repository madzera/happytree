package com.miuey.happytree.demo;

import org.junit.Test;

import com.miuey.happytree.exception.TreeException;

abstract class CommonDemoTest {
	
	protected CommonDemoTest() {}
	
	
	protected void isEquals(Object expected, Object actual) {
		org.junit.Assert.assertEquals(expected, actual);
	}
	
	protected void isNotEquals(Object expected, Object actual) {
		org.junit.Assert.assertNotEquals(expected, actual);
	}
	
	protected void isNull(Object object) {
		org.junit.Assert.assertNull(object);
	}
	
	protected void isNotNull(Object object) {
		org.junit.Assert.assertNotNull(object);
	}
	
	protected void isTrue(boolean condition) {
		org.junit.Assert.assertTrue(condition);
	}
	
	protected void isFalse(boolean condition) {
		org.junit.Assert.assertFalse(condition);
	}
	
	@Test
	protected abstract void execute() throws TreeException;
}
