ARG GOLANG_VERSION=1.11.1
ARG RUST_VERSION=1.30.0

## golang tools
FROM golang:$GOLANG_VERSION as golang_builder
RUN go get github.com/genuinetools/reg
RUN go get github.com/github/hub
RUN go get github.com/golang/dep/cmd/dep
RUN go get github.com/gsamokovarov/jump
RUN go get github.com/jessfraz/dockfmt
RUN go get github.com/junegunn/fzf

# vim-go dependencies
FROM golang:$GOLANG_VERSION as vimgo_deps
RUN apt-get update -q && apt-get install -y -qq vim-nox
RUN git clone https://github.com/fatih/vim-go.git /root/.vim/pack/lang/start/vim-go
RUN vim +":set nomore" +GoInstallBinaries +qall

## rustlang tools

FROM rust:$RUST_VERSION as rust_builder
RUN apt-get update && apt-get install -y cmake build-essential pkg-config libssl-dev zlib1g-dev liblzma-dev
RUN cargo install --root /opt/rust-tools cargo-bloat
RUN cargo install --root /opt/rust-tools cargo-bump
RUN cargo install --root /opt/rust-tools cargo-bundle
RUN cargo install --root /opt/rust-tools cargo-deb
RUN cargo install --root /opt/rust-tools cargo-docserver
RUN cargo install --root /opt/rust-tools cargo-edit
RUN cargo install --root /opt/rust-tools cargo-expand
RUN cargo install --root /opt/rust-tools cargo-generate
RUN cargo install --root /opt/rust-tools cargo-license
RUN cargo install --root /opt/rust-tools cargo-release
RUN cargo install --root /opt/rust-tools cargo-tree
RUN cargo install --root /opt/rust-tools cargo-watch
RUN cargo install --root /opt/rust-tools cpubars
RUN cargo install --root /opt/rust-tools diesel_cli
RUN cargo install --root /opt/rust-tools perf-focus
RUN cargo install --root /opt/rust-tools ripgrep
RUN cargo install --root /opt/rust-tools sccache
RUN cargo install --root /opt/rust-tools systemfd
RUN cargo install --root /opt/rust-tools wasm-pack

FROM rustlang/rust:nightly as rust_nightly_builder
RUN apt-get update && apt-get install -y cmake build-essential pkg-config libssl-dev zlib1g-dev liblzma-dev
RUN cargo install --root /opt/rust-tools racer

# install sbt
FROM debian:buster as sbt_builder
RUN apt-get update && apt-get install -y curl ca-certificates
RUN curl -L -o /tmp/sbt.tgz https://piccolo.link/sbt-1.2.6.tgz && tar -C /opt -zxvf /tmp/sbt.tgz &&  rm -rf /tmp/sbt.tgz

FROM debian:buster
# base docker install
RUN apt-get update \
	&& apt-get upgrade -y -q \
	&& apt-get install -y apt-transport-https ca-certificates curl gnupg \
	&& curl -fsSL "https://download.docker.com/linux/debian/gpg" | apt-key add - \
	&& echo "deb [arch=amd64] https://download.docker.com/linux/debian buster stable" > /etc/apt/sources.list.d/docker.list \
	&& apt-get update -qq \
	&& apt-get install -y --no-install-recommends docker-ce \
	&& rm -rf /var/lib/apt/lists/*
# docker-compose
RUN curl -L https://github.com/docker/compose/releases/download/1.21.2/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose && chmod +x /usr/local/bin/docker-compose
# start sshd/mosh/tmux setup
RUN apt-get update \
	&& apt-get install -y \
	openssh-server \
	tmux \
	libprotobuf10 \
	locales \
	mosh \
	--no-install-recommends \
	&& rm -rf /var/lib/apt/lists/*
RUN mkdir /var/run/sshd
RUN sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd
RUN sed 's/#Port 22/Port 3222/' -i /etc/ssh/sshd_config

ENV LANG="en_US.UTF-8"
ENV LC_ALL="en_US.UTF-8"
ENV LANGUAGE="en_US.UTF-8"

RUN echo "en_US.UTF-8 UTF-8" > /etc/locale.gen && \
	locale-gen --purge $LANG && \
	dpkg-reconfigure --frontend=noninteractive locales && \
	update-locale LANG=$LANG LC_ALL=$LC_ALL LANGUAGE=$LANGUAGE

# golang setup
COPY --from=golang_builder /usr/local/go /usr/local/go

# rust essential crates
COPY --from=rust_builder /opt/rust-tools/bin/* /usr/local/bin/
COPY --from=rust_nightly_builder /opt/rust-tools/bin/* /usr/local/bin/

# golang tools
COPY --from=golang_builder /go/bin/* /usr/local/bin/

# vim-go tools
COPY --from=vimgo_deps /go/bin/* /usr/local/bin/

# java tools
COPY --from=sbt_builder /opt/sbt /opt/sbt
RUN ln -sf /opt/sbt/bin/sbt /usr/local/bin/sbt

# dev packages
RUN apt-get update && apt-get install -qq -y \
	apache2-utils \
	build-essential \
	clang \
	cmake \
	default-libmysqlclient-dev \
	direnv \
	dnsutils \
	exuberant-ctags \
	fakeroot-ng \
	gdb \
	git \
	git-crypt \
	htop \
	jq \
	less \
	libclang-dev \
	liblzma-dev \
	libofx-dev \
	libpq-dev \
	libsqlite3-dev \
	libssl-dev \
	lldb \
	mtr-tiny \
	ncdu \
	netcat-openbsd \
	nodejs \
	npm \
	openjdk-8-jdk \
	pkg-config \
	protobuf-compiler \
	pwgen \
	python \
	python3 \
	python3-flake8 \
	python3-pip \
	python3-setuptools \
	python3-venv \
	python3-wheel \
	qrencode \
	quilt \
	shellcheck \
	sqlite3 \
	stow \
	sudo \
	tmate \
	unzip \
	vim-nox \
	zgen \
	zip \
	zlib1g-dev \
	zsh \
	--no-install-recommends \
	&& rm -rf /var/lib/apt/lists/*

ARG user=qmx
ARG uid=1000
ARG github_user=qmx
RUN useradd -m $user -u $uid -G users,sudo,docker -s /bin/zsh
USER $user
RUN mkdir ~/.ssh && curl -fsL https://github.com/$github_user.keys > ~/.ssh/authorized_keys && chmod 700 ~/.ssh && chmod 600 ~/.ssh/authorized_keys

# bin folder
RUN mkdir -p /home/$user/bin /home/$user/.cargo/bin ~/.config ~/tmp

# dotfile setup
RUN git clone --recursive https://github.com/qmx/dotfiles.git ~/.dotfiles
RUN cd ~/.dotfiles && stow -v .

# install rust
RUN curl -sSf https://sh.rustup.rs | zsh -s -- -y
RUN /home/$user/.cargo/bin/rustup component add rust-src
RUN /home/$user/.cargo/bin/rustup component add rustfmt-preview
RUN /home/$user/.cargo/bin/rustup component add rls-preview
RUN /home/$user/.cargo/bin/rustup component add rust-analysis
RUN /home/$user/.cargo/bin/rustup install nightly
RUN /home/$user/.cargo/bin/rustup target add wasm32-unknown-unknown --toolchain=nightly

# make sure we start sshd at the end - always keep this at the bottom
USER root
EXPOSE 3222 63200-63220/udp
ADD entrypoint.sh /bin/entrypoint.sh
ENTRYPOINT ["/bin/entrypoint.sh"]
CMD ["/usr/sbin/sshd", "-D"]
