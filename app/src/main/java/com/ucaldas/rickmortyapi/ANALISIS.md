# ANÁLISIS TÉCNICO - TALLER 8: RICK & MORTY API

## 0. Predicción Inicial (Antes de programar)
**Pregunta:** Si el Repository llama a Retrofit sin try/catch desde viewModelScope.launch, y desactivo internet antes de volver a cargar, ¿qué ocurre y por qué?
**Predicción:**
Al realizar la llamada de red sin una estructura try/catch que capture la excepción (como `UnknownHostException` o `IOException`), el error no manejado se propagará por la corrutina en `viewModelScope`. Esto provocará que la corrutina falle y, al no ser atendido a nivel de hilo/Scope, causará que la aplicación se cierre de forma inesperada (Crash) para el usuario.

## 1. Diferencia en el JSON (PokéAPI vs Rick & Morty)
* **PokéAPI:** Retorna `results` como una lista de objetos simples con las claves `name` y `url`. Para obtener los detalles e imágenes, se requiere hacer llamadas adicionales a cada URL.
* **Rick & Morty API:** Retorna `results` con los objetos de personaje completos directamente dentro del arreglo, incluyendo `id`, `name`, `status`, `species` e `image`.

## 2. Experimento de Crash en Logcat
* **Excepción observada:** [Pegar aquí la excepción exacta obtenida de Logcat, p. ej.: `java.net.UnknownHostException: Unable to resolve host "rickandmortyapi.com"`]
* **Causa técnica:** La corrutina lanzada con `viewModelScope.launch` no capturó la excepción generada en la capa de red. Debido a la jerarquía de supervisión por defecto de las corrutinas, la excepción sin manejar fue escalada al `UncaughtExceptionHandler` del hilo principal, forzando la terminación del proceso (Crash de la app).

## 3. Explicación del Manejo de Excepciones y Retrofit
* **Diferencia entre error HTTP y fallo de conexión:**
    * **Fallo de conexión (IOException / UnknownHostException):** Ocurre en la capa de transporte/red (sin acceso a internet, tiempo de espera agotado, DNS no resuelto). No hay respuesta del servidor.
    * **Error HTTP (HttpException):** Ocurre cuando la petición llega exitosamente al servidor, pero el servidor responde con un código de estado de error (ejemplo: `404 Not Found`, `500 Internal Server Error`).

## 4. Decisión de Diseño del Detalle
* **Enfoque elegido:** [Opción A / Opción B]
    * *Si elegiste Opción A:* Se realiza una nueva petición `obtenerPersonaje(id)` en la pantalla de detalle para garantizar datos frescos incluso si la ruta se abre mediante un deep link o enlace directo.
    * *Si elegiste Opción B:* Se consulta el personaje ya cargado en el ViewModel compartido evitando llamadas de red innecesarias. Si la ruta de detalle se abre directamente sin cargar la lista previa, la pantalla quedará sin datos, requiriendo un fallback.