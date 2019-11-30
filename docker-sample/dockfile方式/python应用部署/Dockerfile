FROM python:3.8.0
RUN mkdir /code
WORKDIR /code
COPY source ./source
COPY bootstrap.py .
COPY start.sh .
RUN pip install pymysql

CMD ["/bin/bash","start.sh"]