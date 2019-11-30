from source.mysql_manager import *
import json
import time


if __name__ == '__main__':
    db = connect_to()
    result = query_data(db.cursor(),"select * from sys_user")
    while True:
        for item in result:
            print("一列数据:%s" % json.dumps(item, cls=TimeEncoder,ensure_ascii=False))
        time.sleep(2)
