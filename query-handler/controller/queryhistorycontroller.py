from flask import current_app
from util.httputil import  HttpUtil


class QueryHistoryController:
    def __init__(self):
        pass

    def publish_history(self, query):

        url = current_app.config.get('HISTORY_PUBLISHING_URL')

        HttpUtil.post_url(url, {'query': query})
        pass