""" Query suggestor """


import numpy as np

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
        self.keywords = sorted(np.unique(self.keywords).tolist())
        
    
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