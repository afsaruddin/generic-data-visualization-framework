#!/bin/bash

max_profession_line=`less dummy-professions.txt | wc -l | sed "s/^[[:space:]]*//g"`

for i in `seq 1 ${max_profession_line}`
do
	name=`sed -n "${i}p" dummy-professions.txt`
	echo "INSERT INTO tm.profession(name) VALUES('${name}');"
done
