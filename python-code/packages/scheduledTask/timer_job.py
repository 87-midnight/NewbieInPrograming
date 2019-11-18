# -*- coding:utf8 -*-
from datetime import datetime
from threading import Timer


# theading模块中的timer
# timer非阻塞函数
def job_timer_exec_per_second(seconds):
    print(datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
    # 第三个参数是元组，(arg,...)
    t = Timer(seconds, job_timer_exec_per_second, (seconds, ))
    t.start()
    return t


if __name__ == '__main__':
    job = job_timer_exec_per_second(5)
    job.cancel()
