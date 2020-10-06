package com.miuey.happytree.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.miuey.happytree.Element;
import com.miuey.happytree.TreeManager;
import com.miuey.happytree.TreeTransaction;
import com.miuey.happytree.core.HappyTree;
import com.miuey.happytree.demo.model.Science;
import com.miuey.happytree.exception.TreeException;

/**
 * Create a new empty tree.
 * 
 * <p>The purpose of this test is build manually a new empty tree and handle it.
 * </p>
 */
public class ComplexTest_EmptyTree {

	Science _medicine = new Science("Medicine");
	Science _pediatrics = new Science("Pediatrics");
	Science _primaryCare = new Science("Primary Care");
	Science _pediatricOncology = new Science("Pediatric Oncology");
	Science _criticalCare = new Science("Critical Care");
	Science _pediatricCardiology = new Science("Pediatric Cardiology");
	Science _oncology = new Science("Oncology");
	Science _medicalOncology = new Science("Medical Oncology");
	Science _radiationOncology = new Science("Radiation Oncology");
	Science _surgicalOncology = new Science("Surgical Oncology");
	Science _pathology = new Science("Pathology");
	Science _anatomicalPathology = new Science("Anatomical Pathology");
	Science _clinicalPathology = new Science("Clinical Pathology");
	
	Science _computerScience = new Science("Computer Science");
	Science _theoretical = new Science("Theoretical");
	Science _theoryOfComputation = new Science("Theory of Computation");
	Science _codingTheory = new Science("Coding Theory");
	Science _dataStructures = new Science("Data Structures");
	Science _algorithms = new Science("Algorithms");
	Science _computerSystems = new Science("Computer Systems");
	Science _computerArchitecture = new Science("Computer Architecture");
	Science _computerEngineering = new Science("Computer Engineering");
	Science _computerNetwork = new Science("Computer Network");
	Science _databases = new Science("Databases");
	Science _security = new Science("Security");
	Science _cryptography = new Science("Cryptography");
	Science _concurrent = new Science("Concurrent");
	Science _computerApplications = new Science("Computer Applications");
	Science _computerGraphics = new Science("Computer Graphics");
	Science _ai = new Science("Artificial Intelligence");
	Science _softwareEngineering = new Science("Software Engineering");
	
	
	@Test
	public void newEmptyTree() throws TreeException {
		final String sessionId = "newEmptyTree";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Science.class);
		
		Element<Science> medicine = manager.createElement("Medicine", null,
				_medicine);
		Element<Science> pediatrics = manager.createElement("Pediatrics", null,
				_pediatrics);
		Element<Science> primaryCare = manager.createElement("Primary Care",
				null, _primaryCare);
		Element<Science> pediatricOncology = manager.createElement("Pediatric Oncology", null,
				_pediatricOncology);
		Element<Science> criticalCare = manager.createElement("Critical Care", null,
				_criticalCare);
		Element<Science> pediatricCardiology = manager.createElement("Pediatric Cardiology", null,
				_pediatricCardiology);
		Element<Science> oncology = manager.createElement("Oncology", null,
				_oncology);
		Element<Science> medicalOncology = manager.createElement("Medical Oncology", null,
				_medicalOncology);
		Element<Science> radiationOncology = manager.createElement("Radiation Oncology", null,
				_radiationOncology);
		Element<Science> surgicalOncology = manager.createElement("Surgical Oncology", null,
				_surgicalOncology);
		Element<Science> pathology = manager.createElement("Pathology", null,
				_pathology);
		Element<Science> anatomicalPathology = manager.createElement("Anatomical Pathology", null,
				_anatomicalPathology);
		Element<Science> clinicalPathology = manager.createElement("Clinical Pathology", null,
				_clinicalPathology);
		
		Element<Science> computerScience = manager.createElement("Computer Science", null,
				_computerScience);
		Element<Science> theoretical = manager.createElement("Theoretical", null,
				_theoretical);
		Element<Science> theoryOfComputation = manager.createElement("Theory of Computation", null,
				_theoryOfComputation);
		Element<Science> codingTheory = manager.createElement("Coding Theory", null,
				_codingTheory);
		Element<Science> dataStructures = manager.createElement("Data Structures", null,
				_dataStructures);
		Element<Science> algorithms = manager.createElement("Algorithms", null,
				_algorithms);
		Element<Science> computerSystems = manager.createElement("Computer Systems", null,
				_computerSystems);
		Element<Science> computerArchitecture = manager.createElement("Computer Architecture", null,
				_computerArchitecture);
		Element<Science> computerEngineering = manager.createElement("Computer Engineering", null,
				_computerEngineering);
		Element<Science> computerNetwork = manager.createElement("Computer Network", null,
				_computerNetwork);
		Element<Science> databases = manager.createElement("Databases", null,
				_databases);
		Element<Science> security = manager.createElement("Security", null,
				_security);
		Element<Science> cryptography = manager.createElement("Cryptography", null,
				_cryptography);
		Element<Science> concurrent = manager.createElement("Concurrent", null,
				_concurrent);
		Element<Science> computerApplications = manager.createElement("Computer Applications", null,
				_computerApplications);
		Element<Science> computerGraphics = manager.createElement("Computer Graphics", null,
				_computerGraphics);
		Element<Science> ai = manager.createElement("Artificial Intelligence", null,
				_ai);
		Element<Science> softwareEngineering = manager.createElement("Software Engineering", null,
				_softwareEngineering);
		
		List<Element<Science>> medicineList = new ArrayList<>();
		medicineList.add(pediatrics);medicineList.add(primaryCare);
		medicineList.add(pediatricOncology);medicineList.add(criticalCare);
		medicineList.add(pediatricCardiology);medicineList.add(oncology);
		medicineList.add(medicalOncology);medicineList.add(radiationOncology);
		medicineList.add(surgicalOncology);medicineList.add(pathology);
		medicineList.add(anatomicalPathology);medicineList.add(clinicalPathology);
		
		List<Element<Science>> compScienceList = new ArrayList<>();
		compScienceList.add(theoretical);compScienceList.add(theoryOfComputation);
		compScienceList.add(codingTheory);compScienceList.add(dataStructures);
		compScienceList.add(algorithms);compScienceList.add(computerSystems);
		compScienceList.add(computerArchitecture);compScienceList.add(computerEngineering);
		compScienceList.add(computerNetwork);compScienceList.add(databases);
		compScienceList.add(security);compScienceList.add(cryptography);
		compScienceList.add(concurrent);compScienceList.add(computerApplications);
		compScienceList.add(computerGraphics);compScienceList.add(ai);
		compScienceList.add(softwareEngineering);
		
		medicine.addChildren(medicineList);
		computerScience.addChildren(compScienceList);
		
		assertEquals("NOT_EXISTED", medicine.lifecycle());
		assertEquals("NOT_EXISTED", computerScience.lifecycle());
		
		manager.persistElement(medicine);
		manager.persistElement(computerScience);
		
		medicine = manager.getElementById("Medicine");
		computerScience = manager.getElementById("Computer Science");
		
		assertEquals("ATTACHED", medicine.lifecycle());
		assertEquals("ATTACHED", computerScience.lifecycle());
		
		assertEquals(12, medicine.getChildren().size());
		assertEquals(17, computerScience.getChildren().size());
		
		for (Element<Science> science : medicine.getChildren()) {
			assertEquals("ATTACHED", science.lifecycle());
		}
		for (Element<Science> science : computerScience.getChildren()) {
			assertEquals("ATTACHED", science.lifecycle());
		}
		
		pediatrics = medicine.getElementById("Pediatrics");
		oncology = medicine.getElementById("Oncology");
		pathology = medicine.getElementById("Pathology");
		
		pediatrics.addChild(pediatricOncology);
		pediatrics.addChild(pediatricCardiology);
		pediatrics.addChild(criticalCare);
		pediatrics.addChild(primaryCare);
		//pediatrics = manager.getElementById("Pediatrics");
		
		assertEquals("DETACHED", pediatrics.lifecycle());
		assertEquals("NOT_EXISTED", pediatricOncology.lifecycle());
		assertEquals("NOT_EXISTED", pediatricCardiology.lifecycle());
		assertEquals("NOT_EXISTED", criticalCare.lifecycle());
		assertEquals("NOT_EXISTED", primaryCare.lifecycle());
		
		String error = "No possible to update the element. Invalid lifecycle"
				+ " state.";
		/*
		 * Trying update the sub element instead of the parent element which
		 * added the sub element (child).
		 */
		try {
			criticalCare = manager.updateElement(criticalCare);
		} catch (TreeException e) {
			assertEquals(error, e.getMessage());
		}
		assertEquals("NOT_EXISTED", criticalCare.lifecycle());

		/*
		 * Trying to update an element which all children are NOT_EXISTED will
		 * throw an exception because this is only possible update elements with
		 * DETACHED state. This is necessary to persist its children first
		 * before updating.
		 */
		try {
			pediatrics = manager.updateElement(pediatrics);
		} catch (TreeException e) {
			assertEquals(error, e.getMessage());
		}
		
		/*
		 * Impossible to persist the Critical Care element because it was
		 * already persisted before when Medicine and its children were added
		 * to the tree. Duplicated ID.
		 */
		try {
			manager.persistElement(criticalCare);
		} catch (TreeException e) {
			String error2 = "Duplicated ID.";
			assertEquals(error2, e.getMessage());
		}
		
		/*
		 * Refresh.
		 */
		pediatrics = manager.getElementById(pediatrics.getId());
		pediatricCardiology = manager.getElementById(pediatricCardiology.
				getId());
		pediatricOncology = manager.getElementById(pediatricOncology.getId());
		criticalCare = manager.getElementById(criticalCare.getId());
		primaryCare = manager.getElementById(primaryCare.getId());
		
		assertNotNull(pediatricCardiology);
		assertNotNull(pediatricOncology);
		assertNotNull(criticalCare);
		assertNotNull(primaryCare);
		
		assertEquals(0, pediatrics.getChildren().size());
		
		List<Element<Science>> pedMedicine = new ArrayList<>();
		pedMedicine.add(criticalCare);
		pediatrics.addChild(pediatricCardiology);
		pediatrics.addChild(pediatricOncology);
		pediatrics.addChildren(pedMedicine);
		
		pediatrics = manager.updateElement(pediatrics);
		
		assertEquals(3, pediatrics.getChildren().size());
		
		pediatricCardiology = manager.getElementById(pediatricCardiology.
				getId());
		pediatricOncology = manager.getElementById(pediatricOncology.getId());
		criticalCare = manager.getElementById(criticalCare.getId());
		
		assertEquals("Pediatrics", pediatricCardiology.getParent());
		assertEquals("Pediatrics", pediatricOncology.getParent());
		assertEquals("Pediatrics", criticalCare.getParent());
		
		primaryCare = manager.cut(primaryCare, pediatrics);
		
		pediatrics = manager.getElementById(pediatrics.getId());
		
		assertEquals(4, pediatrics.getChildren().size());
		assertNotEquals("Medicine", primaryCare.getParent());
		assertEquals("Pediatrics", primaryCare.getParent());
		
		
		medicalOncology = manager.getElementById(medicalOncology.getId());
		radiationOncology = manager.getElementById(radiationOncology.getId());
		surgicalOncology = manager.getElementById(surgicalOncology.getId());
		
		medicalOncology.setParent(oncology);
		radiationOncology.setParent(oncology.getId());
		surgicalOncology.setParent(oncology.getId());
		
		medicalOncology = manager.updateElement(medicalOncology);
		radiationOncology = manager.updateElement(radiationOncology);
		surgicalOncology = manager.updateElement(surgicalOncology);
		
		oncology = manager.getElementById(oncology.getId());
		
		/*
		 * particularly, this element had its parent intentionally defined in a
		 * wrong way so that this element is moved to the root.
		 */
		assertEquals(sessionId, medicalOncology.getParent());
		
		assertEquals("Oncology", radiationOncology.getParent());
		assertEquals("Oncology", surgicalOncology.getParent());
		
		/*
		 * particularly, this element had its parent intentionally defined in a
		 * wrong way so that this element is moved to the root.
		 */
		assertTrue(manager.containsElement(sessionId, medicalOncology.getId()));
		
		assertTrue(manager.containsElement(oncology, radiationOncology));
		assertTrue(manager.containsElement(oncology, surgicalOncology));
		
		
		pathology = manager.getElementById(pathology.getId());
		anatomicalPathology = manager.getElementById(anatomicalPathology.
				getId());
		clinicalPathology = manager.getElementById(clinicalPathology.getId());
		
		assertEquals("Medicine", pathology.getParent());
		assertEquals(0, pathology.getChildren().size());
		assertEquals("Medicine", anatomicalPathology.getParent());
		assertEquals("Medicine", clinicalPathology.getParent());
		
		anatomicalPathology = manager.cut(anatomicalPathology.getId(),
				pathology.getId());
		clinicalPathology = manager.cut(clinicalPathology.getId(),
				pathology.getId());
		
		pathology = manager.getElementById(pathology.getId());
		
		assertEquals(2, pathology.getChildren().size());
		assertEquals("Pathology", anatomicalPathology.getParent());
		assertEquals("Pathology", clinicalPathology.getParent());
		
		assertTrue(manager.containsElement(pathology.getId(),
				anatomicalPathology.getId()));
		assertTrue(manager.containsElement(pathology.getId(),
				clinicalPathology.getId()));
		
		computerScience = manager.getElementById("Computer Science");
		theoretical = manager.getElementById("Theoretical");
		theoryOfComputation = manager.getElementById("Theory of Computation");
		codingTheory = manager.getElementById("Coding Theory");
		dataStructures = manager.getElementById("Data Structures");
		algorithms = manager.getElementById("Algorithms");
		
		assertEquals(sessionId, computerScience.getParent());
		assertEquals(0, theoretical.getChildren().size());
		assertEquals(computerScience.getId(), theoretical.getParent());
		assertEquals(computerScience.getId(), theoryOfComputation.getParent());
		assertEquals(computerScience.getId(), codingTheory.getParent());
		assertEquals(computerScience.getId(), dataStructures.getParent());
		assertEquals(computerScience.getId(), algorithms.getParent());
		
		assertTrue(manager.containsElement(computerScience, theoretical));
		assertTrue(manager.containsElement(computerScience, theoryOfComputation));
		assertTrue(manager.containsElement(computerScience, codingTheory));
		assertTrue(manager.containsElement(computerScience, dataStructures));
		assertTrue(manager.containsElement(computerScience, algorithms));
		
		List<Element<Science>> theoreticalList = new ArrayList<>();
		theoreticalList.add(theoryOfComputation);
		theoreticalList.add(codingTheory);
		theoreticalList.add(dataStructures);
		theoreticalList.add(algorithms);
		
		theoretical.addChildren(theoreticalList);
		
		assertEquals(17, computerScience.getChildren().size());
		
		theoretical = manager.updateElement(theoretical);
		
		computerScience = manager.getElementById(computerScience.getId());
		
		assertEquals(13, computerScience.getChildren().size());
		
		assertEquals(computerScience.getId(), theoretical.getParent());
		assertEquals(4, theoretical.getChildren().size());
		assertEquals(theoretical.getId(), theoryOfComputation.getParent());
		assertEquals(theoretical.getId(), codingTheory.getParent());
		assertEquals(theoretical.getId(), dataStructures.getParent());
		assertEquals(theoretical.getId(), algorithms.getParent());
		
		assertTrue(manager.containsElement(theoretical, theoryOfComputation));
		assertTrue(manager.containsElement(theoretical, codingTheory));
		assertTrue(manager.containsElement(theoretical, dataStructures));
		assertTrue(manager.containsElement(theoretical, algorithms));
		
		try {
			Element<Science> root = manager.root();
			manager.copy(theoretical, root);
		} catch (TreeException e) {
			String error2 = "Duplicated ID.";
			assertEquals(error2, e.getMessage());
		}
		
		computerScience = manager.getElementById("Computer Science");
		computerSystems = manager.getElementById("Computer Systems");
		computerArchitecture = manager.getElementById("Computer Architecture");
		computerEngineering = manager.getElementById("Computer Engineering");
		computerNetwork = manager.getElementById("Computer Network");
		databases = manager.getElementById("Databases");
		
		assertEquals(0, computerSystems.getChildren().size());
		assertEquals(computerScience.getId(), computerSystems.getParent());
		assertEquals(computerScience.getId(), computerArchitecture.getParent());
		assertEquals(computerScience.getId(), computerEngineering.getParent());
		assertEquals(computerScience.getId(), computerNetwork.getParent());
		assertEquals(computerScience.getId(), databases.getParent());
		
		assertTrue(manager.containsElement(computerScience, computerSystems));
		assertTrue(manager.containsElement(computerScience, computerArchitecture));
		assertTrue(manager.containsElement(computerScience, computerEngineering));
		assertTrue(manager.containsElement(computerScience, computerNetwork));
		assertTrue(manager.containsElement(computerScience, databases));
		
		computerArchitecture = manager.cut(computerArchitecture,
				computerSystems);
		computerEngineering = manager.cut(computerEngineering,
				computerSystems);
		computerNetwork = manager.cut(computerNetwork, computerSystems);
		databases = manager.cut(databases, computerSystems);
		
		computerSystems = manager.getElementById("Computer Systems");
		
		assertEquals(4, computerSystems.getChildren().size());
		assertEquals(computerSystems.getId(), computerArchitecture.getParent());
		assertEquals(computerSystems.getId(), computerEngineering.getParent());
		assertEquals(computerSystems.getId(), computerNetwork.getParent());
		assertEquals(computerSystems.getId(), databases.getParent());
		
		assertTrue(manager.containsElement(computerSystems, computerArchitecture));
		assertTrue(manager.containsElement(computerSystems, computerEngineering));
		assertTrue(manager.containsElement(computerSystems, computerNetwork));
		assertTrue(manager.containsElement(computerSystems, databases));
		
		assertEquals("ATTACHED", computerSystems.lifecycle());
		
		computerSystems.removeChild(computerEngineering);
		
		assertEquals("DETACHED", computerSystems.lifecycle());
		assertEquals(3, computerSystems.getChildren().size());
		
		computerSystems = manager.getElementById(computerSystems.getId());
		computerEngineering = manager.getElementById(computerEngineering.getId());
		
		assertEquals("ATTACHED", computerSystems.lifecycle());
		assertEquals(4, computerSystems.getChildren().size());

		assertTrue(manager.containsElement(computerSystems, computerEngineering));
		
		computerSystems.removeChild(computerEngineering);
		
		/*
		 * Detached computerSystems element.
		 */
		assertFalse(manager.containsElement(computerSystems, computerEngineering));
		
		assertTrue(manager.containsElement(computerSystems.getId(),
				computerEngineering.getId()));
		
		computerSystems = manager.updateElement(computerSystems);
		assertEquals(3, computerSystems.getChildren().size());
		assertFalse(manager.containsElement(computerSystems.getId(),
				computerEngineering.getId()));
		
		try {
			computerEngineering = manager.persistElement(computerEngineering);
		} catch (TreeException e) {
			String error3 = "No possible to persist the element. Invalid"
					+ " lifecycle state.";
			assertEquals(error3, e.getMessage());
		}
		
	}
}
