package com.miuey.happytree.demo.model;

import com.miuey.happytree.annotation.Id;
import com.miuey.happytree.annotation.Parent;
import com.miuey.happytree.annotation.Tree;

@Tree
public class Science {
	@Id
	private String subArea;
	@Parent
	private String area;
	
	
	public Science(String subArea) {
		this.subArea = subArea;
	}
	
	
	public String getSubArea() {
		return subArea;
	}
	public void setSubArea(String subArea) {
		this.subArea = subArea;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
}
