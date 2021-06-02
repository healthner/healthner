FROM centos
RUN yum -y update && \
    yum -y install java-11
COPY /build/libs/healthner*.jar /home/
EXPOSE 8080/tcp
WORKDIR /home
ENTRYPOINT java -jar healthner*.jar
