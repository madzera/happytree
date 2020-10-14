
# Happy Tree

“Java POJO API to transform linear objects into real trees. Handle
these objects as if they were nodes of a tree, similar to the
*JavaScript HTML DOM*.”


## About the HappyTree API

### What is it?

HappyTree is a data structure API designed for the Java programming 
language that consists of transforming linear objects into a tree 
structure, where each object represents a node in the tree and can 
have none or several other objects of the same type. It is similar 
to the Document Object Model (DOM) and provides an interface for 
manipulating these objects in various trees.

In addition to the transformation process, the HappyTree API provides
mechanisms to handle these objects within trees, as if these objects
were tree nodes, all in an easy and intuitive way.

### What is your purpose?

The HappyTree API has two main purposes:

<ol>
<li>Transform the linear data structure of
 **POJO/Model Java objects** into trees;</li>
<li>Handle nodes within trees, in order to perform operations such as
 copying, cutting, removing, creating, persisting or updating;</li>
</ol>

The first purpose is suitable for situations in which the API client
needs to transform a collection of plain objects of which there is a
logical tree relation between them, but which is not being
represented structurally as a tree.

The second purpose represents the basic operations on the trees, when
the API client desires to reallocate nodes (officially called
**Elements** in the context of the API) on the trees.

### For who?

For Java developers who feel the need to work with trees on their
**Model Objects (POJO)**. The HappyTree API acts only on the
**Model Object Layer** of a project's architecture.

### When to use?

When the API client has a data structure which the logical represents
a tree but its objects are only linearly referenced to each other,
this API has precisely this purpose of transforming the linear
structure into a physical tree structure.

If, for example, the API client has a directory structure in its 
system, or if it needs to assemble a screen of visual components, 
through objects that are not inside each other, but objects that 
refer others through simple attributes, then this is the right 
opportunity to use Happy Tree, since objects will now be inside 
each other thus assembling a tree, as well as offering interfaces 
to handle them.

<b>If you have something like this:</b>
<code>
<pre>
//Linear structure representing a tree through parent attribute<br/>  
public class Node {  
	//Own ID  
	private int id;  
	//Super node reference  
	private int parent;  
	//Simple attribute  
	private String name;  
}  
</pre>
</code>

<b>But you want do this:</b>
<code>
<pre>
//Tree structure representing a tree through parent attribute<br/>
public class Node {  
	private Collection&lt;Node&gt; children;  
	private String name;  
}  
</pre>
</code>

### How to use?

### Official documentation

The oficial HappyTree documentation is in:


## HappyTree Metadata


### How to contribute?

### Requirements

### Dependencies

### Code structure

HappyTree is structured into different directories:

- [`src/main`](./src/main): API code.
- [`src/test`](./src/test): JUnit unitary test code.

### Build

### Testing
