echo "Start system..." &&
echo "Take files from lib..." &&
cp --force -r ../../src/HashTable HashTable &&
echo "Compile file..." &&
ghc --make -Wall -threaded -rtsopts -with-rtsopts=-N -O3 HashTableBench &&
echo "Run bench..." &&
./HashTableBench --output HashTableBench.html &&
echo "Removing usless files..." &&
rm *.hi &&
rm *.o &&
rm -r HashTable &&
rm HashTableBench