# GDVF - Query Tokenizer
This module is responsible for converting a raw human query into the KNOWN tokens of table names / column names etc of underlying data source (postgresql, mysql, spark etc). Used by the `querytoken-to-sql` module through a http api endpoint.

# Dependencies

##### Python 2.7.9

##### NLTK
Natural Language Toolkit http://www.nltk.org/install.html

`sudo pip install -U nltk`

`python -m nltk.downloader all`

##### [Tornado](http://www.tornadoweb.org/en/stable/) (if you haven't already install it for `query-suggestor` module)
A Python web framework and asynchronous networking library. We used it for exposing a http API for `querytoken-to-sql` module.

`pip install tornado`

# API Endpoints

- Url: http://169.44.61.115:9091/querytotoken
- Method: GET
- Param:
..1. `q` - the query to tokenize
- Example:

```bash
    akiz@akiz-mac$ curl -i -H "Content-Type: application/json" 'http://169.44.61.115:9091/querytotoken?q=show%20tour%20cost'
    HTTP/1.1 200 OK
    Date: Mon, 22 Feb 2016 06:31:59 GMT
    Content-Length: 55
    Etag: "4d79dcec5f38721d43e0d48b4b90059d39674718"
    Content-Type: application/json; charset=UTF-8
    Server: TornadoServer/4.3
    
    {"query": "show tour cost", "select": ["cost", "tour"]}
```

# Running on server
```bash
    python tokenizer_api.py
```

