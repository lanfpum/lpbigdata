# -*- encoding=utf-8 -*-

import re
import urllib.request

import lxpsee.top.hbase.crawler.PageDao as pd


def downLoad(url):
    resp = urllib.request.urlopen(url)
    pageBytes = resp.read()
    resp.close()

    if not pd.exists(url):
        pd.savePage(url, pageBytes)

    try:
        pageStr = pageBytes.decode("utf-8")
        pattern = u'<a[\u0000-\uffff&&^[href]]*href="([\u0000-\uffff&&^"]*?)"'
        res = re.finditer(pattern, pageStr)

        for r in res:
            addr = r.group(1)

            if addr.startswith("//"):
                addr = addr.replace("//", "http://")

            if addr.startswith("http://") and url != addr and (not pd.exists(addr)):
                downLoad(addr)

    except Exception as e:
        print(e)
        print(pageBytes.decode("gbk", errors='ignore'))
        return


downLoad("http://jd.com")
