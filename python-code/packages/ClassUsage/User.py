# -*- coding:utf8 -*-


class User:
    count = 0

# __init__隶属于类的构造函数，类的方法默认第一个参数为指向类的实例
    def __init__(test, name, gender):
        test.name = name
        test.gender = gender

    def to_string(self):
        return "["+self.name+","+self.gender+"]"
