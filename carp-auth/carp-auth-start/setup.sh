#!/usr/bin/env bash
image_version=`date +%Y%m%d%H%M`;
# 关闭carp-auth容器
docker stop carp-auth || true;
# 删除carp-auth容器
docker rm carp-auth || true;
# 删除carp-auth镜像
docker rmi --force $(docker images | grep carp-auth | awk '{print $3}')
# 构建carp-auth:$image_version镜像
docker build . -t carp-auth:$image_version;
# 查看镜像列表
docker images;
# 基于carp-auth 镜像 构建一个容器 carp-auth
docker run\
    -p 8001:8080 -d\
    --name carp-auth\
    --add-host carp-gateway:192.168.0.116\
    --add-host carp-auth:192.168.0.116\
    --add-host carp-ums:192.168.0.116\
    --add-host carp-dev:192.168.0.116\
    carp-auth:$image_version ;
# 查看日志
docker logs carp-auth;
#删除build过程中产生的镜像    #docker image prune -a -f
docker rmi $(docker images -f "dangling=true" -q)
# 对空间进行自动清理
docker system prune -a -f