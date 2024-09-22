# En este bloque generamos el código de la UI
FROM node:22-alpine3.19 as build_ui

COPY src/main/gui /source_code/gui
WORKDIR /source_code/gui
RUN apk update && \
    apk add git && \
    npm install -g grunt grunt-cli bower && \
    npm install -g && \
    bower instal && \
    grunt build

# En este bloque se genera el archivo jar que contiene el compilado del código de java
FROM maven:3.9.9 as build

COPY src/ /source_code/src
COPY pom.xml /source_code/pom.xml
# Borramos el código existente de la UI
RUN rm -rf /source_code/src/main/resources/static
# Copiamos el código de la UI generado en el paso anterior
COPY --from=build_ui /source_code/resources/static /source_code/src/main/resources/static

WORKDIR /source_code/
RUN mvn -DskipTests install

# En este último bloque se crea el la imagen de docker que se puede utilizar
FROM openjdk:8-jre-alpine

RUN mkdir -p /opt/target

COPY --from=build /source_code/target/yporque-0.1.1.jar /opt/target/yporque-0.1.1.jar
COPY init.sh /opt/init.sh

RUN chmod a+x /opt/init.sh

LABEL MAINTAINER="Francisco Giana <gianafrancisco@gmail.com>"

EXPOSE 8080

CMD ["/opt/init.sh"]
