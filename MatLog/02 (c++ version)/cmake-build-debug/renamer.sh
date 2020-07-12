cd test_in
find . -name "*AC.out" -print0 | while read -d $'\0' file
do
	normal_name="${file:2:-6}.out"
	pair="${file:2}"
	echo "$file: from $pair to $normal_name"
	mv "$pair" "$normal_name"
done
