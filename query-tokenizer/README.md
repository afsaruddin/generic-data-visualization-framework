# GDVF - Query Tokenizer
This module is responsible for converting a raw human query into the KNOWN tokens of table names / column names etc of underlying data source (postgresql, mysql, spark etc).

# Dependencies
Python 2.7.9

Natural Language Toolkit http://www.nltk.org/install.html

`sudo pip install -U nltk`

`python -m nltk.downloader all`

Tornado (if you haven't already install it for `QuerySuggestor` module)
`pip install tornado`
