# GDVF - Query Tokenizer
This module is responsible for converting a raw human query into the KNOWN tokens of table names / column names etc of underlying data source (postgresql, mysql, spark etc). Used by the `querytoken-to-sql` module through a http api endpoint.

# Dependencies
##### Python 2.7.9

##### NLTK
Natural Language Toolkit http://www.nltk.org/install.html

`sudo pip install -U nltk`

`python -m nltk.downloader all`

##### Tornado (if you haven't already install it for `query-suggestor` module)
A Python web framework and asynchronous networking library. We used it for exposing a http API for `querytoken-to-sql` module.

`pip install tornado`

# API Endpoints

- `/querytotoken`
   
   - method: `GET`
   - param: `q` - the query to tokenize