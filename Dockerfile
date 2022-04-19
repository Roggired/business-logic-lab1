FROM ubuntu:20.04

ENV TZ="Europe/Moscow"
ENV WILDFLY_VERSION 24.0.1.Final
ENV JBOSS_HOME /opt/widlfly
ENV POSTGRESQL_DRIVER_VERSION 42.2.23

ARG DB_HOST=db
ARG DB_PORT=5432
ARG DB_NAME=postgres
ARG DB_USER=postgres
ARG DB_PASS=postgres

RUN apt update --yes && apt install --yes \
      openjdk-11-jre \
      wget \
      tar \
      curl \
    && wget https://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz -P /tmp \
    && mkdir -p $JBOSS_HOME \
    && tar xf /tmp/wildfly-$WILDFLY_VERSION.tar.gz -C /tmp/ \
    && mv /tmp/wildfly-$WILDFLY_VERSION/* $JBOSS_HOME/ \
    && rm -r /tmp/wildfly-$WILDFLY_VERSION.tar.gz

RUN $JBOSS_HOME/bin/add-user.sh admin -p admin -s

RUN cd /tmp && \
    curl --location --output postgresql-$POSTGRESQL_DRIVER_VERSION.jar --url http://search.maven.org/remotecontent?filepath=org/postgresql/postgresql/$POSTGRESQL_DRIVER_VERSION/postgresql-$POSTGRESQL_DRIVER_VERSION.jar \
    && /bin/sh -c '$JBOSS_HOME/bin/standalone.sh &' \
    && sleep 10 \
    && $JBOSS_HOME/bin/jboss-cli.sh --connect --command="deploy /tmp/postgresql-${POSTGRESQL_DRIVER_VERSION}.jar" \
    && $JBOSS_HOME/bin/jboss-cli.sh --connect --command="xa-data-source add --name=PostgresDS --jndi-name=java:/PostgresDS --user-name=${DB_USER} --password=${DB_PASS} --driver-name=postgresql-${POSTGRESQL_DRIVER_VERSION}.jar --xa-datasource-class=org.postgresql.xa.PGXADataSource --xa-datasource-properties=ServerName=${DB_HOST},PortNumber=${DB_PORT},DatabaseName=${DB_NAME} --valid-connection-checker-class-name=org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLValidConnectionChecker --exception-sorter-class-name=org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLExceptionSorter" \
    && $JBOSS_HOME/bin/jboss-cli.sh --connect --command=:shutdown \
    && rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/ $JBOSS_HOME/standalone/log/* \
    && rm -rf /tmp/postgresql-*

RUN ln -s $JBOSS_HOME/standalone/deployments /deployments
RUN mkdir /uploaded-files

EXPOSE 65100 9990

CMD ["sh", "-c", "$JBOSS_HOME/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 -Djboss.http.port=65100"]