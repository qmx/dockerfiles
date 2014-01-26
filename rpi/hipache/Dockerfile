FROM qmxme/rpi-arch
MAINTAINER Douglas Campos <qmx@qmx.me>

# update image
RUN pacman -Syu --noconfirm

# install supervisor
RUN pacman -S supervisor --noconfirm
ADD ./supervisord.conf /etc/supervisord.conf

# install redis
RUN pacman -S redis --noconfirm
ADD ./supervisord/redis.ini /etc/supervisor.d/redis.ini

# install hipache
RUN pacman -S nodejs --noconfirm
RUN pacman -S git --noconfirm
RUN npm install -g hipache
RUN mkdir -p /etc/hipache
ADD ./hipache_config.json /etc/hipache/config.json
ADD ./supervisord/hipache.ini /etc/supervisor.d/hipache.ini

EXPOSE 80
EXPOSE 6379

CMD ["-n", "-c", "/etc/supervisord.conf"]
ENTRYPOINT ["/usr/bin/supervisord"]