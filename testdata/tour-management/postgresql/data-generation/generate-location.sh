#!/bin/bash

max_location_line=`less dummy-locations.txt | wc -l | sed "s/^[[:space:]]*//g"`

for i in `seq 1 ${max_location_line}`
do
	name=`sed -n "${i}p" dummy-locations.txt`
	echo "INSERT INTO tm.location(name) VALUES('${name}');"
done
