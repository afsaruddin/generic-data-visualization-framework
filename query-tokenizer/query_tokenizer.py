""" Query tokenizer """

import nltk
import numpy as np
from nltk.tag import pos_tag

class QueryTokenizer:
    """ Query tokenizer contains necessary methods
        to process aribtrary queries of general user and 
        return structured tokens
    """
    
    # Initialization 
    def __init__(self, db_schema=None):
        if db_schema is None:
            self.db_schema = {
                'profession': ['name'],
                'location': ['name'],
                'traveller': ['name', 'gender', 'age', 'employee'],
                'employee': ['name', 'gender', 'age'],
                'tour': ['start', 'end', 'cost', 'employee'],
                'tourpath': ['start', 'end', 'tour', 'location'],
                'tourtraveller': ['tour', 'traveller'],
                'feedback':['tour', 'traveller']
            };
        else:
            self.db_schema = db_schema
            
        for key in self.db_schema:
            self.db_schema[key] = self.sort(self.db_schema[key]);
            
        self.keywords = [item for key in list(self.db_schema) for item in self.db_schema[key]]
        self.keywords += list(self.db_schema)
        self.keywords = sorted(np.unique(self.keywords).tolist())
                    
    # Get final suggestion query with selected tokens needed to build SQL             
    def get_final_query_with_tokens(self, query):
        """ Get final query with keywords selected and processed 
        
            Parameters
            ----------
            
            query : string, mandatory
                The initial query 
        """
        
        result = {};
        words = pos_tag(nltk.word_tokenize(query))
        result['select'] = [word for word,pos in words if pos.startswith('NN')]
        result['query'] = query
        
        # return query and required tokens
        return result
        
        
qs = QueryTokenizer()
inputs = ['s', 'what type of persons travels ?', 'who are travelling ?', 'what is the tour schedule ?', 'what is the cost for a tour?']
for q in inputs:
    print qs.get_final_query_with_tokens(q)