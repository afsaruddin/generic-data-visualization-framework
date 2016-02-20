#!/bin/bash

max_location_line=`less dummy-location-ids.txt | wc -l | sed "s/^[[:space:]]*//g"`
max_tour_line=`less dummy-tour-ids.txt | wc -l | sed "s/^[[:space:]]*//g"`

getRandomLineNumber() {
	echo "($RANDOM % $1) + 1" | bc
}

makeInsertQuery() {
	rand_tour_line=`getRandomLineNumber ${max_tour_line}`
	rand_tour=`sed -n "${rand_tour_line}p" dummy-tour-ids.txt`

	location_num=`getRandomLineNumber 10`
	for i in `seq 1 ${location_num}`
	do
		rand_location_line=`getRandomLineNumber ${max_location_line}`
		rand_location=`sed -n "${rand_location_line}p" dummy-location-ids.txt`

		rand_cost=`echo "($RANDOM % 1000) * 100" | bc`

		rand_year=`echo "($RANDOM % 15) + 2000" | bc`
		rand_month=`echo "($RANDOM % 12) + 1" | bc | xargs printf "%02d"`
		rand_day=`echo "($RANDOM % 28) + 1" | bc | xargs printf "%02d"`
		rand_hour=`echo "($RANDOM % 24)" | bc | xargs printf "%02d"`
		rand_min=`echo "($RANDOM % 60)" | bc | xargs printf "%02d"`
		rand_sec=`echo "($RANDOM % 60)" | bc | xargs printf "%02d"`
	
		rand_date="${rand_year}-${rand_month}-${rand_day} ${rand_hour}:${rand_min}:${rand_sec}"

		rand_start_date="to_timestamp('${rand_date}', 'YYYY-MM-DD HH24:MI:SS')"
		rand_end_day_interval=`echo "($RANDOM % 20) + 3" | bc`
		rand_end_date="(to_timestamp('${rand_date}', 'YYYY-MM-DD HH24:MI:SS') + INTERVAL '${rand_end_day_interval} day')"

		echo "INSERT INTO tm.tourpath(startTime, endTime, tourId, locationId) VALUES(${rand_start_date}, ${rand_end_date}, ${rand_tour}, ${rand_location});"
	done
}

for i in `seq 1 1000`
do
	makeInsertQuery
done
