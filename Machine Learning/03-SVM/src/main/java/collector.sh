for file in `find ru -type f -name "*"`
do
	echo "";
	echo "";
	echo "";
	echo "----------------------------------------------------------";
	echo "";
	echo "";
	echo "";
	echo -e -- ${file} -- && cat -- ${file};
done > colletion.txt
