package com.madzera.happytree.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents, conceptually, an identifier of an object that is not this one, but
 * an object that will be its parent in a tree model.
 * 
 * <p>When the <b>API Transformation Process</b> is triggered, then during the
 * tree building, the id of an object will be linked to the id of another one that
 * represents its <i>parent</i> object when the tree is built.</p>
 * 
 * <p>When the parent identifier is not found or is <code>null</code>, then
 * this object that has an unknown parent will be placed at the root level of
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
 * <b>The {@literal @Id} and {@literal @Parent} must have the same class type.</b>
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
