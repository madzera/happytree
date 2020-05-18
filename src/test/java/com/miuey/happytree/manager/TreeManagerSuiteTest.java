package com.miuey.happytree.manager;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	TreeManagerTest.class,
	TreeManagerAlternativeTest.class,
	TreeManagerErrorTest.class})
public class TreeManagerSuiteTest {

	@Test
	public void toDo() {
		assertTrue(Boolean.TRUE);
	}
}
