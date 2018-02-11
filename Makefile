SUBDIRS := $(filter-out helper/., $(wildcard */.))

all: $(SUBDIRS)
$(SUBDIRS):
	$(MAKE) -C $@

.PHONY: all $(SUBDIRS)
