FROM mysql:8.0.32

ENV MYSQL_ROOT_PASSWORD root_password
ENV MYSQL_DATABASE mediscreen_patient_it_tests
ENV MYSQL_USER admin
ENV MYSQL_PASSWORD admin

ADD mysql/a_database_test_schema.sql /docker-entrypoint-initdb.d
ADD mysql/b_insert_data_test.sql /docker-entrypoint-initdb.d