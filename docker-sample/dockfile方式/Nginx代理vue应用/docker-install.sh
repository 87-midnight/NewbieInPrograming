docker stop vue-demo
docker rm vue-demo
docker build -t vue-images .
docker run -d -i -t --name vue-demo -p5000:80 vue-images