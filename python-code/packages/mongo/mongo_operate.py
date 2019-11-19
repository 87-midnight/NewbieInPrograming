# -*- coding:utf8 -*-

import pymongo
import json


def database_info():
    db = pymongo.MongoClient('mongodb://localhost:27017/')
    db_list = db.list_database_names()
    print("数据库列表:", db_list)
    return db


def insert_data(db, user):
    result = db["user"].insert_one(user)
    print("插入数据结果:", result)


def query_data(db, list_name):
    result = db[list_name].find()
    for item in result:
        print("查询结果:", item)


if __name__ == '__main__':
    db = database_info()
    insert_data(db['local'], user={"name": "test", "age": "111"})
    query_data(db['local'], 'user')
