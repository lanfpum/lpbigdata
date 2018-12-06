# -*- encoding=utf-8 -*-
import _thread
import threading
import time


def hello():
    tname = threading.currentThread().getName()
    for i in [1, 2, 3, 4, 5, 6, 7, 8, 9]:
        print(tname + " : " + str(i))
        cc = int(time.time() * 1000)
        print(cc)


_thread.start_new_thread(hello, ())
_thread.start_new_thread(hello, ())

time.sleep(2)
