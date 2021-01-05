#!/bin/bash

start=$1
count=$2
for (( i=0; i < count; i++ ))
do
	current=$(($start+$i))
	echo "Run $current"
	wine "/home/vladkuznetsov/Vl/Projects/Reverse/IDA 7.2/ida64.exe" "/home/vladkuznetsov/Vl/Projects/Reverse/HW-08/14/100libs_hurt/libs/verify$current.so"
done
