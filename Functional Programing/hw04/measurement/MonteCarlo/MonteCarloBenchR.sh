echo "Start system..." &&
echo "Take files from lib..." &&
cp --force -r ../../src/MonteCarlo MonteCarlo &&
echo "Compile file..." &&
ghc --make -Wall -threaded -rtsopts -with-rtsopts=-N4 -O2 MonteCarloBench &&
echo "Run bench..." &&
./MonteCarloBench --output MonteCarloBench.html &&
echo "Removing usless files..." &&
rm *.hi &&
rm *.o &&
rm -r MonteCarlo
rm MonteCarloBench