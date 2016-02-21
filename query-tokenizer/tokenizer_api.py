from tornado import web, ioloop
import query_tokenizer

qt = query_tokenizer.QueryTokenizer()

class RestApiHandler(web.RequestHandler):
    def get(self):
        q = self.get_argument('q', '')
        tokens = qt.get_final_query_with_tokens(q)
        print tokens
        self.write(tokens)

application = web.Application([
    (r'/querytotoken', RestApiHandler)
])

if __name__ == "__main__":
    application.listen(9091)
    ioloop.IOLoop.instance().start()