#!/usr/bin/python
# -*- coding: cp949 -*-
import socket
import sys
import time

# Thread Pool
IS_PY2 = sys.version_info < (3, 0)

if IS_PY2:
    from Queue import Queue
else:
    from queue import Queue

from threading import Thread


class Worker(Thread):
    """ Thread executing tasks from a given tasks queue """
    def __init__(self, tasks):
        Thread.__init__(self)
        self.tasks = tasks
        self.daemon = True
        self.start()

    def run(self):
        while True:
            func, args, kargs = self.tasks.get()
            try:
                func(*args, **kargs)
            except Exception as e:
                # An exception happened in this thread
                print("Worker Thread 오류=%s" % e)
            finally:
                # Mark this task as done, whether an exception happened or not
                self.tasks.task_done()


class ThreadPool:
    """ Pool of threads consuming tasks from a queue """
    def __init__(self, num_threads):
        self.tasks = Queue(num_threads)
        for _ in range(num_threads):
            Worker(self.tasks)

    def add_task(self, func, *args, **kargs):
        """ Add a task to the queue """
        self.tasks.put((func, args, kargs))

    def map(self, func, args_list):
        """ Add a list of tasks to the queue """
        for args in args_list:
            self.add_task(func, args)

    def wait_completion(self):
        """ Wait for completion of all the tasks in the queue """
        self.tasks.join()

def connect_socket(info):
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.connect(info)
        time.sleep(60)
        s.close()
    except Exception as e:
        traceback.print_exc()

if __name__ == "__main__":
    if len(sys.argv) != 3 and len(sys.argv) != 4:
        print("Usage) %s IP PORT (NUM)" % sys.argv[0])
        print("NUM : the number of clients connected")
        print("NUM : 접속할 클라이언트 개수")
        sys.exit(-1)

    nums = 500
    if len(sys.argv) == 4:
        nums = int(sys.argv[3])
    ip = sys.argv[1]
    port = int(sys.argv[2])
    print("Thread numbers = %d" % nums)

    pool = ThreadPool(nums)

    for _ in range(nums):
        pool.add_task(connect_socket, (ip, port))

    time.sleep(6000)
