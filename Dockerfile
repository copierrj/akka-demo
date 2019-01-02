FROM azul/zulu-openjdk:8 as builder
COPY . /akka-demo/
WORKDIR /akka-demo
RUN ["/bin/bash", "gradlew", "distTar"]
RUN ["tar", "-xvf", "build/distributions/akka-demo.tar"]

FROM azul/zulu-openjdk:8
COPY --from=builder /akka-demo/akka-demo /opt/akka-demo/
ENTRYPOINT ["/opt/akka-demo/bin/akka-demo"]
