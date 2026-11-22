# LOQAH frontend

This is a dependency-free HTML/CSS/JavaScript frontend starter.

Run the backend on port 8080, then serve this folder using VS Code Live Server or:
`python -m http.server 5500`

Open http://localhost:5500. To point the UI at a deployed API, set `window.LOQAH_API_URL` before loading `app.js` (for example, add a small config script to index.html). Do not put secrets in frontend files.