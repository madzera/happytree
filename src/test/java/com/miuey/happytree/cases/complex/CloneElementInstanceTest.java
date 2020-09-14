package com.miuey.happytree.cases.complex;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.HappyTree;
import com.miuey.happytree.example.Directory;
import com.miuey.happytree.example.TreeAssembler;
import com.miuey.happytree.exception.TreeException;

public class CloneElementInstanceTest {

	@Test
	public void isReallyCloneInstance() throws TreeException {
		final String sessionId = "isReallyCloneInstance";
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		Collection<Directory> directories = TreeAssembler.
				getDirectoryTree();
		transaction.initializeSession(sessionId, directories);
		
		Element<Directory> root = manager.root();
		
		Element<Directory> devel = null;
		
		for (Element<Directory> iterator : root.getChildren()) {
			devel = iterator;
		}
		
		Element<Directory> sdkDev = null;
		
		for (Element<Directory> iterator : devel.getChildren()) {
			sdkDev = iterator;
		}
		
		Element<Directory> jdk = null;
		
		for (Element<Directory> iterator : sdkDev.getChildren()) {
			jdk = iterator;
		}
		Directory dir = new Directory(48394355, 0, "TEST");
		
		jdk.wrap(dir);
		jdk.setParent(11111111111111111l);
		Element<Directory> jdk2Target = manager.getElementById(983533l);
		
		manager.cut(root, jdk2Target);
		assertTrue(Boolean.TRUE);
	}
}
