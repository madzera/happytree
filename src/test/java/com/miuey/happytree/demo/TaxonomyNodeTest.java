package com.miuey.happytree.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.HappyTree;
import com.miuey.happytree.demo.model.TaxonomyNode;
import com.miuey.happytree.demo.util.TaxonomyNodeAssembler;
import com.miuey.happytree.exception.TreeException;

/**
 * Build a taxonomy node structure in a corporate system business.
 * 
 * <p>The purpose of this test is to simulate a taxonomy node structure in a
 * corporate system business.</p>
 * 
 * <p>The model (<code>TaxonomyNode</code>) consists in a real model
 * class that is used in a real project. The class got some annotations by
 * HappyTree to make possible to assemble the tree.</p>
 * 
 * <p>Model:</p>
 * {@link TaxonomyNode}
 * 
 * <p>Utility Tree Assembler:</p>
 * {@link TaxonomyNodeAssembler}
 */
public class TaxonomyNodeTest {

	@Test
	public void taxonomyNode() throws TreeException {
		final String sessionId = "Taxonomy";
		
		List<TaxonomyNode> nodes = TaxonomyNodeAssembler.getTaxonomyNodeList();
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, nodes);
		
		Element<TaxonomyNode> root = manager.root();
		Element<TaxonomyNode> dispatches = manager.getElementById(92);
		Element<TaxonomyNode> _2020 = manager.getElementById(4002);
		Element<TaxonomyNode> july = manager.getElementById(54393);
		Element<TaxonomyNode> august = manager.getElementById(98474);
		Element<TaxonomyNode> september = manager.getElementById(93549);
		
		assertNotNull(root);
		assertNotNull(september);
		assertNotNull(august);
		assertNotNull(_2020);
		assertNotNull(dispatches);
		assertNotNull(september);
		
		assertFalse(manager.containsElement(july, september));
		assertFalse(manager.containsElement(september, july));
		assertFalse(manager.containsElement(august.getId(), september.getId()));
		
		assertFalse(manager.containsElement(august, root));
		assertTrue(manager.containsElement(root, august));
		
		assertTrue(manager.containsElement(_2020, july));
		assertTrue(manager.containsElement(_2020, august));
		assertTrue(manager.containsElement(_2020, september));
		
		assertTrue(manager.containsElement(dispatches, july));
		assertTrue(manager.containsElement(dispatches, august));
		assertTrue(manager.containsElement(dispatches, september));
		assertTrue(manager.containsElement(dispatches, _2020));
		
		assertEquals("Dispatches", dispatches.unwrap().getNodeName());
		assertEquals("2020", _2020.unwrap().getNodeName());
		assertEquals("July", july.unwrap().getNodeName());
		assertEquals("August", august.unwrap().getNodeName());
		assertEquals("September", september.unwrap().getNodeName());
		
		TaxonomyNode tProcesses = new TaxonomyNode();
		TaxonomyNode tCriminal = new TaxonomyNode();
		TaxonomyNode tJanuary = new TaxonomyNode();
		
		tProcesses.setNodeName("Processes");
		tCriminal.setNodeName("Criminal");
		tJanuary.setNodeName("January");
		
		Element<TaxonomyNode> processes = manager.createElement(58583, null,
				null);
		Element<TaxonomyNode> criminal = manager.createElement(243556, null,
				null);
		Element<TaxonomyNode> january = manager.createElement(243557, null,
				null);
		
		processes.wrap(tProcesses);
		criminal.wrap(tCriminal);
		january.wrap(tJanuary);
		
		criminal.addChild(january);
		processes.addChild(criminal);
		
		assertEquals("NOT_EXISTED", processes.lifecycle());
		
		/*
		 * Doing any operation inside of the tree, this is necessary to 'refresh'
		 * the elements invoking getElementById.
		 */
		processes = manager.persistElement(processes);
		root = manager.root();
		criminal = manager.getElementById(criminal.getId());
		january = manager.getElementById(january.getId());
		
		assertEquals("ATTACHED", processes.lifecycle());
		
		assertTrue(manager.containsElement(processes, criminal));
		assertTrue(manager.containsElement(criminal, january));
		assertTrue(manager.containsElement(processes, january));
		
		assertFalse(manager.containsElement(dispatches, processes));
		assertTrue(manager.containsElement(root, processes));
		
		assertEquals(2, root.getChildren().size());

		/*
		 * Passing Ids instead elements, this is not necessary to 'refresh' the
		 * elements.
		 */
		processes = manager.cut(processes, _2020);
		assertTrue(manager.containsElement(_2020.getId(), processes.getId()));
		assertTrue(manager.containsElement(_2020.getId(), criminal.getId()));
		assertTrue(manager.containsElement(_2020.getId(), january.getId()));
		assertTrue(manager.containsElement(dispatches.getId(),
				processes.getId()));
		assertTrue(manager.containsElement(dispatches.getId(),
				criminal.getId()));
		assertTrue(manager.containsElement(dispatches.getId(), january.getId()));
		assertEquals(1, manager.root().getChildren().size());
		
		processes = manager.removeElement(processes);
		
		assertFalse(manager.containsElement(_2020.getId(), _2020.getId()));
		assertFalse(manager.containsElement(_2020.getId(),
				criminal.getId()));
		assertFalse(manager.containsElement(_2020.getId(), january.getId()));
		
	}
}
