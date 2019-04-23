FROM confluentinc/cp-kafka-connect:5.2.1

ARG VERSION

# Validate confluent version
RUN ls /usr/share/java/confluent-common/common-utils-5.2.1.jar || false

RUN apt-get update && apt-get install -y unzip && rm -rf /var/lib/apt/lists/*

############################################################################################################
# Prepare plugin path, this will also set the "plugin.path" config key
############################################################################################################

ENV CONNECT_PLUGIN_PATH /usr/share/java/kafka-connect-plugins

RUN mkdir -p ${CONNECT_PLUGIN_PATH}
RUN mv /usr/share/java/kafka-connect-jdbc ${CONNECT_PLUGIN_PATH}/connectors-jdbc

############################################################################################################
# Debezium connectors
############################################################################################################

RUN curl -L https://repo1.maven.org/maven2/io/debezium/debezium-connector-postgres/0.8.3.Final/debezium-connector-postgres-0.8.3.Final-plugin.tar.gz -o /tmp/debezium-0.8.3.Final-postgres.tar.gz
RUN tar xzf /tmp/debezium-0.8.3.Final-postgres.tar.gz -C /tmp/
RUN mkdir -p ${CONNECT_PLUGIN_PATH}/connectors-debezium
RUN mv /tmp/debezium-connector-postgres/*.jar ${CONNECT_PLUGIN_PATH}/connectors-debezium/
RUN rm -rf /tmp/*


############################################################################################################
# Logback
############################################################################################################

# Remove log4j and replace it with logback

RUN rm /usr/share/java/kafka/log4j-1.2.17.jar \
 && rm /usr/share/java/kafka/slf4j-log4j12-1.7.25.jar \
 && curl -L http://central.maven.org/maven2/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar -o /usr/share/java/kafka/slf4j-api-1.7.25.jar \
 && curl -L http://central.maven.org/maven2/ch/qos/logback/logback-core/1.2.3/logback-core-1.2.3.jar -o /usr/share/java/kafka/logback-core-1.2.3.jar \
 && curl -L http://central.maven.org/maven2/ch/qos/logback/logback-classic/1.2.3/logback-classic-1.2.3.jar -o /usr/share/java/kafka/logback-classic-1.2.3.jar \
 && curl -L http://central.maven.org/maven2/org/codehaus/janino/janino/3.0.7/janino-3.0.7.jar -o /usr/share/java/kafka/janino-3.0.7.jar \
 && curl -L http://central.maven.org/maven2/org/codehaus/janino/commons-compiler/3.0.7/commons-compiler-3.0.7.jar -o /usr/share/java/kafka/commons-compiler-3.0.7.jar \
 && curl -L http://central.maven.org/maven2/net/logstash/logback/logstash-logback-encoder/4.11/logstash-logback-encoder-4.11.jar -o /usr/share/java/kafka/logstash-logback-encoder-4.11.jar

ADD logback.xml /etc/kafka/connect-logback.xml
ENV KAFKA_LOG4J_OPTS "-Dlogback.configurationFile=/etc/kafka/connect-logback.xml"

############################################################################################################
# Start
############################################################################################################

# Disable the health checks because they do not work correctly with SASL
RUN sed -i '/\/etc\/confluent\/docker\/ensure/d' /etc/confluent/docker/run

CMD ["/etc/confluent/docker/run"]
