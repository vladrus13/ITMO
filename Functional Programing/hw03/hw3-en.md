# **Haskell: Homework 3 -- Use the language to create a practical application.**

The second homework assignment tests the understanding of the basic languages that are used in production programming. Please note that the task corresponds to the material that is covered in topics 5 to 7 [here](https://github.com/jagajaga/FP-Course-ITMO).

You need to create tests for the task.

Documentation in [Haddock](https://www.haskell.org/haddock/) format will be a significant bonus.

If you see ambiguity in the wording of the homework assignment, then please contact the teachers by email or in the official channel in slack.

The task is rated at a maximum of 10 points + bonuses.

For formatting, you can use both the standard style guide and [ormolu](https://hackage.haskell.org/package/ormolu).

# **Deadline**

23: 59 (UTC+3) December 20, 2020.

**File Manager**

It is necessary to implement a program for manipulating files in the Haskell language, using monad transformer constructs and using the IO capabilities of the monad. Any techniques that go beyond the current course will be welcome.

The file manager has a simple functionality for viewing/creating/deleting files and folders.

It is important to work with the file system in its pure form. Start the implementation by formalizing the type of description of the file system and its components, as well as the types for commands. After the program is finished, the results of its action must exist in your file system and the directories that you have manipulated.

In tests, you need to take advantage of the&quot; purity &quot;of functions for working with the file system and instead of the real file system, which is accessed via IO, use a clean representation of the&quot; toy &quot; file system, the same for each run.

Pay attention to error handling.

It is forbidden to use the built-in OS directory switching (SetCurrentDirectory).

P.S. You do not need to read the entire file system into RAM. Read files and folders only when the user requires you to do so via the command line. Similarly with the write to file command. You write the most ordinary file manager, just covered with tests :). P.P.S. Use type classes to abstract functionality, use one stack of monads in the implementation and another in tests.

To work with a real file system, use the [directory](https://hackage.haskell.org/package/directory-1.3.6.1) library. For parsing command-line arguments, we recommend [optparse-applicative](https://hackage.haskell.org/package/optparse-applicative).

The necessary functionality:

1. command line interface (can be implemented as an interactive command line);
2. navigate through directories;
3. show the contents of the current directory;
4. create a folder/file;
5. to display the contents of the file;
6. delete a folder/file;
7. write text information to a file;
8. search for a file by name in the current directory and its subdirectories and output the path to the file;
9. display information about a given file:
  - the way;
  - access rights;
  - file type;
  - time of creation and / or modification;
  - size;
10. to display information about directory:
  - size;
  - the way;
  - number of files inside;
  - access rights;

Example:
```
$ my-best-file-manager ~/
/users/my_user/ > help
cd <folder> -- go to directory
dir -- show the contents of the current directory
ls <folder> -- show the contents of the selected directory
create-folder "folder-name" -- create a directory in the current folder
cat <file> -- show file contents
create-file "file-name" -- create an empty file in the current directory
remove <folder | file> -- delete the selected directory or file
write-file <file> "text" -- write text to a file
find-file "file-name" -- search for a file in the current directory and subdirectories
information <file> -- show information about the file
information <folder> -- show directory information
help -- show usage guide
exit -- program shutdown


/users/my_user/ > cd folder-that-exist
/users/my_user/folder-that-exist > dir
a
b
c
/users/my_user/folder-that-exist > ls a
a is not a folder
/users/my_user/folder-that-exist > cat a
aaaaaa
/users/my_user/folder-that-exist > cd no-folder
no-folder does not exist
/users/my_user/folder-that-exist > create-file d
/users/my_user/folder-that-exist > dir
a
b
c
d
/users/my_user/folder-that-exist > exit
```

**Bonuses**

We issue additional points for:

- Additional commands (provided that they offer interesting semantics, help in Russian is not an additional command)
- The interface for this program (TUI or GUI) (a lot of bonuses)
- Implementation of property tests with random generation of file systems
- For supporting going outside of the cd ../../.. / startup directory. If you run into root -- throw out the error that .. doesn&#39;t exist.
