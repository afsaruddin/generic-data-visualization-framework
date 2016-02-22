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

##### Tornado
A Python web framework and asynchronous networking library. We used it for establishing the websocket communication between the users and for exposing a http API for `Query Handler` module by which it will feed query history to all currently connected user.

`pip install tornado`

# API Endpoints
##### websocket endpoint
- `ws`://ip:port`/ws` (recieves a query and sends suggestions for that)
##### http endpoint
- `/pushhistory` - recievs query(json data) as `request body` and that is pushed to all connected clients
  
  - method: `POST`