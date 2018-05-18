FROM debian:stretch
# base docker install
RUN apt-get update \
	&& apt-get upgrade -y -q \
	&& apt-get install -y apt-transport-https ca-certificates curl gnupg \
	&& curl -fsSL "https://download.docker.com/linux/debian/gpg" | apt-key add - \
	&& echo "deb [arch=amd64] https://download.docker.com/linux/debian stretch stable" > /etc/apt/sources.list.d/docker.list \
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
COPY --from=qmxme/golang-tools:latest /usr/local/go /usr/local/go

# neovim install
COPY --from=qmxme/neovim-builder:latest /opt/neovim /opt/neovim

# dev packages
RUN apt-get update && apt-get install -qq -y \
	build-essential \
	clang \
	cmake \
	default-libmysqlclient-dev \
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
	libpq-dev \
	libsqlite3-dev \
	libssl-dev \
	lldb \
	mtr-tiny \
	pkg-config \
	pwgen \
	python \
	python3 \
	python3-pip \
	python3-flake8 \
	python3-pep8 \
	python3-setuptools \
	python3-wheel \
	shellcheck \
	sqlite3 \
	stow \
	sudo \
	tmate \
	unzip \
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
COPY --from=qmxme/rust-tools:latest /opt/rust-tools/bin/* /home/$user/.cargo/bin/

# golang + vim-go tools
COPY --from=qmxme/golang-tools:latest /go/bin/* /home/$user/bin/

# universal-ctags
COPY --from=qmxme/universal-ctags:latest /opt/local/bin/ctags /home/$user/bin

# dotfile setup
RUN git clone https://github.com/qmx/dotfiles.git ~/.dotfiles
RUN cd ~/.dotfiles && git pull && git reset --hard 658934b131b8acda0c0aa8a32ee1f0c9e9d6a1b0 && stow -v .

# install rust
RUN curl -sSf https://sh.rustup.rs | zsh -s -- -y
RUN /home/$user/.cargo/bin/rustup component add rust-src
RUN /home/$user/.cargo/bin/rustup toolchain install nightly
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
