FROM rust:1.23 as rust-builder
RUN apt-get update && apt-get install -y cmake build-essential pkg-config libssl-dev zlib1g-dev liblzma-dev
RUN cargo install racer
RUN cargo install cargo-bloat
RUN cargo install cargo-deb
RUN cargo install cargo-edit
RUN cargo install cargo-tree
RUN cargo install ripgrep
RUN cargo install sccache

FROM golang:1.9.3 as golang-builder
RUN go get github.com/github/hub && go install github.com/github/hub
RUN go get github.com/junegunn/fzf && go install github.com/junegunn/fzf
RUN go get github.com/GoogleCloudPlatform/container-diff && go install github.com/GoogleCloudPlatform/container-diff
RUN go get github.com/jessfraz/dockfmt && go install github.com/jessfraz/dockfmt


# vim-go dependencies
RUN go get github.com/alecthomas/gometalinter && go install github.com/alecthomas/gometalinter
RUN go get github.com/davidrjenni/reftools/cmd/fillstruct && go install github.com/davidrjenni/reftools/cmd/fillstruct
RUN go get github.com/dominikh/go-tools/cmd/keyify && go install github.com/dominikh/go-tools/cmd/keyify
RUN go get github.com/fatih/gomodifytags && go install github.com/fatih/gomodifytags
RUN go get github.com/fatih/motion && go install github.com/fatih/motion
RUN go get github.com/golang/lint/golint && go install github.com/golang/lint/golint
RUN go get github.com/josharian/impl && go install github.com/josharian/impl
RUN go get github.com/jstemmer/gotags && go install github.com/jstemmer/gotags
RUN go get github.com/kisielk/errcheck && go install github.com/kisielk/errcheck
RUN go get github.com/klauspost/asmfmt/cmd/asmfmt && go install github.com/klauspost/asmfmt/cmd/asmfmt
RUN go get github.com/nsf/gocode && go install github.com/nsf/gocode
RUN go get github.com/rogpeppe/godef && go install github.com/rogpeppe/godef
RUN go get github.com/zmb3/gogetdoc && go install github.com/zmb3/gogetdoc
RUN go get golang.org/x/tools/cmd/goimports && go install golang.org/x/tools/cmd/goimports
RUN go get golang.org/x/tools/cmd/gorename && go install golang.org/x/tools/cmd/gorename
RUN go get golang.org/x/tools/cmd/guru && go install golang.org/x/tools/cmd/guru

FROM qmxme/workstation-box:latest
RUN apt-get update && apt-get install -qq -y \
	build-essential \
	clang \
	cmake \
	direnv \
	gdb \
	git \
	git-crypt \
	htop \
	jq \
	less \
	libclang-dev \
	liblzma-dev \
	libofx-dev \
	libsqlite3-dev \
	libssl-dev \
	lldb \
	mtr-tiny \
	neovim \
	pkg-config \
	pwgen \
	python \
	python3 \
	python3-pip \
	shellcheck \
	sqlite3 \
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
# user setup
USER $user
RUN mkdir -p /home/$user/bin /home/$user/.cargo/bin

# rust essential crates
COPY --from=rust-builder /usr/local/cargo/bin/racer /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/cargo-add /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/cargo-rm /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/cargo-upgrade /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/cargo-deb /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/cargo-tree /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/rg /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/cargo-bloat /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/sccache /home/$user/.cargo/bin/

COPY --from=golang-builder /go/bin/hub /home/$user/bin/
COPY --from=golang-builder /go/bin/fzf /home/$user/bin/
COPY --from=golang-builder /go/bin/container-diff /home/$user/bin/
COPY --from=golang-builder /go/bin/dockfmt /home/$user/bin/

# vim-go dependencies
COPY --from=golang-builder /go/bin/gometalinter /home/$user/bin/
COPY --from=golang-builder /go/bin/fillstruct /home/$user/bin/
COPY --from=golang-builder /go/bin/keyify /home/$user/bin/
COPY --from=golang-builder /go/bin/gomodifytags /home/$user/bin/
COPY --from=golang-builder /go/bin/motion /home/$user/bin/
COPY --from=golang-builder /go/bin/golint /home/$user/bin/
COPY --from=golang-builder /go/bin/impl /home/$user/bin/
COPY --from=golang-builder /go/bin/gotags /home/$user/bin/
COPY --from=golang-builder /go/bin/errcheck /home/$user/bin/
COPY --from=golang-builder /go/bin/asmfmt /home/$user/bin/
COPY --from=golang-builder /go/bin/gocode /home/$user/bin/
COPY --from=golang-builder /go/bin/godef /home/$user/bin/
COPY --from=golang-builder /go/bin/gogetdoc /home/$user/bin/
COPY --from=golang-builder /go/bin/goimports /home/$user/bin/
COPY --from=golang-builder /go/bin/gorename /home/$user/bin/
COPY --from=golang-builder /go/bin/guru /home/$user/bin/

# neovim and python dependencies
RUN python3 -m pip install setuptools && python3 -m pip install wheel && python3 -m pip install neovim && python3 -m pip install flake8 && python3 -m pip install autopep8


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

RUN ln -sf /mnt/codez/dev /home/$user/dev
RUN ln -sf /mnt/secrets /home/$user/.secrets
VOLUME ["/mnt/codez"]
VOLUME ["/mnt/secrets"]

USER root
