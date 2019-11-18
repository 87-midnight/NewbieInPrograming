# -*- coding:utf8 -*-
import re


def reg_exp(reg, str_):
    print(re.match(reg, str_, re.M).span())
    print(re.findall(reg,str_))
