FROM java:8-jre-alpine

RUN mkdir -p /opt/target

COPY target/yporque-0.1.0.jar /opt/target/yporque-0.1.0.jar
COPY init.sh /opt/init.sh

RUN chmod a+x /opt/init.sh

MAINTAINER Francisco Giana <gianafrancisco@gmail.com>

EXPOSE 8080

ENTRYPOINT /opt/init.sh