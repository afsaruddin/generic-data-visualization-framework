git pull

pkill java
ps ax | grep -i java

mvn clean package

sleep 5
ps ax | grep -i java

nohup java -jar target/query-to-sql-1.0.0.jar &
