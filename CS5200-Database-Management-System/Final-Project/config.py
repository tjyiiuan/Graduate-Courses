# -*- coding: utf-8 -*-
import configparser


conf = configparser.ConfigParser()
conf.read("./configuration.conf")

# Database Config
DB_HOST = conf.get("MySQL", "HOST")
DB_PORT = conf.get("MySQL", "PORT")
DB_USER = conf.get("MySQL", "USERNAME")
DB_PSWD = conf.get("MySQL", "PASSWORD")
DB_DEV = conf.get("MySQL", "DEV")
DB_TEST = conf.get("MySQL", "TEST")
DB_PROD = conf.get("MySQL", "PROD")

# Mail Config
M_SERVER = conf.get("Mail", "SERVER")
M_PORT = conf.get("Mail", "PORT")
M_USE_TLS = conf.get("Mail", "USE_TLS")
M_USERNAME = conf.get("Mail", "USERNAME")
M_PASSWORD = conf.get("Mail", "PASSWORD")
M_DEFAULT_SENDER = conf.get("Mail", "SENDER")

# Forum Config
F_ADMIN = conf.get("Forum", "ADMIN")
F_PPP = conf.get("Forum", "ADMIN")
F_RPP = conf.get("Forum", "ADMIN")
F_FPP = conf.get("Forum", "ADMIN")


class Config(object):
    SECRET_KEY = "hard to guess string"
    SQLALCHEMY_COMMIT_ON_TEARDOWN = True
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    MAIL_SERVER = M_SERVER
    MAIL_PORT = M_PORT
    MAIL_USE_TLS = M_USE_TLS
    MAIL_USERNAME = M_USERNAME
    MAIL_PASSWORD = M_PASSWORD
    MAIL_DEFAULT_SENDER = M_DEFAULT_SENDER
    FORUM_ADMIN = F_ADMIN
    FORUM_POSTS_PER_PAGE = F_PPP
    FORUM_REPLIES_PER_PAGE = F_RPP
    FORUM_FOLLOWERS_PER_PAGE = F_FPP
    
    @staticmethod
    def init_app(app):
        pass


class DevelopmentConfig(Config):
    DEBUG = True
    SQLALCHEMY_DATABASE_URI = \
        f"mysql://{DB_USER}:{DB_PSWD}@{DB_HOST}:{DB_PORT}/{DB_DEV}"

class TestingConfig(Config):
    TESTING = True
    SQLALCHEMY_DATABASE_URI = \
        f"mysql://{DB_USER}:{DB_PSWD}@{DB_HOST}:{DB_PORT}/{DB_TEST}"
            

class ProductionConfig(Config):
    SQLALCHEMY_DATABASE_URI = \
        f"mysql://{DB_USER}:{DB_PSWD}@{DB_HOST}:{DB_PORT}/{DB_PROD}"

config = {
    "development": DevelopmentConfig,
    "testing": TestingConfig,
    "production": ProductionConfig,

    "default": DevelopmentConfig
}
