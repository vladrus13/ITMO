# javadoc -cp info.kgeorgiy.java.advanced.implementor.jar ru/ifmo/rain/kuznetsov/impl/Implementor.java

HOMEDir="/home/vladkuznetsov/Vl/Projects/Java/java-advanced-2020/05. JarImplementor/HW05"

javadoc -d javadoc -link https://docs.oracle.com/en/java/javase/11/docs/api --module-path "$HOMEDir/src"  -private -author --module-source-path modules --module ru.ifmo.rain.kuznetsov.impl modules/info.kgeorgiy.java.advanced.implementor/info/kgeorgiy/java/advanced/implementor/JarImpler.java modules/info.kgeorgiy.java.advanced.implementor/info/kgeorgiy/java/advanced/implementor/ImplerException.java