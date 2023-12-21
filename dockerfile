# # 1. Utiliser l'image java
# FROM openjdk:17
#
# # 2. Setter le repertoire de travail du conteneur
# WORKDIR /app
#
# # 3. Copier le .jar dans le conteneur
# COPY target/Airport-0.0.1.jar /app/Airport-0.0.1.jar
#
# # 4. Exposer le port
# EXPOSE 8080
#
# # 5. Executer le fichier
# CMD ["java","-jar","Airport-0.0.1.jar"]


# Etape 1 Build avec Maven
FROM maven:3.6.3-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

# Etape 2 créer l'image
FROM openjdk:17
ARG JAR_FILE=/app/target/*.jar
COPY --from=builder ${JAR_FILE} aeroport.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/aeroport.jar"]