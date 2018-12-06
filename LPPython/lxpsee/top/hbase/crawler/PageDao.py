# -*- encoding=utf-8 -*-

import base64

from thrift.protocol import TBinaryProtocol
from thrift.transport import TSocket

from lxpsee.top.hbase import THBaseService
from lxpsee.top.hbase.ttypes import *

transPort = TSocket.TSocket("ip201", 9090)
transPort = TTransport.TBufferedTransport(transPort)
protocol = TBinaryProtocol.TBinaryProtocol(transPort)
client = THBaseService.Client(protocol)


def savePage(url, page):
    transPort.open()
    urlBase64Bytes = base64.encodebytes(url.encode("utf-8"))

    table = b"ns1:pages"
    rowKey = urlBase64Bytes
    col_val = TColumnValue(b"f1", b"page", page)
    vals = [col_val]
    put = TPut(rowKey, vals)
    client.put(table, put)
    transPort.close()


def exists(url):
    print(url)
    transPort.open()
    urlBase64Bytes = base64.encodebytes(url.encode("utf-8"))

    table = b"ns1:pages"
    rowKey = urlBase64Bytes
    col_page = TColumnValue(b"f1", b"page")
    cols = [col_page]
    get = TGet(rowKey, cols)
    res = client.get(table, get)
    transPort.close()
    return res.row is not None
