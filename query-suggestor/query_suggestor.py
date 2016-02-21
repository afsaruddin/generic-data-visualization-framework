""" Query suggestor """

# Authors: Akiz Uddin Ahmed <shawpan.du@gmail.com>
#          Abdul Mukit <mukit.sust027@gmail.com>

import nltk
#nltk.download('punkt')

class QuerySuggestor:
    """ Query suggestor contains necessary methods
        to process aribtrary queries of general user and 
        return structured query suggestions
    """
    
    # Initialization 
    def __init__(self):
        pass
    
    # Get structured query suggestions given user query
    def get_suggestions(self, query):
        """ Get query suggestions 
        
            Parameters
            ----------
            
            query : string, mandatory
                The initial query 
        """
        return nltk.word_tokenize(query)
        
        