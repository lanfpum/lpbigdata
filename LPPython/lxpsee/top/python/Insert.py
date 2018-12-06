import pymysql

# try:
#     conn = pymysql.connect(host="localhost", user="root", passwd="123", db="python", port=3306, charset="utf8")
#     cur = conn.cursor()
#     sql = "insert into person(name,age) values('%s',%d)" % ("tom", 12)
#     cur.execute(sql)
#     conn.commit()
#     cur.close()
#     conn.close()
# except Exception:
#     print("抛出异常")

# try:
#     conn = pymysql.connect(host="localhost", user="root", passwd="123", db="python", port=3306, charset="utf8")
#     cur = conn.cursor()
#     i = 1
#     while i < 1000:
#         if i % 2 == 0:
#             sql = "insert into person(name,age) values('%s',%d)" % ("jim" + str(i), i % 100)
#             if i % 10 == 0:
#                 sql = "insert into person(name,age) values('%s',%d)" % ("kobe", i % 120)
#             else:
#                 sql = "insert into person(name,age) values('%s',%d)" % ("green", 12)
#         else:
#             sql = "insert into person(name,age) values('%s',%d)" % ("tom" + str(i), i % 100)
#         print(sql)
#         cur.execute(sql)
#         i += 1
#     conn.commit()
#     cur.close()
#     conn.close()
# except Exception:
#     print("抛出异常")

# try:
#     conn = pymysql.connect(host="localhost", user="root", passwd="123", db="python", port=3306, charset="utf8")
#     cur = conn.cursor()
#     sql = "select * from person"
#     cur.execute(sql)
#     res = cur.fetchall()
#     for re in res:
#         # print(re)
#         print(str(re[0]) + "---" + re[1] + "--" + str(re[2]))
#     conn.commit()
#     cur.close()
#     conn.close()
# except Exception:
#     print("抛出异常")

# try:
#     conn = pymysql.connect(host="localhost", user="root", passwd="123", db="python", port=3306, charset="utf8")
#     conn.autocommit(False)
#     conn.begin()
#     cour = conn.cursor()
#     sql = "delete from person where id > 899"
#     cour.execute(sql)
#     conn.commit()
#     cour.close()
# except Exception:
#     print("抛出异常")
#     conn.rollback()
# finally:
#     conn.close()

# try:
#     conn = pymysql.connect(host="localhost", user="root", passwd="123", db="python", port=3306, charset="utf8")
#     cur = conn.cursor()
#     conn.autocommit(False)
#     conn.begin()
#     sql = "update person set age=age-20 where age >30"
#     cur.execute(sql)
#     conn.commit()
#     cur.close()
# except Exception:
#     print("抛出异常")
#     conn.rollback()
# finally:
#     conn.close()

try:
    conn = pymysql.connect(host="localhost", user="root", passwd="123", db="python", port=3306, charset="utf8")
    cur = conn.cursor()
    conn.autocommit(False)
    conn.begin()
    sql = "select count(*) from person where age > 50"
    cur.execute(sql)
    res = cur.fetchone()
    print(res[0])
    conn.commit()
    cur.close()
except Exception:
    print("抛出异常")
    conn.rollback()
finally:
    conn.close()
