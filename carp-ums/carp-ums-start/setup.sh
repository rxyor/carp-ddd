#!/usr/bin/env bash
image_version=`date +%Y%m%d%H%M`;
# 关闭carp-ums容器
docker stop carp-ums || true;
# 删除carp-ums容器
docker rm carp-ums || true;
# 删除carp-ums镜像
docker rmi --force $(docker images | grep carp-ums | awk '{print $3}')
# 构建carp-ums:$image_version镜像
docker build . -t carp-ums:$image_version;
# 查看镜像列表
docker images;
# 基于carp-ums 镜像 构建一个容器 carp-ums
docker run\
    -p 8002:8002 -d\
    --name carp-ums\
    --add-host carp-gateway:192.168.0.116\
    --add-host carp-auth:192.168.0.116\
    --add-host carp-ums:192.168.0.116\
    --add-host carp-dev:192.168.0.116\
    carp-ums:$image_version ;
# 查看日志
docker logs carp-ums;
#删除build过程中产生的镜像    #docker image prune -a -f
docker rmi $(docker images | grep carp-ums -f "dangling=true" -q)
# 对空间进行自动清理
#docker system prune -a -f