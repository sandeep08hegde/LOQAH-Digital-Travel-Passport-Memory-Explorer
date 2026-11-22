# LOQAH — Digital Travel Passport & Memory Explorer

LOQAH is a travel passport and memory-exploration application. This repository contains the current full-stack starter and free-tier deployment configuration.

## Stack
Java 21 · Spring Boot 3.5 · Maven · Spring Security · JWT · PostgreSQL · Spring Data JPA · Flyway · HTML/CSS/JavaScript.

## Run locally (no Docker required)
Requirements: JDK 21, Maven 3.9+, and a PostgreSQL database.

1. Create a local PostgreSQL database named `loqah` and a user/password.
2. In a terminal, set `DB_USERNAME` and `DB_PASSWORD` to match your local PostgreSQL user.
3. Start the API: `cd backend && mvn spring-boot:run`.
4. Open `frontend/config.js` and set `window.LOQAH_API_URL = "http://localhost:8080/api";`.
5. Serve the frontend: `python -m http.server 5500` from `frontend/`, then open `http://localhost:5500`.

Flyway creates the initial schema and sample destinations on first API startup.

## Free deployment (Render + Neon)
The repository includes `render.yaml` for a Render Blueprint deployment:
- `loqah-api`: Spring Boot API (free web service).
- `loqah-frontend`: static HTML/CSS/JavaScript site (free static site).
- Neon: external free PostgreSQL database.

### Deploy
1. Create a free PostgreSQL project at [Neon](https://neon.com/). Copy the connection details for the main branch. Keep the password private.
2. In Neon, copy the connection string. Convert it to a JDBC URL for Spring Boot:
   - Neon format: `postgresql://USER:PASSWORD@HOST/DB?sslmode=require`
   - JDBC format: `jdbc:postgresql://HOST/DB?sslmode=require`
   - Set username and password separately in Render.
3. Sign in to [Render](https://render.com/), choose **New → Blueprint**, and connect this GitHub repository. Render reads `render.yaml`.
4. When prompted, set the API service variables:
   - `SPRING_DATASOURCE_URL`: the JDBC URL from step 2.
   - `SPRING_DATASOURCE_USERNAME`: Neon database username.
   - `SPRING_DATASOURCE_PASSWORD`: Neon database password.
   - `JWT_SECRET`: a long random secret (Render's generated value is suitable).
5. Deploy the Blueprint. Wait for the API health check at `https://loqah-api.onrender.com/api/health`.
6. The frontend config already points to `https://loqah-api.onrender.com/api`. If Render assigns a different API URL, update `frontend/config.js` and push the change.
7. Open `https://loqah-frontend.onrender.com`.

**Free-tier caveats:** Render free web services sleep after 15 minutes without traffic and can take about a minute to wake. Render's free PostgreSQL database expires after 30 days, so this setup uses Neon instead. Neon's free plan currently includes 1 GB storage and 100 compute-hours per project per month; compute sleeps after 5 minutes idle and resumes when queried. Review provider limits before relying on the service for important data.

### Why is there a Dockerfile?
Render's documented Java hosting path builds Java apps from a Dockerfile. You do **not** need Docker Desktop or Docker commands on your Windows machine for this deployment; Render builds the image in its own build environment. Postman is separate and is used only to test HTTP APIs.

## Postman
Import both files from `postman/` into Postman:
- `LOQAH.postman_collection.json`
- `LOQAH.postman_environment.json`

Select the environment. Set `baseUrl` to `http://localhost:8080/api` for local testing or `https://loqah-api.onrender.com/api` after deployment. Run **Register** or **Login** first; the collection stores the returned JWT in `token`. Run **List destinations** before **Destination details** or **Create visit** so `destinationId` is populated.

## API
- `GET /api/health` — health check
- `POST /api/auth/register` — create account
- `POST /api/auth/login` — login and receive JWT
- `GET /api/profile/me` — authenticated profile
- `GET /api/destinations` — public destination list; optional `state` or `category` query
- `GET /api/destinations/{id}` — destination details
- `POST /api/visits` — authenticated visit record
- `GET /api/visits` — current user's visits

Authenticated endpoints require `Authorization: Bearer <token>`.

## Configuration
Local settings can use `DB_USERNAME`, `DB_PASSWORD`, `DATABASE_URL`, and `JWT_SECRET`. Render uses `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, and `JWT_SECRET`. Never commit real credentials.

## Current scope and next modules
The current foundation includes authentication, profiles, destination browsing and pending visit records. It does not yet implement verified stamp issuance, GPS anti-spoofing, media uploads, diary management, achievements/titles, Surprise Me, friend requests, community feed or admin management. A visit is deliberately marked PENDING; a five-minute timer or client-supplied coordinates alone must not be treated as proof of a real-world visit.

Next: add visit verification and stamp lifecycle, passport/diary/media storage, achievements, social features, admin workflows, integration tests and deployment automation.
