module ru.ifmo.rain.kuznetsov.impl {
    requires info.kgeorgiy.java.advanced.implementor;
    requires info.kgeorgiy.java.advanced.base;

    requires java.compiler;
    requires java.desktop;

    exports ru.ifmo.rain.kuznetsov.impl;
    opens ru.ifmo.rain.kuznetsov.impl;
}