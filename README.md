
# HappyTree

“Java POJO API to transform linear objects into real trees. Handle
these objects as if they were nodes of a tree, similar to the
*JavaScript HTML DOM*.”


<p align="center">
    <a href="LICENSE" target="_blank">
        <img alt="Software License" src="https://img.shields.io/github/license/Miuey/happytree">
    </a>
    <a href="https://github.com/Miuey/happytree/actions?query=workflow%3ABuild" target="_blank">
        <img alt="Build Status" src="https://github.com/Miuey/happytree/workflows/Build/badge.svg">
    </a>
    <a href="https://sonarcloud.io/dashboard?id=Miuey_happytree" target="_blank">
        <img alt="Code Quality" src="https://sonarcloud.io/api/project_badges/measure?project=Miuey_happytree&metric=alert_status">
    </a>
    <a href="https://sonarcloud.io/dashboard?id=Miuey_happytree" target="_blank">
        <img alt="Maintainability Rating" src="https://sonarcloud.io/api/project_badges/measure?project=Miuey_happytree&metric=sqale_rating">
    </a>
    <a href="https://sonarcloud.io/dashboard?id=Miuey_happytree" target="_blank">
        <img alt="Reliability" src="https://sonarcloud.io/api/project_badges/measure?project=Miuey_happytree&metric=reliability_rating">
    </a>
    <a href="https://sonarcloud.io/dashboard?id=Miuey_happytree" target="_blank">
        <img alt="Security Rating" src="https://sonarcloud.io/api/project_badges/measure?project=Miuey_happytree&metric=security_rating">
    </a>
</p>


## About the HappyTree API

### What is it?

HappyTree is a data structure API designed for the Java programming 
language that consists of transforming linear objects into a tree 
structure, where each object represents a node in the tree and can 
have none or several other objects of the same type. It is similar 
to the Document Object Model (DOM) and provides an interface for 
manipulating these objects in various trees.

In addition to the **Transformation Process**, the HappyTree API
provides mechanisms to handle these objects within trees, as if these
objects were tree nodes, all in an easy and intuitive way.

### What is your purpose?

The HappyTree API has two main purposes:

<ol>
<li>Transform the linear data structure of
 <b>POJO/Model Java objects</b> into trees;</li>
<li>Handle nodes within trees, in order to perform operations such as
 copying, cutting, removing, creating, persisting or updating;</li>
</ol>

The first purpose is suitable for situations in which the API client
needs to transform a collection of plain objects of which there is a
tree logical relation between them, but which is not being
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
opportunity to use HappyTree, since objects will now be inside 
each other thus assembling a tree, as well as offering interfaces 
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
//Recursive tree structure.
public class Directory {
	private Collection<Directory> subDirs;
	private String dirName;
	
		//getters and setters
}
```

### How to use?

If you want to do something described above, that is, using the
HappyTree API to transform a linear structure into a tree structure,
you must first add the following annotations to the
**Java Model Class (POJO)** to which they will be inserted as a tree
node:

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

After that, just group these elements into a collection.

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
[Element](./src/main/java/com/miuey/happytree/Element.java)
type objects, which will encapsulate the "original objects"
represented as the respective nodes. From there, just use the
[TreeManager](./src/main/java/com/miuey/happytree/TreeManager.java)
interface to handle these elements.


## HappyTree Metadata


### How to contribute?

In order to contribute with us?
We are extremely grateful to collaborators.
Thank you.

See [Contributing.](./.github/CONTRIBUTING.md)

<a name="techs"></a>

### Requirements


Because it acts on a specific layer of the architecture of a
**Java Project**, the HappyTree API has a very limited list of
requirements, so in addition to being easy to use, the HappyTree API
also has an easy installation.

Both to make use of, as well as to contribute, the requirements are:

* **Java 1.8**;
* **Maven 3.6.3**;
* **The Java and Maven environment variables must be well configured ($JAVA_HOME, $MAVEN_HOME and $PATH).**
* **SonarLint**: It is optional, but highly recommended to fix code
 issues.

### Dependencies

The HappyTree API has only one dependency, which is used to perform
unit tests:

* **JUnit 4.13.1**.

### Code structure

HappyTree API is structured into different directories:

- [`src/main`](./src/main/java/com/miuey/happytree): API code.
- [`src/test`](./src/test/java/com/miuey/happytree): JUnit test code.

### Coding

* [HappyTree API coding standards](/.github/coding/CODING_STANDARDS.md)
* [Writing tests](/.github/coding/WRITING_TESTS.md)
* [Checklist](/.github/coding/CHECKLIST.md)

### Build

For building, go to the project root folder and enter the following
command:

<code>
	mvn clean package
</code>
