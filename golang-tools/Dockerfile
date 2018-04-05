FROM golang:1.10.1 as builder
RUN go get github.com/github/hub
RUN go get github.com/junegunn/fzf
RUN go get github.com/GoogleCloudPlatform/container-diff
RUN go get github.com/jessfraz/dockfmt
RUN go get github.com/gsamokovarov/jump
RUN go get github.com/golang/dep/cmd/dep


# vim-go dependencies
RUN go get github.com/alecthomas/gometalinter
RUN go get github.com/davidrjenni/reftools/cmd/fillstruct
RUN go get github.com/dominikh/go-tools/cmd/keyify
RUN go get github.com/fatih/gomodifytags
RUN go get github.com/fatih/motion
RUN go get github.com/golang/lint/golint
RUN go get github.com/josharian/impl
RUN go get github.com/jstemmer/gotags
RUN go get github.com/kisielk/errcheck
RUN go get github.com/klauspost/asmfmt/cmd/asmfmt
RUN go get github.com/nsf/gocode
RUN go get github.com/rogpeppe/godef
RUN go get github.com/zmb3/gogetdoc
RUN go get golang.org/x/tools/cmd/goimports
RUN go get golang.org/x/tools/cmd/gorename
RUN go get golang.org/x/tools/cmd/guru

FROM golang:1.10.1
COPY --from=builder /go/bin /go/bin
