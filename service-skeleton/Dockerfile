FROM gradle:latest
COPY src /home/matcher/src
COPY build.gradle /home/matcher/
COPY settings.gradle /home/matcher/
WORKDIR /home/matcher/
RUN gradle build
CMD ["gradle", "bootRun"]
