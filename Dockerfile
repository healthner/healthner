FROM centos
ENV JARFILE = healthner-*.jar
ENV TARGET_DIR = /build/libs
RUN yum -y update && \
    yum -y install java-11
COPY ${TARGET_DIR}/${JARFILE} /home/
EXPOSE 8080/tcp
CMD java -jar /home/${JARFILE}
