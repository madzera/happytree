package com.madzera.happytree.demo;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeTransaction;
import com.madzera.happytree.core.HappyTree;
import com.madzera.happytree.demo.model.TaxonomyNode;
import com.madzera.happytree.demo.util.TaxonomyNodeAssembler;
import com.madzera.happytree.exception.TreeException;

/**
 * Build a taxonomy node structure in a corporate system business.
 * 
 * <p>The purpose of this test is to simulate a taxonomy node structure in a
 * corporate system business.</p>
 * 
 * <p>The model (<code>TaxonomyNode</code>) consists in a real class model
 * that is used in a real project. The class got some annotations by
 * HappyTree to make possible to assemble the tree.</p>
 * 
 * <p>Object Model:</p>
 * {@link TaxonomyNode}
 * 
 * <p>Utility Tree Assembler:</p>
 * {@link TaxonomyNodeAssembler}
 */
public class TaxonomyNodeTest extends CommonDemoTest {

	@Test
	public void execute() throws TreeException {
		final String sessionId = "Taxonomy";
		
		List<TaxonomyNode> nodes = TaxonomyNodeAssembler.getTaxonomyNodeList();
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, nodes);
		
		assertNotNull(transaction);
		
		Element<TaxonomyNode> root = manager.root();
		Element<TaxonomyNode> dispatches = manager.getElementById(92);
		Element<TaxonomyNode> _2020 = manager.getElementById(4002);
		Element<TaxonomyNode> july = manager.getElementById(54393);
		Element<TaxonomyNode> august = manager.getElementById(98474);
		Element<TaxonomyNode> september = manager.getElementById(93549);
		
		isNotNull(root);
		isNotNull(september);
		isNotNull(august);
		isNotNull(_2020);
		isNotNull(dispatches);
		isNotNull(september);
		
		isFalse(manager.containsElement(july, september));
		isFalse(manager.containsElement(september, july));
		isFalse(manager.containsElement(august.getId(), september.getId()));
		
		isFalse(manager.containsElement(august, root));
		isTrue(manager.containsElement(root, august));
		
		isTrue(manager.containsElement(_2020, july));
		isTrue(manager.containsElement(_2020, august));
		isTrue(manager.containsElement(_2020, september));
		
		isTrue(manager.containsElement(dispatches, july));
		isTrue(manager.containsElement(dispatches, august));
		isTrue(manager.containsElement(dispatches, september));
		isTrue(manager.containsElement(dispatches, _2020));
		
		isEquals("Dispatches", dispatches.unwrap().getNodeName());
		isEquals("2020", _2020.unwrap().getNodeName());
		isEquals("July", july.unwrap().getNodeName());
		isEquals("August", august.unwrap().getNodeName());
		isEquals("September", september.unwrap().getNodeName());
		
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
		
		isEquals("NOT_EXISTED", processes.lifecycle());
		
		/*
		 * Doing any operation inside of the tree, this is necessary to 'refresh'
		 * the elements invoking getElementById.
		 */
		processes = manager.persistElement(processes);
		root = manager.root();
		criminal = manager.getElementById(criminal.getId());
		january = manager.getElementById(january.getId());
		
		isEquals("ATTACHED", processes.lifecycle());
		
		isTrue(manager.containsElement(processes, criminal));
		isTrue(manager.containsElement(criminal, january));
		isTrue(manager.containsElement(processes, january));
		
		isFalse(manager.containsElement(dispatches, processes));
		isTrue(manager.containsElement(root, processes));
		
		isEquals(2, root.getChildren().size());

		/*
		 * Passing Ids instead elements, this is not necessary to 'refresh' the
		 * elements.
		 */
		processes = manager.cut(processes, _2020);
		isTrue(manager.containsElement(_2020.getId(), processes.getId()));
		isTrue(manager.containsElement(_2020.getId(), criminal.getId()));
		isTrue(manager.containsElement(_2020.getId(), january.getId()));
		isTrue(manager.containsElement(dispatches.getId(),
				processes.getId()));
		isTrue(manager.containsElement(dispatches.getId(),
				criminal.getId()));
		isTrue(manager.containsElement(dispatches.getId(), january.getId()));
		isEquals(1, manager.root().getChildren().size());
		
		processes = manager.removeElement(processes);
		
		isFalse(manager.containsElement(_2020.getId(), _2020.getId()));
		isFalse(manager.containsElement(_2020.getId(),
				criminal.getId()));
		isFalse(manager.containsElement(_2020.getId(), january.getId()));
		
	}
}
