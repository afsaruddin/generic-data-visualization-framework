
class QueryHandlerController:
    def __init__(self):
        pass

    def process_query(self, category, query):
        ret_values = []

        print ('Query handlers method is called')
        # TODO : Prepare a hardCoded SQL for now

        ret_values = {'status': 'success', 'sqlquery': 'Select * from tm.tour limit 20'}

        return ret_values

    def execute_query(self, sql_query):

        print ('Query handlers execute_query method is called')




        pass