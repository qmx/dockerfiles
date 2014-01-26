FROM qmxme/rpi-arch
MAINTAINER Douglas Campos <qmx@qmx.me>

RUN pacman -Syu --noconfirm
RUN pacman -S nginx --noconfirm
RUN echo "daemon off;" >> /etc/nginx/nginx.conf
EXPOSE 80
CMD /usr/sbin/nginx -c /etc/nginx/nginx.conf