FROM debian:stretch-slim
RUN apt-get update -q && apt-get install -y ca-certificates calibre --no-install-recommends && rm -rf /var/lib/apt/lists/*
WORKDIR /work
VOLUME ["/work"]
COPY generate-ebook /bin/generate-ebook
CMD ["/bin/generate-ebook"]
