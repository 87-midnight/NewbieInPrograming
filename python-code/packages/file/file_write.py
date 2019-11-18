# -*- coding:utf-8 -*-


def write_files(path,write_content):
    file = open(path, "r+")
    file.write(write_content+"\n")
    file.close()
