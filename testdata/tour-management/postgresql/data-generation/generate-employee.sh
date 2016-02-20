#!/bin/bash

max_gender_line=`less dummy-genders.txt | wc -l | sed "s/^[[:space:]]*//g"`
max_age_line=`less dummy-ages.txt | wc -l | sed "s/^[[:space:]]*//g"`
max_name_line=`less dummy-names.txt | wc -l | sed "s/^[[:space:]]*//g"`

getRandomLineNumber() {
	echo "($RANDOM % $1) + 1" | bc
}

makeInsertQuery() {
	rand_gender_line=`getRandomLineNumber ${max_gender_line}`
	rand_gender=`sed -n "${rand_gender_line}p" dummy-genders.txt`

	rand_age_line=`getRandomLineNumber ${max_age_line}`
	rand_age=`sed -n "${rand_age_line}p" dummy-ages.txt`

	rand_name_line=`getRandomLineNumber ${max_name_line}`
	rand_name=`sed -n "${rand_name_line}p" dummy-names.txt`

	echo "INSERT INTO tm.employee(name, gender, age) VALUES('${rand_name}', '${rand_gender}', ${rand_age});"
}

for i in `seq 1 1000`
do
	makeInsertQuery
done
