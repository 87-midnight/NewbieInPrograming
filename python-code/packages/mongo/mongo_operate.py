# -*- coding:utf8 -*-

import pymongo


def database_info():
    db = pymongo.MongoClient('mongodb://localhost:27017/')
    db_list = db.list_database_names()
    print("数据库列表:", db_list)
    return db_list


def insert_data(db, user):
    if user is None:
        user = {"name": "test", "age": 17}
    result = db["user"].insert_one(user)
    print("插入数据结果:", result)


if __name__ == '__main__':
    database_info()
