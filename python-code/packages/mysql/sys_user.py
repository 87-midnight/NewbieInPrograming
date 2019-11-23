from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, BIGINT, String

Base = declarative_base()


class User(Base):
    __tablename__ = 'sys_user'
    id = Column(BIGINT, primary_key=True)
    name = Column(String)
    gender = Column(String)
    address = Column(String)

    def __repr__(self):
        return '[User(id={},name={},gender={},address={})]'.format(self.id, self.name, self.gender, self.address)
