FROM alpine:3.8
RUN apk add --no-cache libvirt-client
ENTRYPOINT ["virsh", "-c", "qemu:///system" ]
