FROM openjdk:21
WORKDIR /home/project/work4
COPY *.jar /home/project/work4
CMD ["nohup", "java", "-jar", "demo_10-0.0.10.jar", "&"]