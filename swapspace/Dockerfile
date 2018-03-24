FROM debian:stretch as builder
RUN apt-get update -qq
RUN apt-get install -y build-essential
RUN apt-get install -y wget
WORKDIR /work
RUN wget http://pqxx.org/download/software/swapspace/swapspace-1.10.tar.gz
RUN tar -zxvf swapspace-1.10.tar.gz
WORKDIR /work/swapspace-1.10/src
ENV LDFLAGS "-static"
RUN make

FROM scratch
COPY --from=builder /work/swapspace-1.10/src/swapspace /bin/swapspace
