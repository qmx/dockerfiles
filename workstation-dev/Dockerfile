FROM rust:1.23 as rust-builder
RUN apt-get update && apt-get install -y cmake build-essential pkg-config libssl-dev zlib1g-dev liblzma-dev
RUN cargo install racer
RUN cargo install cargo-bloat
RUN cargo install cargo-deb
RUN cargo install cargo-edit
RUN cargo install cargo-tree
RUN cargo install ripgrep

FROM golang:1.9.3 as golang-builder
RUN go get github.com/github/hub && go install github.com/github/hub
RUN go get github.com/junegunn/fzf && go install github.com/junegunn/fzf

FROM qmxme/workstation-box:latest
RUN apt-get update && apt-get install -qq -y \
	build-essential \
	cmake \
	direnv \
	gdb \
	git \
	git-crypt \
	htop \
	jq \
	liblzma-dev \
	libssl-dev \
	mtr-tiny \
	neovim \
	pkg-config \
	pwgen \
	python \
	python3 \
	python3-pip \
	shellcheck \
	stow \
	sudo \
	tmate \
	zgen \
	zip \
	zlib1g-dev \
	zsh \
	--no-install-recommends \
	&& rm -rf /var/lib/apt/lists/*

ARG user=dev
RUN useradd -m $user -G users,sudo,docker -s /bin/zsh
USER $user
RUN python3 -m pip install setuptools && python3 -m pip install wheel && python3 -m pip install neovim
RUN git clone https://github.com/qmx/dotfiles.git ~/.dotfiles
RUN cd ~/.dotfiles && stow .
RUN zsh ~/.zshrc || true
RUN nvim +PlugInstall +qall 2> /dev/null > /dev/null
RUN curl -sSf https://sh.rustup.rs | zsh -s -- -y
RUN /home/$user/.cargo/bin/rustup component add rust-src
RUN /home/$user/.cargo/bin/rustup toolchain install nightly
RUN /home/$user/.cargo/bin/rustup component add rustfmt-preview
RUN /home/$user/.cargo/bin/rustup component add rls-preview
RUN /home/$user/.cargo/bin/rustup component add rust-analysis
RUN mkdir -p /home/$user/.go \
	&& cd /home/$user/.go \
	&& curl https://dl.google.com/go/go1.9.3.linux-amd64.tar.gz -o go1.9.3.linux-amd64.tar.gz \
	&& tar -zxvf go1.9.3.linux-amd64.tar.gz
RUN mkdir ~/.ssh && curl -fsL https://github.com/qmx.keys > ~/.ssh/authorized_keys && chmod 700 ~/.ssh && chmod 600 ~/.ssh/authorized_keys

COPY --from=rust-builder /usr/local/cargo/bin/racer /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/cargo-add /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/cargo-rm /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/cargo-upgrade /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/cargo-deb /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/cargo-tree /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/rg /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/cargo-bloat /home/$user/.cargo/bin/
COPY --from=golang-builder /go/bin/hub /home/$user/bin/
COPY --from=golang-builder /go/bin/fzf /home/$user/bin/
VOLUME ["/mnt/codez"]

USER root
