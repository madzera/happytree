package com.madzera.happytree.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.madzera.happytree.TreeTransaction;

/**
 * Represents a reference to the parent identifier of an object in a tree model.
 * 
 * <p>When the <b>API Transformation Process</b> is triggered, through the
 * {@link TreeTransaction#initializeSession(String, java.util.Collection)}
 * method, this annotation indicates to the core API that, during the tree
 * building, the <code>@Id</code> of an object will be linked to the
 * <code>@Id</code> of another one that represents its <b>parent</b> object when
 * the tree is built.</p>
 * 
 * <p>When the <code>@Parent</code> identifier is not found or it is
 * <code>null</code>, then this object has an unknown parent and will be placed
 * at the root level of the tree (first level).</p>
 * 
 * Example:
 * 
 * <pre>
 * <code>@Tree</code>
 * public class Folder {
 * 
 *     <code>@Id</code>
 *     private String folderId;
 *     <code>@Parent</code>
 *     private String parentFolderId;
 * 
 *     private String anyAttribute;
 *     ...
 * }
 * </pre>
 * 
 * <b>The <code>@Id</code> and <code>@Parent</code> must have the same class
 * type.</b>
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
