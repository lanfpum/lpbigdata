# -*- encoding=utf-8 -*-
import socket

cs = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

cs.connect(("localhost", 8888))

# cs.send(b"hello world")
#
# cs.close()

index = 0

while True:
    b = index.to_bytes(4, byteorder="little")
    cs.send(b)
    print("发送数据：" + str(index))
    index += 1

    import time

    time.sleep(1)

cs.close()
