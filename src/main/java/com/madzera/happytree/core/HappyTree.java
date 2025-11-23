package com.madzera.happytree.core;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeSession;
import com.madzera.happytree.TreeTransaction;

/**
 * <i>Helper</i> class that provides the entry point of the HappyTree API.
 * 
 * <p>This class has the only objective to return an instance of the
 * {@link TreeManager} interface that will assist the API client to handle
 * trees. With this instance, it is possible to access the respective
 * {@link TreeTransaction} object.</p>
 * 
 * <p>From this, it is possible to handle trees and sessions and consequently
 * the elements within the trees.</p>
 * 
 * @author Diego Madson de Andrade Nóbrega
 * 
 * @see TreeSession
 * @see Element
 */
public final class HappyTree {

	/*
	 * Protects the constructor class.
	 */
	private HappyTree() {}

	
	/**
	 * Returns an instance of {@link TreeManager}, representing the entry point
	 * of the HappyTree API.
	 * 
	 * <p>This is not a <i>Singleton</i> instance.</p>
	 * 
	 * @return an instance of <code>TreeManager</code>
	 */
	public static TreeManager createTreeManager() {
		return TreeManagerCore.getTreeManagerInstance();
	}
}
