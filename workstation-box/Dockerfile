FROM qmxme/workstation-base:latest
RUN apt-get update && apt-get install -y \
	openssh-server \
	mosh \
	tmux \
	locales \
	--no-install-recommends \
	&& rm -rf /var/lib/apt/lists/*
RUN mkdir /var/run/sshd
RUN sed 's@session\s*required\s*pam_loginuid.so@session optional pam_loginuid.so@g' -i /etc/pam.d/sshd

ENV LANG="en_US.UTF-8"
ENV LC_ALL="en_US.UTF-8"
ENV LANGUAGE="en_US.UTF-8"

RUN echo "en_US.UTF-8 UTF-8" > /etc/locale.gen && \
	locale-gen --purge $LANG && \
	dpkg-reconfigure --frontend=noninteractive locales && \
	update-locale LANG=$LANG LC_ALL=$LC_ALL LANGUAGE=$LANGUAGE

EXPOSE 22 63200/udp

CMD ["/usr/sbin/sshd", "-D"]
