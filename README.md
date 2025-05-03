# AgenciaTurismo App – Parcial de Aplicaciones Móviles

Esta app es el prototipo móvil de la agencia de turismo, desarrollado en Android Studio usando XML y Java.

## 🚀 Características principales

1. **Pantalla “Nuestros Servicios”**  
   - Dinámicamente infla cards de servicios con imagen, título y descripción.  
   - Botón **Reservar** que añade entradas a la sección **Mis Reservas**.  
   - Diseño responsive usando `ConstraintLayout` y `ScrollView`.

2. **Recursos centralizados**  
   - Strings en `res/values/strings.xml`.  
   - Colores en `res/values/colors.xml`.  
   - Dimensiones en `res/values/dimens.xml`.  
   - Drawables optimizados en `res/drawable/`.

3. **Estilo y accesibilidad**  
   - Uso de `@drawable/bg_reservations` para cards con esquinas redondeadas.  
   - Selector de ripple para botones (`btn_reserve_selector.xml`).  
   - Descripciones de contenido (`contentDescription`) y contraste WCAG.
