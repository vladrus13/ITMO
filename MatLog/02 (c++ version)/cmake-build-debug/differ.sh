cd test_in
counter=0
find . -name "*AC.out" -print0 | while read -d $'\0' file
do
	file_normal="${file:2:-1}t"
	echo "---------------------------------------------------------------------------------------"
	pair="${file_normal:0:-6}.out"
	let "counter +=1"	
	echo "$counter test: Get diff $file_normal and $pair"
	diff $file_normal $pair
done
