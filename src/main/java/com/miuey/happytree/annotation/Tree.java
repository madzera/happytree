package com.miuey.happytree.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.miuey.happytree.TreeManager;

/**
 * Declarative annotation to indicate that the object of a class represents a
 * linear node object with a tree behavior that will be transformed by
 * HappyTree API (<b><i>API Transformation Process</i></b>).
 * 
 * <p>The HappyTree API is able to transform a data structure that represents,
 * logically, a tree, but this is physically organized in a linear form. Each
 * object that have this annotation will represent a potential node in a tree.
 * </p>
 * 
 * <p>For instance, if there is, for any reason a necessity of transforming a
 * data structure like this:</p>
 * 
 * <pre>
 * 	{@literal @Tree}
 * 	public class Directory {
 * 		{@literal @Id}
 * 		private long identifier;
 * 
 * 		{@literal @Parent}
 * 		private long parentIdentifier;
 * 
 * 		private String name; //(System32 for example)
 * 		...
 * 	}
 * </pre>
 * 
 * in this:
 * 
 * <pre>
 * 	public class Element&lt;Directory&gt; {
 * 		private Collection&lt;Element&lt;Directory&gt;&gt; children;
 * 		private Directory wrappedNode; //The respective Directory
 * 		
 * 		...
 * 	}
 * </pre>
 * 
 * Then, this API will process and transform it into a real tree structure.
 * This is called of <b>API Transformation Process</b>.
 * 
 * <p>Note, in that example, there is a little change in how that objects are
 * related. The first one, a <code>parentIdentifier</code> of an object just
 * references the <code>identifier</code> of another one, that conceptually will
 * represent the <i>parent</i> of the first one.</p>
 * 
 * <p>The second example shows up that the relationship between the objects is
 * little different, because indeed, an object is <b>literally</b> inside of
 * another one, and so on, representing a tree hierarchical structure.</p>
 * 
 * <p>After the tree is built, it is possible to handle each element in a very
 * flexible way (cut, copy, remove, etc.) through the {@link TreeManager}
 * interface.</p>
 * 
 * <p><b>The <i>getters</i> and <i>setters</i> of the annotated attributes are
 * mandatory.</b></p>
 * 
 * @author Diego Madson de Andrade Nóbrega
 * 
 * @see Id
 * @see Parent
 *
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Tree {
}
