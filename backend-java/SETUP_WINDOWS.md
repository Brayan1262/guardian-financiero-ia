# Configuración en Windows: Error JAVA_HOME

Si al intentar ejecutar el proyecto en Windows ves este error:

```text
Error: JAVA_HOME not found in your environment.
Please set the JAVA_HOME variable in your environment to match the location of your Java installation.
```

Esto significa que, aunque tengas Java instalado, Windows no sabe dónde encontrarlo de forma global. El script `mvnw.cmd` requiere explícitamente esta variable para poder arrancar Spring Boot.

## 1. Verificar tu instalación de Java

Primero, asegúrate de que tienes Java instalado y su versión:
```cmd
java -version
```

Para buscar en qué ruta está instalado, puedes usar:
```cmd
where java
```
Esto te dará una ruta como `C:\Program Files\Eclipse Adoptium\jdk-21.0.11.9-hotspot\bin\java.exe`. 
La ruta que necesitamos para `JAVA_HOME` es la carpeta principal (sin el `\bin\java.exe`), por ejemplo: `C:\Program Files\Eclipse Adoptium\jdk-21.0.11.9-hotspot`.

## 2. Configurar JAVA_HOME temporalmente en CMD

Si solo quieres ejecutar el backend rápidamente sin modificar la configuración profunda de Windows, puedes declararlo en tu misma consola antes de ejecutar el proyecto:

```cmd
set JAVA_HOME=C:\Ruta\Hacia\Tu\Java\jdk-21
.\mvnw.cmd spring-boot:run
```
*(Cambia la ruta por la carpeta real donde tienes Java 21)*

## 3. Configurar JAVA_HOME permanente en Windows

Para que funcione siempre y no tengas que declararlo cada vez:

1. Presiona la tecla **Windows**, escribe **Variables de entorno** y selecciona "Editar las variables de entorno del sistema".
2. Haz clic en el botón **Variables de entorno...** en la parte inferior.
3. En la sección "Variables del sistema" (abajo), haz clic en **Nueva...**.
4. En Nombre de la variable pon: `JAVA_HOME`
5. En Valor de la variable pon la ruta de tu JDK (ej: `C:\Program Files\Eclipse Adoptium\jdk-21.0.11.9-hotspot`).
6. Acepta todas las ventanas, **cierra tu consola actual** y ábrela de nuevo para que detecte los cambios.

## 4. Ejecutar el backend

Una vez configurado, puedes ejecutar el backend de dos formas:

**Forma tradicional:**
```cmd
.\mvnw.cmd spring-boot:run
```

**Usando nuestro script de ayuda:**
Hemos creado un archivo `.bat` para hacer esta verificación por ti y facilitar la ejecución. Simplemente haz doble clic sobre él o ejecútalo en la consola:
```cmd
.\run-backend.bat
```
