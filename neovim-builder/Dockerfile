FROM debian:stretch as builder
RUN apt-get update -q
RUN apt-get install -y git ninja-build libtool libtool-bin autoconf automake cmake g++ pkg-config unzip texinfo

WORKDIR /work
RUN git clone https://github.com/neovim/neovim.git
WORKDIR /work/neovim
RUN git checkout 0a349fd77de5cbf7516f6bc4f0117213b4f52b8b
RUN apt-get install -y curl
RUN make CMAKE_EXTRA_FLAGS="-DCMAKE_INSTALL_PREFIX=/opt/neovim"
RUN make install

FROM debian:9.4
COPY --from=builder /opt/neovim /opt/neovim
