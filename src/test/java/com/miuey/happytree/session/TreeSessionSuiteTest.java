package com.miuey.happytree.session;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	TreeSessionTest.class,
	TreeSessionAlternativeTest.class,
	TreeSessionErrorTest.class
})
public class TreeSessionSuiteTest {

	@Test
	public void toDo() {
		assertTrue(Boolean.TRUE);
	}
}
