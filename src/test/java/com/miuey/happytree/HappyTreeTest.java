package com.miuey.happytree;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.miuey.happytree.demo.ContextMenuTest;
import com.miuey.happytree.demo.TaxonomyNodeTest;
import com.miuey.happytree.demo.VerifyCloneDescendantsTest;
import com.miuey.happytree.element.ElementSuiteTest;
import com.miuey.happytree.manager.TreeManagerSuiteTest;
import com.miuey.happytree.session.TreeSessionSuiteTest;
import com.miuey.happytree.transaction.TreeTransactionSuiteTest;

@RunWith(Suite.class)
@SuiteClasses({
	
	//Basics API interfaces tests.
	ElementSuiteTest.class,
	TreeManagerSuiteTest.class,
	TreeSessionSuiteTest.class,
	TreeTransactionSuiteTest.class,
	
	//Custom tests.
	VerifyCloneDescendantsTest.class,
	
	//Real cases adopted in real projects.
	ContextMenuTest.class,
	TaxonomyNodeTest.class
})
public class HappyTreeTest {}
