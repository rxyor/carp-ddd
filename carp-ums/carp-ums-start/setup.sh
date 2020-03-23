#!/usr/bin/env bash
image_version=`date +%Y%m%d%H%M`;
target_ip='192.168.0.116'
# 关闭carp-ums容器
docker stop carp-ums || true;
# 删除carp-ums容器
ret = `docker rm carp-ums || true`;
# 删除carp-ums镜像
ret = `docker rmi --force $(docker images | grep carp-ums | awk '{print $3}')`
# 构建carp-ums:$image_version镜像
docker build . -t carp-ums:$image_version;
# 查看镜像列表
docker images;
# 基于carp-ums 镜像 构建一个容器 carp-ums
docker run\
    -p 8002:8002 -d\
    --restart always\
    --name carp-ums\
    --net host\
    --add-host carp-gateway:$target_ip\
    --add-host carp-auth:$target_ip\
    --add-host carp-ums:$target_ip\
    --add-host carp-dev:$target_ip\
    carp-ums:$image_version ;
# 查看日志
docker logs carp-ums;
#删除build过程中产生的镜像    #docker image prune -a -f
ret = `docker rmi $(docker images | grep carp-ums -f "dangling=true" -q)` > /dev/null 2>&1
echo 'success'
# 对空间进行自动清理
#docker system prune -a -f