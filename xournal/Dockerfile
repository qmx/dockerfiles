FROM debian:stretch
RUN apt-get update -qq && apt-get install -y --no-install-recommends \
	xournal \
	&& rm -rf /var/lib/apt/lists/*
CMD ["/usr/bin/xournal"]
