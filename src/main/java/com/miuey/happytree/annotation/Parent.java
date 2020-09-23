package com.miuey.happytree.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represent, conceptually, an identifier of a object that is not this one, but
 * an object that will be its parent in a tree model.
 * 
 * <p>When the <b>API Transformation Process</b> is triggered, then during the
 * tree build, an id of a object will be linked to the id of another one that
 * represents its <i>parent</i> object when the tree is built.</p>
 * 
 * <p>When the parent identifier is not found or even <code>null</code>, then
 * this object that have a unknown parent will be placed in the root level of
 * the tree.</p>
 * 
 * Example:
 * 
 * <pre>
 * 	public class Folder {
 * 		private String folderId;
 * 		{@literal @Parent}
 * 		private String parentFolderId;
 * 
 * 		private String anyAttribute;
 * 		...
 * 	}
 * </pre>
 * 
 * <b>The {@literal @Id} and {@literal @Parent} must have the same class type.
 * </b>
 * 
 * @author Diego Madson de Andrade Nóbrega
 * 
 * @see Tree
 * @see Id
 *
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Parent {
}
