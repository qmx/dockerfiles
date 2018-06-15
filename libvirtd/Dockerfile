FROM debian:stretch
RUN apt-get update -qq && apt-get install -y \
	iptables \
	qemu-kvm \
	qemu-system-arm \
	libvirt-daemon-system \
	virtinst \
	--no-install-recommends \
	&& rm -rf /var/lib/apt/lists/*

COPY ./entrypoint /bin/entrypoint
ENTRYPOINT ["/bin/entrypoint"]
CMD ["libvirtd"]
