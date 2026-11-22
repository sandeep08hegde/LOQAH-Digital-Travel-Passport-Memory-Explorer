# Contributing to LOQAH

## Branching model

LOQAH uses a lightweight Git Flow model:

- `main`: production-ready code only. Render's production deployment should track this branch.
- `develop`: integration branch. Completed features are merged here after review and checks.
- `feature/<short-description>`: new product work, branched from `develop`.
- `fix/<short-description>`: non-urgent bug fixes, branched from `develop`.
- `hotfix/<short-description>`: urgent production fixes, branched from `main`; merge the fix into both `main` and `develop`.
- `release/<version>` (when needed): stabilization branch cut from `develop`; merge to `main`, then back into `develop`.

Keep branch names lowercase and use hyphens. Examples:
- `feature/passport-stamps`
- `feature/friends-feed`
- `fix/jwt-expiry-handling`
- `hotfix/login-500-error`

## Standard feature workflow

1. Start from the latest `develop`: `git switch develop && git pull origin develop`.
2. Create a focused branch: `git switch -c feature/short-description`.
3. Make one logical change per commit. Use Conventional Commit-style messages:
   - `feat: add passport stamp endpoint`
   - `fix: reject duplicate visit records`
   - `test: cover registration validation`
   - `docs: explain Render deployment`
   - `chore: update Maven plugin`
4. Run checks before pushing:
   - Backend: `cd backend && mvn -B clean verify`
   - Inspect frontend changes in a browser.
5. Push the feature branch: `git push -u origin feature/short-description`.
6. Open a Pull Request into `develop`. Explain the change, testing performed, configuration/database migration impact, and screenshots for UI changes.
7. Address review feedback and merge only after checks pass. Delete the feature branch after merge.

## Production release

1. Confirm `develop` is tested and stable.
2. Open a PR from `develop` into `main`, with a release summary.
3. Merge after review and checks. Production hosting should deploy from `main` only.
4. Tag the release, e.g. `v0.1.0`.
5. For an urgent production fix, branch from `main`, PR to `main`, then also merge/cherry-pick the same fix into `develop`.

## Pull request checklist

- [ ] Scope is focused and branch is up to date with its base.
- [ ] `mvn -B clean verify` passes for backend changes.
- [ ] No credentials, tokens, private keys, or personal data are committed.
- [ ] Database changes include a new Flyway migration; do not edit an already-applied migration.
- [ ] API changes include request/response and error behavior notes; update Postman collection when applicable.
- [ ] Frontend changes work at mobile and desktop sizes.
- [ ] README/configuration docs are updated where needed.
- [ ] No unverified feature is described as complete.

## Important deployment rule

Render production should follow `main`, not feature branches or `develop`. Use a separate preview service/environment for testing if needed. Never put Neon credentials or production secrets in Git; enter them only in the hosting provider's environment-variable settings.
