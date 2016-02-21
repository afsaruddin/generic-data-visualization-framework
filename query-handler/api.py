from flask import Flask
from flask_restful import Api

from resources.queryhandler import QueryHandler

app = Flask(__name__)
api = Api(app)

app.config.from_object('settings.configuemodule.DevelopmentConfig')

api.add_resource(QueryHandler, '/queryhandler/')


if __name__ == '__main__':
    app.run(debug=True)