FROM debian:stretch
RUN apt-get update
RUN apt-get install -y --no-install-recommends build-essential
RUN apt-get install -y --no-install-recommends autoconf automake pkg-config git
RUN git clone git://github.com/universal-ctags/ctags.git /root/ctags
WORKDIR /root/ctags
RUN ./autogen.sh
RUN ./configure
RUN make
