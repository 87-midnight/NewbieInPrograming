FROM ubuntu:latest
ENV LC_ALL C
ENV DEBIAN_FRONTEND noninteractive
ENV DEBCONF_NONINTERACTIVE_SEEN true
ENV LANG C.UTF-8

RUN apt-get update && apt-get install -y software-properties-common
RUN add-apt-repository -y ppa:mozillateam/firefox-next
RUN apt-get update && apt-get install -y firefox \
  && rm -rf /var/lib/apt/lists/*
RUN apt-get update && apt-get install -y \
    python3 python3-pip curl unzip libgconf-2-4 wget
RUN pip3 install selenium && mkdir /usr/local/download

RUN wget -P /usr/local/download https://github.com/mozilla/geckodriver/releases/download/v0.26.0/geckodriver-v0.26.0-linux64.tar.gz \
&& tar xvzf /usr/local/download/geckodriver-*.tar.gz ./ \
&& rm -f /usr/bin/geckodriver \
&& ln -s /usr/local/download/geckodriver /usr/bin/geckodriver
