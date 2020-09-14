package com.miuey.happytree.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.HappyTree;
import com.miuey.happytree.demo.model.EManagementContextMenu;
import com.miuey.happytree.demo.util.ContextMenuAssembler;
import com.miuey.happytree.exception.TreeException;

/**
 * Build a menu through <code>Enum</code> objects.
 * 
 * <p>The purpose of this test is to simulate a menu behavior composed by
 * <code>enum</code> objects.</p>
 * 
 * <p>The model (<code>EManagementContextMenu</code>) consists in a real model
 * class that is used in a real project. The class got some annotations by
 * HappyTree to make possible to assemble the tree.</p>
 * 
 * <p>Model:</p>
 * {@link EManagementContextMenu}
 * 
 * <p>Utility Tree Assembler:</p>
 * {@link ContextMenuAssembler}
 * 
 * @author Diego Nóbrega.
 */
public class ContextMenuTest {

	@Test
	public void contextMenu() throws TreeException {
		final String sessionId = "Menus";
		
		String menu1 = EManagementContextMenu.MENU_1.name();
		String menu2 = EManagementContextMenu.MENU_2.name();
		String menu3 = EManagementContextMenu.MENU_3.name();
		
		String submenu1 = EManagementContextMenu.SUBMENU_1.name();
		String submenu2 = EManagementContextMenu.SUBMENU_2.name();
		String submenu3 = EManagementContextMenu.SUBMENU_3.name();
		String submenu4 = EManagementContextMenu.SUBMENU_4.name();
		String submenu5 = EManagementContextMenu.SUBMENU_5.name();
		
		List<EManagementContextMenu> menus = ContextMenuAssembler.
				getContextMenuListTree();
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, menus);
		
		Element<EManagementContextMenu> m2 = manager.getElementById(menu2);
		Element<EManagementContextMenu> sub4 = manager.getElementById(submenu4);
		Element<EManagementContextMenu> sub5 = manager.getElementById(submenu5);
		
		Element<EManagementContextMenu> m1 = manager.getElementById(menu1);
		Element<EManagementContextMenu> sub1 = manager.getElementById(submenu1);
		Element<EManagementContextMenu> sub2 = manager.getElementById(submenu2);
		Element<EManagementContextMenu> sub3 = manager.getElementById(submenu3);
		
		Element<EManagementContextMenu> m3 = manager.getElementById(menu3);
		
		assertTrue(manager.containsElement(m1, sub3));
		assertTrue(manager.containsElement(m1, sub1));
		assertTrue(manager.containsElement(m1, sub2));
		
		assertTrue(manager.containsElement(m2, sub5));
		assertTrue(manager.containsElement(m2, sub4));
		
		assertEquals(0, m3.getChildren().size());
		
		EManagementContextMenu management = m1.unwrap();
		EManagementContextMenu transfer = m2.unwrap();
		EManagementContextMenu authorization = m3.unwrap();
		
		assertEquals("management", management.getOperationResource());
		assertEquals("transfer", transfer.getOperationResource());
		assertEquals("authorization", authorization.getOperationResource());
	}
}
