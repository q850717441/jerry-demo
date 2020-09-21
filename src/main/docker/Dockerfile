#使用jdk8作为基础镜像
FROM java:8
#指定作者
MAINTAINER jerry
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp
#将复制指定的demo-1.0.jar为容器中的app.jar，相当于拷贝到容器中取了个别名
ADD demo-1.0.jar app.jar
#相当于在容器中用cmd命令执行jar包
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]