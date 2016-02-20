# GDVF - Query Suggestor
This module is responsible for suggesting possible correct queries to the raw query that a user is typing.
For publishing the suggestions, it will have 2 way WebSocket communications.

# Dependencies
Python 2.7.9

Natural Language Toolkit http://www.nltk.org/install.html

```pyhton
import nltk
nltk.download('punkt')
```

`Tornado` [`pip install tornado`]
