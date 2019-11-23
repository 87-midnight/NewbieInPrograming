import time
import sys

sys.path.append("J:\\NewbieInPrograming\\python-code\\packages\\")

from loop import *


def time_test():
    timestamp = time.time()
    print("当前时间戳:%s" % timestamp)
    localtime = time.localtime(time.time())
    print(localtime)
    localtime1 = time.asctime(time.localtime(time.time()))
    print("本地时间:%s" % localtime1)
    print("格式化时间:%s" % time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()))


if __name__ == '__main__':
    list_()
