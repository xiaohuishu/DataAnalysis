# coding=utf-8
__author__ = 'antsmarch'

import urllib
import urllib2
import re
import sys, os
import json

reload(sys)
sys.setdefaultencoding('utf8')


def convert_pinyin(str):
    length = len("我")
    intord = ord(str[0:1])
    if 48 <= intord <= 57:
        return str[0:1]
    if (65 <= intord <= 90) or (97 <= intord <= 122):
        return str[0:1].lower()
    str = str[0:length]

    with open(cur_file_dir() + '/convert-utf-8.txt', 'r+') as reader:
        for line in reader:
            if str in line:
                return line[length:len(line) - 3]


def cur_file_dir():
    path = sys.path[0]
    if os.path.isdir(path):
        return path
    elif os.path.isfile(path):
        return os.path.dirname(path)


def http_get(url):
    try:
        request = urllib2.Request(url)
        response = urllib2.urlopen(request)
        return response.read()
    except urllib2.URLError, e:
        if hasattr(e, "reason"):
            print u"连接失败， 错误原因: ", e.reason
            return None


def http_post(url, data):
    encodedata = urllib.urlencode(data)
    try:
        request = urllib2.Request(url, encodedata)
        response = urllib2.urlopen(request)
        return response.read()
    except urllib2.URLError, e:
        if hasattr(e, "reason"):
            print u"访问失败， 错误原因: ", e.reason
            return None


class lagouIndex:
    def __init__(self, indexUrl):
        self.indexUrl = indexUrl

    def getIndexContent(self):
        http_get(self.indexUrl)

    def getPositionType(self, context):
        pattern = re.compile(
            ur'<a href=".*" data-lg-tj-id="4O00" data-lg-tj-no=".*" data-lg-tj-cid=".*" class=".*">(.*)</a>')
        # print pattern.search(str)
        items = re.findall(pattern, context)
        for item in items:
            print item

        curdirpath = cur_file_dir()
        # write file
        with open(curdirpath + '/data/position.txt', 'w+') as write:
            for item in items:
                write.writelines(item + '\n')


class lagouPositionAndCompanyData:
    def __init__(self, baseUrl, position, city=None):
        self.baseUrl = baseUrl
        self.position = position
        if not city:
            self.city = 'all'
        else:
            self.city = city

    def build_formdata(self, searchPage):
        first = 'false'
        if searchPage == 1:
            first = 'true'
        formdata = {
            'first': first,
            'pn': searchPage,
            'kd': self.position
        }
        return formdata

    def getTotalPageCount(self):
        url = self.baseUrl + '/positionAjax.json?'
        if self.city and self.city != 'all':
            url = url + 'city=' + self.city.encode('utf-8')
        jst = json.loads(http_post(url, self.build_formdata(1)))
        if jst['content']:
            totalPageCount = jst['content']['totalPageCount']
            if totalPageCount and totalPageCount > 0:
                return totalPageCount
            return 0
        return None

    def getPositionContent(self, page):
        url = self.baseUrl + '/positionAjax.json?'
        if self.city and self.city != 'all':
            url = url + 'city=' + self.city.encode('utf-8')
        return http_post(url, self.build_formdata(page))

    def getCompanyContent(self, page):
        url = self.baseUrl + '/companyAjax.json?'
        if self.city and self.city != 'all':
            url = url + 'city=' + self.city.encode('utf-8')
        http_post(url, self.build_formdata(page))


def parseJson(jsonList, city, position):
    pinyin_city = city
    if city != 'all':
        clist = []
        for cindex in range(len(city)):
            clist.append(convert_pinyin(city[cindex:cindex + 1]))
        if clist:
            pinyin_city = ''.join(clist)

    pinyin_position = position
    plist = []
    for pindex in range(len(position)):
        plist.append(convert_pinyin(position[pindex:pindex + 1]))

    if plist:
        pinyin_position = ''.join(plist)

    if jsonList:
        for jsonStr in jsonList:
            jst = json.loads(jsonStr)
            if jst['content']:
                content = jst['content']['result']
                if content:
                    with open(cur_file_dir() + '/data/positionData' + '_' + pinyin_city + '_' + pinyin_position + '.txt', 'a') as datawrite:
                        index = 0
                        for item in content:
                            index = index + 1
                            datawrite.writelines('company' + str(index) + '\n')
                            for key, value in item.items():
                                # print key, value
                                if not isinstance(value, list):
                                    datawrite.writelines(str(key) + '\t' + str(value) + '\n')
                                else:
                                    values = ','.join(value)
                                    datawrite.writelines(str(key) + '\t' + values + '\n')


baseUrl = 'http://www.lagou.com/jobs'
jsonList = []
argvs = sys.argv
print argvs
if len(argvs) == 3:
    position = argvs[1]
    city = argvs[2]
    lagouPositionData = lagouPositionAndCompanyData(baseUrl, unicode(position, 'utf-8'), unicode(city, 'utf-8'))
    totalPage = lagouPositionData.getTotalPageCount()

    for index in range(totalPage):
        jsonList.append(lagouPositionData.getPositionContent(index + 1))

    parseJson(jsonList, lagouPositionData.city, lagouPositionData.position)
else:
    print 'args not invalid'

'''
indexUrl = 'http://www.lagou.com/'
lagouIndex = lagouIndex(indexUrl)
context = lagouIndex.getIndexContent()
lagouIndex.getPositionType(context)
'''
