package com.madzera.happytree;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.madzera.happytree.demo.ComplexEmptyTreeTest;
import com.madzera.happytree.demo.ContextMenuTest;
import com.madzera.happytree.demo.TaxonomyNodeTest;
import com.madzera.happytree.demo.VerifyCloneDescendantsTest;
import com.madzera.happytree.element.ElementSuiteTest;
import com.madzera.happytree.manager.TreeManagerSuiteTest;
import com.madzera.happytree.session.TreeSessionSuiteTest;
import com.madzera.happytree.transaction.TreeTransactionSuiteTest;

@RunWith(Suite.class)
@SuiteClasses({
	
	//Basics API interfaces tests.
	ElementSuiteTest.class,
	TreeManagerSuiteTest.class,
	TreeSessionSuiteTest.class,
	TreeTransactionSuiteTest.class,
	
	//Custom tests.
	VerifyCloneDescendantsTest.class,
	ComplexEmptyTreeTest.class,
	
	//Real cases adopted in real projects.
	ContextMenuTest.class,
	TaxonomyNodeTest.class
})
public class HappyTreeTest {}
