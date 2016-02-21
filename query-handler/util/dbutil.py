
import psycopg2
import pprint

class DbUtil(object):
    @staticmethod
    def execute_query(self, query):

        conn_string = "host='localhost' dbname='gdvf' user='gdvf_user' password='gdvf_PASS'"

        # print the connection string we will use to connect
        print "Connecting to database\n	->%s" % (conn_string)

        # get a connection, if a connect cannot be made an exception will be raised here
        conn = psycopg2.connect(conn_string)

        # conn.cursor will return a cursor object, you can use this cursor to perform queries
        cursor = conn.cursor()

        # execute our Query
        cursor.execute(query)

        # retrieve the records from the database
        records = cursor.fetchall()


        # Will remove this
        pprint.pprint(records)

        return records