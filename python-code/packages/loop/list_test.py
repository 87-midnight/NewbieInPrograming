# -*- coding:utf8 -*-


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
