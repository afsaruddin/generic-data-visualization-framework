# GDVF - Query Suggestor
This module is responsible for suggesting possible correct queries to the raw query that a user is typing.
For publishing the suggestions, it will have 2 way WebSocket communications.

# Dependencies
##### Python 2.7.9

##### NumPy
NumPy is the fundamental package for scientific computing with Python.

A `C` compiler, either `GCC` or `clang`, is needed because the `numpy` library we are using has some C extensions, which will need to be compiled.
We suggest to install the whole `build-essential` and `python-dev`

`apt-get install build-essential python-dev`

##### [Tornado](http://www.tornadoweb.org/en/stable/)
A Python web framework and asynchronous networking library. We used it for establishing the websocket communication between the users and for exposing a http API for `Query Handler` module by which it will feed query history to all currently connected user.

`pip install tornado`

# API Endpoints
##### websocket endpoint
- `ws`://ip:port`/ws` (recieves a query and sends suggestions for that)
##### http endpoint
- `/pushhistory` - recievs query(json data) as `request body` that is pushed to all connected clients
  
  - method: `POST`
  
  ###### Example
   `curl -H "Content-Type: application/json" -X POST -d '{"query":"tour cost employee"}' http://169.44.61.115:9090/pushhistory`

# Running on server
- `python suggestor_api.py`