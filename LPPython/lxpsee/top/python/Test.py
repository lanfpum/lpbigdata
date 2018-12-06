# print("hello")
# # print("你好，中国")
# # if True :
# #     print("true")
# # else:
# #     print("false")
# # print("ok")

# str = "xxxxx/r/n/r/nhello"
# str1 = '''xxxxx
# niha
# hello'''
# print(str)
# print(str1)

# list = [1, 2, 3, "hello"]
# print(list)
# list[3] = 100
# print(list)
# print(list.__len__())
# print(list * 5)

t = (1, 2, 3)
print(t)
print(t[2])
print(t[0:2])

# dict = {100:"tom",200:"jim",300:"kobe"}
# print(dict)
# dict[100] = "gxp"
# print(dict[100])

# str = "200"
# # # print(int(str) + 200)

# str = "200 + 300"
# print(eval(str))

# seq = 1,2,3,4
# t = tuple(seq)
# print(t)
# list = list(seq)
# print(list)

# seq = (1,2),(2,3),(3,4)
# d = dict(seq)
# print(d)

# a = 100
# b = 100
# print( a is b)
# t1 = (1,2,3,4)
# t2 = (1,2,3,4)
# print(t1 is t2)

# rows = [1, 2, 3, 4, 5, 6, 7, 8, 9]
# cols = [1, 2, 3, 4, 5, 6, 7, 8, 9]
# for r in rows:
#     for c in cols:
#         if c <= r:
#             print("%d x %d = %d" % (c, r, (c * r)), end='/t')
#     print()
#
# print("----------------------------------------------------------------------------------")
#
# r = 1
# while r <= 9:
#     c = 1
#     while c <= r:
#         print("%d * %d = %d" % (c,r,(c*r)),end="/t")
#         c += 1
#     print()
#     r += 1
#
# print("----------------------------------------------------------------------------------")

# t1 = (1, 2, 3)
# t2 = (2, 3, "Hello")
# print(t1 + t2)

# list = [1,2,3,4,5,6]
# del list[1]
# print(list)
# list = [1,2,3,4,5,6]
# list.append(8)
# print(list)
# list.insert(0,10)
# print(list)
# list.pop()
# print(list)
# list.remove(10)
# print(list)

# f = open("D:/workDir/otherFile/scala/test.txt")
# lines = f.readlines()
# for line in lines :
#     print(line,end="")
# print()
#
# f = open("D:/workDir/otherFile/scala/test.txt")
# li = f.readline()
# print(li)

# f = open("D:/workDir/otherFile/scala/test.txt")
# while True:
#     line = f.readline()
#     if line != "":
#         print(line,end="")
#     else:
#         break

# f1 = open("D:/workDir/otherFile/temp/pic.zip","rb")
# f2 = open("D:/workDir/otherFile/temp/t.txt","wb")
#
# data = f1.read()
# f1.close()
# f2.write(data)
# f2.close()

# f1 = open("D:/othersDir/wechatLP/WeChat Files/lake513421121/Files/pic.txt","rb")
# f2 = open("D:/othersDir/wechatLP/WeChat Files/lake513421121/Files/Files.zip","wb")
# data = f1.read()
# f1.close()
# f2.write(data)
# f2.close()

import pymysql

try:
    conn = pymysql.connect(host='localhost', user='root', passwd='123', db='python', port=3306, charset='utf8')
    cur = conn.cursor()
    cur.execute('select version()')
    version = cur.fetchone()
    print(version)
    cur.close()
    conn.close()
except  Exception:
    print("发生异常")
