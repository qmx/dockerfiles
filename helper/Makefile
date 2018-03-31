mkfile_path := $(abspath $(lastword $(MAKEFILE_LIST)))
current_dir := $(notdir $(patsubst %/,%,$(dir $(mkfile_path))))

build:
	docker build -t qmxme/${current_dir} .

build-nocache:
	docker build --no-cache -t qmxme/${current_dir} .

push:
	docker push qmxme/${current_dir}
