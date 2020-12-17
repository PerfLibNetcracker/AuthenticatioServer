**Для сборки и запуска докерфайла:**
`docker build -t springio/authserver-spring-boot-docker .` в корне директории
Внутри докерфайла можно указать `ENV env1=var` чтобы указать переменные окружения (к примеру `DATASOURCE_URL`)
Если бд находится на той же машине, то запускается контейнер через:
`docker run -d --net host springio/authserver-spring-boot-docker`
Если была указана переменная окружения для внешнего сервера, то запуск через:
`docker run -d -p 8081:8081 springio/authserver-spring-boot-docker`
