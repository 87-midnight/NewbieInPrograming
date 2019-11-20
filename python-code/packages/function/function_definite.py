# -*- coding:utf8 -*-
# https://blog.csdn.net/qq_36387683/article/details/82257213


def print_something():
    print("function test")


def print_something_return(content):
    print("you input:", content)
    return content


def print_multi_variables(list=None, name='default', *tuple, **dic):
    print("list=", list, "name=", name, ",tuple=", tuple, ",dic=", dic)


def _private_print():
    print("[_private_print],this function can only be called in private")


if __name__ == '__main__':
    print_something()
    print_multi_variables('hello', ('apple', 'banana'), {"id": 1}, {"id": 2}, dic={"id": 3, 4: 'well'})
    print(print_something_return("this is content test."))
