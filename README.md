# 🌦️ Weather API - Spring Boot

Este proyecto implementa una API REST que consulta y devuelve información del clima desde una API externa (**Visual Crossing**), utilizando caché con **Redis** y **limitación de velocidad** (rate limiting).

---

## 🧰 Tecnologías utilizadas

- ⚙️ Java 17
- 🚀 Spring Boot 3.5.4-SNAPSHOT
- ☁️ WebClient (para consumo de APIs externas)
- 💾 Redis (caché de respuesta)
- 🧱 Bucket4j (rate limiting por IP)
- 🧰 Maven
- 📦 Docker (opcional, para Redis)

---

## ⚙️ Instrucciones para correr el proyecto

### 1. Clonar el repositorio

\`\`\`bash
git clone https://github.com/JeronimoVR/weather-api-springboot.git
cd weather-api-springboot
\`\`\`

### 2. Configurar el archivo \`application.properties\`

Ubicado en `src/main/resources/application.properties`:

```properties
weather.api.key=TU_API_KEY
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

Puedes conseguir una API key gratuita en:  
👉 https://www.visualcrossing.com/weather-api

---

### 3. Correr Redis (opcional con Docker)

Si no tienes Redis instalado localmente:

```bash
docker run -d -p 6379:6379 --name redis redis
```

---

### 4. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

La aplicación se expondrá en:  
📍 `http://localhost:8080`

---

## 🔌 Endpoints disponibles

| Método | Endpoint                | Parámetros              | Descripción                                      |
|--------|-------------------------|-------------------------|--------------------------------------------------|
| GET    | `/weather`              | `city` (ciudad,pais), `date` (yyyy-MM-dd) | Obtiene el clima de una ciudad para cierta fecha |

Ejemplo:

```bash
"http://localhost:8080/weather?city=Cali,col&date=2021-01-10"
```

---
## 📥 Ejemplo de respuesta JSON

Cuando haces una solicitud al endpoint `/weather`, obtendrás una respuesta como la siguiente:

```json
{
  "city": "Cali, Colombia",
  "temperature": 87.5,
  "humidity": 50,
  "description": "Rain, Partially cloudy"
}
```
---

## 🧠 Funcionalidades importantes

### ✅ Consumo de API externa

- Se usa WebClient para consultar Visual Crossing.
- La URL y API key están externalizadas por variables de entorno o properties.

### ✅ Redis como caché

- Se guarda la respuesta de la API usando la ciudad como clave.
- El TTL por defecto es de 12 horas (\`43200\` segundos).
- Se serializa como JSON usando Jackson.

### ✅ Rate Limiting con Bucket4j

- Se limita a 100 peticiones por hora por IP.
- Se devuelve HTTP 429 si se supera el límite.