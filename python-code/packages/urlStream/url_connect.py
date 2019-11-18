# -*- coding:utf8 -*-
import urllib.request
import urllib3


def url_open(url):
    """
    打开链接，打印文档内容
    :param url: 链接地址
    :return:
    """
    request = urllib.request.Request(url)
    response = urllib.request.urlopen(request)
    print(response.read().decode('utf-8'))


def sogou_search(search_keyword):
    """
    搜狗搜索关键字
    :param search_keyword:
    :return:
    """
    data = {'query': search_keyword}
    http = urllib3.PoolManager(num_pools=5, headers={'User-Agent': 'ABCDE'})
    # resp1 = http.request('POST', 'http://www.httpbin.org/post', fields=data)
    resp1 = http.request('GET', 'https://www.sogou.com/web', fields=data)
    print(resp1.data.decode())

if __name__ == '__main__':
    sogou_search("fpx")
