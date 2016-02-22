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
    
    # get similarity between two words
    def get_similarity(self, w1, w2, sim=wn.path_similarity):
        syn_sets1 = wn.synsets(w1)
        syn_sets2 = wn.synsets(w2)
        max_score = -1.0;
        for syn_set1 in syn_sets1:
            for syn_set2 in syn_sets2:
                score = sim(syn_set1, syn_set2)
                if score > max_score:
                    max_score = score
         
         
        return max_score
        
    # Get the most similar words given token    
    def get_most_similar_words(self, token, targets):
        max_score = -100.0
        target_words = []
        scores = []
        for target in targets:
            score = self.get_similarity(token, target)
            scores.append(score)
        
        max_score = max(scores)
        for index, score in enumerate(scores):
            if score == max_score and score > 0.25:
                target_words.append(targets[index])
             
        return target_words
        
    # Get select tokes
    def get_select_tokens(self, words):
        select_result = [word.lower() for word,pos in words if pos.startswith('NN') or pos == 'WP']
        selects = []
        for select in select_result:
            if select in self.keywords:
                selects.append(select)
            else:
                selects += self.get_most_similar_words(select, self.keywords)
         
        return np.unique(selects).tolist()
                    
    # Get final suggestion query with selected tokens needed to build SQL             
    def get_final_query_with_tokens(self, query):
        """ Get final query with keywords selected and processed 
        
            Parameters
            ----------
            
            query : string, mandatory
                The initial query 
        """
        
        result = {};
        result['query'] = query
        words = pos_tag(nltk.word_tokenize(query))
        #grammar = "NP: {<DT>?<JJ>*<NN>}"
        #cp = nltk.RegexpParser(grammar)
        #tree = cp.parse(words);
        #print(tree)
        #NPs = list(tree.subtrees(filter=lambda x: x.label()=='NP' or x.label().startswith('NN') or x.label()=='WP'))
        #print [' '.join(NP.leaves()[0]) for NP in NPs ]
        #print [(word, pos) for word,pos in words if pos.startsWith('NN') or pos == 'WP']
        result['select'] = self.get_select_tokens(words)
        
        # return query and required tokens
        return result
        
        
#qs = QueryTokenizer()
#inputs = ['s', 'what type of persons travels ?', 'who are travelling ?', 'what is the tour schedule ?', 'what is the cost for a tour?']
#for q in inputs:
#    print qs.get_final_query_with_tokens(q)