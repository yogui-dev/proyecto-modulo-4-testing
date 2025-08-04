# Proyecto Módulo 4 - Testing Automatizado v1.2.1

## 📋 Descripción

Proyecto de testing automatizado desarrollado con **Selenium WebDriver** y **TestNG** para realizar pruebas de funcionalidad en aplicaciones web. El proyecto incluye tests automatizados para login y registro de usuarios, utilizando el patrón **Page Object Model**, datos de prueba desde archivos CSV y validaciones avanzadas de mensajes de error y redirecciones.

## 🆕 Novedades v1.2.1

- **Captura automática de screenshots**: Sistema mejorado que toma capturas en cada fase de las pruebas (inicio, éxito, fallo, omisión)
- **Nombres de archivo con fecha y hora**: Screenshots con formato de fecha legible (YYYY-MM-DD_HH-MM-SS)
- **Documentación JavaDoc completa**: Todas las clases y métodos documentados con JavaDoc
- **Listeners de TestNG mejorados**: Implementación de listeners para monitoreo completo de pruebas

## 🆕 Novedades v1.2.0

- **WebDriverManager**: Integración de WebDriverManager para gestión automática de drivers
- **Validaciones mejoradas**: Verificación de mensajes de error específicos usando selectores CSS/XPath
- **Estructura de tests por navegador**: Organización de tests en paquetes separados para Chrome y Firefox
- **Formato CSV expandido**: Soporte para columnas adicionales (selector, type, testType) en datos de prueba
- **Manejo de casos de prueba**: Diferenciación entre casos de éxito y error con validaciones específicas
- **Eliminación de Edge**: Simplificación a solo Chrome y Firefox como navegadores de prueba

## 🛠️ Tecnologías Utilizadas

- **Java 24** - Lenguaje de programación principal
- **Selenium WebDriver 4.21.0** - Automatización de navegadores web
- **WebDriverManager 5.8.0** - Gestión automática de drivers de navegadores
- **TestNG 7.10.0** - Framework de testing
- **Apache Commons CSV 1.10.0** - Lectura y procesamiento de archivos CSV
- **Logback 1.5.13** - Sistema de logging
- **Maven** - Gestión de dependencias y construcción del proyecto
- **Chrome y Firefox** - Navegadores para la ejecución de tests

## 📁 Estructura del Proyecto

```
proyecto-modulo-4-testing/
├── src/
│   ├── main/java/
│   │   ├── listeners/
│   │   ├── org/
│   │   ├── pages/
│   │   │   ├── LoginPage.java      # Page Object para página de login
│   │   │   └── RegisterPage.java   # Page Object para página de registro
│   │   └── utils/
│   └── test/
│       ├── java/
│       │   ├── listeners/
│       │   │   └── ScreenshotListener.java  # Listener para captura de screenshots
│       │   ├── tests/
│       │   │   ├── BaseTest.java   # Clase base para todos los tests
│       │   │   ├── chrome/         # Tests específicos para Chrome
│       │   │   │   ├── LoginTest.java
│       │   │   │   └── RegisterTest.java
│       │   │   └── firefox/        # Tests específicos para Firefox
│       │   │       ├── LoginTest.java
│       │   │       └── RegisterTest.java
│       │   └── utils/
│       │       └── ScreenshotUtil.java  # Utilidad para captura de screenshots
│       └── resources/
│           └── data/
│               ├── login.csv       # Datos de prueba para login
│               └── register.csv    # Datos de prueba para registro
├── testng.xml                      # Configuración de TestNG
├── pom.xml                         # Configuración de Maven
└── README.md                       # Este archivo
```

## 🚀 Configuración e Instalación

### Prerrequisitos

1. **Java JDK 24** o superior
2. **Maven 3.6+**
3. **Chrome y Firefox** instalados en el sistema

### Instalación

1. Clona el repositorio:
```bash
git clone <url-del-repositorio>
cd proyecto-modulo-4-testing
```

2. Instala las dependencias:
```bash
mvn clean install
```

3. Verifica que Chrome y Firefox estén instalados y accesibles.

## ▶️ Ejecución de Tests

### Ejecutar todos los tests
```bash
mvn test
```

### Ejecutar tests específicos con TestNG
```bash
mvn test -Dtest=chrome.LoginTest
mvn test -Dtest=firefox.RegisterTest
```

### Ejecutar con archivo de configuración TestNG
```bash
mvn test -DsuiteXmlFile=testng.xml
```

## 📊 Tests Incluidos

### LoginTest
- **Propósito**: Validar la funcionalidad de inicio de sesión
- **Datos**: Lee credenciales y mensajes esperados desde `login.csv`
- **Validaciones**: 
  - Para casos exitosos: Verifica redirección a URL esperada
  - Para casos de error: Verifica mensaje de error específico usando selectores CSS/XPath
- **Implementación**: Disponible para Chrome y Firefox

### RegisterTest
- **Propósito**: Validar la funcionalidad de registro de usuarios
- **Datos**: Lee emails y mensajes esperados desde `register.csv`
- **Validaciones**: Verifica mensajes de error específicos usando selectores CSS
- **Implementación**: Disponible para Chrome y Firefox

## 📄 Datos de Prueba

### Login (login.csv)

```csv
username,password,expectedMessage,selector,type,testType
user_testing,F$xV@jsRnNGCoYRA7QxypydQ,https://tienda.demo.yoguilab.space/mi-cuenta/,,,success
usuario_invalido,cualquierclave,"Error: El nombre de usuario usuario_invalido no está registrado en este sitio. Si no estás seguro de tu nombre de usuario, prueba con tu dirección de correo electrónico en su lugar.",.wc-block-components-notice-banner__content,css,"error"
user_testing,password_invalida,"Error: la contraseña que has introducido para el nombre de usuario user_testing no es correcta. ¿Has olvidado tu contraseña?",.wc-block-components-notice-banner__content,css,"error"
```

### Registro (register.csv)

```csv
email,expectedMessage,selector,type,testType
user@email.com,Error: Ya hay una cuenta registrada con user@email.com. Inicia sesión o utiliza otra dirección de correo electrónico.,.wc-block-components-notice-banner.is-error, css,error
```

### Formato de los CSV
- **login.csv**: username, password, expectedMessage, selector, type, testType
- **register.csv**: email, expectedMessage, selector, type, testType
- **testType**: 
  - "success" para casos de prueba exitosos (verifica URL)
  - "error" para casos de prueba fallidos (verifica mensaje de error)
- **type**: Tipo de selector (css, xpath) para localizar elementos

## 🏗️ Arquitectura

### Page Object Model
El proyecto utiliza el patrón **Page Object Model** para:
- Separar la lógica de los tests de los elementos de la UI
- Mejorar la mantenibilidad del código
- Reutilizar componentes entre diferentes tests

### WebDriverManager
Se utiliza **WebDriverManager** para:
- Gestionar automáticamente la descarga y configuración de los drivers de navegadores
- Eliminar la necesidad de descargar y configurar manualmente los drivers
- Asegurar compatibilidad entre versiones de navegadores y drivers

### Estructura de Clases
- **BaseTest**: Clase base con métodos comunes para todos los tests
- **LoginPage**: Encapsula elementos y acciones de la página de login
- **RegisterPage**: Encapsula elementos y acciones de la página de registro
- **Tests por navegador**: Implementaciones específicas para Chrome y Firefox

## 📝 Logging

El proyecto utiliza **Logback** para el sistema de logging:
- Logs informativos del inicio y fin de cada test
- Registro de usuarios utilizados en las pruebas
- Información sobre la validación de tipos de test

## 🔧 Configuración

### TestNG Configuration (testng.xml)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<suite name="M4Suite">
    <test name="LoginYRegistro_Chrome">
        <classes>
            <class name="tests.chrome.LoginTest"/>
            <class name="tests.chrome.RegisterTest"/>
        </classes>
    </test>
    <test name="LoginYRegistro_Firefox">
        <classes>
            <class name="tests.firefox.LoginTest"/>
            <class name="tests.firefox.RegisterTest"/>
        </classes>
    </test>
</suite>
```

### Dependencias Principales (pom.xml)
- **Selenium WebDriver**: Automatización de navegadores
- **WebDriverManager**: Gestión automática de drivers
- **TestNG**: Framework de testing con data providers
- **Apache Commons CSV**: Lectura eficiente de archivos CSV
- **Logback**: Sistema de logging robusto

## 🐛 Solución de Problemas

### Error: NoSuchDriverException
**Solución**: El proyecto utiliza WebDriverManager para gestionar automáticamente los drivers:
```java
WebDriverManager.chromedriver().setup();
WebDriverManager.firefoxdriver().setup();
```

### Error: ElementClickInterceptedException
**Solución**: La clase LoginPage implementa manejo de excepciones para elementos interceptados:
```java
try {
    button.click();
} catch (ElementClickInterceptedException e) {
    // Esperar a que desaparezca un posible overlay
    wait.until(ExpectedConditions.invisibilityOfElementLocated(...));
    button.click(); // Reintento
}
```

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 📞 Contacto

Para preguntas o sugerencias sobre este proyecto, por favor contacta al equipo de desarrollo.

---

**Nota**: Este proyecto es parte del Módulo 4 de testing automatizado y está diseñado con fines educativos y de demostración de buenas prácticas en automatización de pruebas.

## 📷 Capturas de Pantalla

El proyecto incluye un sistema automático de captura de screenshots que registra el estado del navegador en diferentes momentos de la ejecución de las pruebas:

- **Al inicio de cada prueba** (`_START`): Captura el estado inicial antes de realizar acciones
- **Al finalizar con éxito** (`_SUCCESS`): Captura el estado final después de una prueba exitosa
- **Al ocurrir un fallo** (`_FAILURE`): Captura el estado en el momento exacto del fallo
- **Al omitir una prueba** (`_SKIPPED`): Captura el estado cuando una prueba es omitida

Las capturas se guardan en el directorio `test-output/screenshots/` con un formato de nombre:
```
YYYY-MM-DD_HH-MM-SS_NombreDeLaPrueba_ESTADO.png
```

Esto facilita la identificación cronológica de las capturas y el seguimiento del flujo de ejecución de las pruebas.
