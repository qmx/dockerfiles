FROM golang:1.10.1
RUN go get github.com/github/hub && go install github.com/github/hub
RUN go get github.com/junegunn/fzf && go install github.com/junegunn/fzf
RUN go get github.com/GoogleCloudPlatform/container-diff && go install github.com/GoogleCloudPlatform/container-diff
RUN go get github.com/jessfraz/dockfmt && go install github.com/jessfraz/dockfmt


# vim-go dependencies
RUN go get github.com/alecthomas/gometalinter && go install github.com/alecthomas/gometalinter
RUN go get github.com/davidrjenni/reftools/cmd/fillstruct && go install github.com/davidrjenni/reftools/cmd/fillstruct
RUN go get github.com/dominikh/go-tools/cmd/keyify && go install github.com/dominikh/go-tools/cmd/keyify
RUN go get github.com/fatih/gomodifytags && go install github.com/fatih/gomodifytags
RUN go get github.com/fatih/motion && go install github.com/fatih/motion
RUN go get github.com/golang/lint/golint && go install github.com/golang/lint/golint
RUN go get github.com/josharian/impl && go install github.com/josharian/impl
RUN go get github.com/jstemmer/gotags && go install github.com/jstemmer/gotags
RUN go get github.com/kisielk/errcheck && go install github.com/kisielk/errcheck
RUN go get github.com/klauspost/asmfmt/cmd/asmfmt && go install github.com/klauspost/asmfmt/cmd/asmfmt
RUN go get github.com/nsf/gocode && go install github.com/nsf/gocode
RUN go get github.com/rogpeppe/godef && go install github.com/rogpeppe/godef
RUN go get github.com/zmb3/gogetdoc && go install github.com/zmb3/gogetdoc
RUN go get golang.org/x/tools/cmd/goimports && go install golang.org/x/tools/cmd/goimports
RUN go get golang.org/x/tools/cmd/gorename && go install golang.org/x/tools/cmd/gorename
RUN go get golang.org/x/tools/cmd/guru && go install golang.org/x/tools/cmd/guru
RUN go get github.com/gsamokovarov/jump && go install github.com/gsamokovarov/jump

