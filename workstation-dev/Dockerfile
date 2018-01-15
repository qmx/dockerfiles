FROM rust:1.23 as rust-builder
RUN apt-get update && apt-get install -y build-essential pkg-config libssl-dev zlib1g-dev liblzma-dev
RUN cargo install racer cargo-edit
RUN cargo install cargo-deb
RUN apt-get install -y cmake
RUN cargo install cargo-tree

FROM qmxme/workstation-box:latest
RUN apt-get update && apt-get install -qq -y \
	build-essential \
	direnv \
	git \
	htop \
	liblzma-dev \
	libssl-dev \
	neovim \
	pkg-config \
	pwgen \
	python3 \
	python3-pip \
	stow \
	sudo \
	tmate \
	zgen \
	zlib1g-dev \
	zsh \
	--no-install-recommends \
	&& rm -rf /var/lib/apt/lists/*

ARG user=dev
RUN useradd -m $user -G users,sudo,docker -s /bin/zsh
USER $user
RUN python3 -m pip install setuptools && python3 -m pip install wheel && python3 -m pip install neovim
RUN mkdir ~/.ssh && curl -fsL https://github.com/qmx.keys > ~/.ssh/authorized_keys && chmod 700 ~/.ssh && chmod 600 ~/.ssh/authorized_keys
RUN git clone https://github.com/qmx/dotfiles.git ~/.dotfiles
RUN cd ~/.dotfiles && stow .
RUN zsh ~/.zshrc || true
RUN nvim +PlugInstall +qall 2> /dev/null > /dev/null
RUN curl -sSf https://sh.rustup.rs | zsh -s -- -y
RUN /home/$user/.cargo/bin/rustup component add rust-src
COPY --from=rust-builder /usr/local/cargo/bin/racer /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/cargo-add /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/cargo-rm /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/cargo-upgrade /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/cargo-deb /home/$user/.cargo/bin/
COPY --from=rust-builder /usr/local/cargo/bin/cargo-tree /home/$user/.cargo/bin/
VOLUME ["/home/$user/dev"]

USER root
