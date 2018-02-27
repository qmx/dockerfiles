FROM debian:stretch
RUN apt-get update -qq && apt-get install -y --no-install-recommends \
	dirmngr \
	gnupg2 \
	openssh-client \
	scdaemon \
	&& rm -rf /var/lib/apt/lists/*

ENV LC_ALL=C.UTF-8 LANG=C.UTF-8
WORKDIR /root/
