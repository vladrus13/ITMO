#!/bin/bash
java --class-path "$(dirname ${BASH_SOURCE[0]})/lib/*" clojure.main "$@"
