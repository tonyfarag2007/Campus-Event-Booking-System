# Campus Event Booking System
This project is a campus event booking system written in Java, which uses CSV files to store the data.

# Naming Conventions/Style Guide
Below is the style guide/conventions to use during development:
- Use `snake_case` for variable/function names.
- **Use `camelCase` for all getters/setters, since JavaFX's PropertyValueFactory method requires it.**
- Use `TitleCase` for class/enum names and **enum values**.

# How To Add Tests (JUnit)
Make sure you have added JUnit to your local build dependencies. [Follow JetBrains's Guide here](https://www.jetbrains.com/help/idea/junit.html#intellij). Once you have to enter a JUnit version, specify `5.12.2`. From there, you can hit Alt+Enter on any class to generate tests for their methods.

# Build/Run Instructions
You must have the following dependencies installed:
1) JavaFX (`java-openjfx`)
2) JUnit (for testing) (`org.junit.jupiter:junit-jupiter:5.12.2` from Maven)

**Aleks/Verbum or another person who has built the project successfully should add build instructions here.**

# A Note About Comments
Code should be self-documenting. Comments should be used to explain oddities/"quirks" of the specific function/class/method. See below for an example.
```java
// Below are some examples of BAD uses of comments
// The function foo(), when called, returns the number 1
void foo() {
    return 1;
}

// This creates a dynamic array called 'arr' that has an initial capacity of 1000 entries
ArrayList<Integer> arr = new ArrayList<Integer>(1000);

// Below are some examples GOOD uses of comments

// Constructor will call overridden toString() method containing passed parameters
Object("hello". "world");
```

Comments are here to make abstraction clearer to someone that doesn't know the underlying implementation for some code.