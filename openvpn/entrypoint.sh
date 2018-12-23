#!/bin/sh
mkdir -p /dev/net
if [ ! -c /dev/net/tun ]; then
    mknod /dev/net/tun c 10 200
fi

iptables -t nat -A POSTROUTING -s "$SERVER_CIDR" -o eth0 -j MASQUERADE

openvpn --config /etc/openvpn/openvpn.conf
