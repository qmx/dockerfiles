FROM alpine:3.7 as builder
RUN apk add --no-cache libvirt-dev go git musl-dev
RUN go get github.com/dmacvicar/terraform-provider-libvirt

FROM alpine:3.7

RUN apk add --no-cache ansible terraform ca-certificates openssh libvirt-client
COPY --from=builder /root/go/bin/terraform-provider-libvirt /usr/local/bin/
