FROM qmxme/workstation-box:latest
COPY --from=golang:1.9.4 /usr/local/go /usr/local/go
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
COPY --from=qmxme/rust-tools:latest /usr/local/cargo/bin/cargo-add /home/$user/.cargo/bin/
COPY --from=qmxme/rust-tools:latest /usr/local/cargo/bin/cargo-bloat /home/$user/.cargo/bin/
COPY --from=qmxme/rust-tools:latest /usr/local/cargo/bin/cargo-bump /home/$user/.cargo/bin/
COPY --from=qmxme/rust-tools:latest /usr/local/cargo/bin/cargo-deb /home/$user/.cargo/bin/
COPY --from=qmxme/rust-tools:latest /usr/local/cargo/bin/cargo-expand /home/$user/.cargo/bin/
COPY --from=qmxme/rust-tools:latest /usr/local/cargo/bin/cargo-license /home/$user/.cargo/bin/
COPY --from=qmxme/rust-tools:latest /usr/local/cargo/bin/cargo-rm /home/$user/.cargo/bin/
COPY --from=qmxme/rust-tools:latest /usr/local/cargo/bin/cargo-tree /home/$user/.cargo/bin/
COPY --from=qmxme/rust-tools:latest /usr/local/cargo/bin/cargo-upgrade /home/$user/.cargo/bin/
COPY --from=qmxme/rust-tools:latest /usr/local/cargo/bin/cargo-watch /home/$user/.cargo/bin/
COPY --from=qmxme/rust-tools:latest /usr/local/cargo/bin/cpubars /home/$user/.cargo/bin/
COPY --from=qmxme/rust-tools:latest /usr/local/cargo/bin/perf-focus /home/$user/.cargo/bin/
COPY --from=qmxme/rust-tools:latest /usr/local/cargo/bin/racer /home/$user/.cargo/bin/
COPY --from=qmxme/rust-tools:latest /usr/local/cargo/bin/rg /home/$user/.cargo/bin/
COPY --from=qmxme/rust-tools:latest /usr/local/cargo/bin/sccache /home/$user/.cargo/bin/

COPY --from=qmxme/golang-tools:latest /go/bin/hub /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/fzf /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/container-diff /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/dockfmt /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/jump /home/$user/bin/

# vim-go dependencies
COPY --from=qmxme/golang-tools:latest /go/bin/gometalinter /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/fillstruct /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/keyify /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/gomodifytags /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/motion /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/golint /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/impl /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/gotags /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/errcheck /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/asmfmt /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/gocode /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/godef /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/gogetdoc /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/goimports /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/gorename /home/$user/bin/
COPY --from=qmxme/golang-tools:latest /go/bin/guru /home/$user/bin/


# neovim and python dependencies
RUN python3 -m pip install setuptools && python3 -m pip install wheel && python3 -m pip install neovim && python3 -m pip install flake8 && python3 -m pip install autopep8


RUN git clone https://github.com/qmx/dotfiles.git ~/.dotfiles
RUN mkdir -p ~/.config
RUN cd ~/.dotfiles && stow .
RUN zsh ~/.zshrc || true
RUN nvim +PlugInstall +qall 2> /dev/null > /dev/null
RUN curl -sSf https://sh.rustup.rs | zsh -s -- -y
RUN /home/$user/.cargo/bin/rustup component add rust-src
RUN /home/$user/.cargo/bin/rustup toolchain install nightly
RUN /home/$user/.cargo/bin/rustup component add rustfmt-preview
RUN /home/$user/.cargo/bin/rustup component add rls-preview
RUN /home/$user/.cargo/bin/rustup component add rust-analysis

COPY --from=qmxme/universal-ctags:latest /root/ctags/ctags /home/$user/bin

VOLUME ["/mnt/codez"]
VOLUME ["/mnt/secrets"]
VOLUME ["/home/$user/.ssh"]
VOLUME ["/home/$user/.cargo/registry"]

USER root
ENV TARGET_USER=$user
COPY entrypoint /bin/entrypoint
ENTRYPOINT ["/bin/entrypoint"]
CMD ["/usr/sbin/sshd", "-D"]
