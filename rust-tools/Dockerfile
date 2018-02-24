FROM rust:1.24
RUN apt-get update && apt-get install -y cmake build-essential pkg-config libssl-dev zlib1g-dev liblzma-dev
RUN cargo install racer
RUN cargo install cargo-bloat
RUN cargo install cargo-deb
RUN cargo install cargo-edit
RUN cargo install cargo-tree
RUN cargo install ripgrep
RUN cargo install sccache
RUN cargo install perf-focus
RUN cargo install cargo-watch
RUN cargo install cargo-expand
RUN cargo install cargo-license
RUN cargo install cpubars
