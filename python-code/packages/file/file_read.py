# -*- coding:utf-8 -*-


def read_files(path):
    file = open(path, 'r+')
    str_ = file.read()
    print(str_)
    file.close()

