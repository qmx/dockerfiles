FROM debian:buster
RUN apt-get update -qqy && apt-get install -y devscripts reprepro debootstrap sbuild
RUN sbuild-createchroot --include=eatmydata,ccache,gnupg,dh-cargo,cargo,lintian,perl-openssl-defaults \
      --chroot-prefix debcargo-buster buster \
      /srv/chroot/debcargo-buster-amd64-sbuild http://deb.debian.org/debian
