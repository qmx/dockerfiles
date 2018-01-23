FROM alpine:3.7
RUN apk add --no-cache openvpn
ENTRYPOINT ["openvpn", "--config", "/etc/openvpn/client.conf"]
