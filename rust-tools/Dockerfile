FROM rust:1.25 as builder
RUN apt-get update && apt-get install -y cmake build-essential pkg-config libssl-dev zlib1g-dev liblzma-dev
RUN cargo install --root /opt/rust-tools cargo-bloat
RUN cargo install --root /opt/rust-tools cargo-bump
RUN cargo install --root /opt/rust-tools cargo-deb
RUN cargo install --root /opt/rust-tools cargo-edit
RUN cargo install --root /opt/rust-tools cargo-expand
RUN cargo install --root /opt/rust-tools cargo-license
RUN cargo install --root /opt/rust-tools cargo-tree
RUN cargo install --root /opt/rust-tools cargo-watch
RUN cargo install --root /opt/rust-tools cpubars
RUN cargo install --root /opt/rust-tools perf-focus
RUN cargo install --root /opt/rust-tools racer
RUN cargo install --root /opt/rust-tools ripgrep
RUN cargo install --root /opt/rust-tools sccache
RUN cargo install --root /opt/rust-tools diesel_cli

FROM rust:1.25
COPY --from=builder /opt/rust-tools/bin /opt/rust-tools/bin
