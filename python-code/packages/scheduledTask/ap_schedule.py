#!E:\Program Files\Python36\python.exe
# -*-coding:utf8 -*-

# https://www.jianshu.com/p/403bcb57e5c2
from apscheduler.schedulers.blocking import BlockingScheduler
from datetime import datetime
import random

# 输出时间
def job():
    print(datetime.now().strftime("%Y-%m-%d %H:%M:%S"))


def job_2():
    print(random.randint(0,999))

# BlockingScheduler
scheduler = BlockingScheduler()
scheduler.add_job(job, "interval", seconds=2)
scheduler.add_job(job_2,"interval",seconds=6)
scheduler.start()
