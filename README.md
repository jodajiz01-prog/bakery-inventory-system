# Bakery Inventory System — Panadería Los Ángeles

Sistema de inventario para una panadería real, construido con Java y Spring Boot. Permite gestionar productos, registrar entradas y salidas de inventario, y controlar el acceso según el rol del usuario.

## Motivación

Este proyecto está inspirado en una necesidad real: la panadería de mi papá, Panadería Los Ángeles. El sistema está pensado para que él (como jefe) y un empleado puedan usarlo en el día a día para llevar control del inventario, con permisos distintos según el rol de cada uno.

## Funcionalidades

- **Autenticación con roles** — Jefe y Empleado, con permisos diferenciados (solo el Jefe puede borrar productos)
- **Gestión de productos** — CRUD completo, con categorías (pan dulce / pan salado)
- **Alertas de stock bajo** — se resaltan automáticamente los productos que llegaron a su nivel mínimo
- **Registro de entradas y salidas** — cada movimiento de inventario queda guardado con fecha, usuario responsable y nota opcional
- **Historial filtrable** — por producto y por rango de fechas

## Stack técnico

- Java 25 (LTS)
- Spring Boot (Web, Data JPA, Security)
- Thymeleaf + Bootstrap
- MySQL
- Maven

## Modelo de datos

- `Product` — nombre, descripción, precio, stock actual, stock mínimo, categoría
- `User` — nombre completo, usuario, contraseña (encriptada con BCrypt), rol
- `InventoryMovement` — producto, tipo (entrada/salida), cantidad, fecha, usuario responsable, nota

## Decisiones de diseño

**Roles con permisos reales, no solo visuales.** La restricción de "solo el Jefe puede borrar productos" no es solo un botón oculto en la interfaz — está aplicada también a nivel de rutas en `SecurityConfig`, así que aunque alguien intente acceder directo a la URL de borrado, el sistema la rechaza si no tiene el rol correcto.

**Registro de movimientos en vez de edición directa de stock.** En lugar de permitir editar el número de stock a mano, el sistema obliga a registrar cada cambio como un movimiento (entrada o salida). Esto genera un historial real y evita que alguien "arregle" el número sin dejar rastro de qué pasó.

**Contraseñas encriptadas con BCrypt.** Ninguna contraseña se guarda en texto plano — se usa el encriptador estándar de Spring Security antes de guardar cualquier usuario en la base de datos.

## Cómo correrlo localmente

1. Crear una base de datos MySQL llamada `bakery_db`
2. Configurar `application.properties` con las credenciales de tu MySQL local
3. Correr `BakeryInventorySystemApplication`
4. El sistema crea automáticamente un usuario Jefe (`admin`) y un usuario Empleado (`empleado`) la primera vez que arranca
5. Acceder en `http://localhost:8080/products`

## Capturas

*(agregar aquí una o dos capturas de la lista de productos y del historial de movimientos)*
