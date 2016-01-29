# coding=utf-8
__author__ = 'antsmarch'

import sys
import os

reload(sys)
sys.setdefaultencoding('utf8')


def cur_file_dir():
    path = sys.path[0]
    if os.path.isdir(path):
        return path
    elif os.path.isfile(path):
        return os.path.dirname(path)


def getCityList():
    print cur_file_dir()
    cityList = []
    with open(cur_file_dir() + '/data/city.txt', 'r+') as reader:
        for city in reader.readlines():
            if city[:-1] == "全国":
                continue
            else:
                cityList.append(city[:-1])
    return cityList



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


print getCityList()

'''cityList = ["全国",
            "北京",
            "上海",
            "深圳",
            "广州",
            "杭州",
            "成都",
            "南京",
            "武汉",
            "西安",
            "厦门",
            "长沙",
            "苏州",
            "天津",
            "重庆",
            "郑州",
            "青岛",
            "合肥",
            "福州",
            "济南",
            "大连",
            "珠海",
            "无锡",
            "佛山",
            "东莞",
            "宁波",
            "常州",
            "沈阳",
            "石家庄",
            "昆明",
            "南昌",
            "南宁",
            "哈尔滨",
            "海口",
            "中山",
            "惠州",
            "贵阳",
            "长春",
            "太原",
            "嘉兴",
            "泰安",
            "昆山",
            "烟台",
            "兰州",
            "泉州"]
for city in cityList:
    if city != 'all':
        city = unicode(city, 'utf-8')
        clist = []
        for cindex in range(len(city)):
            clist.append(convert_pinyin(city[cindex:cindex + 1]))
        if clist:
            pinyin_city = ''.join(clist).replace("chong2,", "").replace("ji4,", "")
            print pinyin_city
'''
