import queue
import threading


class User:

    def __init__(self, username,password):
        self.username = username
        self.password = password

    def str(self):
        return "User[username=",self.username,",password=",self.password,"]"


class UserHandler(threading.Thread):
    def __init__(self,lock,queue):
        threading.Thread.__init__(self)
        self.queue = queue
        self.lock = lock

    def run(self):
        self.lock.acquire()
        u_ = self.queue.get()
        print(u_.str())
        self.lock.release()

if __name__ == '__main__':
    flag = 0
    work = queue.Queue(120)
    lock = threading.Lock()
    i = 1
    lock.acquire()
    while i < 20:
        user_ = User("john_{}".format(i), "12345")
        i += 1
        work.put(user_, block=True)
    lock.release()
    i = 1
    while i<10:
        i += 1
        handler = UserHandler(lock,work)
        handler.start()

