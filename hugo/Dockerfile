FROM golang:1.11 as builder
RUN go get github.com/gohugoio/hugo

FROM debian:stretch
COPY --from=builder /go/bin/hugo /usr/local/bin/hugo
