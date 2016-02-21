""" Query suggestor """

import nltk
import numpy as np
from nltk.tag import pos_tag

class QuerySuggestor:
    """ Query suggestor contains necessary methods
        to process aribtrary queries of general user and 
        return structured query suggestions
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
        self.keywords = self.sort(np.unique(self.keywords).tolist())
        
    def sort(self, items):
        return np.sort(items).tolist()
    
    # Get next query suggestions based on db schema   
    def get_suggestions_from_schema(self, query):
        last_word = query.split()[-1]
        query_wo_lastword = ' '.join(query.split()[:-1])
        if ( len(query_wo_lastword) == 0 ): query_wo_lastword= 'show'

        if (last_word in self.db_schema):
            return [query + ' ' + next_word for next_word in self.db_schema[last_word]]
        if (any(item.startswith(last_word) for item in self.keywords)) :
            entities = [item for item in self.keywords if item.startswith(last_word)]
            
            return [query_wo_lastword + ' ' + next_word for next_word in entities]
            
        return [query + ' ' + next_word for next_word in list(self.db_schema.keys())]
            
    def extract_entities(self, text):
        for sent in nltk.sent_tokenize(text):
            for chunk in nltk.ne_chunk(nltk.pos_tag(nltk.word_tokenize(sent))):
                if hasattr(chunk, 'node'):
                    print chunk.node, ' '.join(c[0] for c in chunk.leaves())
                    
    # Get final suggestion query with selected tokens needed to build SQL             
    def get_final_query(self, query):
        """ Get final query with keywords selected and processed 
        
            Parameters
            ----------
            
            query : string, mandatory
                The initial query 
        """
        
        result = {'suggestions': []};
        suggestion_text = {};
        words = pos_tag(nltk.word_tokenize(query))
        suggestion_text['select'] = [word for word,pos in words if pos.startswith('NN')]
        suggestion_text['text'] = query
        result['suggestions'] = [suggestion_text]
        
        # return suggestions and required tokens
        return result
                    
    # Get query suggestions given user query
    def get_suggestions(self, query):
        """ Get query suggestions 
        
            Parameters
            ----------
            
            query : string, mandatory
                The initial query 
        """
        
        result = {'suggestions': []};
        suggestions_on_db_schema = self.get_suggestions_from_schema(query)
        result['suggestions'] = [{
            'text': text
        } for text in suggestions_on_db_schema]
        
        # return suggestions
        return result
        
        
qs = QuerySuggestor()
inputs = ['s', 'what type of persons travels ?', 'who are travelling ?', 'what is the tour schedule ?', 'what is the cost for a tour?']
for q in inputs:
    print qs.get_suggestions(q)