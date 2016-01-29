# coding=utf-8
__author__ = 'antsmarch'

import sys
import lagou
import threadpool

reload(sys)
sys.setdefaultencoding('utf8')

baseUrl = 'http://www.lagou.com/jobs'


def record(request, result):
    print request, result

def crawl(city, position):
    jsonList = []
    lagouPositionData = lagou.lagouPositionAndCompanyData(baseUrl, unicode(position, 'utf-8'), unicode(city, 'utf-8'))
    totalPage = lagouPositionData.getTotalPageCount()

    for index in range(totalPage):
        jsonList.append(lagouPositionData.getPositionContent(index + 1))

    lagou.parseJson(jsonList, lagouPositionData.city, lagouPositionData.position)


def crawlByPool(psType, num_thread):
    main = threadpool.ThreadPool(num_thread)
    for city in lagou.getCityList():
        try:
            req = threadpool.WorkRequest(crawl, args=[city, psType], kwds={}, callback=record)
            main.putRequest(req)
        except Exception, e:
            print e
    while True:
        try:
            main.poll()
        except threadpool.NoResultsPending:
            print "no pending results"
            break
        except Exception, e:
            print e
    main.wait()

argvs = sys.argv
crawlByPool(argvs[1], 50)
sys.exit(0)
