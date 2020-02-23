#!/usr/bin/env bash
image_version=`date +%Y%m%d%H%M`;
# 关闭carp-gateway容器
docker stop carp-gateway || true;
# 删除carp-gateway容器
docker rm carp-gateway || true;
# 删除carp-gateway镜像
docker rmi --force $(docker images | grep carp-gateway | awk '{print $3}')
# 构建carp-gateway:$image_version镜像
docker build . -t carp-gateway:$image_version;
# 查看镜像列表
docker images;
# 基于carp-gateway 镜像 构建一个容器 carp-gateway
docker run\
    -p 8999:8999 -d\
    --name carp-gateway\
    --add-host carp-gateway:192.168.0.116\
    --add-host carp-auth:192.168.0.116\
    --add-host carp-ums:192.168.0.116\
    --add-host carp-dev:192.168.0.116\
    carp-gateway:$image_version ;
# 查看日志
docker logs carp-gateway;
#删除build过程中产生的镜像    #docker image prune -a -f
docker rmi $(docker images -f "dangling=true" -q)
# 对空间进行自动清理
docker system prune -a -f