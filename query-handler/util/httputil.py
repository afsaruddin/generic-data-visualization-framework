import requests
import json


class HttpUtil(object):
    @staticmethod
    def post_url(url, request_body=None):

        post_request = requests.post(url, data=json.dumps(request_body), headers={"content-type": "text/javascript"})

        if post_request.status_code == 200:

            return {'success': True,
                    'data': post_request.json()}

        else:
            return {'success': False}

        return {'success': False}