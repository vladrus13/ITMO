# Haskell: HW 1 – Basic language constructs and more 

The first homework assignment tests your understanding of basic language constructs, as well as standard functions and basic interfaces.

Please note that the assignment matches the material covered in topics 2 through 4 [from here](https://github.com/jagajaga/FP-Course-ITMO).

Before doing this HW, we recommend [these slides](https://slides.com/fp-ctd/lecture-55) as additional material about testing. 

Some tasks have advanced versions that should be done **in addition to regular tasks**, but those will also be assessed with extra points. 

This homework assignment requires you to get acquainted with some testing libraries and do the tests using them.

Tests should be in the `test /` directory

Tests should run with `stack test`.

The tests to be done are explicitly indicated in the tasks. 

For the homework, please use the `lts-16.5` resolver.

# Deadline

00:00 (UTC+3) 5 Октября 2020.

## Part 1: Algebraic Data Types

In this part, it is allowed to use automatic `deriving` only for `Show`. The rest of the instances must be implemented by yourself.

### Task 1: Days of the Week

**Tests:** This task should be tested with simple unit tests.

Define your own data type for _Day of the Week_. Implement the following functions:

1.` nextDay`: Returns the day that follows the day of the week that was given as input.
2. `afterDays`: Returns the day of the week after a given number of days has passed.
3. `isWeekend`: Checks if the day counts as being on the weekend.
4. `daysToParty`: Displays the number of days left until Friday.

### Task 2: Natural numbers

**Tests:** This task should be tested with simple unit tests. Property-based tests are optional (and are evaluated with additional points).

#### Basic task

The datatype for natural numbers is defined as follows:

```haskell
data Nat = Z | S Nat
```

Implement the following operations (which should be implemented entirely on your own):

1. Addition of two natural numbers.
2. Multiplication of two natural numbers.
3. Subtraction of natural numbers.
4. Converting integers to natural numbers and vice versa.
5. Checking natural numbers for equality.
6. Comparison of natural numbers.

#### Advanced task

Additionally, you need to implement the following functions:

7. Checking a natural number for parity.
8. Integer division of natural numbers.
9. Getting a remainder of dividing a natural number by another.

### Task 3: Greens

**Tests:** This task should be tested with simple unit tests.

The data type of a binary tree has two constructors:

1. Leaf node, contains no data.
2. Branch node,  contains a _non-empty_ list of identical values ​​and has two children.

A _binary tree_ is called a _binary search tree_ if it satisfies the following conditions: the values ​​of all the elements in the left subtree are smaller than the value ​​in the node, and the values ​​of all the elements in the right subtree are greater than the values ​​in the node.

Implement the following operations on a _binary search tree_:

1. Checking if a tree is empty. 
2. Calculating the size of the tree (that is, the number of elements in it).
3. Search for a given item in the tree (use the fact that the tree is a search tree).
4. Inserting a new item into the _binary search tree_. If the inserted element is already in the tree, then it is necessary to add it to the list of the node in which this element is located. It should be noted here that if a tree node has a list of elements, then this list is always non-empty.
5. Function `fromList` that creates a tree from a list of elements.
6. A function that removes a given element from the tree.

## Part 2: Roll up

### Task 1: A Foldable instance for Tree

**Tests:** This task should be tested with simple unit tests. Property-based tests are optional (and are evaluated with additional points).

In this assignment, **be sure** to use the `-XInstanceSigs` language extension and specify function types in instances. It is necessary to implement both `foldMap` and` foldr` functions.

Implement a Foldable instance for the Tree type. The instance must be implemented in such a way that the property: `toList. fromList ≡ sort` holds true.

### Task 2: Breaking it down

**Tests:** This task should be tested with simple unit tests. Property-based tests are optional (and are evaluated with additional points).

#### Basic task

Using fold, implement the `splitOn` function, which splits a list into sublists by a given item.

```haskell
ghci> splitOn '/' "path/to/file"
["path", "to", "file"]
```

Note that the splitOn function always returns a non-empty list of elements. This should be reflected in its type. The example given is for a regular list, even though that solution is not entirely correct.

#### Advanced task

Additionally, implement the function (again using fold) `joinWith`, the opposite of` splitOn`. Moreover, your implementation must satisfy the property:

`joinWith x . splitOn x ≡ id`.

```haskell
ghci> joinWith '/' ["path", "to", "file"]
"path/to/file"
```

Note that the `joinWith` function accepts a non-empty list.

## Part 3: Monoids

### Task 1

**Tests:** This task should be tested with simple unit tests.

#### Basic task

Write a function that takes a list of lists inside Maybe and returns all the inner lists, concatenated.

```haskell
ghci> maybeConcat [Just [1,2,3], Nothing, Just [4,5]]
[1,2,3,4,5]
```

#### Advanced task

The function must accept an arbitrary set of `Either`, where both` Left` and `Right` contain some monoidal elements. It must return a pair with the results of the monoidal concatenation of elements inside` Left` and elements inside `Right` separated.

```haskell
ghci> eitherConcat [Left (Sum 3), Right [1,2,3], Left (Sum 5), Right [4,5]]
(Sum {getSum = 8}, [1,2,3,4,5])
```

### Task 2

** Tests: ** This task should be tested with simple unit tests.

#### Basic task

Implement instances of algebraic typeclasses for the following data structures. Your instances must comply with the laws for these structures.

1. `Semigroup` for `data NonEmpty a = a :| [a]`.
2. `Semigroup` for the data type `data ThisOrThat a b = This a | That b | Both a b`.

#### Advanced task

Additionally, implement the following instances:

1. `Semigroup` and `Monoid` for strings, combined with `'.'`.

```haskell
ghci> Name "root" <> Name "server"
Name "root.server"
```

2. `Semigroup` and `Monoid` for `newtype Endo a = Endo { getEndo :: a -> a }`.

