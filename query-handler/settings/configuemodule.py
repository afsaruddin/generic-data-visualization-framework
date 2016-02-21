

class Config(object):
    DEBUG = False
    DATABASE_URI = "host='localhost' dbname='gdvf' user='gdvf_user' password='gdvf_PASS'"
    QUERY_PROCESSING_URL = "http://169.45.220.234:4567/querytosql"
    HISTORY_PUBLISHING_URL = "http://169.44.61.115:9090/pushhistory"

class DevelopmentConfig(Config):
    DEBUG = True



class ProductionConfig(Config):
    DEBUG = False