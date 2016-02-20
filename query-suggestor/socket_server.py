# Entry point of `query-suggestor` module. Creates a socket connection to listen
# for user given natural language query. `Tornado` (a Python web framework) is used to 
# manage the websocket

import tornado.web
import tornado.ioloop
import tornado.websocket

class WSHandler(tornado.websocket.WebSocketHandler):
    # To accept all cross-origin traffic
    def check_origin(self, origin):
        return True

    # Invoked when a new WebSocket is opened
    def open(self):
    	print 'Connection opened.'
    	self.write_message("Welcome to my websocket server.")
    
    # Handles incoming messages on the WebSocket
    def on_message(self, message):
        if message == "help":
            self.write_message("Need help, huh?")
        else :
    	    print 'Message received: \'%s\'' % message
            self.write_message(message)

    # Invoked when the WebSocket is closed
    def on_close(self):
      print 'Connection closed.'

# Open the socket connection on `/websocket` route
application = tornado.web.Application([
    (r'/ws', WSHandler)
])

if __name__ == "__main__":
    application.listen(9090)
    tornado.ioloop.IOLoop.instance().start()
