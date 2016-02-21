# Entry point of `query-suggestor` module. Creates a socket connection to listen
# for user given natural language query. `Tornado` (a Python web framework) is used to 
# manage the websocket communtication.

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
        message = message.strip()
        querysubmit = False

        if message.endswith('-qs'):
            querysubmit = True
            message = message.rstrip('-qs').strip()

        if querysubmit:
            final_query = qs.get_final_query(message)
            self.write_message(json.dumps(final_query))
        else:
            suggestions = qs.get_suggestions(message)
            self.write_message(json.dumps(suggestions))

    # Invoked when the WebSocket is closed
    def on_close(self):
        if self in clients:
            clients.remove(self)
        print 'Connection closed.'

# Open the socket connection on `/ws` route
application = web.Application([
    (r'/ws', SocketHandler)
])

if __name__ == "__main__":
    application.listen(9090)
    ioloop.IOLoop.instance().start()
