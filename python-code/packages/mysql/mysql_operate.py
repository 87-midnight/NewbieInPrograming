# -*- coding:utf8 -*-
import pymysql
import json
from datetime import datetime
from datetime import date
import logging


class TimeEncoder(json.JSONEncoder):

    def default(self, obj):
        if isinstance(obj, datetime):
            return obj.strftime('%Y-%m-%d %H:%M:%S')
        elif isinstance(obj, date):
            return obj.strftime('%Y-%m-%d')
        else:
            return json.JSONEncoder.default(self, obj)


def connect_to(host='localhost', port=3306, username='root', password='123456', database_name='test'):
    return pymysql.connect(host, username, password, database_name, port)


def query_data(cursor, sql):
    try:
        cursor.execute(sql)
        result = cursor.fetchall()
        for item in result:
            print("一列数据:%s" % json.dumps(item, cls=TimeEncoder))
            logging.info('数据:%s', json.dumps(item, cls=TimeEncoder))
    except Exception as var1:
        raise var1


def insert_data(db, cursor, sql, params):
    try:
        cursor.execute(sql, params)
        db.commit()
    except Exception as var2:
        db.rollback()
        raise var2


if __name__ == '__main__':
    logging.basicConfig(level=logging.INFO)  #设置日志级别为info
    db = connect_to()
    query_data(db.cursor(), 'select * from user')
    insert_data(db, cursor=db.cursor(), sql='insert into article(`content_`,`title_`,`create_time`) values(%s,%s,%s)', params=['well,well,well', 'hello,python', datetime.now().strftime("%Y-%m-%d %H:%M:%S")])
    query_data(db.cursor(), 'select * from article')
    db.close()

