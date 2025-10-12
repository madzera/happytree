package com.madzera.happytree.demo.model;

import java.io.Serializable;

import com.madzera.happytree.annotation.Id;
import com.madzera.happytree.annotation.Parent;
import com.madzera.happytree.annotation.Tree;

@Tree
public class Science implements Serializable {
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
