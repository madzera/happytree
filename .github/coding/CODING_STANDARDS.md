
# HappyTree API Coding Standards

The HappyTree API is characterized by being a simple and flexible
project, in which collaborators have some freedom to help us. To
maintain a certain standard and direction, below are just a few
recommendations of what the project's compliant code would look like.


## Exposure

The most important thing about Coding Standard is the exposure. The
HappyTree API has a specification indicating that the API only
publicly exposes classes and methods when a feature for the API
client is made available, that is:

> Do nor expose to the API client what they don't need to know.

There are two main packages for implementing HappyTree API
functionality. The **happytree** root package, of which the API
client uses the functionalities through the interfaces and the
**core** package, which represents the internal package and which
***must not be exposed to the API client***.

This is the only standard in which collaborators must follow strictly
in order not to violate the HappyTree API specifications.

In a class, the order of its members is:

1. Static constants;
2. Attributes;
3. Constructors;
4. Methods;
5. Inner classes.

In a class, the order of the access modifiers is:

1. public;
2. protected;
3. default;
4. private.

## Packages

The HappyTree API has 4 Java class packages:

### HappyTree Package (Root)

Represents the main package that will serve as an interface for the
API client. In this package it is only allowed to have interfaces,
which represent the HappyTree API functionalities.

#### When to code?

When, and only when, new functionality is assigned to the HappyTree
API.

### Annotation Package

Represents the package of Java annotations that indicate to a class
in which its objects are liable to be transformed into a tree. Also,
this package is public to the API client.

#### When to code?

When there is a need to write a new annotation for a new feature.

### Exception Package

Package that stores the HappyTree API exception classes. In addition,
this package is public to the API client so that the API client can
handle exceptions.

#### When to code?

When there is a need to designate a new exception.

### Core Package

Represents the most important package of the HappyTree API. This is
where all the implemented specifications of the HappyTree API are
contained.

**Everything in this package must not be accessible to the API client**
(except the [HappyTree](../../src/main/java/com/madzera/happytree/core/HappyTree.java)
class).

The core package consists of:

* Validators;
* Pipeline;
* Repository;
* Factory;
* Utils & Helpers.

#### When to code?

The collaborator has the freedom to develop or correct any problem at
any time, as long as he does not expose the details to the API client
and writes a legible and easy to understand code.

### ATP Package

This package is inside of the Core Package. It aims to separate from
the Core Package what is only related to the
**API Transformation Process** (ATP).

#### When to code?

The same as for the Core Package.

## Encoding

The encoding used in the HappyTree API is UTF-8.

## Naming Conventions

In the most part, the HappyTree API follows the
[Oracle Code Conventions](https://www.oracle.com/java/technologies/javase/codeconventions-contents.html)

### Classes/Interfaces

* Names of classes or interfaces in the HappyTree API represent nouns
 and each spelled word has its first letter in capital letter;
* There are not "$", "_" and numbers.

### Methods

* Method names usually indicate verb (not necessarily);
* The first letter must always be lowercase;
* After the first spelled word, all words have the first letter in
 capital letter;
* There are not "$", "_" and numbers.

### Variables

* Variable names must represent the context it has in a method or
 class;
* The first letter must always be lowercase;
* After the first spelled word, all words have the first letter in
 capital letter;
* There are not "$", "_" and numbers.

### Constants

* Method constants must follow the same as variables;
* Class constants are all capitalized;
* If this is a class constant, each spelled word is separated by "_"
 e.g <code>DEFAULT_VERSION</code>;
* There are not "$", "_" and numbers.

## Indentation

Each hierarchy of code blocks must have the indentation of a single
tab. The model follows:

```java
public class Example {
	private Integer foo;
	private String[] bars;
	
	
	public Example() {
	 
	}
	
	
	public boolean doIt() {
		String helloWorld = null;
		
		for (String bar : bars ) {
			if (!bar.equals("")) {
			
				switch (bar.length()) {
					case 1: {
						return true;
					}
					default: {
						return false;
					}
				}
			}
		}
		return false;
	}
}  
```

## Layout

### Brackets

The braces is used beside of the class instead the variable.

Compliant:

```java
public static void main(String[] args) {

}
```

```java
String[] names = new String[size-1];
```

Non-Compliant:

```java
public static void main(String args[]) {

}
```

```java
String names[] = new String[size-1];
```

### Parentheses

There is no space between the parentheses and arguments or the
parentheses and the name of the method.

Compliant:

```java
public static void main(String[] args) {

}
```

Non-Compliant:

```java
public static void main( String args[] ) {

}
```
```java
public static void main (String args[]) {

}
```

There is only one space between the parentheses and a key word.

Compliant:

```java
if (!foo) {

}
```

Non-Compliant:

```java
if(!foo) {

}
```

### Braces

Braces always are used on the same line of a class or interface,
method bodies and constructor bodies, *if* statement, *for-do-while*
loop, *switch*, etc.

Compliant:

```java
public static void main(String[] args) {

}
```

Non-Compliant:

```java
public static void main(String[] args)
{

}
```

### Commenting

Comments are only required on API client interfaces. The collaborator
can feel free to elaborate line comments in internal methods, if the
collaborator thinks it is necessary.

In public interfaces, the HappyTree API adopts **Javadoc** as a
comment convention.

### Edge margin

The HappyTree API is coded with a margin of 80 characters.

## Prerequisites

For a code contribution to be successful, it has to pass the
following steps:

1. **Coding Standards;**
2. **Unity Tests;**
3. **Sonar Analysis.**