from flask import current_app
from util.httputil import  HttpUtil


class QueryHistoryController:
    def __init__(self):
        pass

    def publish_history(self, query):

        print("Hello in publish history method")
        url = current_app.config.get('HISTORY_PUBLISHING_URL')

        postdata = {'query': query}
        HttpUtil.post_url(url, postdata)

        pass