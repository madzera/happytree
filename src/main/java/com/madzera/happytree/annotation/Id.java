package com.madzera.happytree.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represent an identifier of an object of a class that represents a linear node
 * with a tree behavior that will be transformed by HappyTree API.
 * 
 * <p>This id <b>must</b> be unique when a collection of objects that will be
 * transformed into a real tree hierarchical structure is passed through of a
 * tree session initialization.</p>
 * 
 * <p>This happens because an object (declared with {@literal @Tree} annotation)
 * that will be transformed through <b>API Transformation Process</b> will
 * represent, respectively, a <i>node</i> in a tree concept. So, each node in a
 * tree <b>must</b> have a not <code>null</code> id and an <b>unique id</b>.</p>
 * 
 * Example:
 * <pre>
 * 	public class TreeNode {
 * 		{@literal @Id}
 * 		private String index;
 * 		private String parentIndex;
 * 
 * 		private String anyAttribute;
 * 		...
 * 	}
 * </pre>
 * 
 * @author Diego Madson de Andrade Nóbrega
 * 
 * @see Tree
 * @see Parent
 * 
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
}
