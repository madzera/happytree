package com.miuey.happytree.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represent, conceptually, an identifier of a object that is not this, but an
 * object that will be its parent in a tree model.
 * 
 * <p>When the <b>API Transformation Process</b> is triggered, then during the
 * tree build, an <code>Id</code> of a object will be linked to the
 * <code>Id</code> of another one that represents its <i>parent</i> object.</p>
 * 
 * <p>If the parent identifier is not found or even <code>null</code>, then
 * this object that have a unknown parent will be placed in root level of the
 * tree.</p>
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
 * <b>The {@literal @Id} and {@literal @Parent} must have same class type.</b>
 * 
 * @author Diego Nóbrega
 * @author Miuey
 * 
 * @see {@link Tree}
 * @see {@link Id}
 * 
 * @version %I%, %G%
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Parent {
}
