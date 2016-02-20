""" Query suggestor """

# Authors: Akiz Uddin Ahmed <shawpan.du@gmail.com>
#          Abdul Mukit <mukit.sust027@gmail.com>

import nltk
from nltk.tag import pos_tag

class QuerySuggestor:
    """ Query suggestor contains necessary methods
        to process aribtrary queries of general user and 
        return structured query suggestions
    """
    
    # Initialization 
    def __init__(self):
        pass
    
    def extract_entities(self, text):
        for sent in nltk.sent_tokenize(text):
            for chunk in nltk.ne_chunk(nltk.pos_tag(nltk.word_tokenize(sent))):
                if hasattr(chunk, 'node'):
                    print chunk.node, ' '.join(c[0] for c in chunk.leaves())
                    
                    
    # Get structured query suggestions given user query
    def get_suggestions(self, query):
        """ Get query suggestions 
        
            Parameters
            ----------
            
            query : string, mandatory
                The initial query 
        """
        
        words = pos_tag(nltk.word_tokenize(query))
        
        # return words that are nouns
        return [word for word,pos in words if pos.startswith('NN')]
        
        
#qs = QuerySuggestor()
#inputs = ['which locations are covered?', 'what type of persons travels ?', 'who are travelling ?', 'what is the tour schedule ?', 'what is the cost for a tour?']
#for q in inputs:
#    print qs.get_suggestions(q)