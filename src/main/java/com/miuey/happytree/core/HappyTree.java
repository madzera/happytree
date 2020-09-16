package com.miuey.happytree.core;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeSession;
import com.miuey.happytree.TreeTransaction;

/**
 * <i>Helper</i> class that shows up the entry point of the HappyTree API.
 * 
 * <p>This class only serve to return an instance of the main
 * <code>interface</code> which will assist the API client to handle trees.</p>
 * 
 * <p>The entry point of the API represents an instance of {@link TreeManager},
 * from which it is possible to access its respective {@link TreeTransaction}
 * object.</p>
 * 
 * <p>From this, it is possible to manipulate trees and sessions and
 * consequently the elements within the trees, thus contemplating the absolute
 * use of the API.</p>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 * 
 * @see TreeSession
 * @see Element
 * 
 */
public final class HappyTree {

	/*
	 * Protects the Helper constructor class.
	 */
	private HappyTree() {}

	
	/**
	 * Returns an instance of <code>TreeManager</code> interface, representing
	 * the entry point of the HappyTree API.
	 * 
	 * <p>Each thread that use the HappyTree API needs to create its respective
	 * TreeManager</code> interface instance. Therefore each thread will have
	 * its instance. This is not a <i>Singleton</i> instance.</p>
	 * 
	 * @return an instance of <code>TreeManager</code>
	 */
	public static TreeManager createTreeManager() {
		return TreeManagerCore.getTreeManagerInstance();
	}
}
