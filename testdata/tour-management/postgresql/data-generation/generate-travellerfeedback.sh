#!/bin/bash

max_traveller_line=`less dummy-traveller-ids.txt | wc -l | sed "s/^[[:space:]]*//g"`
max_tour_line=`less dummy-tour-ids.txt | wc -l | sed "s/^[[:space:]]*//g"`
max_feedback_line=`less dummy-feedbacks.txt | wc -l | sed "s/^[[:space:]]*//g"`

getRandomLineNumber() {
	echo "($RANDOM % $1) + 1" | bc
}

makeInsertQuery() {
	rand_tour_line=`getRandomLineNumber ${max_tour_line}`
	rand_tour=`sed -n "${rand_tour_line}p" dummy-tour-ids.txt`

	location_num=`getRandomLineNumber 10`
	for i in `seq 1 ${location_num}`
	do
		rand_traveller_line=`getRandomLineNumber ${max_traveller_line}`
		rand_traveller=`sed -n "${rand_traveller_line}p" dummy-traveller-ids.txt`

		rand_feedback_line=`getRandomLineNumber ${max_feedback_line}`
		rand_feedback=`sed -n "${rand_feedback_line}p" dummy-feedbacks.txt`

		echo "INSERT INTO tm.travellerfeedback(tourId, travellerId, feedback) VALUES(${rand_tour}, ${rand_traveller}, '${rand_feedback}');"
	done
}

for i in `seq 1 1000`
do
	makeInsertQuery
done
