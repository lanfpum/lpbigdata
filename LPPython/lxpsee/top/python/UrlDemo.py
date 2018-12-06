# -*- encoding=utf-8 -*-
import os
import re
import urllib.request


# resp = urllib.request.urlopen("http://www.python.org")
#
# myBytes = resp.read()
# myStr = myBytes.decode("utf-8")
# resp.close()
#
# f = open("D:/workDir/otherFile/scala/python.html","wb")
# f.write(myBytes)
# f.close()
#
# print(myStr)
#
# print("--------------------------------------")
# resp = urllib.request.urlopen("http://a1.att.hudong.com/34/09/01300000040940120965090265861.jpg")
# pic = resp.read()
# resp.close()
#
# f = open("D:/workDir/otherFile/scala/lq.jpg","wb")
# f.write(pic)
# f.close()

def fileExists(url, localPath):
    path = url
    path = path.replace(":", "_")
    path = path.replace("/", "$")
    path = path.replace("?", "$")
    path = localPath + "/" + path
    return os.path.exists(path)


def downLoad(url):
    path = url
    path = path.replace(":", "_")
    path = path.replace("/", "$")
    path = path.replace("?", "$")
    path = "D:/workDir/otherFile/scala/jd/" + path

    resp = urllib.request.urlopen(url)
    pageBytes = resp.read()
    resp.close()

    if not os.path.exists(path):
        f = open(path, "wb")
        f.write(pageBytes)
        f.close()

    try:
        pageStr = pageBytes.decode("utf-8")
        pattern = u'<a[\u0000-\uffff&&^[href]]*href="([\u0000-\uffff&&^"]*?)"'
        res = re.finditer(pattern, pageStr)

        for r in res:
            addr = r.group(1)
            # print(addr)

            if addr.startswith("//"):
                addr = addr.replace("//", "http://")

            if (addr.startswith("http://")) and not fileExists(addr, "D:/workDir/otherFile/scala/jd"):
                downLoad(addr)
    except Exception as e:
        print(e)
        print(pageBytes.decode("gbk", errors="ignore"))
        return


downLoad("http://www.it18zhang.com")
