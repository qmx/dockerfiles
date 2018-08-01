ARG GOLANG_VERSION=1.10.2
ARG RUST_VERSION=1.27.2

## golang tools
FROM golang:$GOLANG_VERSION as golang_builder
RUN go get github.com/github/hub
RUN go get github.com/junegunn/fzf
RUN go get github.com/GoogleCloudPlatform/container-diff
RUN go get github.com/jessfraz/dockfmt
RUN go get github.com/gsamokovarov/jump
RUN go get github.com/golang/dep/cmd/dep

# vim-go dependencies
RUN go get github.com/alecthomas/gometalinter
RUN go get github.com/davidrjenni/reftools/cmd/fillstruct
RUN go get github.com/fatih/gomodifytags
RUN go get github.com/fatih/motion
RUN go get github.com/golang/lint/golint
RUN go get github.com/josharian/impl
RUN go get github.com/jstemmer/gotags
RUN go get github.com/kisielk/errcheck
RUN go get github.com/klauspost/asmfmt/cmd/asmfmt
RUN go get github.com/nsf/gocode
RUN go get github.com/rogpeppe/godef
RUN go get github.com/zmb3/gogetdoc
RUN go get golang.org/x/tools/cmd/goimports
RUN go get golang.org/x/tools/cmd/gorename
RUN go get golang.org/x/tools/cmd/guru

## rustlang tools

FROM rust:$RUST_VERSION as rust_builder
RUN apt-get update && apt-get install -y cmake build-essential pkg-config libssl-dev zlib1g-dev liblzma-dev
RUN cargo install --root /opt/rust-tools cargo-bloat
RUN cargo install --root /opt/rust-tools cargo-bump
RUN cargo install --root /opt/rust-tools cargo-bundle
RUN cargo install --root /opt/rust-tools cargo-deb
RUN cargo install --root /opt/rust-tools cargo-edit
RUN cargo install --root /opt/rust-tools cargo-expand
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
RUN cargo install --root /opt/rust-tools cargo-docserver

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
	--no-install-recommends \
	&& rm -rf /var/lib/apt/lists/*
COPY --from=qmxme/mosh-builder:latest /tmp/mosh_1.3.2-2.1_amd64.deb /tmp/
RUN dpkg -i /tmp/mosh_1.3.2-2.1_amd64.deb && rm /tmp/*.deb
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

# neovim install
COPY --from=qmxme/neovim-builder:latest /opt/neovim /opt/neovim

# dev packages
RUN apt-get update && apt-get install -qq -y \
	build-essential \
	clang \
	cmake \
	default-libmysqlclient-dev \
	direnv \
	dnsutils \
	exuberant-ctags \
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
	pkg-config \
	pwgen \
	python \
	python3 \
	python3-flake8 \
	python3-pip \
	python3-setuptools \
	python3-wheel \
	quilt \
	shellcheck \
	sqlite3 \
	stow \
	sudo \
	tmate \
	unzip \
	vim-nox \
	yapf3 \
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
RUN mkdir -p /home/$user/bin /home/$user/.cargo/bin ~/.config
# rust essential crates
COPY --from=rust_builder /opt/rust-tools/bin/* /home/$user/.cargo/bin/

# golang + vim-go tools
COPY --from=golang_builder /go/bin/* /home/$user/bin/

# dotfile setup
RUN git clone https://github.com/qmx/dotfiles.git ~/.dotfiles
RUN cd ~/.dotfiles && stow -v .

# install rust
RUN curl -sSf https://sh.rustup.rs | zsh -s -- -y
RUN /home/$user/.cargo/bin/rustup component add rust-src
RUN /home/$user/.cargo/bin/rustup component add rustfmt-preview
RUN /home/$user/.cargo/bin/rustup component add rls-preview
RUN /home/$user/.cargo/bin/rustup component add rust-analysis

# neovim and python dependencies
RUN /opt/neovim/bin/nvim +PlugInstall +qall 2> /dev/null > /dev/null
RUN python3 -m pip install neovim

# make sure we start sshd at the end - always keep this at the bottom
USER root
EXPOSE 3222 63200-63220/udp
ADD entrypoint.sh /bin/entrypoint.sh
ENTRYPOINT ["/bin/entrypoint.sh"]
CMD ["/usr/sbin/sshd", "-D"]
