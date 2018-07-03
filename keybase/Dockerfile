FROM golang:1.10.2 as builder
RUN go get github.com/keybase/client/go/keybase
RUN cd /go/src/github.com/keybase/client && git reset --hard v2.1.0
RUN go install -tags production github.com/keybase/client/go/keybase

FROM debian:stretch-slim
RUN apt-get update -qq && apt-get install -y --no-install-recommends curl ca-certificates gnupg && rm -rf /var/lib/apt/lists/*
COPY --from=builder /go/bin/keybase /bin/keybase
RUN useradd -m keybase
USER keybase
ENTRYPOINT ["/bin/keybase"]
