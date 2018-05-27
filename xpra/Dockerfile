FROM debian:stable
RUN apt-get update
RUN apt-get install -y --no-install-recommends xpra openssh-server chromium stterm curl ca-certificates
RUN mkdir /var/run/sshd
RUN sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd
RUN useradd -m qmx -G users -s /bin/bash
USER qmx
RUN mkdir ~/.ssh && curl -fsL https://github.com/qmx.keys > ~/.ssh/authorized_keys && chmod 700 ~/.ssh && chmod 600 ~/.ssh/authorized_keys
USER root
EXPOSE 22
CMD ["/usr/sbin/sshd", "-D"]
