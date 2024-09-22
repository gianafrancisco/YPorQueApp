# Iniciar app desde docker

Para descargar la úyltima versión desde docker hub ejecutar

```bash
docker pull fgiana/yporque:latest
docker run -p 8080:8080 fgiana/yporque:latest
```

Luego abrir el nevegador e ingrear a http://localhost:8080

Por otro lado para ejecutar el buil local ejecutar

```bash
docker run --rm -p 8080:8080 --name yporque yporque:latest
# Para hacer stop de la app ejecutar
# docker stop yporque
```

# Generar un nuevo build (local)

```bash
docker build -t yporque .
```

```bash
docker run --rm -p 8080:8080 --name yporque yporque:latest
docker cp yporque:/opt/target/yporque-0.1.1.jar .
docker stop yporque
```


# Obtener el .jar final

En caso de que no se use docker para correr la APP, se puede extraer el archivo jar para ejecutarlo en forma local.

# Requerimientos para correlo local

- maven
- java 8 (SDK & JRE)
- Spring boot framework
- angular 1.4
- grunt (para compilar el codigo de angular)
- Mysql database

# Estructura del projecto

- src/test: test unitarios
- src/main: Código java
- src/gui: Código web en angular (no angular-ng)
