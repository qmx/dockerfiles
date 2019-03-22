#!/bin/bash

HOSTNAME="$(hostname)"
echo "127.0.0.1 $HOSTNAME" >> /etc/hosts

exec "$@"
