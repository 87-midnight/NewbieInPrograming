#!E:\Program Files\Python36\python.exe
# -*-coding:utf8 -*-

# https://www.jianshu.com/p/403bcb57e5c2
from apscheduler.schedulers.blocking import BlockingScheduler
from datetime import datetime


# 输出时间
def job():
    print(datetime.now().strftime("%Y-%m-%d %H:%M:%S"))


# BlockingScheduler
scheduler = BlockingScheduler()
scheduler.add_job(job, "cron", day_of_week="1-5", hour=6, minute=30)
scheduler.start()
