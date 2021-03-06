#!/bin/sh
image_version=`date +%Y%m%d%H%M`;
target_ip='192.168.0.116'
# 关闭carp-gateway容器
docker stop carp-gateway || true;
# 删除carp-gateway容器
ret = `docker rm carp-gateway || true`;
# 删除carp-ums镜像
ret = `docker rmi --force $(docker images | grep carp-gateway | awk '{print $3}')`
# 构建carp-gateway:$image_version镜像
docker build . -t carp-gateway:$image_version;
# 查看镜像列表
docker images;
# 基于carp-gateway 镜像 构建一个容器 carp-gateway
docker run\
    -p 8999:8999 -d\
    --restart always\
    --name carp-gateway\
    --add-host carp-gateway:$target_ip\
    --add-host carp-auth:$target_ip\
    --add-host carp-ums:$target_ip\
    --add-host carp-dev:$target_ip\
    carp-gateway:$image_version ;
# 查看日志
docker logs carp-gateway;
#删除build过程中产生的镜像    #docker image prune -a -f
ret = `docker rmi $(docker images | grep carp-gateway -f "dangling=true" -q)` > /dev/null 2>&1
echo 'success'
# 对空间进行自动清理
#docker system prune -a -f