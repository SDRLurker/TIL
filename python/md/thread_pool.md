## 출처

https://www.metachris.com/2016/04/python-threadpool/

---

# Python 쓰레드 풀

> 쓰레드 풀은 주어진 일을 할 준비가 된 미리 만들어진 한가한 쓰레드 그룹입니다. 이들은 마쳐야 할 긴 작업의 작은 쓰레드 개수보다 짧은 작업의 많은 쓰레드 개수가 있을 때 각 작업에 대한 쓰레드를 인스턴스화 하는 것이 더 선호됩니다.

인터넷에서 문서 1000개를 다운로드하고 싶지만 한 번에 50개를 다운로드 할 수 있는 리소스 만 갖고 있다고 가정합니다. 해결책은 스레드 풀을 사용하여 고정된 수의 스레드를 생성하여 큐에서 모든 URL을 한 번에 50개씩 다운로드합니다.

스레드 풀을 사용하기 위해 Python 3.x에는 [ThreadPoolExecutor](https://docs.python.org/dev/library/concurrent.futures.html#threadpoolexecutor) 클래스가 포함되어 있고 Python 2.x와 3.x에는 `multiprocessing.dummy.ThreadPool` 이 있습니다. [`multiprocessing.dummy`](https://docs.python.org/3/library/multiprocessing.html#module-multiprocessing.dummy)는 [멀티프로세싱(multiprocessing)](https://docs.python.org/3/library/multiprocessing.html#module-multiprocessing) API를 복제하지만 [threading](https://docs.python.org/3/library/threading.html#module-threading) 모듈 주변의 래퍼(wrapper)일 뿐입니다.

```multiprocessing.dummy.ThreadPool``` 의 단점은 Python 2.x에서 대기열의 모든 작업이 스레드에 의해 완료되기 전에 프로그램을 종료 할 수 없다는 것입니다. 예를 들어 KeyboardInterrupt가 있습니다.  

Python 2.x 및 3.x에서 ([PDFx](https://www.metachris.com/pdfx)에서 사용하기 위해) 인터럽트 가능한 스레드 대기열(큐,queue)을 얻기 위해 [stackoverflow.com/a/7257510](http://stackoverflow.com/a/7257510)에서 영감을 얻어 이 코드를 작성했습니다. Python 2.x 및 3.x에서 작동하는 스레드 풀을 구현합니다.

```Python
import sys
IS_PY2 = sys.version_info < (3, 0)

if IS_PY2:
    from Queue import Queue
else:
    from queue import Queue

from threading import Thread


class Worker(Thread):
    """ 주어진 작업들에 대한 대기열(큐,queue)로부터 작업을 실행할 쓰레드 """
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
                # 이 쓰레드에서 발생된 exception
                print(e)
            finally:
                # exception이 발생하던 안 하던 이 작업의 종료를 마크합니다.
                self.tasks.task_done()


class ThreadPool:
    """ 대기열(큐,queue)로부터 작업을 소비하는 쓰레드 풀 """
    def __init__(self, num_threads):
        self.tasks = Queue(num_threads)
        for _ in range(num_threads):
            Worker(self.tasks)

    def add_task(self, func, *args, **kargs):
        """ 대기열(큐,queue)에 작업을 추가 """
        self.tasks.put((func, args, kargs))

    def map(self, func, args_list):
        """ 대기열(큐,queue)에 작업의 리스트를 추가 """
        for args in args_list:
            self.add_task(func, args)

    def wait_completion(self):
        """ 대기열(큐,queue)에 모든 작업의 완료를 기다림 """
        self.tasks.join()


if __name__ == "__main__":
    from random import randrange
    from time import sleep

    # 쓰레드에서 실행될 함수
    def wait_delay(d):
        print("sleeping for (%d)sec" % d)
        sleep(d)

    # 임의의 지연시간 생성
    delays = [randrange(3, 7) for i in range(50)]

    # 5개의 worker 쓰레드로 쓰레드 풀을 인스턴스화
    pool = ThreadPool(5)

    # 쓰레드 풀로 대량의 작업을 추가. 하나씩 작업을 추가하기 위해 `pool.add_task`
    # 사용 가능. 이 코드는 이 곳에서 막힐(block) 것이지만 
    # 현재 실행하고 있는 worker의 배치작업이 완료되면
    # exception으로 쓰레드 풀을 취소하는 것이 가능하도록 만들 수 있습니다.
    pool.map(wait_delay, delays)
    pool.wait_completion()
```

큐 크기는 스레드 수와 유사합니다 (`self.tasks = Queue(num_threads)` 참조). 따라서 `pool.map(..)` 및 `pool.add_task(..)`로 작업을 추가하면 Queue의 새 슬롯이 사용가능할 때까지 막힐(block)것 입니다.

Ctrl + C를 눌러 KeyboardInterrupt를 실행하면 현재 Worker 배치가 완료되고 프로그램이 `pool.map(..)` 단계에서 exception로 종료됩니다.
