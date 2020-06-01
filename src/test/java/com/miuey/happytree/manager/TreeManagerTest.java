package com.miuey.happytree.manager;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.HappyTree;

public class TreeManagerTest {

	@Test
	public void getTransaction() {
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		assertNotNull(transaction);
	}
}
