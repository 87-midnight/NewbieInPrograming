import threading
import _thread
from datetime import datetime


def _print(name):
    i = 0
    time = datetime.now()
    while i < 6:
        print("received arg from main entry:", time,",count:", i)
        i += 1


class PrintServiceRunner(threading.Thread):

        def __init__(self, name, time,):
            threading.Thread.__init__(self)
            self.name = name
            self.time = time

        def run(self):
            print("thread:[",self.name,"] is running at ",self.time)


if __name__ == '__main__':
    try:
        _thread.start_new_thread(_print, ('test_1',))
        thread_1 = PrintServiceRunner('thread_1', datetime.now())
        thread_2 = PrintServiceRunner('thread_2', datetime.now())
        thread_1.start()
        thread_2.start()
        # thread_1.join()
    except Exception as e:
        print("error",e)
    # while 1:
    #     pass
