import socket

ss = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

ss.bind(("localhost", 8888))

ss.listen(0)

print("启动监听了！！！！！！！！！！！！！！！！！")

buffer = 1024

while True:
    (cs, addr) = ss.accept()
    print("有链接了：" + str(addr))
    while True:
        data = cs.recv(buffer)
        i = int.from_bytes(data, byteorder="little")
        print("recved : " + str(i))

print("数据为：%s" % (data))
