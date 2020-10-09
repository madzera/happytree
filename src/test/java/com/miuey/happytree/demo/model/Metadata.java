package com.miuey.happytree.demo.model;

import com.miuey.happytree.annotation.Id;
import com.miuey.happytree.annotation.Parent;
import com.miuey.happytree.annotation.Tree;

/**
 * A class that represents metadata info of any contexts.
 * 
 * <p>A <i>model</i> class used by a specific test case. This class will be used
 * when a attempt of put an element inside of a tree which is not of its type,
 * thus resulting in error.</p>
 * 
 * <p>Therefore, a <codeDirectory</code> type tree cannot have an element
 * inserted in a <code>Metadata</code> type tree.</p>
 * 
 * @author Diego Madson de Andrade Nóbrega
 * 
 */
@Tree
public class Metadata {
	@Id
	private String id;
	@Parent
	private String parentId;
	private String metadata;
	
	
	public Metadata(String id, String parentId, String metadata) {
		this.id = id;
		this.parentId = parentId;
		this.metadata = metadata;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getMetadata() {
		return metadata;
	}
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
}
