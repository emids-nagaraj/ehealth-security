FROM openjdk:8
ADD build/libs/gradle-spring-boot-project.jar gradle-spring-boot-project
EXPOSE 8080
ENTRYPOINT ["java", "-jar","gradle-spring-boot-project"]