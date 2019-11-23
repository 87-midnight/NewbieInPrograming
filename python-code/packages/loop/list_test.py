# -*- coding:utf8 -*-
import sys

sys.path.append("J:\\NewbieInPrograming\\python-code\\packages\\")

# 引用同级目录的py文件时，需要先设置一个根路径，这样python执行当前文件时
# 会在sys.path下去找其他包的文件
from dateTime import time_test


def list_():
    _array = [1, 2, 3, 4, 5]
    print(_array)


def map_():
    _map = {1: 'hello', "2": 220}
    print("遍历map:%s" % _map)
    if _map.__contains__("2"):
        print(_map.get("2"))
    for key in _map.keys():
        print("[%s,%s]" % (key, _map.get(key)))


if __name__ == '__main__':
    time_test()
