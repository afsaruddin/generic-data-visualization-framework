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

- `/querytotoken`
   
   - method: `GET`
   - param: `q` - the query to tokenize

    ###### Example
   `curl http://169.44.61.115:9091/querytotoken?q=show%20tour%20cost`

# Running on server
- `python tokenizer_api.py`