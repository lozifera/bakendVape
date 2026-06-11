# Documentación completa del proyecto bakend_vape

Fecha: 2026-06-06

Este documento reúne en un solo archivo la estructura actual del proyecto, la arquitectura que estás usando, las convenciones, implementaciones importantes, problemas detectados y soluciones, y pasos prácticos para compilar, ejecutar y mantener la base de datos (migraciones Flyway).

---

Índice
1. Resumen rápido
2. Arquitectura y patrones usados
3. Estructura de carpetas clave
4. Contratos y mapeos (Entity, JpaRepository, Mapper, Adapter)
5. Configuración importante (`application.properties`) y explicación
6. Migraciones con Flyway: flujo de trabajo y recomendaciones
7. Errores comunes detectados y soluciones aplicadas
8. Detalles prácticos: comandos PowerShell para compilar y ejecutar
9. Buenas prácticas y próximos pasos
10. Anexos: snippets útiles

---

1) Resumen rápido
- Proyecto: bakend_vape (Spring Boot 4.x, Hibernate/JPA, Spring Data, Flyway, PostgreSQL).
- Arquitectura: hexagonal/puertos y adaptadores. Dominio aislado; infraestructura con JPA, mappers y adaptadores.
- Propósito del documento: compendiar lo que hay en el repo y ofrecer guía clara para continuar el desarrollo y evitar errores frecuentes.

2) Arquitectura y patrones usados
- Capas:
  - Dominio: `*.domain.model`, `*.domain.repository` — modelos del dominio y contratos (interfaces) que representan puertos.
  - Infraestructura: `*.infrastructure.persistence.entity` (Entities JPA), `*.infrastructure.persistence.jpa` (Interfaces `JpaRepository`), `*.infrastructure.persistence.adapter` (adaptadores que implementan los repositorios de dominio), `*.infrastructure.mapper` (conversión Entity <-> Domain).
  - Application / API (no siempre en todos los paquetes): servicios, controladores, DTOs.
- Patrón por recurso (ejemplo `usuario`):
  - `UsuarioEntity` (JPA) <-persistencia-> `JpaUsuarioRepository` (Spring Data)
  - `UsuarioMapper` convierte `UsuarioEntity <-> Usuario` (modelo de dominio)
  - `UsuarioRepositoryAdapter` implementa el puerto `UsuarioRepository` del dominio, usa `JpaUsuarioRepository` y `UsuarioMapper`

3) Estructura de carpetas clave (extracto)
- `src/main/java/com/example/bakend_vape/`
  - `atributo/` (atributo.domain, atributo.infrastructure...)
  - `aviso/`
  - `carrito/`
  - `categoria/`
  - `imagen/`
  - `marca/`
  - `pedido/`
  - `producto/`
  - `puntos/`
  - `rol/`
  - `subasta/`
  - `usuario/`
  - `valoracion/`
  - `shared/` (value objects como `Money`, `Puntos`, utilidades)
- `src/main/resources/`
  - `application.properties` — configuración
  - `db/migration/V1__init.sql` — migración inicial Flyway

4) Contratos y mapeos (cómo conectar capas)
- Convención:
  - Entities: `*.infrastructure.persistence.entity` anotadas con `@Entity`, campos `@Id`, `@ManyToOne`, `@OneToMany`, `@CreationTimestamp`, `@UpdateTimestamp`, etc.
  - Jpa repositories: `JpaFooRepository extends JpaRepository<FooEntity, Long>` en `*.infrastructure.persistence.jpa`.
  - Mappers: clases `FooMapper` en `*.infrastructure.mapper` que convierten entre `FooEntity` y `Foo` (dominio). Si se inyectan en adaptadores, deben ser beans (`@Component`) o se instancian manualmente.
  - Adapters: `FooRepositoryAdapter implements FooRepository` en `*.infrastructure.persistence.adapter`, anotados con `@Repository` y con dependencia al `JpaFooRepository` y `FooMapper`.

- Ejemplo de métodos JPA comunes (convention):
  - `Optional<Entidad> findByNombre(String nombre);`
  - `List<Entidad> findByCategoriaIdCategoria(Long categoriaId);`
  - `boolean existsByNombre(String nombre);`
  - `List<Entidad> findByUsuarioIdUsuario(Long idUsuario);`

Notas: asegúrate de usar camelCase correcto (`findByNombre` no `findbyNombre`).

5) `application.properties` — configuración importante y explicación
Fragmento actual usado en proyecto:

spring.application.name=bakend_vape
spring.datasource.url=jdbc:postgresql://localhost:5432/shop_vape
spring.datasource.username=postgres
spring.datasource.password=loza
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.validate-on-migrate=true
spring.flyway.out-of-order=true

Explicación:
- `spring.jpa.hibernate.ddl-auto`:
  - `none` en producción (usar Flyway para control de esquema).
  - `validate` causa que Hibernate valide que las tablas existan y coincidan con entidades — útil para detectar divergencias, pero puede fallar si migraciones no aplicadas aún.
  - `update` crea/actualiza tablas automáticamente desde entidades (no recomendado en producción).
- Flyway:
  - `spring.flyway.locations=classpath:db/migration` — busca scripts `V1__*.sql`, `V2__*.sql`, etc.
  - `validate-on-migrate=true` — valida checksums de scripts ya aplicados; si modificas scripts antiguos, Flyway fallará.
  - `out-of-order=true` — permite aplicar migraciones con versión anterior añadidas después (útil en equipos), usar con precaución.

6) Migraciones con Flyway: flujo de trabajo y recomendaciones
- Regla principal: nunca modifiques un script de migración que ya fue aplicado en una BD compartida.
- Si necesitas cambiar esquema:
  1. Crear nueva migración incremental `V{N}__descripcion.sql` con `ALTER TABLE ...` o `CREATE TABLE ...` según el cambio.
  2. Commit y push; al iniciar la app Flyway aplicará la nueva migración.
- Si accidentalmente cambiaste `V1__init.sql` y ya la aplicaste localmente, tienes opciones:
  - Revertir el script al estado aplicado (mejor cuando trabajas en equipo).
  - Ejecutar `flyway repair` (solo en entornos de desarrollo) para actualizar checksums en la tabla de historial (metadatos). No hacer en producción sin revisar.
  - Borrar y recrear la base de datos en entornos de desarrollo para reaplicar desde V1.
- Comando útil (PowerShell):
```powershell
# empaquetar y ejecutar la app (Flyway correrá al arrancar):
.\mvnw.cmd clean package; .\mvnw.cmd spring-boot:run
```

7) Errores comunes detectados y soluciones aplicadas
- Flyway `Migration checksum mismatch for migration version 1`:
  - Causa: modificado `V1__init.sql` después de haber sido aplicado. Solución: revertir script, crear nueva migración o ejecutar `flyway repair` (solo dev).
- Hibernate `Schema validation: missing table [atributo]` con `ddl-auto=validate`:
  - Causa: Hibernate valida existencia de tablas que aún no existen. Solución: usar `ddl-auto=none` y dejar que Flyway cree/valide tablas, o asegurarse que Flyway corre antes (Spring Boot normalmente lo hace).
- Spring `No qualifying bean of type 'XMapper' available`:
  - Causa: intentas inyectar un mapper que no es bean. Solución: anotar mappers que quieras inyectar con `@Component` o crear instancias manualmente en el adapter.
- `ClassNotFoundException: com.example...BakendVapeApplication` al ejecutar:
  - Causa: clase principal no está en classpath (no compilaron clases o la configuración de ejecución está mal). Solución: ejecutar `.\mvnw.cmd clean package` y luego `java -jar target/<artifact>.jar` o arrancar desde IDE con configuración correcta.
- SQL Error 55006: DB en uso al intentar `DROP/CREATE DATABASE`:
  - Causa: sesiones abiertas. Solución: cerrar conexiones (pgAdmin, psql) o terminar backends con `pg_terminate_backend(pid)` (usar con cuidado).

8) Errores de código y convención observados
- Métodos Spring Data con camelCase incorrecto: `findbyNombre` -> debe ser `findByNombre`.
- Inyecciones de mappers: si adaptador espera mapper por constructor y mapper no está anotado como `@Component`, Spring fallará.
- Getters de value objects: si tu `Money` expone `getAmount()` pero en mappers usas `getValue()` (o viceversa), ajusta el nombre en la clase `Money` o en los mappers para coincidir.
- `map` en `List` no existe en Java 8: estás usando `stream().map(...)` correctamente; si usas `list.map` da error. Usa `jpa.findAll().stream().map(mapper::toDomain).toList()` (Java 16+) o `collect(Collectors.toList())`.

9) Detalles prácticos: commands PowerShell para compilar/ejecutar
- Compilar y ejecutar con Maven wrapper en Windows PowerShell:
```powershell
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```
- Ejecutar jar producido:
```powershell
java -jar target\bakend_vape-<version>.jar
```
- Si necesitas usar Flyway CLI (opcional) para repair:
```powershell
flyway -url=jdbc:postgresql://localhost:5432/shop_vape -user=postgres -password=loza repair
```

10) Buenas prácticas y recomendaciones
- No uses `spring.jpa.hibernate.ddl-auto=update` en producción.
- Mantén las migraciones inmutables: si necesitas cambiar, añade nuevas migraciones.
- Marca como bean (`@Component`) los mappers que se inyectan mediante Spring; si son simples y sin dependencias, puedes instanciarlos en el adapter.
- Mantén la convención de nombres en métodos Spring Data JPA (camelCase y propiedades anidadas con nombre de propiedad y campo id si es necesario: `findByUsuarioIdUsuario`).
- Documenta cada migración con una línea de comentario al inicio del SQL para saber qué hace.

11) Próximos pasos prácticos sugeridos
- Revisa todos los mappers que lanzaron `No qualifying bean` y añade `@Component` o ajusta los adapters para instanciarlos manualmente.
- Revisar `V1__init.sql`; si la BD ya tiene datos y cambiaste el script, crea `V2__...` en lugar de editar `V1__...`.
- Ejecuta `.\mvnw.cmd clean package` y luego `.\mvnw.cmd spring-boot:run` y revisa logs de Flyway; si hay fallo por checksum, aplicar una de las soluciones de la sección 6.

12) Anexo: ejemplo de adaptador (patrón)
```java
// Adapter ejemplo (resumen)
@Repository
public class FooRepositoryAdapter implements FooRepository {
    private final JpaFooRepository jpa;
    private final FooMapper mapper;

    public FooRepositoryAdapter(JpaFooRepository jpa, FooMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper; // mapper puede inyectarse si es @Component
    }

    @Override
    public Foo save(Foo foo) {
        FooEntity entity = mapper.toEntity(foo);
        FooEntity saved = jpa.save(entity);
        return mapper.toDomain(saved);
    }
}
```

---

Si quieres, puedo:
- abrir `DOC_PROYECTO_COMPLETO.md` en el editor para revisarlo ahora;
- añadir una versión reducida `README.md` con lo esencial;
- listar automáticamente todas las clases `*Mapper` que no están anotadas con `@Component` y ofrecer un patch para convertirlas en beans.

Dime qué prefieres y continúo (puedo abrir el archivo y ejecutar un chequeo rápido de errores en código si quieres).
