package com.madzera.happytree.demo.model;

import java.io.Serializable;

import com.madzera.happytree.annotation.Id;
import com.madzera.happytree.annotation.Parent;
import com.madzera.happytree.annotation.Tree;

/**
 * A class that represents a directory inside of the Operation System.
 * 
 * <p>A <i>model</i> class used by the tests cases. Each <i>directory</i>
 * represents conceptually a folder in OS. It is just a example to demonstrate
 * a tree behavior and a directory schema is one the best way to do this.</p>
 * 
 * <p>Any class with this behavior can be used in HappyTree API.</p>
 * 
 * <p>Examples of interesting classes that could be used in a real project:</p>
 * 
 * <ul>
 * 	<li>Catalog</li>
 * 	<li>Node</li>
 * 	<li>Chart</li>
 * 	<li>Component</li>
 * </ul>
 * 
 * <p>These objects can easily represent a hierarchical tree structure through
 * the conceptual nature of these objects.</p>
 * 
 * @author Diego Madson de Andrade Nóbrega
 *
 */
@Tree
public class Directory implements Serializable {

	/*
	 * The id of this folder.
	 */
	@Id
	private Long identifier;
	
	/*
	 * The id of the folder with this one will be inside of.
	 */
	@Parent
	private Long parentIdentifier;
	
	/*
	 * The name of the folder to be easy to identify.
	 */
	private String name;
	
	
	public Directory(Long identifier, Long parentIdentifier, String name) {
		super();
		this.identifier = identifier;
		this.parentIdentifier = parentIdentifier;
		this.name = name;
	}

	
	public Long getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}
	
	public Long getParentIdentifier() {
		return parentIdentifier;
	}
	
	public void setParentIdentifier(Long parentIdentifier) {
		this.parentIdentifier = parentIdentifier;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void transformNameToUpperCase() {
		this.name = this.name.toUpperCase();
	}
	
	@Override
	public String toString() {
		return "Directory [name=" + name + "]";
	}
}
