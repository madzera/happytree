package com.madzera.happytree.core.atp;

import java.util.Collection;
import java.util.Map;

import com.madzera.happytree.Element;
import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeTransaction;
import com.madzera.happytree.annotation.Id;
import com.madzera.happytree.annotation.Parent;
import com.madzera.happytree.annotation.Tree;
import com.madzera.happytree.core.HappyTree;
import com.madzera.happytree.core.TreePipeline;
import com.madzera.happytree.exception.TreeException;

/**
 * This class is a helper to mock the ATP test, an extension of the unit tests.
 * The ATP is an internal package which its responsibility is to assemble the
 * tree given the provided input of a list of elements. The ATP is a package
 * that is not exposed to the API, so there is the necessity to expose it to the
 * external world for the test package be able to access it.
 * 
 * <p>An example is the post validation phase of the ATP, which is responsible
 * for just checking if the resulting tree is consistent, when theoretically the
 * execution will never reach the exceptions, or at least, it should not reach.
 * <p>
 * 
 * <p>Also, the purpose of this helper is to assist unit tests about coverage,
 * mainly in difficult scenarios to reach. <b>This class is not used by the API,
 * it is only for testing purpose.</b></p>
 */
public final class ATPTestHelper<T> extends ATPGenericPhase<Element<T>> {
	
    public void testPostValidationWithErrorDiffSizeElementList()
        throws TreeException {
        preparePostValidationDiffSizeElementList();
        this.run(pipelineMock);
	}
    
    public void testPostValidationWithErrorDiffWrappedParentNodes()
        throws TreeException {
        preparePostValidationDiffWrappedParentNodes();
        this.run(pipelineMock);
    }

    public void testPostValidationWithErrorDiffWrappedOwnNodes()
        throws TreeException {
        preparePostValidationDiffWrappedOwnNodes();
        this.run(pipelineMock);
    }

    @Override
    protected void run(TreePipeline pipeline) throws TreeException {
        PostValidation<TestATPNode> postValidation = new PostValidation<>();
        postValidation.run(pipelineMock);
    }

    private void preparePostValidationDiffSizeElementList()
        throws TreeException {
        Map<Object, Object> nodesMap = createHashMap();
        Collection<TestATPNode> nodesFirstList = createHashSet();
        Collection<TestATPNode> nodesSecondList = createHashSet();
        Collection<Element<TestATPNode>> mockedElements = createHashSet();

        nodesFirstList.add(new TestATPNode(0, null));
        nodesFirstList.add(new TestATPNode(1, 0));
        nodesFirstList.add(new TestATPNode(2, 0));
        nodesFirstList.add(new TestATPNode(3, 1));
        nodesFirstList.add(new TestATPNode(4, 1));
        nodesSecondList.add(new TestATPNode(5, 2));
        nodesSecondList.add(new TestATPNode(6, 2));
        
        TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		transaction.initializeSession("TEST", nodesSecondList);
        Element<TestATPNode> child5 = manager.getElementById(5);
        Element<TestATPNode> child6 = manager.getElementById(6);
        mockedElements.add(child5);
        mockedElements.add(child6);
        pipelineMock.addAttribute(ATPPipelineAttributes.ELEMENTS,
            mockedElements);

        nodesMap.put("TEST", nodesFirstList);
        pipelineMock.addAttribute(ATPPipelineAttributes.NODES_MAP, nodesMap);
    }

    private void preparePostValidationDiffWrappedParentNodes()
        throws TreeException {
        Map<Object, Object> nodesMap = createHashMap();
        Map<Object, Object> nodesParentMap = createHashMap();
        Collection<TestATPNode> nodes = createHashSet();
        Collection<TestATPNode> parentNodes = createHashSet();
        Collection<Element<TestATPNode>> mockedElements = createHashSet();

        nodes.add(new TestATPNode(0, null));
        nodes.add(new TestATPNode(1, 0));
        nodes.add(new TestATPNode(2, 1));

        parentNodes.add(new TestATPNode(350, null));
        parentNodes.add(new TestATPNode(45431, 350));
        parentNodes.add(new TestATPNode(43453, 350));
        
        TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		transaction.initializeSession("TEST", nodes);

        Element<TestATPNode> root = manager.root();
        Element<TestATPNode> child1 = manager.getElementById(1);
        Element<TestATPNode> child2 = manager.getElementById(2);

        mockedElements.add(root);
        mockedElements.add(child1);
        mockedElements.add(child2);
        pipelineMock.addAttribute(ATPPipelineAttributes.ELEMENTS,
            mockedElements);

        for (Element<TestATPNode> element : mockedElements) {
            nodesMap.put(element.getId(), element.unwrap());
            nodesParentMap.put(element.getId(), element.getParent());
        }

        pipelineMock.addAttribute(ATPPipelineAttributes.NODES_MAP, nodesMap);
        pipelineMock.addAttribute(ATPPipelineAttributes.NODES_PARENT_MAP,
            nodesMap);
    }

    @SuppressWarnings("unchecked")
    private void preparePostValidationDiffWrappedOwnNodes()
        throws TreeException {
        Map<Object, Object> nodesMap = createHashMap();
        Map<Object, Object> nodesParentMap = createHashMap();
        Collection<TestATPNode> nodes = createHashSet();
        Collection<TestATPNode> parentNodes = createHashSet();
        Collection<Element<TestATPNode>> mockedElements = createHashSet();

        nodes.add(new TestATPNode(0, null));
        nodes.add(new TestATPNode(1, 0));
        nodes.add(new TestATPNode(2, 1));

        parentNodes.add(new TestATPNode(0, null));
        
        TreeManager manager = HappyTree.createTreeManager();
		TreeTransaction transaction = manager.getTransaction();
		transaction.initializeSession("TEST", nodes);

        Element<TestATPNode> root = manager.root();
        Element<TestATPNode> child1 = manager.getElementById(1);
        Element<TestATPNode> child2 = manager.getElementById(2);

        mockedElements.add(root);
        mockedElements.add(child1);
        mockedElements.add(child2);
        pipelineMock.addAttribute(ATPPipelineAttributes.ELEMENTS,
            mockedElements);

        for (int index = 0; index < mockedElements.size(); index++) {
            Element<TestATPNode> element = mockedElements.toArray(
                new Element[mockedElements.size()])[index];
                
            nodesMap.put(element.getId(),
                new TestATPNode(0, null));
            nodesParentMap.put(element.getId(), element.getParent());
        }

        pipelineMock.addAttribute(ATPPipelineAttributes.NODES_MAP, nodesMap);
        pipelineMock.addAttribute(ATPPipelineAttributes.NODES_PARENT_MAP,
            nodesMap);
    }


    @Tree
    public class TestATPNode {
        @Id
        private Integer id;
        @Parent
        private Integer parentId;

        TestATPNode(Integer id, Integer parentId) {
            this.id = id;
            this.parentId = parentId;
        }


        public Integer getId() {
            return id;
        }
        public Integer getParentId() {
            return parentId;
        }
    }
}
