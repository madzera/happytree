
# Writing Tests

The HappyTree API has a well-defined structure of unit tests for each
method and each interface made available to the API client. In this
context, JUnit is adopted for unit testing.

In the first part, will be showed up the unit tests structure. Lastly
this document will introduce how the collaborators can help us, by
determining standards/recommendations of writing tests.

## The unit tests structure

Currently, the Happy Tree API provides to the API client, four
interfaces for handling trees. Each interface contains a set of
services (methods) that helps the API Client to handle trees. Those
interfaces are:

* [Element](../../src/main/java/com/miuey/happytree/Element.java)
* [TreeManager](../../src/main/java/com/miuey/happytree/TreeManager.java)
* [TreeTransaction](../../src/main/java/com/miuey/happytree/TreeTransaction.java)
* [TreeSession](../../src/main/java/com/miuey/happytree/TreeSession.java)

With this in mind, the unit tests structure are based in two types of
tests:

### Demo tests

They are customized tests, free of any rule. Any collaborator can
perform any type of tests for the HappyTree API here. The test
classes are localized in: [demo](../../src/test/java/com/miuey/happytree/demo).

Already, there are **model** and **util** sub-packages, for better
organization.

### API client interfaces tests

These tests are fundamentals for each available service provided by
their respective interfaces. So, each interface are represented by
a package, and each package can contain:

* The happy scenario tests (mandatory);
* The alternative scenario tests (optional);
* The error scenario tests (optional);
* The suited test class to run whole the tests from a package which
 represents an interface (mandatory).

#### Nomenclature

For each interface, it is adopted the following nomenclature, for
example: **[TreeManager]**

**[Interface Name] + [Scenario Test] + [Test Suffix]** (Except for the happy scenario)

For happy scenario, the **[Scenario Test]** is omitted.

* **Happy Scenario:** *TreeManagerTest*
* **Alternative Scenario:** *TreeManagerAlternativeTest*
* **Error Scenario:** *TreeManagerErrorTest*
* **Suited Test Class:** *TreeManagerSuiteTest*

### Main Test Class

The main test class of the HappyTree API is in:
[HappyTreeTest](../../src/test/java/com/miuey/happytree/HappyTreeTest.java)
in the root level of the test package.

Just as [HappyTree](../../src/main/java/com/miuey/happytree/core/HappyTree.java)
represents an entry class for the API, **HappyTreeTest** represents
an entry test class to perform all unit tests of the HappyTree API.
It seems like a *Master Suited Test Class*. Every new suited test
class has to be added to the **HappyTreeTest**.

```
HappyTreeTest -> TreeManagerSuiteTest -> TreeManagerTest
														 -> TreeManagerAlternativeTest
														 -> TreeManagerErrorTest
```

## Standards


> Each interface method **must** have at least one test in the happy
scenario..

