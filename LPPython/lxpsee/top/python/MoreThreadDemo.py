# -*- encoding=utf-8 -*-
import threading

tickets = 100
loc = threading.Lock()


def getTicket():
    global loc
    # loc.acquire
    loc.acquire
    global tickets
    tmp = 0
    if tickets > 0:
        tmp = tickets
        tickets -= 1
    else:
        tmp = 0
    loc.release
    return tmp


class Saler(threading.Thread):
    def run(self):
        while True:
            ticket = getTicket()
            if ticket != 0:
                print(self.getName() + "---" + str(ticket))
            else:
                return


s1 = Saler()
s1.setName("lp")
s2 = Saler()
s2.setName("gxp")
s1.start()
s2.start()
