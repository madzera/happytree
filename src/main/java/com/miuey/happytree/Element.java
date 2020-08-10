package com.miuey.happytree;

import java.util.Collection;
import com.miuey.happytree.exception.TreeException;

/**
 * An <code>Element</code> represents a node within a tree. It consists of the
 * elementary unit of the HappyTree API, which is directly manipulated through
 * {@link TreeManager} interface. An element can move from one tree to another,
 * be removed, copied and even encapsulate any object within it, making this
 * encapsulated object hierarchically available within a tree structure.
 * 
 * <p>This interface is only responsible for manipulating the element itself and
 * its children only. Here, there is no direct relationship with the other
 * available interfaces, considering that, as an element corresponds to an
 * elementary unit, it is only possible to relate to itself and its children.
 * </p>
 * 
 * <p><b>An element must be within a tree session and can only be manipulated
 * directly by {@link TreeManager}. In order to be manipulated, the tree session
 * must be active.</b></p>
 * 
 * <p><b>Considering an element previously attached to some session, Each
 * subsequent manipulation from the perspective of the element itself will turn
 * its state to &quot;detached&quot;, thus requiring an update by invoking
 * {@link TreeManager#updateElement(Element)}.</b></p>
 * 
 * <p>An element contains the following characteristics:</p>
 * 
 * <table>
 * 	<th>Characteristics</th><th>Description</th>
 * 	<tr>
 * 		<td><code>id</code></td><td>The element identifier within the tree
 * 		(<b>must be unique within the tree</b>).</td>
 * 	</tr>
 * 	<tr>
 * 		<td><code>parentId</code></td><td>The parent element identifier within
 * 		the tree (if the parent element is not found or <code>null</code>
 * 		then this element will be moved to tree root level).</td>
 * 	</tr>
 * 	<tr>
 * 		<td><code>children</code></td><td>The children list of the element.</td>
 * 	</tr>
 * 	<tr>
 * 		<td><code>wrappedObject</code></td><td>The encapsulated object.</td>
 * 	</tr>
 * 	<tr>
 * 		<td><code>sessionId</code></td><td>The session identifier which the
 * 		element	belongs.</td>
 * 	</tr>
 * 	<tr>
 * 		<td><code>attached</code></td><td>A flag that indicates whether the
 * 		element is attached in the tree or not.</td>
 * 	</tr>
 * </table>
 * 
 * <p>The relationship between <code>id</code> and <code>parentId</code> in the
 * tree composition represents the heart of the HappyTree API. The tree is
 * assembled strictly by this relation, of which a <code>parentId</code> of an
 * element references the <code>id</code> of the parent element. Therefore, an
 * <code>id</code> of a element can never be <code>null</code> and if a
 * <code>parentId</code> of an element is <code>null</code> or not found, that
 * same element is moved to the root level of the tree.</p>
 * 
 * <p>As stated above, an element cannot have null <code>id</code>, but this
 * only applies when an element is going to be inserted/updated within a tree.
 * This does not apply to detached tree elements, thus making this interface
 * free to handle or throw {@link TreeException}.</p>
 * 
 * <p>When getting an element is within a tree, the element itself and all its
 * children are returned, but the same does not occur when climbing the
 * hierarchy, that is, returning the element parent, grandfather, etc. Because
 * if the parent element were returned, the entire tree would also be returned
 * in the {@link TreeManager#getElementById(Object)} method call, which would
 * not make sense.</p>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 * 
 * @see {@link TreeTransaction}
 * 
 * @version %I%, %G%
 *
 * @param <T> the class type of the source wrapped object that will be
 * encapsulated into the this <code>Element</code>
 */
public interface Element<T> {
	
	/**
	 * Get the element identifier.
	 * 
	 * <p><b>This identifier is unique within the tree session when it is
	 * attached to the tree.</b></p>
	 * 
	 * @return the element identifier
	 */
	public Object getId();
	
	/**
	 * Set the element identifier.
	 * 
	 * <p><b>The <code>id</code> must be unique and <code>not-null</code> when
	 * attaching it to the tree.</b></p>
	 * 
	 * <p>These criteria are only for when the element is attached to the tree.
	 * </p>
	 * 
	 * @param id the element identifier to be set
	 */
	public void setId(Object id);
	
	/**
	 * Get the parent identifier reference of this element.
	 * 
	 * <p>If the parent reference is <code>null</code> or references a
	 * non-existing element, then it is certain that this element is in the root
	 * level of the tree when it is get directly from the tree.</p>
	 * 
	 * @return the parent element identifier
	 */
	public Object getParent();
	
	/**
	 * Set the parent identifier reference of this element.
	 * 
	 * <p>If the <code>parent</code> reference is <code>null</code> or
	 * references a non-existing element, then it is certain that this element
	 * will be moved to the root level of the tree when it is attached in the
	 * tree.</p>
	 * 
	 * @param parent the parent element identifier reference to be set
	 */
	public void setParent(Object parent);
	
	/**
	 * Get all the children elements from the current element. This includes all
	 * elements within the child elements recursively.
	 * 
	 * @return all children of the current element
	 */
	public Collection<Element<T>> getChildren();
	
	/**
	 * Add a new child element into the current element.
	 * 
	 * <p>If the <code>child</code> element has inside it children, then they
	 * will be added too.</p>
	 * 
	 * @param child the element to be added as child
	 * 
	 */
	public void addChild(Element<T> child);
	
	public Element<T> getElementById(Object id);
	
	/**
	 * Add a list of children to be concatenated to the current children list.
	 * 
	 * <p>If each element within the <code>children</code> list contains more
	 * children within it recursively, they will also be added</p>
	 * 
	 * @param children the children list to be concatenated to the current
	 * children list
	 * 
	 */
	public void addChildren(Collection<Element<T>> children);
	
	/**
	 * Remove a collection of elements inside of this element.
	 * 
	 * <p>The element references are removed if they are found in this current 
	 * element children list. When an element is removed, all of its children as
	 * well as the elements below the hierarchy are also removed, recursively.
	 * </p>
	 * 
	 * @param children the list of elements to be removed
	 */
	public void removeChildren(Collection<Element<T>> children);
	
	/**
	 * Remove the <code>child</code> from the children list of the current
	 * element.
	 * 
	 * <p>The <code>child</code> element reference is removed if he is found in
	 * this current element children list. When an element is removed, all of
	 * its children as well as the elements below the hierarchy are also
	 * removed, recursively.</p>
	 * 
	 * @param child the element to be removed
	 */
	public void removeChild(Element<T> child);
	
	/**
	 * Remove the element from the children list of the current element by
	 * <code>id</code>.
	 * 
	 * <p>First, a search is made (by invoking internally the 
	 * {@link TreeManager#getElementById(Object)}) to get the element by the
	 * corresponding <code>id</code>. If it exists, then the element and all of
	 * its children are removed.</p>
	 * 
	 * @param id the element to be removed
	 */
	public void removeChild(Object id);
	
	/**
	 * Encapsulate any object within the element, as long as this object has the
	 * same type as other objects that were encapsulated within the same tree
	 * session.
	 * 
	 * <p>As an element conceptually represents a <i>node</i> within a tree, the
	 * encapsulated object would be simulating precisely its position within the
	 * tree, being encapsulated by the <i>node</i> in question, which represents
	 * the object of this class, the <code>Element</code>.</p>
	 * 
	 * <p>The object itself can be wrapped at two different times:</p>
	 * <p>
	 * 	<ol>
	 * 		<li>Creating a new empty tree session;</li>
	 * 		<li>Create a tree session from the <b>API Transformation Process
	 * 		</b>.</li>
	 * 	</ol>
	 * </p>
	 * 
	 * <p>The first one would be to create an empty tree. In this way, the
	 * encapsulated object is determined after the tree is ready, invoking this
	 * method. This includes the choice of not determining, making it with the
	 * <code>null</code> value.</p>
	 * 
	 * <p>The last one happens in the <b>API Transformation Process</b>, that
	 * is, when the moment of initialization, before the tree is created, the
	 * (in {@link TreeTransaction#initializeSession(String, Collection)})
	 * transforms a previous linear structure into a real hierarchical
	 * structure of a tree.</p>
	 * 
	 * <p>Therefore, each object of this linear structure would be represented
	 * precisely by this encapsulated object. In this case, during the
	 * <b>API Transformation Process</b> life cycle, the object will be
	 * transformed, managed and automatically encapsulated within the own
	 * element.</p>
	 * 
	 * <p>There are some requirements for this object to be encapsulated when
	 * initializing a session from the API Transformation Process:</p>
	 * <p>
	 * 	<ul>
	 * 		<li>The class of this object must be annotated by {@literal @Tree};
	 * 		</li>
	 * 		<li>The class of this object must be annotated by {@literal @Parent}
	 * 		;</li>
	 * 		<li>The class of this object must be annotated by {@literal @Id};
	 * 		</li>
	 * 		<li>The {@literal @Id} cannot be <code>null</code>;
	 * 		</li>
	 * 		<li>The {@literal @Id} and {@literal @Parent} must be of the same
	 * 		type.</li>
	 * 	</ul>
	 * </p>
	 * 
	 * <p>Those requirements are only applied when the session was initialized
	 * by API Transformation Process or when the element itself is ready to be
	 * persisted/updated.</p>
	 * 
	 * @param object the annotated object to be encapsulated within the element
	 * 
	 * @throws TreeException when one of the above requirements is not met
	 */
	public void wrap(T object) throws TreeException;
	
	/**
	 * Get the previously encapsulated object within the element itself.
	 * 
	 * <p>As an element conceptually represents a <i>node</i> within a tree, the
	 * encapsulated object would be simulating precisely its position within the
	 * tree, being encapsulated by the <i>node</i> in question, which represents
	 * the object of this class, the <code>Element</code>.</p>
	 * 
	 * <p>The object itself can be wrapped at two different times:</p>
	 * <p>
	 * 	<ol>
	 * 		<li>Creating a new empty tree session;</li>
	 * 		<li>Create a tree session from the <b>API Transformation Process
	 * 		</b>.</li>
	 * 	</ol>
	 * </p>
	 * 
	 * <p>The first one would be to create an empty tree. In this way, the
	 * encapsulated object is determined after the tree is ready, invoking this
	 * method. This includes the choice of not determining, making it with the
	 * <code>null</code> value.</p>
	 * 
	 * <p>Therefore, when there is no encapsulated object within the element,
	 * this method simply returns <code>null</code>, since it is not mandatory
	 * for an element to contain an encapsulated object within it.</p>
	 * 
	 * <p>The last one happens in the <b>API Transformation Process</b>, that
	 * is, when the moment of initialization, before the tree is created, the
	 * (in {@link TreeTransaction#initializeSession(String, Collection)})
	 * transforms a previous linear structure into a real hierarchical
	 * structure of a tree.</p>
	 * 
	 * <p>Therefore, each object of this linear structure would be represented
	 * precisely by this encapsulated object. In this case, during the
	 * <b>API Transformation Process</b> life cycle, the object will be
	 * transformed, managed and automatically encapsulated within the own
	 * element.</p>
	 * 
	 * @return the instance of an encapsulated object with the <code>T</code>
	 */
	public T unwrap();
	
	/**
	 * Show up the session identifier which this element belongs in.
	 * 
	 * <p>If an <code>Element</code> object has not been attached to a session,
	 * by invoking {@link TreeManager#persistElement(Element)} then it has no
	 * session. Therefore, in this case, this method returns <code>null</code>.
	 * </p>
	 * 
	 * <p>An element can have a session even if it is detached from this
	 * session. It occurs when this element is got by invoking 
	 * {@link TreeManager#getElementById(Object)} and it is changed after that.
	 * So, in this case, the element is not synchronized with its session,
	 * becoming it as <i>detached</i>, but this element has a session because it
	 * was added to the tree previously.</p>
	 * 
	 * <p><b>Be sure that before invoking this, the element is previously
	 * persisted.</b></p>
	 * 
	 * @return the session which this element belongs
	 */
	public TreeSession attachedTo();
}