# -*- coding:utf-8 -*-
import sys

sys.path.append("J:\\NewbieInPrograming\\python-code\\packages\\file\\")

from file_read import *

read_files('J:\\我的简历.doc')


def write_files(path,write_content):
    file = open(path, "r+")
    file.write(write_content+"\n")
    file.close()
