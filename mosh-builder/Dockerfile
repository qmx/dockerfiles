FROM debian:9.4
RUN apt-get update -q
RUN apt-get upgrade -y
RUN apt-get install -y --no-install-recommends \
	bash-completion \
	build-essential \
	devscripts \
	dh-autoreconf \
	git \
	less \
	libncurses5-dev \
	libprotobuf-dev \
	libssl-dev \
	libutempter-dev \
	pkg-config \
	protobuf-compiler \
	tmux \
	zlib1g-dev
RUN apt-get install -y locales
RUN apt-get install -y ca-certificates
WORKDIR /tmp
RUN git clone https://github.com/qmx/mosh.git
WORKDIR /tmp/mosh
RUN git checkout origin/agent-forward
RUN dch -i "added agent forwarding patch"
RUN dpkg-buildpackage -uc -us -b

