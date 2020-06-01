package com.miuey.happytree.manager;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	TreeManagerTest.class,
	TreeManagerAlternativeTest.class,
	TreeManagerErrorTest.class})
public class TreeManagerSuiteTest {
}
