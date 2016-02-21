""" QuerySuggestor API """

from tornado import websocket, web, ioloop
from query_suggestor import QuerySuggestor
import json
from StringIO import StringIO

db_schema = {
                'profession': ['name'],
                'location': ['name'],
                'traveller': ['name', 'gender', 'age', 'employee'],
                'employee': ['name', 'gender', 'age'],
                'tour': ['start', 'end', 'cost', 'employee'],
                'tourpath': ['start', 'end', 'tour', 'location'],
                'tourtraveller': ['tour', 'traveller'],
                'feedback':['tour', 'traveller']
            };

qs = QuerySuggestor(db_schema)

# List to hold the connected clients
clients = []

# Handler for `/ws` endpoint
class SocketHandler(websocket.WebSocketHandler):
    # To accept all cross-origin traffic
    def check_origin(self, origin):
        return True

    # Invoked when a new WebSocket is opened
    def open(self):
        if self not in clients:
            clients.append(self)
        print 'Connection opened.'
    
    # Handles incoming messages on the WebSocket
    def on_message(self, message):
        suggestions = qs.get_suggestions(message)
        self.write_message(json.dumps(suggestions))

    # Invoked when the WebSocket is closed
    def on_close(self):
        if self in clients:
            clients.remove(self)
        print 'Connection closed.'

#Handler for `/pushhistory` endpoint
class PushHistoryHandler(web.RequestHandler):
    def set_default_headers(self):
        self.set_header("Access-Control-Allow-Origin", "*")
        self.set_header("Access-Control-Allow-Headers", "x-requested-with")
        self.set_header('Access-Control-Allow-Methods', 'POST, GET, OPTIONS')

    def post(self):
        history = self.request.body
        print history
        for client in clients:
            client.write_message(history)
        self.write({"success": "true"})

# Expose endpoints for websocket communication and pushing history to clients on `/ws` and `/pushhistory` route
application = web.Application([
    (r'/ws', SocketHandler),
    (r'/pushhistory', PushHistoryHandler)
])

if __name__ == "__main__":
    application.listen(9090)
    ioloop.IOLoop.instance().start()
