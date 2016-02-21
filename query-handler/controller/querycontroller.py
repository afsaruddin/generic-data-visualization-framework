from flask import current_app
from util.httputil import  HttpUtil
import pprint



class QueryHandlerController:
    def __init__(self):
        pass

    def process_query(self, category, query):
        ret_values = []

        #print ('process_query method is called')
        #print(query)
        url = current_app.config.get('QUERY_PROCESSING_URL')


        pprint.pprint( HttpUtil.post_url( url ))

        print ('Quries will be proceed soon')


        return ret_values

    def execute_query(self, sql_query):

        print ('Query handlers execute_query method is called')




        pass