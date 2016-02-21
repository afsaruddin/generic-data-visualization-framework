""" Query tokenizer """

import nltk
import numpy as np
from nltk.tag import pos_tag
from nltk.corpus import wordnet as wn

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
            self.db_schema[key] = sorted(self.db_schema[key]);
            
        self.keywords = [item for key in list(self.db_schema) for item in self.db_schema[key]]
        self.keywords += list(self.db_schema)
        self.keywords = sorted(np.unique(self.keywords).tolist())
        
    def get_most_similar_word(self, w1, w2, sim=wn.path_similarity):
        syn_sets1 = wn.synsets(w1)
        syn_sets2 = wn.synsets(w2)
        max_score = -1.0;
        target_word = w1
        for syn_set1 in syn_sets1:
            for syn_set2 in syn_sets2:
                score = sim(syn_set1, syn_set2)
                if score > max_score:
                    max_score = score
                    target_word = w2
         
         
        return (target_word, max_score)
                    
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
        for token in result['select']:
            for keyword in self.keywords:
                print self.get_most_similar_word(token, keyword)
        result['query'] = query
        
        # return query and required tokens
        return result
        
        
qs = QueryTokenizer()
inputs = ['s', 'what type of persons travels ?', 'who are travelling ?', 'what is the tour schedule ?', 'what is the cost for a tour?']
for q in inputs:
    qs.get_final_query_with_tokens(q)