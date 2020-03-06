@javac -d _out RunJS.java ^
    && java ^
        -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI ^
        --module-path=graal --upgrade-module-path=graal/compiler.jar ^
        -cp _out RunJS
