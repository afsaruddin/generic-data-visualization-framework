#!/bin/bash

max_employee_line=`less dummy-employee-ids.txt | wc -l | sed "s/^[[:space:]]*//g"`

getRandomLineNumber() {
	echo "($RANDOM % $1) + 1" | bc
}

makeInsertQuery() {
	rand_employee_line=`getRandomLineNumber ${max_employee_line}`
	rand_employee=`sed -n "${rand_employee_line}p" dummy-employee-ids.txt`

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

	echo "INSERT INTO tm.tour(startTime, endTime, costPerPerson, planOwnerId) VALUES(${rand_start_date}, ${rand_end_date}, ${rand_cost}, ${rand_employee});"
}

for i in `seq 1 1000`
do
	makeInsertQuery
done
