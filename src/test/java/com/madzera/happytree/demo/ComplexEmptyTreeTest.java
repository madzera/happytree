package com.madzera.happytree.demo;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeSession;
import com.madzera.happytree.TreeTransaction;
import com.madzera.happytree.core.HappyTree;
import com.madzera.happytree.demo.model.Science;
import com.madzera.happytree.exception.TreeException;

/**
 * Create a new empty tree.
 * 
 * <p>The purpose of this test is build manually a new empty tree and handle it.
 * </p>
 */
public class ComplexEmptyTreeTest extends CommonDemoTest {

	Science medicineObj = new Science("Medicine");
	Science pediatricsObj = new Science("Pediatrics");
	Science primaryCareObj = new Science("Primary Care");
	Science pediatricOncologyObj = new Science("Pediatric Oncology");
	Science criticalCareObj = new Science("Critical Care");
	Science pediatricCardiologyObj = new Science("Pediatric Cardiology");
	Science oncologyObj = new Science("Oncology");
	Science medicalOncologyObj = new Science("Medical Oncology");
	Science radiationOncologyObj = new Science("Radiation Oncology");
	Science surgicalOncologyObj = new Science("Surgical Oncology");
	Science pathologyObj = new Science("Pathology");
	Science anatomicalPathologyObj = new Science("Anatomical Pathology");
	Science clinicalPathologyObj = new Science("Clinical Pathology");
	
	Science computerScienceObj = new Science("Computer Science");
	Science theoreticalObj = new Science("Theoretical");
	Science theoryOfComputationObj = new Science("Theory of Computation");
	Science codingTheoryObj = new Science("Coding Theory");
	Science dataStructuresObj = new Science("Data Structures");
	Science algorithmsObj = new Science("Algorithms");
	Science computerSystemsObj = new Science("Computer Systems");
	Science computerArchitectureObj = new Science("Computer Architecture");
	Science computerEngineeringObj = new Science("Computer Engineering");
	Science computerNetworkObj = new Science("Computer Network");
	Science databasesObj = new Science("Databases");
	Science securityObj = new Science("Security");
	Science cryptographyObj = new Science("Cryptography");
	Science concurrentObj = new Science("Concurrent");
	Science computerApplicationsObj = new Science("Computer Applications");
	Science computerGraphicsObj = new Science("Computer Graphics");
	Science aiObj = new Science("Artificial Intelligence");
	Science softwareEngineeringObj = new Science("Software Engineering");
	
	
	@Test
	public void execute() throws TreeException {
		final String sessionId = "newEmptyTree";
		final String clonedSessionId = "clonedTree";
		
		TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		
		transaction.initializeSession(sessionId, Science.class);
		
		assertNotNull(transaction);
		
		Element<Science> medicine = manager.createElement("Medicine", null,
				medicineObj);
		Element<Science> pediatrics = manager.createElement("Pediatrics", null,
				pediatricsObj);
		Element<Science> primaryCare = manager.createElement("Primary Care",
				null, primaryCareObj);
		Element<Science> pediatricOncology = manager.createElement("Pediatric Oncology", null,
				pediatricOncologyObj);
		Element<Science> criticalCare = manager.createElement("Critical Care", null,
				criticalCareObj);
		Element<Science> pediatricCardiology = manager.createElement("Pediatric Cardiology", null,
				pediatricCardiologyObj);
		Element<Science> oncology = manager.createElement("Oncology", null,
				oncologyObj);
		Element<Science> medicalOncology = manager.createElement("Medical Oncology", null,
				medicalOncologyObj);
		Element<Science> radiationOncology = manager.createElement("Radiation Oncology", null,
				radiationOncologyObj);
		Element<Science> surgicalOncology = manager.createElement("Surgical Oncology", null,
				surgicalOncologyObj);
		Element<Science> pathology = manager.createElement("Pathology", null,
				pathologyObj);
		Element<Science> anatomicalPathology = manager.createElement("Anatomical Pathology", null,
				anatomicalPathologyObj);
		Element<Science> clinicalPathology = manager.createElement("Clinical Pathology", null,
				clinicalPathologyObj);
		
		Element<Science> computerScience = manager.createElement("Computer Science", null,
				computerScienceObj);
		Element<Science> theoretical = manager.createElement("Theoretical", null,
				theoreticalObj);
		Element<Science> theoryOfComputation = manager.createElement("Theory of Computation", null,
				theoryOfComputationObj);
		Element<Science> codingTheory = manager.createElement("Coding Theory", null,
				codingTheoryObj);
		Element<Science> dataStructures = manager.createElement("Data Structures", null,
				dataStructuresObj);
		Element<Science> algorithms = manager.createElement("Algorithms", null,
				algorithmsObj);
		Element<Science> computerSystems = manager.createElement("Computer Systems", null,
				computerSystemsObj);
		Element<Science> computerArchitecture = manager.createElement("Computer Architecture", null,
				computerArchitectureObj);
		Element<Science> computerEngineering = manager.createElement("Computer Engineering", null,
				computerEngineeringObj);
		Element<Science> computerNetwork = manager.createElement("Computer Network", null,
				computerNetworkObj);
		Element<Science> databases = manager.createElement("Databases", null,
				databasesObj);
		Element<Science> security = manager.createElement("Security", null,
				securityObj);
		Element<Science> cryptography = manager.createElement("Cryptography", null,
				cryptographyObj);
		Element<Science> concurrent = manager.createElement("Concurrent", null,
				concurrentObj);
		Element<Science> computerApplications = manager.createElement("Computer Applications", null,
				computerApplicationsObj);
		Element<Science> computerGraphics = manager.createElement("Computer Graphics", null,
				computerGraphicsObj);
		Element<Science> ai = manager.createElement("Artificial Intelligence", null,
				aiObj);
		Element<Science> softwareEngineering = manager.createElement("Software Engineering", null,
				softwareEngineeringObj);
		
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
		
		isEquals("NOT_EXISTED", medicine.lifecycle());
		isEquals("NOT_EXISTED", computerScience.lifecycle());
		
		manager.persistElement(medicine);
		manager.persistElement(computerScience);
		
		medicine = manager.getElementById("Medicine");
		computerScience = manager.getElementById("Computer Science");
		
		isEquals("ATTACHED", medicine.lifecycle());
		isEquals("ATTACHED", computerScience.lifecycle());
		
		isEquals(12, medicine.getChildren().size());
		isEquals(17, computerScience.getChildren().size());
		
		for (Element<Science> science : medicine.getChildren()) {
			isEquals("ATTACHED", science.lifecycle());
		}
		for (Element<Science> science : computerScience.getChildren()) {
			isEquals("ATTACHED", science.lifecycle());
		}
		
		pediatrics = medicine.getElementById("Pediatrics");
		oncology = medicine.getElementById("Oncology");
		pathology = medicine.getElementById("Pathology");
		
		pediatrics.addChild(pediatricOncology);
		pediatrics.addChild(pediatricCardiology);
		pediatrics.addChild(criticalCare);
		pediatrics.addChild(primaryCare);
		
		isEquals("DETACHED", pediatrics.lifecycle());
		isEquals("NOT_EXISTED", pediatricOncology.lifecycle());
		isEquals("NOT_EXISTED", pediatricCardiology.lifecycle());
		isEquals("NOT_EXISTED", criticalCare.lifecycle());
		isEquals("NOT_EXISTED", primaryCare.lifecycle());
		
		String error = "No possible to update the element. Invalid lifecycle"
				+ " state.";
		/*
		 * Trying update the sub element instead of the parent element which
		 * added the sub element (child).
		 */
		try {
			criticalCare = manager.updateElement(criticalCare);
		} catch (TreeException e) {
			isEquals(error, e.getMessage());
		}
		isEquals("NOT_EXISTED", criticalCare.lifecycle());

		/*
		 * Trying to update an element which all children are NOT_EXISTED will
		 * throw an exception because this is only possible update elements with
		 * DETACHED state. This is necessary to persist its children first
		 * before updating.
		 */
		try {
			pediatrics = manager.updateElement(pediatrics);
		} catch (TreeException e) {
			isEquals(error, e.getMessage());
		}
		
		/*
		 * Impossible to persist the Critical Care element because it was
		 * already persisted before when Medicine and its children were added
		 * to the tree. Duplicate ID.
		 */
		try {
			manager.persistElement(criticalCare);
		} catch (TreeException e) {
			String error2 = "Duplicate ID.";
			isEquals(error2, e.getMessage());
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
		
		isNotNull(pediatricCardiology);
		isNotNull(pediatricOncology);
		isNotNull(criticalCare);
		isNotNull(primaryCare);
		
		isEquals(0, pediatrics.getChildren().size());
		
		List<Element<Science>> pedMedicine = new ArrayList<>();
		pedMedicine.add(criticalCare);
		pediatrics.addChild(pediatricCardiology);
		pediatrics.addChild(pediatricOncology);
		pediatrics.addChildren(pedMedicine);
		
		pediatrics = manager.updateElement(pediatrics);
		
		isEquals(3, pediatrics.getChildren().size());
		
		pediatricCardiology = manager.getElementById(pediatricCardiology.
				getId());
		pediatricOncology = manager.getElementById(pediatricOncology.getId());
		criticalCare = manager.getElementById(criticalCare.getId());
		
		isEquals("Pediatrics", pediatricCardiology.getParent());
		isEquals("Pediatrics", pediatricOncology.getParent());
		isEquals("Pediatrics", criticalCare.getParent());
		
		primaryCare = manager.cut(primaryCare, pediatrics);
		
		pediatrics = manager.getElementById(pediatrics.getId());
		
		isEquals(4, pediatrics.getChildren().size());
		isNotEquals("Medicine", primaryCare.getParent());
		isEquals("Pediatrics", primaryCare.getParent());
		
		
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
		isEquals(sessionId, medicalOncology.getParent());
		
		isEquals("Oncology", radiationOncology.getParent());
		isEquals("Oncology", surgicalOncology.getParent());
		
		/*
		 * particularly, this element had its parent intentionally defined in a
		 * wrong way so that this element is moved to the root.
		 */
		isTrue(manager.containsElement(sessionId, medicalOncology.getId()));
		
		isTrue(manager.containsElement(oncology, radiationOncology));
		isTrue(manager.containsElement(oncology, surgicalOncology));
		
		
		pathology = manager.getElementById(pathology.getId());
		anatomicalPathology = manager.getElementById(anatomicalPathology.
				getId());
		clinicalPathology = manager.getElementById(clinicalPathology.getId());
		
		isEquals("Medicine", pathology.getParent());
		isEquals(0, pathology.getChildren().size());
		isEquals("Medicine", anatomicalPathology.getParent());
		isEquals("Medicine", clinicalPathology.getParent());
		
		anatomicalPathology = manager.cut(anatomicalPathology.getId(),
				pathology.getId());
		clinicalPathology = manager.cut(clinicalPathology.getId(),
				pathology.getId());
		
		pathology = manager.getElementById(pathology.getId());
		
		isEquals(2, pathology.getChildren().size());
		isEquals("Pathology", anatomicalPathology.getParent());
		isEquals("Pathology", clinicalPathology.getParent());
		
		isTrue(manager.containsElement(pathology.getId(),
				anatomicalPathology.getId()));
		isTrue(manager.containsElement(pathology.getId(),
				clinicalPathology.getId()));
		
		computerScience = manager.getElementById("Computer Science");
		theoretical = manager.getElementById("Theoretical");
		theoryOfComputation = manager.getElementById("Theory of Computation");
		codingTheory = manager.getElementById("Coding Theory");
		dataStructures = manager.getElementById("Data Structures");
		algorithms = manager.getElementById("Algorithms");
		
		isEquals(sessionId, computerScience.getParent());
		isEquals(0, theoretical.getChildren().size());
		isEquals(computerScience.getId(), theoretical.getParent());
		isEquals(computerScience.getId(), theoryOfComputation.getParent());
		isEquals(computerScience.getId(), codingTheory.getParent());
		isEquals(computerScience.getId(), dataStructures.getParent());
		isEquals(computerScience.getId(), algorithms.getParent());
		
		isTrue(manager.containsElement(computerScience, theoretical));
		isTrue(manager.containsElement(computerScience, theoryOfComputation));
		isTrue(manager.containsElement(computerScience, codingTheory));
		isTrue(manager.containsElement(computerScience, dataStructures));
		isTrue(manager.containsElement(computerScience, algorithms));
		
		List<Element<Science>> theoreticalList = new ArrayList<>();
		theoreticalList.add(theoryOfComputation);
		theoreticalList.add(codingTheory);
		theoreticalList.add(dataStructures);
		theoreticalList.add(algorithms);
		
		theoretical.addChildren(theoreticalList);
		
		isEquals(17, computerScience.getChildren().size());
		
		theoretical = manager.updateElement(theoretical);
		
		computerScience = manager.getElementById(computerScience.getId());
		
		isEquals(13, computerScience.getChildren().size());
		
		isEquals(computerScience.getId(), theoretical.getParent());
		isEquals(4, theoretical.getChildren().size());
		isEquals(theoretical.getId(), theoryOfComputation.getParent());
		isEquals(theoretical.getId(), codingTheory.getParent());
		isEquals(theoretical.getId(), dataStructures.getParent());
		isEquals(theoretical.getId(), algorithms.getParent());
		
		isTrue(manager.containsElement(theoretical, theoryOfComputation));
		isTrue(manager.containsElement(theoretical, codingTheory));
		isTrue(manager.containsElement(theoretical, dataStructures));
		isTrue(manager.containsElement(theoretical, algorithms));
		
		try {
			Element<Science> root = manager.root();
			manager.copy(theoretical, root);
		} catch (TreeException e) {
			String error2 = "Duplicate ID.";
			isEquals(error2, e.getMessage());
		}
		
		computerScience = manager.getElementById("Computer Science");
		computerSystems = manager.getElementById("Computer Systems");
		computerArchitecture = manager.getElementById("Computer Architecture");
		computerEngineering = manager.getElementById("Computer Engineering");
		computerNetwork = manager.getElementById("Computer Network");
		databases = manager.getElementById("Databases");
		
		isEquals(0, computerSystems.getChildren().size());
		isEquals(computerScience.getId(), computerSystems.getParent());
		isEquals(computerScience.getId(), computerArchitecture.getParent());
		isEquals(computerScience.getId(), computerEngineering.getParent());
		isEquals(computerScience.getId(), computerNetwork.getParent());
		isEquals(computerScience.getId(), databases.getParent());
		
		isTrue(manager.containsElement(computerScience, computerSystems));
		isTrue(manager.containsElement(computerScience, computerArchitecture));
		isTrue(manager.containsElement(computerScience, computerEngineering));
		isTrue(manager.containsElement(computerScience, computerNetwork));
		isTrue(manager.containsElement(computerScience, databases));
		
		computerArchitecture = manager.cut(computerArchitecture,
				computerSystems);
		computerEngineering = manager.cut(computerEngineering,
				computerSystems);
		computerNetwork = manager.cut(computerNetwork, computerSystems);
		databases = manager.cut(databases, computerSystems);
		
		computerSystems = manager.getElementById("Computer Systems");
		
		isEquals(4, computerSystems.getChildren().size());
		isEquals(computerSystems.getId(), computerArchitecture.getParent());
		isEquals(computerSystems.getId(), computerEngineering.getParent());
		isEquals(computerSystems.getId(), computerNetwork.getParent());
		isEquals(computerSystems.getId(), databases.getParent());
		
		isTrue(manager.containsElement(computerSystems, computerArchitecture));
		isTrue(manager.containsElement(computerSystems, computerEngineering));
		isTrue(manager.containsElement(computerSystems, computerNetwork));
		isTrue(manager.containsElement(computerSystems, databases));
		
		isEquals("ATTACHED", computerSystems.lifecycle());
		
		computerSystems.removeChild(computerEngineering);
		
		isEquals("DETACHED", computerSystems.lifecycle());
		isEquals(3, computerSystems.getChildren().size());
		
		computerSystems = manager.getElementById(computerSystems.getId());
		computerEngineering = manager.getElementById(computerEngineering.getId());
		
		isEquals("ATTACHED", computerSystems.lifecycle());
		isEquals(4, computerSystems.getChildren().size());

		isTrue(manager.containsElement(computerSystems, computerEngineering));
		
		computerSystems.removeChild(computerEngineering);
		
		/*
		 * Detached computerSystems element.
		 */
		isFalse(manager.containsElement(computerSystems, computerEngineering));
		
		isTrue(manager.containsElement(computerSystems.getId(),
				computerEngineering.getId()));
		
		computerSystems = manager.updateElement(computerSystems);
		isEquals(3, computerSystems.getChildren().size());
		isFalse(manager.containsElement(computerSystems.getId(),
				computerEngineering.getId()));
		
		try {
			manager.persistElement(computerEngineering);
		} catch (TreeException e) {
			String error3 = "No possible to persist the element. Invalid"
					+ " lifecycle state.";
			isEquals(error3, e.getMessage());
		}
		
		computerScience = manager.getElementById("Computer Science");
		security = manager.getElementById("Security");
		cryptography = manager.getElementById("Cryptography");
		concurrent = manager.getElementById("Concurrent");
		
		isEquals(computerScience.getId(), computerSystems.getParent());
		isEquals(computerScience.getId(), security.getParent());
		isEquals(computerScience.getId(), cryptography.getParent());
		isEquals(computerScience.getId(), concurrent.getParent());
		
		security.setParent(computerSystems.getId());
		security = manager.updateElement(security);
		computerScience = manager.getElementById("Computer Science");
		computerSystems = manager.getElementById("Computer Systems");
		
		isNotEquals(computerScience.getId(), security.getParent());
		isEquals(computerSystems.getId(), security.getParent());
		isEquals(4, computerSystems.getChildren().size());
		isTrue(manager.containsElement(computerSystems, security));
		
		cryptography.setParent(computerSystems.getId());
		cryptography = manager.updateElement(cryptography);
		computerScience = manager.getElementById("Computer Science");
		computerSystems = manager.getElementById("Computer Systems");
		
		isNotEquals(computerScience.getId(), cryptography.getParent());
		isEquals(computerSystems.getId(), cryptography.getParent());
		isEquals(5, computerSystems.getChildren().size());
		isTrue(manager.containsElement(computerSystems, cryptography));
		
		concurrent.setParent(computerSystems.getId());
		concurrent = manager.updateElement(concurrent);
		computerScience = manager.getElementById("Computer Science");
		computerSystems = manager.getElementById("Computer Systems");
		
		isNotEquals(computerScience.getId(), concurrent.getParent());
		isEquals(computerSystems.getId(), concurrent.getParent());
		isEquals(6, computerSystems.getChildren().size());
		isTrue(manager.containsElement(computerSystems, concurrent));
		
		
		computerScience = manager.getElementById("Computer Science");
		computerApplications = manager.getElementById("Computer Applications");
		computerGraphics = manager.getElementById("Computer Graphics");
		ai = manager.getElementById("Artificial Intelligence");

		isEquals(0, computerApplications.getChildren().size());
		isEquals(computerScience.getId(), computerApplications.getParent());
		isEquals(computerScience.getId(), computerGraphics.getParent());
		isEquals(computerScience.getId(), ai.getParent());
		
		isNotNull(computerScience.getElementById(computerApplications.
				getId()));
		isEquals(6, computerScience.getChildren().size());
		
		manager.cut(computerApplications, computerScience);
		
		computerScience = manager.getElementById("Computer Science");
		computerApplications = manager.getElementById("Computer Applications");
		
		isEquals(0, computerApplications.getChildren().size());
		isEquals(computerScience.getId(), computerApplications.getParent());
		
		isEquals(6, computerScience.getChildren().size());
		isNotNull(computerScience.getElementById(computerApplications.getId()));
		isTrue(manager.containsElement(computerScience,
				computerApplications));
		
		computerGraphics = manager.cut(computerGraphics, computerApplications);
		
		computerScience = manager.getElementById("Computer Science");
		computerApplications = manager.getElementById("Computer Applications");
		
		isEquals(5, computerScience.getChildren().size());
		isEquals(1, computerApplications.getChildren().size());
		isEquals(computerApplications.getId(), computerGraphics.getParent());
		isTrue(manager.containsElement(computerApplications.getId(),
				computerGraphics.getId()));
		
		ai = manager.cut(ai, computerApplications);
		
		computerScience = manager.getElementById("Computer Science");
		computerApplications = manager.getElementById("Computer Applications");
		
		isEquals(4, computerScience.getChildren().size());
		isEquals(2, computerApplications.getChildren().size());
		isEquals(computerApplications.getId(), ai.getParent());
		isTrue(manager.containsElement(computerApplications.getId(),
				ai.getId()));
		
		ai.setParent(3333);
		List<Element<Science>> compAppsList = new ArrayList<>();
		compAppsList.add(ai);
		
		try {
			manager.removeElement(ai);
		} catch (TreeException e) {
			String error4 = "No possible to copy/cut/remove elements. Invalid"
					+ " lifecycle state.";
			isEquals(error4, e.getMessage());
		}
		
		computerApplications = manager.getElementById("Computer Applications");
		ai = manager.getElementById("Artificial Intelligence");
		
		isTrue(manager.containsElement(computerApplications.getId(),
				ai.getId()));
		isEquals(2, computerApplications.getChildren().size());
		
		compAppsList.clear();
		
		isEquals("ATTACHED", computerApplications.lifecycle());
		isEquals("ATTACHED", computerGraphics.lifecycle());
		
		compAppsList.add(ai);
		compAppsList.add(computerGraphics);
		
		computerApplications.removeChildren(compAppsList);
		
		isEquals("DETACHED", computerApplications.lifecycle());
		
		try {
			manager.persistElement(computerApplications);
		} catch (TreeException e) {
			String error5 = "No possible to persist the element. Invalid"
					+ " lifecycle state.";
			isEquals(error5, e.getMessage());
		}
		
		isEquals("DETACHED", computerGraphics.lifecycle());
		isEquals("DETACHED", ai.lifecycle());
		
		computerApplications = manager.getElementById("Computer Applications");
		computerGraphics = manager.getElementById("Computer Graphics");
		ai = manager.getElementById("Artificial Intelligence");
		
		isEquals("ATTACHED", computerApplications.lifecycle());
		isEquals("ATTACHED", computerGraphics.lifecycle());
		isEquals("ATTACHED", ai.lifecycle());
		
		compAppsList.clear();
		compAppsList.add(ai);
		compAppsList.add(computerGraphics);
		
		computerApplications.removeChildren(compAppsList);
		
		computerScience = manager.getElementById("Computer Science");
		ai = manager.getElementById("Artificial Intelligence");
		computerGraphics = manager.getElementById("Computer Graphics");
		
		isTrue(manager.containsElement(computerScience, ai));
		isTrue(manager.containsElement(computerScience, computerGraphics));
		
		computerApplications = manager.updateElement(computerApplications);
		
		computerScience = manager.getElementById("Computer Science");
		ai = manager.getElementById("Artificial Intelligence");
		computerGraphics = manager.getElementById("Computer Graphics");
		
		isEquals(0, computerApplications.getChildren().size());
		isNull(ai);
		isNull(computerGraphics);
		
		isFalse(manager.containsElement(computerApplications, ai));
		isFalse(manager.containsElement(computerApplications, computerGraphics));
		isFalse(manager.containsElement(computerScience, ai));
		isFalse(manager.containsElement(computerScience, computerGraphics));
		
		computerScience = manager.getElementById("Computer Science");
		softwareEngineering = manager.getElementById(softwareEngineering
				.getId());
		
		isEquals(4, computerScience.getChildren().size());
		isTrue(manager.containsElement(computerScience,	softwareEngineering));
		isEquals(computerScience.getId(), softwareEngineering.getParent());
		
		isEquals("ATTACHED", softwareEngineering.lifecycle());
		
		softwareEngineering = manager.removeElement(softwareEngineering);
		
		computerScience = manager.getElementById("Computer Science");
		
		isEquals("NOT_EXISTED", softwareEngineering.lifecycle());
		isEquals(3, computerScience.getChildren().size());
		isFalse(manager.containsElement(computerScience, softwareEngineering));
		isNotEquals(computerScience.getId(), softwareEngineering.getParent());
		
		isNull(transaction.sessionCheckout(clonedSessionId));
		isNotNull(transaction.sessionCheckout(sessionId));
		
		TreeSession clonedSession = transaction.cloneSession(sessionId,
				clonedSessionId);
		
		isNotNull(clonedSession);
		
		transaction.sessionCheckout(clonedSessionId);
		
		Element<Science> clonedMedicine = manager.getElementById(medicine
				.getId());
		
		Element<Science> clonedComputerScience = manager.getElementById(
				computerScience.getId());
		
		isNotNull(clonedMedicine);
		isNotNull(clonedComputerScience);
		
		isEquals(3, clonedMedicine.getChildren().size());
		isEquals(transaction.currentSession().getSessionId(),
				clonedMedicine.attachedTo().getSessionId());
		
		isEquals(3, clonedComputerScience.getChildren().size());
		isEquals(transaction.currentSession().getSessionId(),
				clonedComputerScience.attachedTo().getSessionId());
		
		transaction.sessionCheckout(sessionId);
		
		medicine = manager.getElementById("Medicine");
		
		isNotNull(medicine);
		isEquals(sessionId, medicine.attachedTo().getSessionId());
		
		transaction.deactivateSession(clonedSessionId);
		
		clonedSession = transaction.sessionCheckout(clonedSessionId);
		
		isNotNull(clonedSession);
		isFalse(clonedSession.isActive());
		
		TreeSession session = transaction.sessionCheckout(sessionId);
		
		isNotNull(session);
		
		transaction.destroySession(clonedSessionId);
		
		clonedSession = transaction.sessionCheckout(clonedSessionId);
		
		isNull(clonedSession);
	}
}
