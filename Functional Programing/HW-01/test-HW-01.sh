for file in `find . -type f -name "*package.yaml"`
do
    file_normal="${file:2:-12}" &&
    echo "---------------------------------------" &&
    echo "Test: $file_normal" &&
    echo "" &&
    ext="${file_normal#*.}" &&
    (cd "$file_normal" && stack test --silent)
done