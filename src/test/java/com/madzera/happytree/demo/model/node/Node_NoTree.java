package com.madzera.happytree.demo.model.node;

import com.madzera.happytree.annotation.Id;
import com.madzera.happytree.annotation.Parent;

public class Node_NoTree {
	@Id
	private Integer id;
	@Parent
	private Integer parent;
	private String name;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParent() {
		return parent;
	}
	public void setParent(Integer parent) {
		this.parent = parent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
