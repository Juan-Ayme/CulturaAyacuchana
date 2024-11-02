# Cultura Ayacucho

Cultura Ayacucho es una aplicación Android que muestra películas y contenido cultural de Ayacucho. La aplicación está construida usando Kotlin, Jetpack Compose y sigue prácticas modernas de desarrollo Android.

## Características

- **Pantalla Principal**: Muestra una lista de películas y contenido cultural.
- **Pantalla de Exploración**: Permite a los usuarios explorar diferentes categorías de contenido.
- **Pantalla de Favoritos**: Los usuarios pueden marcar contenido como favorito y verlo en esta sección.
- **Pantalla de Usuario**: Muestra el perfil del usuario y configuraciones.
- **Pantalla de Detalle de Película**: Muestra información detallada sobre una película seleccionada.

## Tecnologías Utilizadas

- **Kotlin**: El lenguaje de programación utilizado para la aplicación.
- **Jetpack Compose**: Para construir la interfaz de usuario.
- **Navigation Component**: Para manejar la navegación entre pantallas.
- **Retrofit**: Para realizar solicitudes de red.
- **Coil**: Para cargar imágenes.

## Paginas de las implementaciones
Para tener las ultimas actualizaciones de las librerias utilizadas en el proyecto, se puede visitar las siguientes paginas:
- **[Coil](https://github.com/coil-kt/coil/blob/main/CHANGELOG.md#270---july-17-2024)**: Página web de la aplicación.
- **[Retrofit](https://github.com/square/retrofit)**: Página web de la aplicación.


## Estructura del Proyecto

- `data`: Contiene modelos de datos y clases de repositorio.
- `features`: Contiene las diferentes características de la aplicación, cada una en su propio paquete.
  - `home`: Clases y componentes de UI relacionados con la pantalla principal.
  - `explore`: Clases y componentes de UI relacionados con la pantalla de exploración.
  - `favorite`: Clases y componentes de UI relacionados con la pantalla de favoritos.
  - `user`: Clases y componentes de UI relacionados con la pantalla de usuario.
- `core`: Contiene utilidades principales y clases de navegación.

## Comenzando

### Prerrequisitos

- Android Studio Arctic Fox o posterior.
- Gradle 7.0 o posterior.

### Instalación

1. Clona el repositorio:
   ```sh
   git clone https://github.com/yourusername/cultura-ayacucho.git
