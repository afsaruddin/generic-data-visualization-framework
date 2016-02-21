from flask_restful import abort, Resource, reqparse


from controller.querycontroller import QueryHandlerController
from controller.queryhistorycontroller import QueryHistoryController
import pprint

from flask import Flask, jsonify, request

class QueryHandler(Resource):

    def __init__(self):
        self.query_handler_cnt = QueryHandlerController()
        self.query_history_cnt = QueryHistoryController()
        pass

    def post(self, category):

        #print('All the code is done')
        #print ('category is {} ').format(category)

        json_data = request.get_json(force=True)

        #print(json_data['text'])

        processed_query = self.query_handler_cnt.process_query(category, json_data)

        if processed_query['success'] == True:

            query_data = self.query_handler_cnt.execute_query(processed_query['data']['sql'])
            self.query_history_cnt.publish_history(json_data['text'])

            return jsonify( query_data)
        else:
            return jsonify({'success': False})


    def delete(self):
        abort(501, message="Operation not supported")

    def put(self):
        abort(501, message="Operation not supported")

    def get(self):
        abort(501, message="Operation not supported")
