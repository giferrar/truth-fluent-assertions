# 🧪 Unit Testing best practices and fluent assertions with Google Truth

## Introduction
Unit testing is a software testing method that focus on proving the correct functionality of an individual software component in isolation.
Unit tests are used in TDD to facilitate the correct development of the component.

A complete test case includes tests about the following elements of a component:
- Logic 
- Boundaries 
- Error handling

Common benefits of unit testing are: 
- Reduced occurrence of bugs 
- Improved code quality 
- Increased confidence in maintenance and refactoring tasks 
- Works as a kind of documentation for the code base

Unit tests should be run every time a change is introduced in the code.

## 🐐 Unit test best practices

### Define your test boundaries (aka Unit tests are not integration tests)

While unit tests verify the correct functionality of a given module in a software, integration tests verify the interactions between different components in a system.
A common mistake in writing a test suite is to write integration tests in place of unit tests.
A reason for this can be haste: try to write as few tests as possible to cover as many lines of code as possible, often because tests are neglected and written at the end of the development phase, like a kind of business requirement that has to be fulfilled.
The problem with this approach is that integration tests are more complex to write, maintain and are slower to execute.

Unit tests have clearly defined boundaries: we do not go out of the method to test, meaning that the rest of the system is for us like a black box.
In case the method under test contains external dependencies, we need to deal with them using mocks or verifications.

```java
public void methodToBeTested() {
    // ...
    // logic to be tested
    // ...
    
    someOtherService.doSomethingElse(); // We do not test the logic of what is in here
}
```

For the sake of testing the correct flow of the logic of the method, we could use verifications to test if a method has been correctly called or not:

```java
public void testingSomeMethod() {
    
    serviceUnderTest.methodToBeTested();
    
    // My assertions here...
    verify(someOtherServiceMocked).doSomethingElse(); // We verify that the method has been called
    verify(someOtherServiceMocked, never()).doSomethingElse(); // or has not been called
    verify(someOtherServiceMocked, times(2)).doSomethingElse(); // or has been called 2 times
}
```

### Use consistent method naming (given - when - then)

It is a good practice to use a consistent method naming strategy for unit tests. 
In particular, it is important with the method name, to convey useful information about the test in question.
A naming strategy is `given - when - then` in which we define in the method name the preconditions of the test, what are we going to test and the expected results.
In case of tests written in Kotlin it is recommended the use of backticks to write a description as test method name. In Java is it possible to use the annotation `@DisplayName` to do the same.

```java
// Some bad names examples
public void myGenericNameOKTest() {} // Do not use keywords like Test, OK, KO
public void irrelevantTestName() {} // Conveys misleading information about the test in question
public void soMuchCamelCaseSoMuchCamelCapeSoMuchCamelCase() {} // Very difficult to read

// Using the `given - when - then` strategy with snake case
public void given_null_parameter_when_method_to_be_tested_then_exception_is_thrown() {}
public void given_non_existing_id_when_get_user_then_return_null() {}
```

### AAA (Arrange - Act - Assert)

In the same way as with method naming, we can follow the same strategy when we write the body of the test method.
With `AAA (Arrange - Act - Assert)` the body is divided in 3 blocks separated by one line comments: one with the preparation for the test, the middle one with the call to the method to test, and in the end the assertions.
Since we use the `given - when - then` strategy, the blocks will also have the same names.

```java
public void given_non_existing_id_when_get_user_then_return_null() {
    // Given
    Long doesNotExist = 9999L;
    
    // When
    User user = userService.getUser(doesNotExist);
    
    // Then
    assertThat(user).isNull();
}
```
Here is an example that shows how to keep the `when` and `then` blocks separated while using `assertThrows` or `assertDoesNotThrow`:

```java
public void given_something_when_method_to_test_then_exception_is_thrown() {
    // Given
    // ...

    // When
    Executable executable = () -> myService.methodToTest(parameter);
    
    // Then
    MyException exception = assertThrows(MyException.class, executable);
    // ...
}

public void given_something_else_when_method_to_test_then_no_exception_is_thrown() {
    // Given
    // ...

    // When
    ThrowingSupplier<MyReturnClass> supplier = () -> myService.methodToTest(parameter);

    // Then
    MyReturnClass response = assertDoesNotThrow(supplier);
}
```

It is important to say that the `When` block must include only one method call (otherwise it means we are trying to write multiple tests at once).

### Nesting tests

It is possible to include in the Test class some nested classes used to contain tests specific to a given method.
The advantage is to achieve greater order in the test class and to have a better organization of the tests.

```java
import org.junit.jupiter.api.Nested;

public class MyCLassTest {
    @Nested
    class PutTests {
        // All test for method put here...
    }
    @Nested
    class GetAllTests {
        // All test for method getAll here...
    }
}
```

### Do not include complex logic

Unit tests are straightforward and have a clearly defined responsibility. Therefore, they do not have included complex logic like `if` statements or loops. In case different very similar tests have to be written for a method, a parametrized test is a good solution.

```java
public void given_something_when_method_to_test_then_exception_is_thrown() {
    // Given
    // ...
    
    // When
    Result result = myService.methodToTest(parameter);
    
    // Then
    assertThat(result).isNotNull();
    
    // :) Start of the Madness
    if (result.isError()) {
        assertThat(result.getErrorMessage()).isEqualTo("Error message");
    } else {
        assertThat(result.getSuccessMessage()).isEqualTo("Success message");
        
        for (ResponseObject responseObject : result.getResponseObjects()) {
            assertThat(responseObject).isNotNull();
            assertThat(responseObject.getName()).isEqualTo("Name");
        }
    }
}
```

### Test functionalities and not code (aka, the problem with code coverage)

Another problem we have briefly seen in the definition of integration tests is the urge to achieve a certain code coverage at the expenses of purpose.
Code coverage as a metric per se is unuseful if not backed by precise and reliable assertions.
A 100% code coverage can only be achieved at the expense of the assertions, thus making the tests meaningless.

There is no correct number for a minimum code coverage, because it is not supposed to be a business metric, but a team metric.
Therefore, when we write code we should not strive simply to "have code covered", but to test the functionalities we implemented.

_We test functionalities, not code!_

### Highlight what is irrelevant/relevant

When writing tests for methods with multiple parameters it is a good practice to make clear which parameters are irrelevant for the test by extracting them to static variables and objects.
The names of the variables should make clear that they are irrelevant, so that the test is self-explanatory. In the same way relevant parameters or objects can be also extracted to static variables.

```java
    // When
    Result result = myService.methodToTest(relevantParameter, SOME_ID);
    
    // When
    Result result = myService.anotherMethod(SOME_USER);
```

## Fluent assertions

Most of our best practices rules have to do with improving the readability of the test method. This is also what fluent assertions bring to the table.
Fluent assertions can allow chaining of multiple assertions in a single statement.

### Truth assertion library

Truth is an assertion library developed by Google. It is similar to AssertJ but provides a simpler API and, in some cases, error messages that are clearer to understand.
Having fewer assertion methods helps to reduce the learning curve, find methods with the IDE autocomplete and have a single way to achieve assertions.
The structure of the library helps also reduce so called 'Puzzlers', a misuse of the API where the method names mislead us into thinking we are asserting something, while instead we are not.
For this reason in Truth the chaining of assertions is not always possible.


The library is imported with the following dependency:
```xml
<dependency>
  <groupId>com.google.truth</groupId>
  <artifactId>truth</artifactId>
  <version>1.4.4</version>
  <scope>test</scope>
</dependency>
```

Basic assertions are written using one of the two methods:
```java
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
```

Here some examples:
```java
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public void truth_basic_usage() {
    assertThat(objectA).isEqualTo(objectB); // Checks equality according to equals() method
    assertThat(objectA).isIn(arrayList); // Checks object is in the list
    assertThat(objectA).isNotNull(); // Checks object is not null
    assertThat(objectA).isInstanceOf(Class.class); // Checks object is an instance of Class
    assertThat(iterableA).contains(item); // Checks iterable contains item
    assertThat(iterableA).containsExactly(items); // Checks iterable contains exactly items
    assertThat(aList).containsExactlyElementsIn(anotherList).inOrder(); // Checks list contains elements in another list
    assertThat(mapA).containsEntry(key, value); // Checks map contains entry
    assertThat(mapA).containsKey(key); // Checks map contains key
    assertThat(anInteger).isGreaterThan(0); // Checks integer is greater than 0
    assertThat(anInteger).isWithin(1, 10); // Checks integer is within range
    assertThat(aString).startsWith("A"); // Checks string starts with "A"
}
```

Objects can also implement the Comparable interface and be compared according to a custom comparator:
```java
import static com.google.common.truth.Truth.assertThat;

public class MyObject implements Comparable<MyObject> {
    // ...
    
    public int compareTo(MyObject other) {
        return this.getName().compareToIgnoreCase(other.getName());
    }
}
public void truth_custom_comparator() {
    MyObject objectA = new MyObject("A");
    MyObject objectB = new MyObject("B");
    assertThat(objectA).isEquivalentAccordingToCompareTo(objectB);    
}
```

Exceptions can also be checked, first using JUnit assertThrows to retrieve an exception instance and then using Truth as follows:
```java
import static com.google.common.truth.Truth.assertThat;

public void truth_exception_assertions() {
    
    SpecificException exception = assertThrows(SpecificException.class, () -> doSomethingThatThrowsException());
    
    assertThat(exception).isInstanceOf(SpecificException.class);
    assertThat(exception).hasMessageThat().contains("my message");
    assertThat(exception).hasCauseThat().isInstanceOf(IllegalArgumentException.class);
}
```

Here is an example on how to verify Optionals:
```java
import static com.google.common.truth.Truth.assertThat;

public void truth_assertions_on_optionals() {
    Optional<String> emptyOptional = Optional.empty();
    Optional<String> nonEmptyOptional = Optional.of("value");
    
    assertThat(emptyOptional).isEmpty();
    assertThat(nonEmptyOptional).isPresent();
    assertThat(nonEmptyOptional).hasValue("value");
}
```

Here an example on how to use assertWithMessage:
```java
import static com.google.common.truth.Truth.assertWithMessage;

public void truth_assert_with_message() {
    assertWithMessage("Message")
            .that(objectA).isNotNull();
}
```

Truth can be extended to support custom types:
```java
public class FoodSubject extends Subject {

   private final Food actual;

   // Constructor where we set the actual object to test
   public FoodSubject(FailureMetadata failureMetadata, Food actual) {
      super(failureMetadata, actual);
      this.actual = actual;
   }

   // Method to create the factory necessary to set the object type in truth
   private static Subject.Factory<FoodSubject, Food> getFactory() {
      return FoodSubject::new;
   }

   // Entry point for the assertion
   public static FoodSubject assertThatFood(Food actual) {
      return Truth.assertAbout(FoodSubject.getFactory()).that(actual);
   }

   public FoodSubject hasName(String expectedName) {
      check("name").that(actual.name).isEqualTo(expectedName);
      return this;
   }

   public FoodSubject hasQuantity(int expectedQuantity) {
      check("quantity").that(actual.quantity).isEqualTo(expectedQuantity);
      return this;
   }

}
```

Here is an example of a complete custom call chain:
```java
import static com.google.common.truth.Truth;

public static void myMethod(MyObject actual) {
   Truth.assert_().withMessage("My custom message").about(MyObjectSubject.getFactory()).that(actual);
}
```

## 📝 Exercises
1) Open the file `FridgeTest.java`. Comment in which way these tests do not follow the best practices. Describe a possible solution.
2) In the class `FridgeFluentTest.java` write a test suite with the help of the class `TestableFridge` and `FoodSubject`. Follow the rules we learned:
    - Test nesting
    - AAA naming
    - Given - When - Then strategy
    - Fluent assertions
3) Write an extension for `TestableFridge` called `TestableFridgeSubject` to assert if the fridge is empty or not.


## 📚 Resources
https://truth.dev/

https://sepl.dibris.unige.it/publications/2018-leotta-QUATIC.pdf

https://microsoft.github.io/code-with-engineering-playbook/automated-testing/unit-testing/

https://medium.com/@kaanfurkanc/unit-testing-best-practices-3a8b0ddd88b5
