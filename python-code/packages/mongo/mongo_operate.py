# -*- coding:utf8 -*-

import pymongo


def database_info():
    db = pymongo.MongoClient('mongodb://localhost:27017/')
    db_list = db.list_database_names()
    print("数据库列表:", db_list)
