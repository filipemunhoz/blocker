FROM amazoncorretto:11-alpine-jdk
MAINTAINER filipemunhoz
COPY target/blockers-api-0.0.3-SNAPSHOT.jar blockers-api-0.0.3-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","-Xms1G", "-Xmx1G", "blockers-api-0.0.3-SNAPSHOT.jar"]
