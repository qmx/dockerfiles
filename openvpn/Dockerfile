FROM alpine:3.8
RUN apk add --no-cache openvpn
COPY entrypoint.sh /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
