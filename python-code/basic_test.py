# -*- coding:utf-8 -*-
from packages.ClassUsage import User
from packages.RegExp import reg_exp
from packages.dateTime import time_test
from packages.file import read_files
from packages.file import write_files
# 如果不在loop目录下的__init__.py里 加入 from .for_loop import for_test，那么就要用下面的全路径方式引入
# from packages.loop.for_loop import for_test
# 如果在__init__.py定义了后，就可以使用以下的方式
from packages.loop import for_test
from packages.loop import list_
from packages.loop import map_


def hello():
    _str = input("input something:")
    print("%s" % _str)
    list_()
    map_()


# hello()

if __name__ == '__main__':
    for_test()
    read_files("F:/robots.txt")
    write_files("F:/robots.txt","hello,python")
    read_files("F:/robots.txt")
    user = User("patric", "male")
    User.count += 1  # 只有使用类.类变量名来改变值才会生效，实例化变量.类变量名的方式只能访问类变量名的值
    print("user实例化信息:%s" % user.to_string())
    user1 = User("candy", "female")
    user1.count += 2  # 使用实例化对象调用count来计算，不会生效
    print("User类的变量count=%s" % user.count)
    print("user1实例化信息:%s" % user1.to_string())
    reg_exp("abc","abcdwadafgabcdfgk243abc43da")
    time_test()
