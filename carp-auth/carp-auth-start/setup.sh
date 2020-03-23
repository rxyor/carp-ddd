#!/usr/bin/env bash
image_version=`date +%Y%m%d%H%M`;
target_ip='192.168.0.116'
# 关闭carp-auth容器
docker stop carp-auth || true;
# 删除carp-auth容器
ret = `docker rm carp-auth || true`;
# 删除carp-ums镜像
ret = `docker rmi --force $(docker images | grep carp-auth | awk '{print $3}')`
# 构建carp-auth:$image_version镜像
docker build . -t carp-auth:$image_version;
# 查看镜像列表
docker images;
# 基于carp-auth 镜像 构建一个容器 carp-auth
docker run\
    -p 8001:8001 -d\
    --restart always\
    --name carp-auth\
    --net host\
    --add-host carp-gateway:$target_ip\
    --add-host carp-auth:$target_ip\
    --add-host carp-ums:$target_ip\
    --add-host carp-dev:$target_ip\
    carp-auth:$image_version ;
# 查看日志
docker logs carp-auth;
#删除build过程中产生的镜像    #docker image prune -a -f
ret = `docker rmi $(docker images | grep carp-auth -f "dangling=true" -q)` > /dev/null 2>&1
echo 'success'
# 对空间进行自动清理
#docker system prune -a -f