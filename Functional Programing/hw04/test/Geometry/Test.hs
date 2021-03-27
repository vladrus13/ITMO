import Geometry.Point (Point (..), crossProduct, distance, doubleArea, fastDistance, minus, perimeter, plus, scalarProduct)
import Test.HUnit

test_plus_1 :: Test
test_plus_1 = TestCase $ assertEqual "Plus null points" (Point 0 0) (plus (Point 0 0) (Point 0 0))

test_plus_2 :: Test
test_plus_2 = TestCase $ assertEqual "Plus not-null points" (Point 50 (-50)) (plus (Point 10 (-40)) (Point 40 (-10)))

plusTests :: [Test]
plusTests = [test_plus_1, test_plus_2]

test_minus_1 :: Test
test_minus_1 = TestCase $ assertEqual "Munus null points" (Point 0 0) (minus (Point 0 0) (Point 0 0))

test_minus_2 :: Test
test_minus_2 = TestCase $ assertEqual "Munus not-null points" (Point 10 (-10)) (minus (Point 50 (-50)) (Point 40 (-40)))

minusTests :: [Test]
minusTests = [test_minus_1, test_minus_2]

test_fastDistance_1 :: Test
test_fastDistance_1 =
  TestCase $
    assertBool
      "Munus not-null points"
      (abs (distance (Point 1 1) (Point 0 0) - fastDistance (Point 1 1) (Point 0 0)) < 0.000001)

test_fastDistance_2 :: Test
test_fastDistance_2 =
  TestCase $
    assertBool
      "Munus not-null points"
      (abs (distance (Point 50 (-50)) (Point 40 (-40)) - fastDistance (Point 50 (-50)) (Point 40 (-40))) < 0.000001)

test_fastDistance_3 :: Test
test_fastDistance_3 =
  TestCase $
    assertBool
      "Null points"
      (abs (distance (Point 1 0) (Point 1 0) - fastDistance (Point 1 0) (Point 1 0)) < 0.000001)

test_fastDistance_4 :: Test
test_fastDistance_4 =
  TestCase $
    assertBool
      "Munus not-null points"
      (abs (distance (Point 50 (-50)) (Point 40 (-40)) - fastDistance (Point 50 (-50)) (Point 40 (-40))) < 0.000001)

fastDistanceTests :: [Test]
fastDistanceTests = [test_fastDistance_1, test_fastDistance_2, test_fastDistance_4, test_fastDistance_3]

test_scalarProduct_1 :: Test
test_scalarProduct_1 = TestCase $ assertEqual "First points" 0 (scalarProduct (Point 0 0) (Point 0 0))

test_scalarProduct_2 :: Test
test_scalarProduct_2 = TestCase $ assertEqual "Second points" 16 (scalarProduct (Point 2 4) (Point 4 2))

scalarProductTests :: [Test]
scalarProductTests = [test_scalarProduct_1, test_scalarProduct_2]

test_crossProduct_1 :: Test
test_crossProduct_1 = TestCase $ assertEqual "First points" 0 (crossProduct (Point 0 0) (Point 0 0))

test_crossProduct_2 :: Test
test_crossProduct_2 = TestCase $ assertEqual "Second points" (-12) (crossProduct (Point 2 4) (Point 4 2))

crossProductTests :: [Test]
crossProductTests = [test_crossProduct_1, test_crossProduct_2]

test_perimeter_1 :: Test
test_perimeter_1 = TestCase $ assertEqual "Null figure" 0 (perimeter [Point 0 0, Point 0 0, Point 0 0])

test_perimeter_2 :: Test
test_perimeter_2 = TestCase $ assertEqual "Line" 2 (perimeter [Point 0 0, Point 0 1])

test_perimeter_3 :: Test
test_perimeter_3 = TestCase $ assertEqual "Square" 12 (perimeter [Point 0 0, Point 3 0, Point 3 3, Point 0 3])

perimeterTests :: [Test]
perimeterTests = [test_perimeter_1, test_perimeter_2, test_perimeter_3]

test_doubleArea_1 :: Test
test_doubleArea_1 = TestCase $ assertEqual "Null figure" 0 (doubleArea [Point 0 0, Point 0 0])

test_doubleArea_2 :: Test
test_doubleArea_2 = TestCase $ assertEqual "Line" 0 (doubleArea [Point 0 0, Point 0 1])

test_doubleArea_3 :: Test
test_doubleArea_3 = TestCase $ assertEqual "Triangle" 1 (doubleArea [Point 0 0, Point 0 1, Point 1 1])

test_doubleArea_4 :: Test
test_doubleArea_4 = TestCase $ assertEqual "Polygon" 15 (doubleArea [Point 0 3, Point 1 4, Point 3 4, Point 4 2, Point 3 1, Point 1 2])

doubleAreaTests :: [Test]
doubleAreaTests = [test_doubleArea_1, test_doubleArea_2, test_doubleArea_3, test_doubleArea_4]

tests :: Test
tests = TestList (plusTests ++ minusTests ++ scalarProductTests ++ crossProductTests ++ perimeterTests ++ doubleAreaTests ++ fastDistanceTests)

main :: IO Counts
main = runTestTT tests