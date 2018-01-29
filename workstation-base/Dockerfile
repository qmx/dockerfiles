FROM debian:buster
RUN apt-get update \
	&& apt-get install -y apt-transport-https ca-certificates curl gnupg \
	&& curl -fsSL "https://download.docker.com/linux/debian/gpg" | apt-key add - \
	&& echo "deb [arch=amd64] https://download.docker.com/linux/debian buster edge" > /etc/apt/sources.list.d/docker.list \
	&& apt-get update -qq \
	&& apt-get upgrade -y -q \
	&& apt-get install -y --no-install-recommends docker-ce \
	&& rm -rf /var/lib/apt/lists/*
