#!/bin/sh

set -e
if [ -S "/var/run/docker.sock" ]; then
	SOCK_GID="$(stat -c "%g" /var/run/docker.sock)"
	CURR_GID="$(getent group docker|cut -d: -f3)"
	if [ "$SOCK_GID" != "$CURR_GID" ]; then
		echo "fixing docker gid"
		groupmod -g "${SOCK_GID}" docker
	fi
fi

exec "$@"
