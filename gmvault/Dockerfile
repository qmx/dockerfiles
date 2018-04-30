FROM alpine:latest

ENV	GMVAULT_DEFAULT_GID="9000" \
	GMVAULT_DEFAULT_UID="9000"

RUN mkdir /app

# Set up environment.
RUN apk add --no-cache \
		bash \
		build-base \
		ca-certificates \
		git \
		mailx \
		openssl-dev \
		py-pip \
		python \
		python-dev \
		py-cffi \
		libffi-dev \
		ssmtp \
		shadow \
		su-exec \
		tzdata \
	&& pip install --upgrade pip \
	&& addgroup -g "$GMVAULT_DEFAULT_GID" gmvault \
	&& adduser \
		-H `# No home directory` \
		-D `# Don't assign a password` \
		-u "$GMVAULT_DEFAULT_UID" \
		-s "/bin/bash" \
		-G "gmvault" \
		gmvault

WORKDIR /app
RUN git clone https://github.com/qmx/gmvault.git
WORKDIR /app/gmvault
RUN git checkout -t origin/gmv-1.9.2
RUN python setup.py install

