from flask_restful import abort, Resource
from controller.querycontroller import QueryHandlerController
from controller.queryhistorycontroller import QueryHistoryController


class QueryHandler(Resource):

    def __init__(self):
        self.query_handler_cnt = QueryHandlerController()
        self.query_history_cnt = QueryHistoryController()
        pass

    def get(self, category, query):

        print('All the code is done')
        print ('category is {} ').format(category)
        print ('Query is {} ').format(query)

        self.query_handler_cnt.process_query(category, query)


        # Will change this later
        self.query_history_cnt.publish_history(query)

        return 'test is done', 200

    def delete(self):
        abort(501, message="Operation not supported")

    def put(self):
        abort(501, message="Operation not supported")