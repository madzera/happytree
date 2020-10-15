
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

<b>If you have something like this:</b><br>
<code>
<pre>
//Linear tree structure.<br/>  
public class Directory {
	//Own ID
	private int dirId;
	//Super node reference
	private int dirParentId;
	//Simple attribute
	private String dirName;
}  
</pre>
</code>

<b>But you want this:</b><br>
<code>
<pre>
//Recursive tree structure.<br/>
public class Directory {
	private Collection&lt;Node&gt; subDirs;
	private String dirName;
}
</pre>
</code>

### How to use?

If you want to do something described above, that is, using the
HappyTree API to transform a linear structure into a tree structure,
you must first add the following annotations to the
**Java Model class (POJO)** to which they will be inserted as a tree
node:

<code>
<pre>
//Linear tree structure.<br/>
@Tree
public class Directory {
	@Id
	private int dirId;
	@Parent
	private int dirParentId;
	//Simple attribute
	private String dirName;
}  
</pre>
</code>

After that, just group these elements into a collection.

A tree can be created either through the **Transformation Process**
of a previously available linear structure or be created freely
through an empty tree.

For the case of the **Transformation Process**, the initialization is
something similar to the code below:

<code>
<pre>
	Collection<Directory> directories = myObject.myMethodToGetDirectories();
	TreeManager manager = HappyTree.createTreeManager();
	TreeTransaction transaction = manager.getTransaction();
	transaction.initializeSession("myFirstHappyTree", directories);
</pre>
</code>

To initialize an empty tree, the code snippet looks something like
the code below:

<code>
<pre>
	TreeManager manager = HappyTree.createTreeManager();
	TreeTransaction transaction = manager.getTransaction();
	transaction.initializeSession("mySecondHappyTree", Directory.class);
</pre>
</code>

Once created, the trees start to work on
[Element](https://github.com/Miuey/happytree/blob/master/src/main/java/com/miuey/happytree/Element.java)
type objects, which will encapsulate the "original objects"
represented as the respective nodes. From there, just use the
[TreeManager](https://github.com/Miuey/happytree/blob/master/src/main/java/com/miuey/happytree/TreeManager.java)
interface to handle these elements.

### Official documentation

The official HappyTree documentation is in: (soon).


## HappyTree Metadata


### How to contribute?

In order to contribute with us?
We are extremely grateful to collaborators.
Thank you.

See [Contributing](https://github.com/Miuey/happytree/blob/0.0.5/CONTRIBUTING.md)

### Requirements

### Dependencies

### Code structure

HappyTree API is structured into different directories:

- [`src/main`](./src/main): API code.
- [`src/test`](./src/test): JUnit unit test code.

### Build

### Testing
