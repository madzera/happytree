package com.miuey.happytree.transaction;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	TreeTransactionTest.class,
	TreeTransactionAlternativeTest.class,
	TreeTransactionErrorTest.class
})
public class TreeTransactionSuiteTest {

	@Test
	public void toDo() {
		assertTrue(Boolean.TRUE);
	}
}
