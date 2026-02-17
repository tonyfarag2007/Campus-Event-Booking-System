# Campus Event Booking System
This project is a campus event booking system written in Java, which uses CSV files to store the data.

# Naming Conventions/Style Guide
Below is the style guide/conventions to use during development:
- Use `snake_case` for variable/function names.
- Use `TitleCase` for class/enum names and **enum values**.

# A note about comments
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