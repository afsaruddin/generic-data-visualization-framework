import requests


class HttpUtil(object):
    @staticmethod
    def post_url(url,request_body=None):
        ret_values = []

        print(request_body)

        post_request = requests.post(url, json=request_body)
        print(post_request.status_code)

        if post_request.status_code == 200:

            return { 'success':True,
                     'data':post_request.json() }

            ret_values['success'] = True
            ret_values['data'] = post_request.json()
        else:
            return {'success': False}

        return {'success': False}