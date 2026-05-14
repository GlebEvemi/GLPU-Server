FROM eclipse-temurin:21-jdk

WORKDIR /GLPUINVENTORY

COPY target/GLPUinventory-0.0.1-SNAPSHOT.jar Application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "Application.jar"]




