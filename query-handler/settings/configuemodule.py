

class Config(object):
    DEBUG = False
    DATABASE_URI = "host='localhost' dbname='gdvf' user='gdvf_user' password='gdvf_PASS'"
    QUERY_PROCESSING_URL = "http://169.45.220.234:4567/querytosql"

class DevelopmentConfig(Config):
    DEBUG = True
    VUYA = 'test vuya'


class ProductionConfig(Config):
    DEBUG = False