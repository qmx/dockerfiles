FROM debian:stretch as builder
RUN apt-get update
RUN apt-get install -y --no-install-recommends build-essential
RUN apt-get install -y --no-install-recommends autoconf automake pkg-config git
RUN git clone git://github.com/universal-ctags/ctags.git /root/ctags
WORKDIR /root/ctags
RUN ./autogen.sh
RUN ./configure
RUN make

FROM debian:stretch
COPY --from=builder /root/ctags/ctags /opt/local/bin/ctags
