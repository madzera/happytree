package com.madzera.happytree.demo;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeTransaction;
import com.madzera.happytree.core.HappyTree;
import com.madzera.happytree.demo.model.Directory;
import com.madzera.happytree.demo.util.TreeAssembler;
import com.madzera.happytree.exception.TreeException;

/**
 * Turns on a descendant of the root as <i>DETACHED</i> before cut the element.
 * 
 * <p>The purpose of this test is to prove that even changing the elements in
 * the lower layers of the trees, the same change is detected by the API and
 * thrown an exception, as it is only possible to cut <i>ATTACHED</i>
 * elements.</p>
 * 
 * <p>Object Model:</p>
 * {@link Directory}
 * 
 * <p>Utility Tree Assembler:</p>
 * {@link TreeAssembler}
 */
public class VerifyCloneDescendantsTest {

	@Test
	public void isReallyCloneInstance() throws TreeException {
		final String sessionId = "isReallyCloneInstance";
		final String msgError = "No possible to copy/cut/remove elements."
				+ " Invalid lifecycle state.";
		
		String error = null;
		
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
		
		try {
			manager.cut(jdk2Target, root);
		} catch (TreeException e) {
			error = e.getMessage();
		} finally {
			assertEquals(msgError, error);
		}
	}
}
