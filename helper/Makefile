mkfile_path := $(abspath $(lastword $(MAKEFILE_LIST)))
current_dir := $(notdir $(patsubst %/,%,$(dir $(mkfile_path))))
VERSION ?= $(shell git describe --always --tags)
REPO ?= qmxme
IMAGE ?= ${REPO}/${current_dir}

build:
	docker build ${DOCKER_BUILD_OPTS} -t ${IMAGE}:${VERSION} .

build-nocache:
	docker build ${DOCKER_BUILD_OPTS} --no-cache -t ${IMAGE}:${VERSION} .

push: build
	docker push ${IMAGE}:${VERSION}

tag: build
	docker tag ${IMAGE}:${VERSION} ${IMAGE}:latest

push-tag: tag
	docker push ${IMAGE}:latest

beta: build
	docker tag ${IMAGE}:${VERSION} ${IMAGE}:beta

push-beta: beta
	docker push ${IMAGE}:beta

export: build
	docker export $$(docker create ${IMAGE}:${VERSION}) | gzip -9c > ${current_dir}-${VERSION}.tar.gz
