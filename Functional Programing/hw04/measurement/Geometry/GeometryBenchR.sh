echo "Start system..." &&
echo "Take files from lib..." &&
cp --force -r ../../src/Geometry Geometry &&
echo "Compile file..." &&
ghc --make -Wall -threaded -rtsopts -with-rtsopts=-N -O3 GeometryBench &&
echo "Run bench..." &&
./GeometryBench --output GeometryBench.html &&
echo "Removing usless files..." &&
rm *.hi &&
rm *.o &&
rm -r Geometry &&
rm GeometryBench