import FileSystem.FileSystem
import Lens.Micro ((^..), (^?))
import Test.HUnit

mockBigFS :: FS
mockBigFS =
  Dir
    { name = "root",
      contents =
        [ Dir
            { name = "3_level",
              contents =
                [ Dir
                    { name = "low_level",
                      contents =
                        [ File
                            { name = "3_2_0.txt"
                            },
                          File
                            { name = "3_2_1.txt"
                            }
                        ]
                    },
                  File
                    { name = "3_1_0.txt"
                    }
                ]
            },
          Dir
            { name = "2_level",
              contents =
                [ File
                    { name = "2_0.txt"
                    }
                ]
            },
          Dir
            { name = "empty",
              contents = []
            },
          File
            { name = "root_file.txt"
            }
        ]
    }

mockFileFS :: FS
mockFileFS = Dir {name = "root", contents = [File {name = "whaaaaa_you_are_really_go_into_tests?.txt"}]}

test_0_0 :: Test
test_0_0 = TestCase (assertEqual "get file from root" (mockBigFS ^? file "root_file.txt") (Just "root_file.txt"))

test_0_1 :: Test
test_0_1 = TestCase (assertEqual "get not-existing file from root" (mockBigFS ^? file "not_existing_root_file.txt") Nothing)

test_0_2 :: Test
test_0_2 = TestCase (assertEqual "get file from file" (mockFileFS ^? file "whaaaaa_you_are_really_go_into_tests?.txt") (Just "whaaaaa_you_are_really_go_into_tests?.txt"))

test_0_3 :: Test
test_0_3 = TestCase (assertEqual "get not-existing file from file" (mockFileFS ^? file "whaaaaa_you_are_really_go_into_tests_YES.txt") Nothing)

test_0_4 :: Test
test_0_4 = TestCase (assertEqual "get file so far!" (mockBigFS ^? cd "3_level" . cd "low_level" . file "3_2_1.txt") (Just "3_2_1.txt"))

test_0_5 :: Test
test_0_5 = TestCase (assertEqual "get not-existing file so far!" (mockBigFS ^? cd "3_level" . cd "low_level" . file "3_2_2.txt") Nothing)

fileGetNameTests :: [Test]
fileGetNameTests = [test_0_0, test_0_1, test_0_2, test_0_3, test_0_4, test_0_5]

test_1_0 :: Test 
test_1_0 = TestCase (assertEqual "contents of root" (mockBigFS ^.. ls) ["3_level", "2_level", "empty", "root_file.txt"])

test_1_1 :: Test 
test_1_1 = TestCase (assertEqual "contents of folder_0" (mockBigFS ^.. cd "3_level" . ls) ["low_level", "3_1_0.txt"])

test_1_2 :: Test 
test_1_2 = TestCase (assertEqual "contents of folder_1" (mockBigFS ^.. cd "3_level" . cd "low_level" . ls) ["3_2_0.txt", "3_2_1.txt"])

test_1_3 :: Test 
test_1_3 = TestCase (assertEqual "contents of non_exists folder" (mockBigFS ^.. cd "4_level" . ls) [])

fileContentsTests :: [Test]
fileContentsTests = [test_1_0, test_1_1, test_1_2, test_1_3]

tests :: Test
tests = TestList (fileGetNameTests ++ fileContentsTests)

main :: IO Counts
main = runTestTT tests