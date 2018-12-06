# -*- encoding=utf-8 -*-
import socket

recver = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

recver.bind(("localhost", 8888))

print("启动了UDP接受者。。。。。")

while True:
    (data, addr) = recver.recvfrom(1024)
    msg = bytes.decode(data)

    print("接受了 from " + str(addr[0]) + ":" + str(addr[1]) + "的数据:" + msg)
