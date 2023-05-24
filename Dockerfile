FROM openjdk:11
LABEL authors="jonathan.G"
ADD build/libs/Mediscreen-patient-0.0.1-SNAPSHOT.jar Mediscreen-patient-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","Mediscreen-patient-0.0.1-SNAPSHOT.jar"]
EXPOSE 8081