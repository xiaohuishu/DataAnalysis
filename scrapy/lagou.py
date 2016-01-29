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

def getCityList():
    print cur_file_dir()
    cityList = []
    with open(cur_file_dir() + '/data/city.txt', 'r+') as reader:
        for city in reader.readlines():
            rcity = city.strip('\n')
            if rcity == "全国":
                continue
            else:
                cityList.append(rcity)
    return cityList

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
    pinyin_position = position
    if city != 'all':
        clist = []
        for cindex in range(len(city)):
            clist.append(convert_pinyin(city[cindex:cindex + 1]))
        if clist:
            pinyin_city = ''.join(clist).replace("chong2,", "").replace("ji4,", "")
            print pinyin_city

    if position.startswith('web'):
        plist = []
        for pindex in range(len(position)):
            plist.append(convert_pinyin(position[pindex:pindex + 1]))
        if plist:
            pinyin_position = ''.join(plist)

    if jsonList:
        totalPage = len(jsonList)
        index = 1
        for jsonStr in jsonList:
            jst = json.loads(jsonStr)
            if jst['content']:
                content = jst['content']['result']
                if content:
                    cur_dir = cur_file_dir() + '/data/positionData/'
                    if os.path.isdir(cur_dir + pinyin_position):
                        pass
                    else:
                        os.mkdir(cur_dir + pinyin_position)

                    with open(cur_dir + pinyin_position + '/positionData_' + pinyin_city + '_' + pinyin_position + '.json', 'a') as datawrite:
                        for item in content:
                            removeKeys = []
                            for key in item.keys():
                                if isinstance(item[key], list):
                                    removeKeys.append(key)
                            for removeKey in removeKeys:
                                del item[removeKey]

                        if index == 1 and index == totalPage:
                            datawrite.writelines(str(content).replace('u\'', '\'').replace("\'", "\"")
                                                 .replace("False", "\"False\"").replace("True", "\"True\"")
                                                 .replace("None", "\"None\"").replace("\c", "/c").replace("\vc", "/c")
                                                 .replace("\C", "/C").replace("\/", "/").replace("\(", "/")
                                                 .replace("\VC", "/VC").replace("\N", "/N").replace("\n", "/n").decode("unicode-escape") + '\n')
                        elif index == 1:
                            datawrite.writelines(str(content).replace('u\'', '\'').replace("\'", "\"")
                                                 .replace("False", "\"False\"").replace("True", "\"True\"")
                                                 .replace("None", "\"None\"").replace('}]', '},').replace("\c", "/c").replace("\vc", "/c")
                                                 .replace("\C", "/C").replace("\/", "/").replace("\(", "/")
                                                 .replace("\VC", "/VC").replace("\N", "/N").replace("\n", "/n").decode("unicode-escape") + '\n')
                        elif index < totalPage:
                            datawrite.writelines(str(content).replace('u\'', '\'').replace("\'", "\"")
                                                 .replace("False", "\"False\"").replace("True", "\"True\"")
                                                 .replace("None", "\"None\"").replace('[{', '{').replace('}]', '},').replace("\c", "/c").replace("\vc", "/c")
                                                 .replace("\C", "/C").replace("\/", "/").replace("\(", "/")
                                                 .replace("\VC", "/VC").replace("\N", "/N").replace("\n", "/n").decode("unicode-escape") + '\n')
                        elif index == totalPage:
                            datawrite.writelines(str(content).replace('u\'', '\'').replace("\'", "\"")
                                                 .replace("False", "\"False\"").replace("True", "\"True\"")
                                                 .replace("None", "\"None\"").replace('[{', '{').replace("\c", "/c").replace("\vc", "/c")
                                                 .replace("\C", "/C").replace("\/", "/").replace("\(", "/")
                                                 .replace("\VC", "/VC").replace("\N", "/N").replace("\n", "/n").decode("unicode-escape") + '\n')
                        index = index + 1

    else:
        cur_dir = cur_file_dir() + '/data/positionData/'
        if os.path.isdir(cur_dir + pinyin_position):
            pass
        else:
            os.mkdir(cur_dir + pinyin_position)
        with open(cur_dir + pinyin_position + '/positionData_' + pinyin_city + '_' + pinyin_position + '.json', 'w+') as f:
                pass

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
