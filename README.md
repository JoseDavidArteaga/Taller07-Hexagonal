# FIET — Gestión de Formatos de Trabajos de Grado (Backend)

## Descripción General

Este proyecto corresponde al **backend** de una aplicación web desarrollada para la **Facultad de Ingeniería Electrónica y Telecomunicaciones (FIET)** de la **Universidad del Cauca**, en el marco de la Resolución 8.4.3-90.2/231 de 2020 del Consejo de la Facultad (CFIET), que reglamenta las modalidades de Trabajos de Grado para los programas de pregrado.

La aplicación expone una **API REST** que permite gestionar los formatos de propuesta de trabajo de grado:

- **Formato PP-A** — Modalidad Práctica Profesional (PP)
- **Formato TI-A** — Modalidad Trabajo de Investigación (TI)

Estos formatos apoyarán el control y seguimiento de los trabajos de grado desarrollados en la FIET.

---

## Contexto del Negocio

La FIET aprueba dos modalidades de trabajo de grado:

| Modalidad | Descripción |
|-----------|-------------|
| **TI** — Trabajo de Investigación | Regulada en el Capítulo II de la Resolución. Permite al estudiante desarrollar un proyecto de investigación como requisito de grado. |
| **PP** — Práctica Profesional | Regulada en el Capítulo III de la Resolución. Permite al estudiante aplicar sus conocimientos en un entorno profesional real. |

Cada propuesta queda registrada mediante un **Formato A** (PP-A o TI-A), el cual pasa por estados de evaluación con sus respectivas observaciones, y es gestionado por **Docentes** pertenecientes al comité evaluador.

---

## Stack Tecnológico

| Capa | Tecnología |
|------|-----------|
| Lenguaje | Java 21 |
| Framework principal | Spring Boot 2.7.6 |
| Persistencia | Spring Data JPA + Hibernate |
| Base de datos (producción) | MySQL 8 |
| Base de datos (pruebas) | H2 (en memoria) |
| Mapeo de objetos (DTO ↔ Dominio) | MapStruct + ModelMapper |
| Reducción de boilerplate | Lombok |
| Validación de entradas | Jakarta Bean Validation |
| Herramientas de desarrollo | Spring DevTools |
| Pruebas | Spring Boot Test |

---

## Arquitectura

El proyecto implementa **Arquitectura Hexagonal** (Puertos y Adaptadores), organizada en **tres contextos delimitados**:

```
src/main/java/co/edu/unicauca/asae/cleanarquitecture/
│
├── formatos/              ← Contexto: Gestión de Formatos (PP-A / TI-A)
│   ├── aplicacion/
│   │   ├── input/         ← Puertos de entrada (interfaces de casos de uso)
│   │   └── output/        ← Puertos de salida (interfaces de gateways)
│   ├── dominio/
│   │   ├── modelos/       ← Entidades de dominio: FormatoA, Evaluacion, Estado, Producto
│   │   └── casosDeUso/    ← Lógica de negocio
│   └── infraestructura/
│       ├── configuracion/ ← Beans de Spring (inyección de dependencias)
│       ├── input/         ← Controladores REST, DTOs, Mappers de entrada
│       └── output/        ← Entidades JPA, Repositories, Mappers de persistencia,
│                              Gateways, Manejo de excepciones
│
├── miembrosComite/        ← Contexto: Gestión de Docentes (Comité evaluador)
│   ├── aplicacion/output/ ← Puerto de salida para Docentes
│   ├── dominio/modelos/   ← Entidad de dominio: Docente
│   └── infraestructura/output/ ← Entity JPA, Repository, Mapper, Gateway
│
└── observaciones/         ← Contexto: Gestión de Observaciones
    ├── dominio/modelos/   ← Entidad de dominio: Observacion
    └── infraestructura/output/ ← Entity JPA, Repository
```

### Principios aplicados

- **Separación de responsabilidades**: el dominio no conoce la infraestructura.
- **Inversión de dependencias**: la lógica de negocio depende de interfaces (puertos), no de implementaciones concretas.
- **Independencia de frameworks**: los modelos de dominio son POJOs sin anotaciones de Spring o JPA.

---

## Modelo de Dominio

```
FormatoA
 ├── idFormatoA : Integer
 ├── titulo     : String
 ├── fecha      : Date
 ├── objetivo   : String
 ├── estado     : Estado          (1:1)
 ├── evaluaciones: List<Evaluacion> (1:N)
 └── docentes   : List<Docente>   (N:M)  ──── MiembrosComite

Estado
 ├── idEstado : Integer
 └── estado   : String  (Ej: "En elaboración", "Aprobado")

Evaluacion
 ├── idEvaluacion  : Integer
 ├── concepto      : String
 ├── fechaRegistro : Date
 └── observaciones : List<Observacion>  (1:N) ──── Observaciones

Observacion
 ├── idObservacion : Integer
 ├── descripcion   : String
 └── fechaRegistro : Date

Docente
 ├── idPersona            : Integer
 ├── tipoIdentificacion   : String
 ├── numeroIdentificacion : String
 ├── nombres              : String
 ├── apellidos            : String
 ├── correo               : String
 └── departamento         : String

Producto  (entidad de ejemplo del Sprint inicial)
 ├── id       : int
 ├── codigo   : String
 ├── nombre   : String
 ├── tipo     : char   ('m', 'a', 'p')
 ├── valor    : float
 └── createAt : Date
```

---

## Endpoints REST Disponibles

Base URL: `http://localhost:5000`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/productos` | Registrar un nuevo producto |
| `GET` | `/api/productos` | Listar todos los productos |

> Los endpoints de Formatos, Docentes y Observaciones forman parte de los sprints posteriores.

### Ejemplo — Crear un producto

**Request** `POST /api/productos`
```json
{
  "codigo": "12345",
  "nombre": "Producto ejemplo",
  "tipo": "a",
  "valor": 15000
}
```

**Response** `201 Created`
```json
{
  "id": 1,
  "codigo": "12345",
  "nombre": "Producto ejemplo",
  "tipo": "a",
  "valor": 15000,
  "createAt": "2026-05-12T00:00:00.000+00:00"
}
```

### Reglas de negocio — Producto

El primer dígito del código debe corresponder al tipo:

| Primer dígito del código | Tipo esperado |
|--------------------------|---------------|
| `0` | `m` (material) |
| `1` | `a` (artículo) |
| `2` | `p` (producto) |

---

## Manejo de Errores

La API devuelve respuestas de error estructuradas:

```json
{
  "codigoError": "GC-0002",
  "mensaje": "ERROR ENTIDAD YA EXISTE, Error, se encuentra en el sistema un producto con el codigo",
  "codigoHttp": 406,
  "url": "http://localhost:5000/api/productos",
  "metodo": "POST"
}
```

| Código | Descripción | HTTP Status |
|--------|-------------|-------------|
| `GC-0001` | Error genérico | 500 |
| `GC-0002` | Entidad ya existe | 406 |
| `GC-0003` | Entidad no encontrada | 404 |
| `GC-0004` | Violación de regla de negocio | 400 |

---

## Configuración y Ejecución

### Prerrequisitos

- Java 21+
- Maven 3.8+
- MySQL 8 (para ejecución en producción)

### Base de datos (MySQL)

```sql
CREATE DATABASE bdProductos;
```

Ajustar credenciales en `src/main/resources/application.properties`:

```properties
server.port=5000
spring.datasource.url=jdbc:mysql://localhost/bdProductos?useSSL=false&serverTimezone=GMT&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
```

### Ejecutar el proyecto

```bash
# Compilar y ejecutar
mvn spring-boot:run

# Solo compilar
mvn clean install
```

La API queda disponible en: `http://localhost:5000`

### Ejecutar pruebas

```bash
mvn test
```

Las pruebas utilizan **H2 en memoria**, por lo que no requieren MySQL. La configuración de pruebas se encuentra en `src/test/resources/application.properties`.

---

## Estructura del Proyecto

```
Taller07-Hexagonal/
├── src/
│   ├── main/
│   │   ├── java/co/edu/unicauca/asae/cleanarquitecture/
│   │   │   ├── CleanarquitectureApplication.java   ← Punto de entrada
│   │   │   ├── formatos/                           ← Contexto Formatos
│   │   │   ├── miembrosComite/                     ← Contexto Miembros del Comité
│   │   │   └── observaciones/                      ← Contexto Observaciones
│   │   └── resources/
│   │       ├── application.properties              ← Configuración principal
│   │       ├── ValidationMessages_es.properties    ← Mensajes de validación en español
│   │       └── import.sql                          ← Datos iniciales de prueba
│   └── test/
│       ├── java/co/edu/unicauca/asae/cleanarquitecture/
│       │   ├── formatos/infraestructura/output/    ← Tests de repositorios Formatos
│       │   └── miembrosComite/infraestructura/output/ ← Tests de repositorios Docentes
│       └── resources/
│           └── application.properties              ← Configuración H2 para pruebas
└── pom.xml
```

---

## Cobertura de Pruebas

| Clase de Prueba | Tipo | Descripción |
|----------------|------|-------------|
| `FormatoARepositoryTest` | `@DataJpaTest` | CRUD y consultas sobre formatos y su relación con docentes |
| `EvaluacionRepositoryTest` | `@DataJpaTest` | CRUD de evaluaciones con observaciones anidadas |
| `DocenteRepositoryTest` | `@DataJpaTest` | CRUD y consultas personalizadas de docentes |

---

## Contexto Académico

> **Universidad del Cauca — Facultad de Ingeniería Electrónica y Telecomunicaciones (FIET)**
>
> Este backend es el Sprint inicial de un proyecto mayor orientado a digitalizar el proceso de gestión de propuestas de trabajo de grado en la FIET. El Sprint inicial establece la base arquitectónica (Arquitectura Hexagonal) y los primeros servicios REST, utilizando Java, Spring Boot y Postman/Thunder como herramienta de prueba de la API.
