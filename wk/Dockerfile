ARG GOLANG_VERSION=1.11.4
ARG RUST_VERSION=1.32.0
ARG DEBIAN_BUSTER_HASH=sha256:4392c8510201816bace1ab55164964c897ebabf5d47ef2e1a20c258d21e80f3b

## golang tools
FROM golang:$GOLANG_VERSION as golang_builder
RUN go get github.com/genuinetools/reg
RUN go get github.com/golang/dep/cmd/dep

# vim-go dependencies
FROM golang:$GOLANG_VERSION as vimgo_deps
RUN apt-get update -q && apt-get install -y -qq vim-nox
RUN git clone https://github.com/fatih/vim-go.git /root/.vim/pack/lang/start/vim-go
RUN vim +":set nomore" +GoInstallBinaries +qall

# rust tools
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
RUN cargo install --root /opt/rust-tools diesel_cli
RUN cargo install --root /opt/rust-tools perf-focus
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

# install terraform
FROM debian:buster as terraform_builder
RUN apt-get update && apt-get install -y curl ca-certificates unzip
RUN curl -L -o /tmp/terraform.zip https://releases.hashicorp.com/terraform/0.11.11/terraform_0.11.11_linux_amd64.zip
RUN cd /usr/local/bin && unzip /tmp/terraform.zip && chmod 755 /usr/local/bin/terraform

# install kubectl
FROM debian:buster as kubectl_builder
RUN apt-get update && apt-get install -y curl ca-certificates unzip
RUN curl -L -o /usr/local/bin/kubectl https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl
RUN chmod 755 /usr/local/bin/kubectl

# base distro
FROM debian:buster@$DEBIAN_BUSTER_HASH
RUN apt-get update -qq && apt-get upgrade -y && apt-get install -qq -y \
	openssh-server \
	--no-install-recommends \
	&& rm -rf /var/lib/apt/lists/*
# sshd setup
RUN mkdir /var/run/sshd
RUN sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd
RUN sed 's/#Port 22/Port 3222/' -i /etc/ssh/sshd_config

# default package set
RUN apt-get update -qq && apt-get upgrade -y && apt-get install -qq -y \
	apache2-utils \
	apt-transport-https \
	build-essential \
	ca-certificates \
	cargo \
	clang \
	cmake \
	curl \
	default-libmysqlclient-dev \
	default-mysql-client \
	direnv \
	dnsutils \
	docker.io \
	entr \
	exuberant-ctags \
	fakeroot-ng \
	flake8 \
	fzf \
	gdb \
	git \
	git-crypt \
	gnupg \
	golang-1.11 \
	htop \
	hub \
	hugo \
	ipcalc \
	jq \
	less \
	libclang-dev \
	liblzma-dev \
	libpq-dev \
	libprotoc-dev \
	libsqlite3-dev \
	libssl-dev \
	lldb \
	locales \
	mosh \
	mtr-tiny \
	musl-tools \
	ncdu \
	netcat-openbsd \
	nodejs \
	npm \
	openjdk-8-jdk-headless \
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
	restic \
	ripgrep \
	rustc \
	rust-src \
	shellcheck \
	socat \
	sqlite3 \
	stow \
	sudo \
	tmate \
	tmux \
	unzip \
	vim-nox \
	zgen \
	zip \
	zlib1g-dev \
	zsh \
	--no-install-recommends \
	&& rm -rf /var/lib/apt/lists/*


ENV LANG="en_US.UTF-8"
ENV LC_ALL="en_US.UTF-8"
ENV LANGUAGE="en_US.UTF-8"

RUN echo "en_US.UTF-8 UTF-8" > /etc/locale.gen && \
	locale-gen --purge $LANG && \
	dpkg-reconfigure --frontend=noninteractive locales && \
	update-locale LANG=$LANG LC_ALL=$LC_ALL LANGUAGE=$LANGUAGE

# rust tools
RUN curl -L -o /tmp/cpubars_0.2.1_amd64.deb https://github.com/qmx/cpubars/releases/download/0.2.1/cpubars_0.2.1_amd64.deb && dpkg -i /tmp/cpubars_0.2.1_amd64.deb && rm /tmp/*.deb
RUN curl -L -o /tmp/marinara_0.2.0_amd64.deb https://github.com/qmx/marinara/releases/download/0.2.0/marinara_0.2.0_amd64.deb && dpkg -i /tmp/marinara_0.2.0_amd64.deb && rm /tmp/*.deb
RUN curl -L -o /tmp/jump_0.22.0_amd64.deb https://github.com/gsamokovarov/jump/releases/download/v0.22.0/jump_0.22.0_amd64.deb && dpkg -i /tmp/jump_0.22.0_amd64.deb && rm /tmp/*.deb
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

# terraform
COPY --from=terraform_builder /usr/local/bin/terraform /usr/local/bin/

# kubectl
COPY --from=kubectl_builder /usr/local/bin/kubectl /usr/local/bin/


# user setup
ARG user=qmx
ARG uid=1000
ARG github_user=qmx
RUN useradd -m $user -u $uid -G users,sudo,docker -s /bin/zsh
USER $user
RUN mkdir ~/.ssh && curl -fsL https://github.com/$github_user.keys > ~/.ssh/authorized_keys && chmod 700 ~/.ssh && chmod 600 ~/.ssh/authorized_keys

# some empty folders, with proper permissions
RUN mkdir -p ~/bin ~/.cargo/bin ~/.config ~/tmp ~/.gnupg ~/.local ~/.vim && chmod 700 ~/.gnupg

# dotfile setup
RUN git clone --recursive https://github.com/qmx/dotfiles.git ~/.dotfiles
RUN cd ~/.dotfiles && stow -v .

# install rust
RUN curl -sSf https://sh.rustup.rs | zsh -s -- -y --default-toolchain none
RUN /home/$user/.cargo/bin/rustup toolchain link system /usr
RUN /home/$user/.cargo/bin/rustup default system

# make sure we start sshd at the end - always keep this at the bottom
USER root
EXPOSE 3222 63200-63220/udp
CMD ["/usr/sbin/sshd", "-D"]
