FROM openjdk:8-jre

ADD simple-user-profile-client*[^cs].jar /usr/local/bin/simple-user-profile-client.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/local/bin/simple-user-profile-client.jar"]
