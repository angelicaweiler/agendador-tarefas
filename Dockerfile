FROM gradle:7.5-jdk17 as build
WORKDIR /app
copy . .
run gradle build --no-daemon

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/agendador-tarefas.jar

EXPOSE 8089

CMD ["java", "-jar", "/app/agendador-tarefas.jar"]