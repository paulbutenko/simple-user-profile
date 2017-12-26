FROM openjdk:8-jre

ADD simple-user-profile-api*[^cs].jar /usr/local/bin/simple-user-profile-api.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/local/bin/simple-user-profile-api.jar"]
