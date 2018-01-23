# openvpn client

run with `docker run --rm -it -v /path/to/my/profile.ovpn:/etc/openvpn/client.conf --cap-add=NET_ADMIN --net=host --device /dev/net/tun -d qmxme/openvpn-client`
