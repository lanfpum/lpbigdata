# -*- encoding=utf-8 -*-

from thrift.protocol import TBinaryProtocol
# 导入thrift的python模块
from thrift.transport import TSocket

# 导入自己编译生成的hbase python模块
from lxpsee.top.hbase import THBaseService
from lxpsee.top.hbase.ttypes import *

# 创建Socket连接，到ip201:9090
transport = TSocket.TSocket("ip201", 9090)
transport = TTransport.TBufferedTransport(transport)
protocol = TBinaryProtocol.TBinaryProtocol(transport)
client = THBaseService.Client(protocol)

transport.open()

table = b"ns1:t1"
row = b"row1"
v1 = TColumnValue(b'f1', b'id', b'101')
v2 = TColumnValue(b'f1', b'name', b'tomas')
v3 = TColumnValue(b'f1', b'age', b'12')
vals = [v1, v2, v3]
put = TPut(row, vals)
client.put(table, put)
print("ok!!")
transport.close()

# table = b"ns1:t1"
# rowKey = b"row1"
# col_id = TColumn(b"f1",b"id")
# col_name = TColumn(b"f1",b"name")
# col_age = TColumn(b"f1",b"age")
#
# cols = [col_id,col_name,col_age]
# get = TGet(rowKey,cols)
# res = client.get(table,get)
# print(bytes.decode(res.columnValues[0].family))
# print(bytes.decode(res.columnValues[0].qualifier))
# print(bytes.decode(res.columnValues[0].value))
# print(res.columnValues[0].timestamp)

# table = b"ns1:t1"
# rowKey = b"row1"
# col_id = TColumn(b"f1",b"id")
# col_name = TColumn(b"f1",b"name")
# cols = [col_id,col_name]
# tdelete = TDelete(rowKey,cols)
# client.deleteSingle(table,tdelete)
# print("ok")

# table = b"ns1:calllogs"
# startRow = b"17,15088889999,20181113161701,0,17055556666,7060"
# endRow = b"65,13211118888,20181113161438,0,14511119999,3472"
# col_pos = TColumn(b"f1", b"calleePos")
# col = TColumn(b"f1", b"callerPos")
# col_flag = TColumn(b"f1", b"flag")
# cols = [col,col_pos,col_flag]
# tscan = TScan(startRow=startRow,stopRow=endRow,columns=cols)
# r = client.getScannerResults(table,tscan,100)
#
# for res in r:
#     print("---------------------------------------")
#     print(bytes.decode(res.columnValues[0].family))
#     print(bytes.decode(res.columnValues[0].qualifier))
#     print(bytes.decode(res.columnValues[0].value))
#     print(res.columnValues[0].timestamp)
#
# transport.close()
