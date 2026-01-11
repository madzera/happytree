package com.madzera.happytree.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.madzera.happytree.TreeTransaction;

/**
 * Represents the identifier of a tree node class. This annotation must be
 * applied to a field that uniquely identifies each node within the tree
 * structure.
 * 
 * <p>The field annotated with <code>@Id</code> is used by the core API to
 * assemble and manage the tree nodes effectively. The assembly process relies
 * on this identifier to distinguish between different nodes and to establish
 * parent-child relationships. It happens when the API client passes a
 * collection of linear objects that have a hierarchical relationship through
 * this identifier, to be organized into a tree structure through the
 * {@link TreeTransaction#initializeSession(String, java.util.Collection)}
 * method. This process is known as <b>API Transformation Process</b>.</p>
 * 
 * <p>It is mandatory that the field annotated with <code>@Id</code> has a
 * unique value within the tree structure and is not <code>null</code>.</p>
 * 
 * Example:
 * <pre>
 * <code>@Tree</code>
 * public class TreeNode {
 *     <code>@Id</code>
 *     private String index;
 *     <code>@Parent</code>
 *     private String parentIndex;
 * 
 *     private String anyAttribute;
 *     ...
 * }
 * </pre>
 * 
 * @author Diego Madson de Andrade Nóbrega
 * 
 * @see Tree
 * @see Parent
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
}
