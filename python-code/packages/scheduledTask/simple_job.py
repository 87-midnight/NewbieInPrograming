# -*- coding:utf8 -*-

from datetime import datetime
import time


def job_exec_per_sec(seconds):
    """
    通过循环方式+sleep实现定时任务
    :param seconds: 定时任务周期间隔描述
    :return: 无返回
    """
    while True:
        print("while循环定时任务执行了，时间:%s" % datetime.now().strftime("%Y-%m-%d  %H:%M:%S"))
        time.sleep(seconds)


if __name__ == '__main__':
    job_exec_per_sec(10)
