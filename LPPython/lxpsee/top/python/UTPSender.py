# -*- encoding=utf-8 -*-
import socket

sender = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

sender.bind(("localhost", 9999))

print("启动了UDP的发送方。。。。。。。。。。。")

index = 0

while True:
    msg = "hello world " + str(index)
    msgBytes = msg.encode("utf-8")
    sender.sendto(msgBytes, ("localhost", 8888))
    print("发送了：" + msg)
    index += 1

    import time

    time.sleep(1)
