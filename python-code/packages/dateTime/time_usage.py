import time
import os,sys

# 可以使用os.path获取当前文件的上级目录路径，这样也能访问同级目录的文件了
root_dir = os.path.dirname(os.path.abspath('.'))
sys.path.append(root_dir+"\\")
print(sys.path)

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
