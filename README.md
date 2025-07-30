# Proyecto Módulo 4 - Testing Automatizado v1.1.0

## 📋 Descripción

Proyecto de testing automatizado desarrollado con **Selenium WebDriver** y **TestNG** para realizar pruebas de funcionalidad en aplicaciones web. El proyecto incluye tests automatizados para login y registro de usuarios, utilizando el patrón **Page Object Model** y datos de prueba desde archivos CSV con validaciones avanzadas de URL.

## 🆕 Novedades v1.1.0

- **Validaciones mejoradas**: Comparación precisa de URLs esperadas vs actuales después del login
- **Testing de casos negativos**: Incluye usuarios inválidos para probar fallos de autenticación
- **Logging avanzado**: Registro detallado de URLs actuales durante la ejecución
- **Estructura CSV expandida**: Soporte para columna `expectedUrl` en datos de prueba
- **Asserts más robustos**: Migración de `assertTrue` a `assertEquals` para mayor precisión

## 🛠️ Tecnologías Utilizadas

- **Java 24** - Lenguaje de programación principal
- **Selenium WebDriver 4.21.0** - Automatización de navegadores web
- **TestNG 7.10.0** - Framework de testing
- **Apache Commons CSV 1.10.0** - Lectura y procesamiento de archivos CSV
- **Logback 1.5.13** - Sistema de logging
- **Maven** - Gestión de dependencias y construcción del proyecto
- **Firefox WebDriver** - Navegador para la ejecución de tests

## 📁 Estructura del Proyecto

```
proyecto-modulo-4-testing/
├── src/
│   ├── main/java/
│   │   └── pages/
│   │       ├── LoginPage.java      # Page Object para página de login
│   │       └── RegisterPage.java   # Page Object para página de registro
│   └── test/
│       ├── java/tests/
│       │   ├── LoginTest.java      # Tests de funcionalidad de login
│       │   └── RegisterTest.java   # Tests de funcionalidad de registro
│       └── resources/data/
│           └── users.csv           # Datos de prueba para tests
├── testng.xml                      # Configuración de TestNG
├── pom.xml                         # Configuración de Maven
└── README.md                       # Este archivo
```

## 🚀 Configuración e Instalación

### Prerrequisitos

1. **Java JDK 24** o superior
2. **Maven 3.6+**
3. **Firefox** instalado en el sistema
4. **GeckoDriver** (se descarga automáticamente con Selenium)

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

3. Verifica que Firefox esté instalado y accesible desde la línea de comandos.

## ▶️ Ejecución de Tests

### Ejecutar todos los tests
```bash
mvn test
```

### Ejecutar tests específicos con TestNG
```bash
mvn test -Dtest=LoginTest
mvn test -Dtest=RegisterTest
```

### Ejecutar con archivo de configuración TestNG
```bash
mvn test -DsuiteXmlFile=testng.xml
```

## 📊 Tests Incluidos

### LoginTest
- **Propósito**: Validar la funcionalidad de inicio de sesión
- **Datos**: Lee credenciales y URLs esperadas desde `users.csv`
- **Validaciones**: 
  - Compara la URL actual vs URL esperada después del login
  - Utiliza `Assert.assertEquals()` para validaciones precisas
  - Incluye casos de prueba positivos y negativos
- **Logging**: Registra el inicio, fin y URL actual de cada test

### RegisterTest
- **Propósito**: Validar la funcionalidad de registro de usuarios
- **Implementación**: Utiliza el patrón Page Object Model

## 📄 Datos de Prueba

Los datos de prueba se almacenan en `src/test/resources/data/users.csv`:

```csv
username,password,expectedUrl
user_testing,F$xV@jsRnNGCoYRA7QxypydQ,https://tienda.demo.yoguilab.space/mi-cuenta/
usuario_invalido,clave123,https://tienda.demo.yoguilab.space/wp-login.php
```

### Formato del CSV
- **Primera fila**: Headers (username, password, expectedUrl)
- **Filas siguientes**: Datos de usuarios para testing
- **expectedUrl**: URL esperada después del intento de login
  - Para usuarios válidos: URL del dashboard/mi-cuenta
  - Para usuarios inválidos: URL de la página de login (sin redirección)

## 🏗️ Arquitectura

### Page Object Model
El proyecto utiliza el patrón **Page Object Model** para:
- Separar la lógica de los tests de los elementos de la UI
- Mejorar la mantenibilidad del código
- Reutilizar componentes entre diferentes tests

### Estructura de Clases
- **LoginPage**: Encapsula elementos y acciones de la página de login
- **RegisterPage**: Encapsula elementos y acciones de la página de registro
- **LoginTest**: Tests de funcionalidad de login con data provider
- **RegisterTest**: Tests de funcionalidad de registro

## 📝 Logging

El proyecto utiliza **Logback** para el sistema de logging:
- Logs informativos del inicio y fin de cada test
- Registro de usuarios utilizados en las pruebas
- Información sobre la cantidad de datos leídos del CSV

## 🔧 Configuración

### TestNG Configuration (testng.xml)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<suite name="M4Suite">
    <test name="LoginYRegistro">
        <parameter name="url" value="https://tienda.demo.yoguilab.space/wp-login.php"/>
        <classes>
            <class name="tests.LoginTest"/>
            <class name="tests.RegisterTest"/>
        </classes>
    </test>
</suite>
```

### Dependencias Principales (pom.xml)
- **Selenium WebDriver**: Automatización de navegadores
- **TestNG**: Framework de testing con data providers
- **Apache Commons CSV**: Lectura eficiente de archivos CSV
- **Logback**: Sistema de logging robusto

## 🐛 Solución de Problemas

### Error: "withFirstRecordAsHeader() is deprecated"
**Solución**: El proyecto ya utiliza la nueva API de Apache Commons CSV:
```java
CSVFormat format = CSVFormat.DEFAULT.builder()
    .setHeader()
    .setSkipHeaderRecord(true)
    .setTrim(true)
    .build();
```

### Error: Firefox no se inicia
**Soluciones**:
1. Verificar que Firefox esté instalado
2. Actualizar Selenium WebDriver
3. Verificar que GeckoDriver sea compatible

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
