FROM qmxme/rpi-arch
RUN pacman -Syu --noconfirm
RUN pacman -S nodejs redis supervisor --noconfirm
RUN pacman -S git --noconfirm
RUN npm install -g hipache
RUN mkdir -p /etc/hipache
ADD ./supervisord.conf /etc/supervisord.conf
ADD ./supervisord/hipache.ini /etc/supervisor.d/hipache.ini
ADD ./supervisord/redis.ini /etc/supervisor.d/redis.ini
ADD ./hipache_config.json /etc/hipache/config.json
EXPOSE 80
EXPOSE 6379
CMD ["-n", "-c", "/etc/supervisord.conf"]
ENTRYPOINT ["/usr/bin/supervisord"]