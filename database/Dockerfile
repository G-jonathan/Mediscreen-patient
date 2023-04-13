FROM mysql:8.0.32
LABEL authors="jonathan.G"

ENV MYSQL_ROOT_PASSWORD root
ENV MYSQL_DATABASE mediscreen_patient
ENV MYSQL_USER test
ENV MYSQL_PASSWORD test

ADD a_database_schema.sql /docker-entrypoint-initdb.d/
ADD b_insert_data.sql /docker-entrypoint-initdb.d/