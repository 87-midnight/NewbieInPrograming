FROM ubuntu:latest
RUN  apt-get update && apt-get -y install xvfb gtk2-engines-pixbuf
RUN apt-get -y install xfonts-cyrillic xfonts-100dpi xfonts-75dpi xfonts-base xfonts-scalable
RUN apt-get -y install imagemagick x11-apps wget gnupg2
RUN Xvfb -ac :99 -screen 0 1280x1024x16 & export DISPLAY=:99

RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
RUN sh -c 'echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list'
RUN apt-get -y install google-chrome-stable


RUN wget http://chromedriver.storage.googleapis.com/79.0.3945.36/chromedriver_linux64.zip
RUN apt-get -y install zip
RUN apt-get -y install unzip python3 python3-pip curl
RUN unzip chromedriver_linux64.zip
RUN cp chromedriver /usr/bin/