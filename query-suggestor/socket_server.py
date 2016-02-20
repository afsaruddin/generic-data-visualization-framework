# Entry point of `query-suggestor` module. Creates a socket connection to listen
# for user given natural language query. `Tornado` (a Python web framework) is used to 
# manage the websocket communtication.

import tornado.web
import tornado.ioloop
import tornado.websocket
from query_suggestor import QuerySuggestor
import json
from StringIO import StringIO

qs = QuerySuggestor()
io = StringIO()

def createResponse(valid, suggestions):
    dict = {}
    dict['valid'] = valid
    dict['suggestions'] = suggestions
    dict['chart'] = 'pie'

    return dict


class WSHandler(tornado.websocket.WebSocketHandler):
    # To accept all cross-origin traffic
    def check_origin(self, origin):
        return True

    # Invoked when a new WebSocket is opened
    def open(self):
    	print 'Connection opened.'
    
    # Handles incoming messages on the WebSocket
    def on_message(self, message):
        sug_arr = qs.get_suggestions(message.strip())
        respJson = createResponse(True, sug_arr)
        io.truncate(0)
        json.dump(respJson, io)
        self.write_message(io.getvalue())

    # Invoked when the WebSocket is closed
    def on_close(self):
      print 'Connection closed.'

# Open the socket connection on `/ws` route
application = tornado.web.Application([
    (r'/ws', WSHandler)
])

if __name__ == "__main__":
    application.listen(9090)
    tornado.ioloop.IOLoop.instance().start()
