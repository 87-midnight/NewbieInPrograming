import pymysql
import json
from datetime import datetime,date


class TimeEncoder(json.JSONEncoder):

    def default(self, obj):
        if isinstance(obj, datetime):
            return obj.strftime('%Y-%m-%d %H:%M:%S')
        elif isinstance(obj, date):
            return obj.strftime('%Y-%m-%d')
        else:
            return json.JSONEncoder.default(self, obj)


def connect_to(host='172.17.0.5', port=3308, username='root', password='123456', database_name='test'):
    return pymysql.connect(host, username, password, database_name, port, charset='utf8')


def query_data(cursor, sql):
    try:
        cursor.execute(sql)
        result = cursor.fetchall()
        return result
    except Exception as var1:
        raise var1


