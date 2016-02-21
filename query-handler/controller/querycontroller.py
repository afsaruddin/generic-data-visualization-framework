from flask import current_app
from util.httputil import  HttpUtil
from util.dbutil import DbUtil


class QueryHandlerController:
    def __init__(self):
        pass

    def process_query(self, query):

        url = current_app.config.get('QUERY_PROCESSING_URL')

        record_data = HttpUtil.post_url(url, query)

        return record_data


    def execute_query(self, sql_query):

        query_data = DbUtil.execute_query(sql_query)

        if query_data['success'] == True:
            processed_datas = []

            # TODO : Have write this code in Lamda
            i = 0
            for x in query_data['data']:
                j = 0
                processed_data = {}
                for y in query_data['column_names']:
                    processed_data[y] = x[j]
                    j += 1
                processed_datas.append(processed_data)
                i += 1

            return {'success': True,
                    'data': processed_datas}
        else:
            return {'success': False}