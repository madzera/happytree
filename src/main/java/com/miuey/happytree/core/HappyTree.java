package com.miuey.happytree.core;

import com.miuey.happytree.TreeManager;

/**
 * <i>Helper</i> class that represents the entry point of the HappyTree API.
 * 
 * <p>It is not justifiable to inherit this class or elaborate any further
 * implementation besides serving only the API entry point, which justifies the
 * fact that the HappyTree class is <i><b>final</b></i>.</p>
 * 
 * <p>The entry point of the API represents an instance of {@link TreeManager},
 * from which it is possible to access its respective
 * {@linkplain com.miuey.happytree.TreeTransaction} object.</p>
 * 
 * <p>From a transaction you can then manipulate trees and sessions and
 * consequently the elements within the trees, thus contemplating the absolute
 * use of the API</p>
 * 
 * <p><code>TreeManager</code> -> <code>TreeTransaction</code> ->
 * <code>TreeSession</code> -> <code>Element</code>.</p>
 * 
 * <p><b>Relation between <code>TreeManager</code> ->
 * <code>TreeTransaction</code>: 1:1</b></p>
 * <p><b>Relation between <code>TreeTransaction</code> ->
 * <code>TreeSession</code>: 1:0-N</b></p>
 * <p><b>Relation between <code>TreeSession</code> ->
 * <code>Element</code>: 1:0-N</b></p>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 * 
 * @see {@linkplain com.miuey.happytree.TreeManager}
 * @see {@linkplain com.miuey.happytree.TreeTransaction}
 * @see {@linkplain com.miuey.happytree.TreeSession}
 * @see {@linkplain com.miuey.happytree.Element}
 * 
 * @version %I%, %G%
 *
 */
public final class HappyTree {

	/*
	 * Protected Helper constructor class.
	 */
	private HappyTree() {}

	
	/**
	 * Return a <b><i>singleton</i></b> instance of <code>TreeManager</code>,
	 * representing the entry point of the HappyTree API.
	 * 
	 * @return the instance of <code>TreeManager</code>
	 */
	public static TreeManager createTreeManager() {
		return TreeManagerCore.getTreeManagerInstance();
	}
}
