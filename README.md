
<p align="center">
  <a href="https://happytreeapi.vercel.app/" target="_blank" >
    <img alt="HappyTree API" src="./.github/resources/happytree_logo.png" width="400" />
  </a>
</p>

“*API designed for handling Java objects with tree-like behavior which 
each object behaves similarly to a node within the tree. Add, remove,
cut, copy, print in JSON/XML elements within a tree.*”


<p align="center">
    <a href="LICENSE" target="_blank">
        <img alt="Software License" src="https://img.shields.io/github/license/madzera/happytree">
    </a>
    <a href="https://github.com/madzera/happytree/actions?query=workflow%3ABuild" target="_blank">
        <img alt="Build Status" src="https://github.com/madzera/happytree/workflows/Build/badge.svg">
    </a>
    <a href="https://sonarcloud.io/dashboard?id=madzera_happytree" target="_blank">
        <img alt="Code Quality" src="https://sonarcloud.io/api/project_badges/measure?project=madzera_happytree&metric=alert_status">
    </a>
    <a href="https://sonarcloud.io/dashboard?id=madzera_happytree" target="_blank">
        <img alt="Maintainability Rating" src="https://sonarcloud.io/api/project_badges/measure?project=madzera_happytree&metric=sqale_rating">
    </a>
    <a href="https://sonarcloud.io/dashboard?id=madzera_happytree" target="_blank">
        <img alt="Reliability" src="https://sonarcloud.io/api/project_badges/measure?project=madzera_happytree&metric=reliability_rating">
    </a>
    <a href="https://sonarcloud.io/dashboard?id=madzera_happytree" target="_blank">
        <img alt="Security Rating" src="https://sonarcloud.io/api/project_badges/measure?project=madzera_happytree&metric=security_rating">
    </a>
    <a href="https://github.com/madzera/happytree/releases/tag/v1.0.0" target="_blank">
        <img alt="GitHub Release" src="https://img.shields.io/github/v/release/madzera/happytree">
    </a>
</p>


## About the HappyTree API

### What is it?

HappyTree is a data structure API design to handling Java objects which
have a tree-like behavior, whereas an @Id attribute of an object is referenced
as a @Parent attribute of its children.

In certain circumstance there are needs to convert a list of Java objects, that
could represent a model layer in a business context, into an actual hierarchical
tree structure in memory, where objects contain its children and each child
contains its grant children and so on.

So, when there are the need to put a collection (Set/List) of objects, that each
object relates to another object of the same type through an identifier
attribute in a tree-like manner, the HappyTree is able to transform this
structure into an actual tree structure in memory, when each object will be
wrapped into a tree node object, called "Element", and each element contains its
children elements whereas each child contains its own children and so on,
recursively. 

From this point, the API client can handle those elements within a tree, such as
adding/removing children from nodes, moving nodes to another point of the tree
or even to another tree, copying nodes to other trees, converting trees into
JSON/XML, etc.

HappyTree is a data structure API designed for the Java programming 
language that consists of transforming linear objects into a tree 
structure, where each object represents a node in the tree and can 
have none or several other objects of the same type.

In addition to the **Transformation Process** (mechanism to
transform this linear structure of objects into a tree structure),
the HappyTree API provides mechanisms to handle these objects within
trees, as if these objects were tree nodes, all in an easy and
intuitive way.

### What is your purpose?

The HappyTree API aims to provide a way of creating new trees, creating trees
from existing collection of objects that have a tree-like behavior, as well as
handling these trees. Therefore, there are three main purposes of the API:

<ol>
	<li>Handle <b>Java Objects</b> as if they were nodes within trees, in
		order to perform operations such as copying, cutting, removing, creating,
		persisting/updating, etc. over those objects;</li>
	<li>Transform linear data structures of <b>Java Objects</b> that have
		tree-like behavior into an actual tree;</li>
	<li>Create new trees from scratch.</li>
</ol>

The first purpose represents the basic operations of the trees, when the API
client desires to change the state of the nodes (officially called of
**Elements** in the context of the API) in the trees, in order to move, copy,
remove, create and update those nodes.

The second purpose is suitable for situations in which the API client
needs to transform a collection of plain objects, of which there is a
tree logical relation between them, into a tree. Here, each element contains
its children elements, and each child contains its own children recursively.

The last one allows the API client to create new trees from scratch, persisting
element to element to build the tree structure as desired.

### For who?

For developers who feel the need to work with trees on their
**Java Object Model**. The HappyTree API only acts on the
**Object Model Layer** of a project's architecture.

### When to use?

When the API client has a data structure which the logical represents
a tree but its objects are only linearly referenced to each other,
this API has precisely this purpose of transforming the linear
structure into a physical tree structure, beyond of providing
mechanisms to handle them.

If, for example, the API client has a directory structure in its 
system, or if it needs to assemble a screen of visual components, 
through objects that are not inside each other, but objects that 
refer others through simple attributes, then this is the right 
opportunity to use the HappyTree API, since objects will now be
inside each other thus like a tree, as well as offering interfaces
to handle them.

<b>If you have something like this:</b><br>

```java
//Linear tree structure.  
public class Directory {
	//Own ID
	private Integer dirId;
	//Super node reference
	private Integer dirParentId;
	//Simple attribute
	private String dirName;
	
	//getters and setters
}  
```

<b>But you want this:</b><br>

```java
//Recursive tree structure wrapped through the Element object.
public class Element<Directory> {
	private Object dirId;
	private Object dirParentId;
	private Collection<Element<Directory>> subDirs;
	
	//Skeleton methods.
	public void addChild(Element<Directory> child);
	
	public void removeChild(Element<Directory> child);
	
	public void wrap(Directory directory);
	
	public Directory unwrap();
}
```

### How to use?

If you want to do something described above, that is, using the
HappyTree API to transform a linear structure into a tree structure,
you must first add the following annotations to the **Java Class**
to which its objects will be inserted as a tree node:

```java
//Linear tree structure.
@Tree
public class Directory {
	@Id
	private Integer dirId;
	@Parent
	private Integer dirParentId;
	//Simple attribute
	private String dirName;
	
	//getters and setters
}
```

After that, just group these objects into a collection.

A tree can be created either through the **Transformation Process**
of a previously available linear structure or be created freely
through an empty tree.

For the case of the **Transformation Process**, the initialization is
something similar to the code below:

```java
	Collection<Directory> directories = myObject.myMethodToGetDirectories();
	TreeManager manager = HappyTree.createTreeManager();
	TreeTransaction transaction = manager.getTransaction();
	transaction.initializeSession("myFirstHappyTree", directories);
```

To initialize an empty tree, the code snippet looks something like
the code below:

```java
	TreeManager manager = HappyTree.createTreeManager();
	TreeTransaction transaction = manager.getTransaction();
	transaction.initializeSession("mySecondHappyTree", Directory.class);
```

Once created, the trees start to work on
[Element](./src/main/java/com/madzera/happytree/Element.java)
type objects, which will encapsulate (wrap) the "original objects"
(Directory) represented as the respective nodes. From there, just use the
[TreeManager](./src/main/java/com/madzera/happytree/TreeManager.java)
interface to handle these elements.

### Documentation

[HappyTree Official Documentation](https://happytreeapi.vercel.app/happytree-1.0.0.pdf)

### Contributing

In order to contribute with us?
We are extremely grateful to collaborators.
Thank you.

See [Contributing.](CONTRIBUTING.md)