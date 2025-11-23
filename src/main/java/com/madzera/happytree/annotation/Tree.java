package com.madzera.happytree.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.madzera.happytree.TreeManager;
import com.madzera.happytree.TreeTransaction;

/**
 * Declarative annotation to indicate that the object of a class represents a
 * node in a tree structure.
 * 
 * <p>When this annotation is applied to a class, it informs the core API
 * that the objects of this class can be organized into a tree structure,
 * provided that they also have the <code>@Id</code> and <code>@Parent</code>
 * annotations.</p>
 * 
 * <p>The HappyTree API is able to transform a data structure that represents,
 * logically, a tree, but is physically organized in a linear form. Each object
 * that has this annotation will represent a node in a tree. It happens when the
 * API client passes a collection of linear objects, that have a hierarchical
 * relationship through the {@link Id} and {@link Parent}, to be organized into
 * an actual tree structure through the
 * {@link TreeTransaction#initializeSession(String, java.util.Collection)}
 * method.</p>
 * 
 * <p>For instance, if there is, for any reason, a need to transform a
 * data structure like this:</p>
 * 
 * <pre>
 * <code>@Tree</code>
 * public class Directory {
 *     <code>@Id</code>
 *     private long identifier;
 * 
 *     <code>@Parent</code>
 *     private long parentIdentifier;
 * 
 *     private String name; //(System32 for example)
 *     ...
 * }
 * </pre>
 * 
 * into this:
 * 
 * <pre>
 * 	public class Element&lt;Directory&gt; {
 * 		private Collection&lt;Element&lt;Directory&gt;&gt; children;
 * 		private Directory wrappedNode; //The respective Directory
 * 		
 * 		...
 * }
 * </pre>
 * 
 * Then, the HappyTree API will process and transform it into an actual tree
 * structure. This process is known as the <b>API Transformation Process</b>.
 * 
 * <p>Note that in this example, there is a slight change in how the objects are
 * related. In the first one, the <code>parentIdentifier</code> of an object
 * just references the <code>identifier</code> of another one, which
 * conceptually will represent the <b>parent</b> of the first one.</p>
 * 
 * <p>The second example shows that the relationship between the objects is
 * slightly different, because, indeed, an object is <b>literally</b> inside of
 * another one, and so on, representing an actual tree structure.</p>
 * 
 * <p>After the tree is built, it is possible to handle each element in a very
 * flexible way (cut, copy, remove, etc.) through the {@link TreeManager}
 * interface.</p>
 * 
 * <p><b>The <i>getters</i> and <i>setters</i> for the <code>@Id</code> and
 * <code>@Parent</code> annotated fields are mandatory.</b></p>
 * 
 * @author Diego Madson de Andrade Nóbrega
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Tree {
}
