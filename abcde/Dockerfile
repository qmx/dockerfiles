FROM debian:stretch
RUN apt-get update -qq && apt-get install -y \
	abcde \
	lame \
	eyed3 \
	&& rm -rf /var/lib/apt/lists/*
WORKDIR /work
VOLUME ["/work"]
