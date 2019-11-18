# -*- coding:utf8 -*-
import sched
import time
from datetime import datetime

# 初始化schedule模块的scheduler类
# 第一个参数是一个可以返回时间戳的函数，第二参数可以在定时未到达之前阻塞
schedule = sched.scheduler(time.time, time.sleep)


def job_detail(inc):
    """
    # 被周期性调度触发函数
    :param inc:
    :return:
    """
    print(datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
    # 注意 sched 模块不是循环的，一次调度被执行后就 Over 了，如果想再执行，请再次 enter(循环调用）
    schedule.enter(inc, 0, job_detail, (inc,))


def start_job(inc=60):
    """
    # 默认参数60s
    # enter四个参数分别为：间隔事件,优先级（用于同时到达两个事件同时执行的顺序），被调度触发的函数
    # 给该触发器函数的参数（tuple形式）
    :param inc:
    :return:
    """
    schedule.enter(0, 0, job_detail, (inc,))
    schedule.run()


if __name__ == '__main__':
    start_job(10)
