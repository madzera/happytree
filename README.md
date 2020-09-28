
# Happy Tree

“Java POJO API to generate and handle hierarchical tree structure.”


## Introduction

HappyTree is a data structure API designed for the Java programming 
language that consists of transforming linear objects into a tree 
structure, where each object represents a node in the tree and can 
have none or several other objects of the same type. It is similar 
to the Document Object Model (DOM) and provides an interface for 
manipulating these objects in various trees.

### Purpose

If the API user has a logical data structure that represent a tree 
but its objects are only linearly referenced to each other, this 
API has precisely this purpose of transforming the linear structure 
into a physical tree structure. Therefore, Happy Tree serves to:

<ul>
<li>Transform linear data structure into trees;</li>
<li>Manipulate nodes within trees where each node represents objects
 previously transformed;</li>
<li>Manage stored tree sessions;</li>
</ul>

### When To Use

If, for example, the API client has a directory structure in its 
system, or if it needs to assemble a screen of visual components, 
through objects that are not inside each other but objects that 
refer others through simple attributes, then this is the right 
opportunity to use Happy Tree, since objects will now be inside 
each other thus assembling a tree, as well as offering interfaces 
to manipulate them.

<p><b>If you have something like this:</b></p>
<code>
//Linear structure representing a tree through parent attribute<br/>
public class Node {  <br />
	//Own ID  
	private int id;  
	//Super node reference  
	private int parent;  
	//Simple attribute  
	private String name;  
}  
</code>
<p><b>But you want do this:</b></p>
<code>
//Tree structure representing a tree through parent attribute<br/>
public class Node {  
	private Collection&lt;Node&gt; children;  
	private String name;  
}  
</code>

### Documentation

The oficial HappyTree documentation is in:

## Code Structure

HappyTree is structured into different directories:

- [`src/main`](./src/main): API code.
- [`src/test`](./src/test): JUnit unitary test code.
