#stage 1
FROM openjdk:21-slim-bookworm as build

ARG JAR_FILE

COPY ${JAR_FILE} app.jar

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf /app.jar)

#stage 2
FROM openjdk:21-slim-bookworm

VOLUME /tmp

ARG DEPENDENCY=/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","trandafyl.dev.hackathontest.HackathonTestApplication"]

EXPOSE 8081