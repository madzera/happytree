package com.miuey.happytree.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declarative annotation to indicate that the object of a class represents a
 * linear node object with a tree behavior that will be transformed by
 * HappyTree API.
 * 
 * <p>The HappyTree API is able to transform a data structure that represents
 * a tree but that this behavior is not real in a real tree. So each object
 * in a <code>Collection<?></code> that have this annotation will represent
 * a node in a tree.</p>
 * 
 * <p>For instance, if there is, for any reason a necessity transforming a data
 * structure like this:</p>
 * 
 * <pre>
 * 	{@literal @Tree}
 * 	public class TreeNode {
 * 		private Integer id;
 * 		private Integer parentId;
 * 
 * 		private String anyAttribute;
 * 		...
 * 	}
 * </pre>
 * 
 * in this:
 * 
 * <pre>
 * 	public class Element {
 * 		private Collection&lt;Element&gt; children;
 * 		private Object transformedObject; //The previous transformed TreeNode 
 * 
 * 	}
 * </pre>
 * 
 * then this API will do the job, but precisely what is called of <i>
 * <b>API Transformation Process</b></i>.
 * 
 * <p>Note, in that example, there is a little change in how that objects are
 * related. The first one, a <code>parentId</code> of an object just references
 * the <code>id</code> of another one object that conceptually will represent
 * the <i>parent</i> of the first one.</p>
 * 
 * <p>The second example shows up that the relationship between the objects is
 * little different, because indeed, an object is <b>literally</b> inside of
 * another one, representing a tree hierarchical structure.</p>
 * 
 * @author Diego Nóbrega
 * @author Mieuy
 * 
 * @see {@link Id}
 * @see {@link Parent}
 * 
 * @version %I%, %G%
 *
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Tree {
}
