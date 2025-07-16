## 🚀 Características principales

1. **Pantalla “Nuestros Servicios”**  
   - Carga dinámica desde **Firestore** de la colección `servicios`.  
   - Cada _card_ muestra imagen, título y descripción.  
   - Botón **Reservar** abre un **formulario** (nombre, pasajeros, teléfono) en un `AlertDialog`.  
   - Al confirmar:  
     - Guarda reserva activa y la añade al **historial**.  
     - Almacena la cantidad de pasajeros (`count_<servicio>`) en `SharedPreferences`.  
     - Muestra Toast de confirmación y actualiza el badge del carrito.  
     - Navega a **Mis Reservas**.

2. **Sección “Mis Reservas” (Historial)**  
   - Muestra todas las reservas completadas en un **CardView** (`item_reservation_history.xml`).  
   - Cada tarjeta incluye:  
     - Nombre del servicio.  
     - Cantidad de pasajeros.  

3. **Recursos centralizados**  
   - **Strings** en `res/values/strings.xml`.  
   - **Colores** en `res/values/colors.xml`.  
   - **Dimensiones** en `res/values/dimens.xml`.  
   - **Drawables** en `res/drawable/` (WebP, vectores).

4. **Estilo y accesibilidad**  
   - `ConstraintLayout` + `ScrollView` para layout responsive.  
   - `CardView` con esquinas redondeadas y sombra en historial.  
   - Ripple selector en botones (`btn_reserve_selector.xml`).  
   - `contentDescription` en imágenes y contraste WCAG.

---

## ⚙️ Instalación y ejecución

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/GereeOK/geremias-irisarri-aplicacionesmoviles.git
   cd geremias-irisarri-aplicacionesmoviles


LINK MOCKUP: https://whimsical.com/final-mockup-TEKacdh2mhe9PwBofsZiB
