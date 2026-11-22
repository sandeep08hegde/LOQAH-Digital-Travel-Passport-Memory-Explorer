# LOQAH — Digital Travel Passport & Memory Explorer

A travel passport and memory-exploration application. This repository currently contains the first runnable full-stack foundation.

## Stack
Java 21 · Spring Boot 3.5 · Maven · Spring Security · JWT · PostgreSQL · Spring Data JPA · Flyway · HTML/CSS/JavaScript.

## Run locally
Requirements: JDK 21, Maven 3.9+, Docker Desktop.

1. Start PostgreSQL: `docker compose up -d`
2. Start API: `cd backend && mvn spring-boot:run`
3. Serve the frontend folder with VS Code Live Server or `python -m http.server 5500` from `frontend/`.
4. Open the frontend server URL. API health: `http://localhost:8080/api/health`.

Flyway creates the initial schema and sample destination data on first API startup.

## API
- `POST /api/auth/register` — create account
- `POST /api/auth/login` — login and receive JWT
- `GET /api/profile/me` — authenticated profile
- `GET /api/destinations` — public destination list; optional `state` or `category` query
- `GET /api/destinations/{id}` — destination details
- `POST /api/visits` — authenticated visit record
- `GET /api/visits` — current user's visits

Authenticated endpoints require `Authorization: Bearer <token>`.

## Configuration
See `.env.example`. Configure `DATABASE_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET` (at least 32 random bytes) and `PORT` as environment variables. Never use the development JWT secret in production.

## Current scope and next modules
The current commit series establishes authentication, profiles, destination browsing and pending visit records. It does not yet implement verified stamp issuance, GPS anti-spoofing, media uploads, diary management, achievements/titles, Surprise Me, friend requests, community feed or admin management. A visit is deliberately marked PENDING; a five-minute timer or client-supplied coordinates alone must not be treated as proof of a real-world visit.

Next: add visit verification and stamp lifecycle, passport/diary/media storage, achievements, social features, admin workflows, integration tests and deployment automation.