package com.miuey.happytree;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.miuey.happytree.element.ElementSuiteTest;
import com.miuey.happytree.manager.TreeManagerSuiteTest;
import com.miuey.happytree.session.TreeSessionSuiteTest;
import com.miuey.happytree.transaction.TreeTransactionSuiteTest;

@RunWith(Suite.class)
@SuiteClasses({
	ElementSuiteTest.class,
	TreeManagerSuiteTest.class,
	TreeSessionSuiteTest.class,
	TreeTransactionSuiteTest.class})
public class HappyTreeTest {

}
