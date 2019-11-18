# -*- coding:utf8 -*-

import urllib.request
from bs4 import BeautifulSoup


def html_parse(url):
    html = urllib.request.urlopen(url)
    return BeautifulSoup(html, 'html.parser')


def html_search(html, search_tag):
    return html.find_all(name=search_tag)


if __name__ == '__main__':
    content = html_parse("http://www.sogou.com")
    print(content.span)
    for item in html_search(content, {'span','div'}):
        print(item)
