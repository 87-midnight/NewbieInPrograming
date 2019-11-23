import sys

sys.path.append("J:\\NewbieInPrograming\\python-code\\packages\\mysql\\")

from sys_user import User
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

# 初始化数据库连接:
engine = create_engine('mysql+pymysql://root:123456@localhost:3306/test?charset=utf8')
# 创建DBSession类型:
DBSession = sessionmaker(bind=engine)
session = DBSession()


def create_user():

    # 创建新User对象:
    new_user = User(id=3, name='john',gender='male',address='河北')
    user1 = User(id=4,name='amy',gender='female',address='天津')
    print(new_user.__tablename__)
    print(new_user.id)
    print(user1.id)
    # 添加到session:
    session.add(new_user)
    session.add(user1)
    # 提交即保存到数据库:
    session.commit()


def query_user():
    list_ = session.query(User).all()
    for item in list_:
        print("查询结果:", item)


def get_user_id(id_=None):
    if id_ is None:
        return "no id set up"
    return session.query(User).filter(User.id == id_).one()


if __name__ == '__main__':
    # create_user()
    query_user()
    print("获取单个用户:", get_user_id(1))
    # 关闭session:
    session.close()
