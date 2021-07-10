# Haskell: HW 2 – Functors and monads

The second homework assignment will help you to learn the material about monads and functors.
Please note that the assignment matches the material covered in topics 4 through 5 [from here](https://github.com/jagajaga/FP-Course-ITMO).

Before doing this HW, we recommend [these slides](https://slides.com/fp-ctd/lecture-55) as additional material about parser combinators and testing.

Some tasks have advanced versions that must be completed ** in addition to the basic **, but they will also be assessed with additional points.

Tests should be in the `test /` directory

Tests should be run by the command `stack test`.

The assignments explicitly indicate which tests should be implemented for them.

For homework, please use the `lts-16.5` resolver.

# Deadline

00:00 UTC 16 November 2020.

Submit to Github classrooms [here](https://classroom.github.com/a/_IUUxwd3).

# Tasks

## Part 1: `Functor` and its friends

### Task 1

**Tests:** This task should be tested with simple unit tests.

#### Basic version

In HW 1, it was necessary to write a function that sums the numbers in a string. The definition of the task included a strong assumption about the validity of input data. In this task, you need to implement a secure function for finding the sum, namely:

```haskell=
stringSum :: String -> Maybe Int
```
The numbers in the string are separated by one or more whitespace characters.
If at least one element of the string cannot be converted to an integer, then it is necessary to return `Nothing`.

The function should use a Traversable instance for the leaf.

#### Advanced version

It is necessary to write a few simple property tests for this function.

### Task 2

Given the following data type:

```haskell=
data Tree a
  = Branch (Tree a) (Tree a)
  | Leaf a
```

Manually implement the `Functor`,` Applicative`, `Foldable` and` Traversable` instances for `Tree`.

The laws of these typeclasses must be followed.


### Task 3:

Given the following data type:

```haskell=
data NonEmpty a = a :| [a]
```

Manually implement the `Functor`,` Applicative`, `Monad`,` Foldable` and `Traversable` instances for` NonEmpty`.

While implementing instances for `NonEmpty`, you can use the corresponding instances for the list.


## Part 2: Monads and monadic computation

### Task 1

**Tests:** This task should be tested with simple unit tests.

An arithmetic expression (precisely the expression, not the result of its calculation) can be represented by a recursive algebraic data type. Implement this data type so that it would be possible to do the following operations with it:

* Integer constants
* Adding two expressions
* Subtracting expressions
* Multiplying expressions
* Dividing expressions
* Exponentiation of expressions

After that, write a function that takes an expression and evaluates it. Please note that the expression might not be evaluated for various reasons.

```haskell
eval :: Expr -> Either ArithmeticError Int
```

That is, you must create your own data type that denotes an arithmetic error and return `Either` – either the error that occurred or the result. If the expression contains several errors, then any of them can be returned.

It is enough to check only for the following arithmetic errors:

1. Division by 0.
2. Raising to a negative power.

**Hint:** if it's hard to implement a function with `Either` right away, then try `eval :: Expr -> Maybe Int`, then replace `Maybe` with` Either String`, and then `String` can be replaced with your own data type.

### Task 2
Implement the [Simple Moving Average] (https://en.wikipedia.org/wiki/Moving_average) algorithm using the State monad.

```haskell
ghci> moving 4 [1, 5, 3, 8, 7, 9, 6]
[1.0, 3.0, 3.0, 4.25, 5.75, 6.75, 7.5]

ghci> moving 2 [1, 5, 3, 8, 7, 9, 6]
[1.0, 3.0, 4.0, 5.5, 7.5, 8.0, 7.5]
```

## Part 3: Parser combinators

This is the most important part of this homework. The implementation of all the exercises in this part will help you understand how parser combinators work, which is important because they are extremely useful in practice. Before solving the tasks, make sure that you understand the material of the lecture and can solve the basic exercises at the following links:

* [Parser Combinators: Basics](http://www.seas.upenn.edu/~cis194/spring13/hw/10-applicative.pdf)
* [Parser Combinators: Implementing simple parser](http://www.seas.upenn.edu/~cis194/spring13/hw/11-applicative2.pdf)

Main exercises from there are a part of this homework, anyway.

**Tests:** for the tasks of this part, you need to write several unit tests using `hspec`. Property-based tests are optional.

### Task 1: Copy-paste

Here’s the data type of a simple parser combinator:

```haskell=
data Parser s a = Parser { runParser :: [s] -> Maybe (a, [s]) }
```

Unlike the parser proposed in practice, it can work not only with a string, but with any data stream. Manually implement the `Functor`,` Applicative`, `Monad` and` Alternative` instances for this parser.

### Task 2: Basic combinators

Implement the following basic combinators:

1. `ok` – the parser never crashes and does not consume input.
2. `eof` – checks that the parser has reached the end of the data stream (otherwise it crashes).
3. `satisfy` – the parser takes a predicate for an element of the stream and returns the element, absorbing it from the stream, if the predicate for an element is` True`, otherwise it falls.
4.` element` and `stream` - parse one or more stream elements (like` char` and `string`).

### Task 3: Simple parsers

Using the existing combinators (and implementing others if needed), write the following string stream parsers:

1. Parser of correct parenthesis sequences (crashes if the sequence is incorrect, and does not crash if it is correct).
2. Parser of an integer, which can be preceded by a `+` or `-`.

### Task 4: A Difficult Parser

Write a parser for a list of lists of numbers separated by commas. The parser must be of type:

```haskell
listlistParser :: Parser Char [[Int]]
```
All numbers are listed separated by commas. At the beginning of each list is a number – the length of the list. This way, you can understand where each list ends. 

That is, the list

```haskell=
[ [1, 10], [5, -7, 2] ]
```

in the string form can be written as follows:

```haskell=
"2, 1,+10  , 3,5,-7, 2"
```

## Bonus task
This task is aimed at studying the `Cont` monad

```haskell
newtype Cont r a = Cont { runCont :: (a -> r) -> r }
```

This monad has no simple analogue in imperative languages, but it can be useful in some cases.

In this task, you are required to figure out or implement on your own the instances of `Functor`,` Applicative`, and `Monad` for` Cont`.

Then, using this monad, implement a small subset of OS system calls.

Namely, `read`,` write`, `exit`,` yield`, `fork` must be supported.

You should be able to write the following code:

```haskell
main' = do
  x <- readLine
  let str = "Hello, " ++ show x ++ "!" in
  writeLine str
  exit Success
```

It is also required to implement the `kernel` function, which will interpret this code.

Information about the Cont monad can be found [here] (https://slides.com/fp-ctd/lecture-55#/29),
and more information on how to implement what is required is [here] (https://www.dropbox.com/s/z1e1kxi32zl8kbd/DDToOS.pdf?dl=0).

