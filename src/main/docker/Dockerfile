#使用jdk8作为基础镜像
FROM java:8
#指定作者
MAINTAINER jerry
#暴露容器的8888端口
EXPOSE 8888
#将复制指定的demo-1.0.jar为容器中的app.jar，相当于拷贝到容器中取了个别名
ADD demo-1.0.jar app.jar
#设置时区
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# 运行jar包
RUN bash -c 'touch /app.jar'
#相当于在容器中用cmd命令执行jar包
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]