package com.miuey.happytree.element;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	ElementTest.class,
	ElementAlternativeTest.class,
	ElementErrorTest.class
})
public class ElementSuiteTest {

	@Test
	public void toDo() {
		assertTrue(Boolean.TRUE);
	}
}
