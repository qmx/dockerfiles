FROM alpine:3.7
RUN apk add --no-cache libvirt-client
ENTRYPOINT ["virsh", "-c", "qemu:///system" ]
