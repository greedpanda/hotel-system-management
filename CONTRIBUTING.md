# Contributing Standards

- [Git Flow](#git-flow)
  - [Example](#example)
- [Commits](#commits)
  - [Commit Message Template](#commit-message-template)
- [Merge Requests](#merge-Requests)
  - [Checklist](#checklist)
  - [Title and Description](#title-and-description)
- [Cloning](#cloning)
- [Building](#building)
- [Testing](#testing-and-unit-testing-standards)
  - [Unit Testing Framework](#unit-testing-framework)
  - [Unit Tests Naming Convention](#unit-tests-naming-convention)
- [Coding Standards](#coding-standards)
  - [Coding styles](#coding-styles)
  - [Maintainability](#maintainability)
- [Logging](#logging)
  - [Logging Conventions](#logging-conventions)
  - [Logging Guidelines](#logging-guidelines)
- [Naming Conventions](#naming-conventions)
  - [Milestones](#milestones)
  - [Issues](#issues)
  - [Branches](#branches)
  - [Epics](#epics)
  - [Version Scheme](#version-scheme)
  - [Merge requests](#merge-requests)
- [Recommended Software](#recommended-software)
  
## Git Flow

There are two branches that are considered protected. The master branch contains reviewed and tested code that provides a functional application at all times. Merges into master will only occur once a sufficient amount of milestones has been reached through sprints. Off master there is the develop branch which is used for development. It is the only branch that can merge into master. Each new sprint branches off from develop with the name. Sprints are merged to develop after a pull request has been initiated and everyone is satisfied with the result. Finally, develop is merged into master for a new release.

### Example

Merge request implementing an issue in a sprint will be merged and requested to the sprint branch, this will have to be approved by the people responsible for that specific part of the application. After we have fulfilled a sprint, we will decide on a meeting (text / voice) if we are satisfied with the sprint and also merge it into develop.


## Commits
Keeping a clean commit history is important to keep track of the progress and history of the repository. It therefore goes without saying that commit messages must be very explicit and descriptive.
```
Fixed SomeClass’ NullPointerException
```
The above commit message leaves a lot unspecified at first glance. The following provides more _context_.
```
Fixed NullPointerException in SomeClasses’ findFoo method when empty array is passed
```
### Commit Message Template
```
Fixed NullPointerException in SomeClasses’ findFoo method when empty array is passed

An empty array was being passed through OtherClass when user
input was negative.

Issue: #2
```
In its entirety there are 3 parts: Title, Body (Optional), Metadata. Avoid ending the title with punctuation and try to keep line length bellow 80 characters.

## Merge Requests

### Checklist

- Make sure you follow the [Coding Standards](#coding-standards).
- Focus on one issue. Small pull requests with a few commits are preferred.
- Add tests.

### Title and Description

Each merge request should have the following format:
```
Issue #X: Title of PR

Description (Optional)

Closes/Related #Y  (Optional)
```
Should there be no related issue (random bug appeared or code cleanup) first create an issue before pull requesting. Each merge request should have a sprint label in order to keep track of the sprint history. One commit merge requests should have description that matches the title and body of the commit otherwise they should be a summary of the commits.

## Cloning

Get a clone of the repo with:

```bash
git clone git@gitlab.lnu.se:1dv508/student/nitrogen/hotel.git
```

Make sure that your ssh key is set up properly on gitlab TODO.

## Building 

This project uses gradle so building is done with:

```bash
./gradlew build
```

## Testing and Unit Testing Standards

Every user story merge request needs an accompanying test case. [Junit 5](https://junit.org/junit5/) is used for tests.

To run tests type:

```bash
./gradlew test
```
in a local command prompt in the root git directory.

Each unit test should test only one thing at a time when possible.

### Unit Testing Framework

we will use [JUnit5](https://junit.org/junit5/docs/current/user-guide/).

### Unit Tests Naming Convention

We will use [Osheroves unit tests naming convention](https://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html).

The basic naming of a test comprises of three main parts:

````
[UnitOfWork_StateUnderTest_ExpectedBehavior]
````

Examples:

````
Public void Add_NegativeNumberAs2ndParam_ExceptionThrown()

Public void Add_simpleValues_Calculated()
````

## Coding Standards

### Coding styles

We will use [Google Code Style](https://google.github.io/styleguide/).

### Maintainability

- It is best to write code that explains itself:
  ```java
  CustomReturnType crt = w.run();  // bad

  CommandCode cmdCode = widget.run()  // ok
  ```

- Avoid too abbreviated words and idiomatic acronyms:
  - Exceptions are the indexes in for loops:
    ```java
    int i = Foo.bar();  // bad
    // use i

    for(int i = 0 ; users.length; ++i )  // ok
        // use users[i]
    ```
  - Very local throwaway variables:
    ```java
    ReturnCode ret = command.run();  // The rest of the statement provides context
    // immediately use ret
    ```
- Comments should be describing the context of the code not describing it line by line:
  Avoid the following:
  ```java
  // run widget
  ReturnCode ret = widget.run();
  ```
- Comments for developers:
  Try to stick to common words like:
  ```
  // TODO: ...
  // INFO: ...
  // FIX: ...
  // BUG: ...
  ```
  So that they can be easily search for by other developers

## Logging

### Logging Conventions 
There are 4 semantic levels of logging used.
- `info` High level information about what the program is doing according to what is expected. Example:
  - Initialising/closing database
  - Changing themes
  - Call to specific function based on criteria
- `warn` Unexpected event occurred worthy of investigation but not severe enough to be considered an error as the program will still yield acceptable results. Example:
  - Failure to load as specific theme leading to using a default
  - Use of deprecated function which should be avoided
- `error` Event that does not allow the normal execution of the application. Example:
  - SQL server not available
  - Running out of memory
- `debug` Developer relevant information that will help during debugging. Examples:
  - Values entered by user
  - Return codes of network functions

### Logging Guidelines
- It is best to keep logging messages short and context specific:
  `Permission denied` is not as helpful as: `Use of 'X()' for permissions 'Staff' denied`
- Use single quotes for quoting strings: `Log.info("Got string '{}' as return value", some_string)`
- Use the overloaded logging methods with signature `(String fmt, Object...)` for more intricate logging as it allows the message in its entirety to be easily read:
  ```java
  Log.error("Use of '{}' for permissions '{}' denied", "foo", Permissions.STAFF)
  ```
- _Never_ log everything

## Naming Conventions

### Milestones

Milestones will be named after the sprint, in our case a sprint would usually be a week, `W14 Sprint`.

### Issues

Issue will be specifically named, user stories will have the entire name spelled out in title and more specific description inside the issue itself. Bug fixes will also be named after what they fix. Labels will be used to filter between sprint issues and bug issues that are not in a sprint. When adding an issue to an epic, make sure to label it with `EPIC-name`, where name is a shortened name of the epic name.

### Branches

Branches should be named the issue-id if applicable and description, so if it covers issue `30` and the name `is fix-this-there` something then, `30-fix-this-there`.

### Epics

Epics will cover project goals and will not be involved in sprints, for example all functionality for reception will be one epic goal.

### Version Scheme

The version scheme for master is a simple `X.Y.Z`. `X` is a major non backwards compatible, `Y` is a minor update and `Z` is a patch.

### Merge requests

Merge requests should have limited scope and strive to only solve one issue. Every merge request should aim to have an issue that it fixes or closes, in rare cases this may be omitted. 

## Recommended Software

Intellij [IDEA](https://www.jetbrains.com/idea/), student license can be acquired but is not needed, community version is fine.
Intellij [IDEA Google Coding plugin](https://github.com/google/google-java-format#intellij-android-studio-and-other-jetbrains-ides)
