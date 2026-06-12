@echo off
echo =======================================================
echo Iniciando Guardián Financiero IA - Backend (Windows)
echo =======================================================

IF "%JAVA_HOME%" == "" (
    echo.
    echo [ERROR] JAVA_HOME no esta configurado en tu sistema.
    echo El proyecto requiere Java 21 para ejecutarse, y Maven Wrapper necesita saber donde esta instalado.
    echo.
    echo Puedes verificar donde esta instalado tu Java usando:
    echo    where java
    echo.
    echo Y puedes comprobar tu version instalada usando:
    echo    java -version
    echo.
    echo Por favor, lee el archivo SETUP_WINDOWS.md para ver como solucionarlo facilmente.
    echo.
    pause
    exit /b 1
) ELSE (
    echo [OK] JAVA_HOME detectado: %JAVA_HOME%
    echo Ejecutando Spring Boot...
    echo.
    .\mvnw.cmd spring-boot:run
)
