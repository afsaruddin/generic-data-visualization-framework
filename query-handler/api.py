from flask import Flask
from flask_restful import Api

from resources.queryhandler import QueryHandler

app = Flask(__name__)
api = Api(app)


api.add_resource(QueryHandler, '/queryHandler/<category>/<query>')


if __name__ == '__main__':
    app.run(debug=True)