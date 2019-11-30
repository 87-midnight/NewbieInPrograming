docker stop mysql-centos-run
docker rm mysql-centos-run && docker rmi mysql-centos
docker build -t mysql-centos .
docker run -d --name mysql-centos-run -p3308:3306 mysql-centos
