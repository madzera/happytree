package com.madzera.happytree.demo.model;

import com.madzera.happytree.annotation.Id;
import com.madzera.happytree.annotation.Parent;
import com.madzera.happytree.annotation.Tree;

@Tree
public class TaxonomyNode {
	@Id
	private Integer taxonomyID;
	private String nodeName;
	@Parent
	private Integer parentNodeID;
	private int level;


	public Integer getTaxonomyID() {
		return taxonomyID;
	}

	public void setTaxonomyID(Integer taxonomyID) {
		this.taxonomyID = taxonomyID;
	}

	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(final String nodeName) {
		this.nodeName = nodeName;
	}

	public Integer getParentNodeID() {
		return this.parentNodeID;
	}

	public void setParentNodeID(final Integer parentNodeID) {
		this.parentNodeID = parentNodeID;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(final int level) {
		this.level = level;
	}
	
	@Override
	public String toString() {
		return this.nodeName;
	}
}