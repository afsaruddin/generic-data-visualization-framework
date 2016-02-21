from flask import current_app
import psycopg2

class DbUtil(object):
    @staticmethod
    def execute_query( query):

        try:

            conn_string = current_app.config.get('DATABASE_URI')
            # get a connection, if a connect cannot be made an exception will be raised here
            conn = psycopg2.connect(conn_string)

            # conn.cursor will return a cursor object, you can use this cursor to perform queries
            cursor = conn.cursor()

            # execute our Query
            cursor.execute(query)

            # retrieve the records from the database
            column_names = [desc[0] for desc in cursor.description]
            records = cursor.fetchall()

            return {'column_names': column_names,
                    'data': records,
                    'success': True}
        except:
            return {'success': False}