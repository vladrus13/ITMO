@echo off
if "%~1" == "" (
    echo Usage: %~n0 ^<variant^>
    exit /b 1
)
javac -d "_out" "--class-path=%~dp0..\javascript;%~dp0..\java" "%~dp0jstest\functional\FunctionalExpressionTest.java" ^
    && java ^
        -ea -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI ^
        "--module-path=%~dp0graal" "--upgrade-module-path=%~dp0graal\compiler.jar" ^
        "--class-path=_out" jstest.functional.FunctionalExpressionTest "%~1"
