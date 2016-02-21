from flask_restful import abort, Resource


class QueryHandler(Resource):
    def get(self, category, query):
        print('All the code is done')
        print ('category is {} ').format(category)
        print ('Query is {} ').format(query)
        return 'test is done', 200

    def delete(self):
        abort(501, message="Operation not supported")

    def put(self):
        abort(501, message="Operation not supported")