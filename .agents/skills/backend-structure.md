# Meal Share — Backend Structure Skill

When the user asks to make changes, add an API endpoint, or fix a backend issue, use this skill to identify ALL files affected by the change. This maps every Java entity, repository, DTO, service, controller, and config file with their import relationships.

## Root Path

All backend files are under: `backend/`

## Build & Config

| File | Purpose |
|------|---------|
| `pom.xml` | Maven config (Spring Boot 3.5.0, Java 21, dependencies) |
| `src/main/resources/application.properties` | Database, JWT, server config |
| `mvnw` / `mvnw.cmd` | Maven wrapper (do NOT commit) |

## Entry Point

| File | Purpose | Depends On |
|------|---------|------------|
| `src/main/java/com/project/foodsaver/FoodsaverApplication.java` | Spring Boot main class | Nothing |

## Configuration

| File | Purpose | Depends On |
|------|---------|------------|
| `src/main/java/com/project/foodsaver/config/CorsConfig.java` | CORS configuration | Nothing |
| `src/main/java/com/project/foodsaver/security/SecurityConfig.java` | Spring Security filter chain, JWT filter, permit list | `JwtAuthTokenFilter`, `JwtAuthEntryPoint`, `UserDetailsServiceImpl` |
| `src/main/java/com/project/foodsaver/security/JwtUtils.java` | JWT token generation/validation | `application.properties` (jwt.secret, jwt.expiration) |
| `src/main/java/com/project/foodsaver/security/JwtAuthEntryPoint.java` | 401 JSON response handler | Nothing |
| `src/main/java/com/project/foodsaver/security/JwtAuthTokenFilter.java` | OncePerRequestFilter extracting JWT from header | `JwtUtils`, `UserDetailsServiceImpl` |
| `src/main/java/com/project/foodsaver/security/UserDetailsServiceImpl.java` | Loads user from ProfileEntity for Spring Security | `ProfileRepo` |
| `src/main/java/com/project/foodsaver/exception/GlobalExceptionHandler.java` | @ControllerAdvice for all exception types | `ErrorResponse` DTO |

## Entities (JPA)

| File | Table | Relationships |
|------|-------|---------------|
| `ProfileEntity.java` | `profiles` | HasMany: DonationEntity, RefreshTokenEntity, DonationRequestEntity, MessageEntity, NotificationEntity, RatingEntity, etc. ManyToOne: RoleEntity |
| `RoleEntity.java` | `roles` | OneToMany: ProfileEntity |
| `RefreshTokenEntity.java` | `refresh_tokens` | ManyToOne: ProfileEntity |
| `LoginAttemptEntity.java` | `login_attempts` | ManyToOne: ProfileEntity |
| `AuditLogEntity.java` | `audit_logs` | ManyToOne: ProfileEntity |
| `DonationEntity.java` | `donations` | ManyToOne: ProfileEntity (donor), FoodCategoryEntity. HasMany: DonationRequestEntity |
| `DonationRequestEntity.java` | `donation_requests` | ManyToOne: DonationEntity, ProfileEntity (requester) |
| `DonationMetricsEntity.java` | `donation_metrics` | None |
| `FoodCategoryEntity.java` | `food_categories` | OneToMany: DonationEntity |
| `MessageEntity.java` | `messages` | ManyToOne: ConversationEntity, ProfileEntity (sender) |
| `ConversationEntity.java` | `conversations` | ManyToMany: ProfileEntity |
| `NotificationEntity.java` | `notifications` | ManyToOne: ProfileEntity |
| `PasswordResetTokenEntity.java` | `password_reset_tokens` | ManyToOne: ProfileEntity |
| `PickupTrackingEntity.java` | `pickup_tracking` | ManyToOne: DonationEntity |
| `RatingEntity.java` | `ratings` | ManyToOne: ProfileEntity (from + to) |
| `ReportEntity.java` | `reports` | ManyToOne: ProfileEntity (reporter) |
| `RequestEntity.java` | `requests` | ManyToOne: ProfileEntity |

## DTOs

| File | Used By | Purpose |
|------|---------|---------|
| `AuthRequest.java` | AuthController | Signup request (name, email, password, role) |
| `LoginRequest.java` | AuthController | Login request (email, password, ipAddress, deviceInfo) |
| `AuthResponse.java` | AuthController, AuthService | Login/signup response (tokens + user) |
| `RefreshTokenRequest.java` | AuthController | Refresh token request |
| `ErrorResponse.java` | GlobalExceptionHandler | Error JSON structure |

### Donation DTOs

| File | Used By | Purpose |
|------|---------|---------|
| `DonationRequest.java` | DonationController | Create/update donation (title, description, categoryId, quantity, location, pickup window, image) |
| `DonationResponse.java` | DonationController, DonationService | Donation output (with donor, category, requestsCount, distance) |
| `DonationListResponse.java` | DonationController | Paginated wrapper { data, page, limit, total, totalPages } |

## Repositories

| File | Entity | Custom Methods |
|------|--------|----------------|
| `ProfileRepo.java` | ProfileEntity | findByEmail, existsByEmail, findByRole |
| `RoleRepo.java` | RoleEntity | findByName |
| `RefreshTokenRepo.java` | RefreshTokenEntity | findByToken, deleteByUser |
| `LoginAttemptRepo.java` | LoginAttemptEntity | findByEmailAndTimestampAfter |
| `AuditLogRepo.java` | AuditLogEntity | None |
| `DonationRepo.java` | DonationEntity | findNearby (native Haversine query), searchDonations (JPQL), findByDonor, findByStatus, findByCategoryId, findAllWithEntityGraph |
| `FoodCategoryRepo.java` | FoodCategoryEntity | findByName |

## Services

| File | Purpose | Depends On |
|------|---------|------------|
| `AuthService.java` | Signup, login (with lockout), refresh token | `ProfileRepo`, `RoleRepo`, `RefreshTokenRepo`, `LoginAttemptRepo`, `JwtUtils`, BCrypt |
| `AuditLogService.java` | Audit logging | `AuditLogRepo` |
| `DonationService.java` | Full CRUD, nearby search (Haversine), category listing, status management, ownership validation | `DonationRepo`, `FoodCategoryRepo`, `ProfileRepo` |

## Controllers (REST APIs)

| File | Base Path | Endpoints | Depends On |
|------|-----------|-----------|------------|
| `AuthController.java` | `/api/auth` | POST signup, POST login, POST refresh, POST logout | `AuthService`, `JwtUtils`, `RefreshTokenRepo` |
| `DonationController.java` | `/api/v1/donations` | GET list, GET {id}, GET mine, POST create, PUT update, PATCH status, DELETE delete, GET nearby, GET categories | `DonationService`, `JwtUtils` |

## API Security (from `SecurityConfig.java`)

Public (no auth required):
- `GET /api/v1/donations/**`
- `/api/auth/**`
- `/api/public/**`

Authenticated (JWT required):
- All other endpoints (POST/PUT/PATCH/DELETE on donations, auth refresh/logout, etc.)

## Folder Reference

```
backend/src/main/java/com/project/foodsaver/
├── FoodsaverApplication.java
├── config/
│   └── CorsConfig.java
├── controller/
│   ├── AuthController.java
│   └── DonationController.java
├── dto/
│   ├── AuthRequest.java
│   ├── AuthResponse.java
│   ├── DonationListResponse.java
│   ├── DonationRequest.java
│   ├── DonationResponse.java
│   ├── ErrorResponse.java
│   ├── LoginRequest.java
│   └── RefreshTokenRequest.java
├── entity/
│   ├── AuditLogEntity.java
│   ├── ConversationEntity.java
│   ├── DonationEntity.java
│   ├── DonationMetricsEntity.java
│   ├── DonationRequestEntity.java
│   ├── FoodCategoryEntity.java
│   ├── LoginAttemptEntity.java
│   ├── MessageEntity.java
│   ├── NotificationEntity.java
│   ├── PasswordResetTokenEntity.java
│   ├── PickupTrackingEntity.java
│   ├── ProfileEntity.java
│   ├── RatingEntity.java
│   ├── RefreshTokenEntity.java
│   ├── ReportEntity.java
│   ├── RequestEntity.java
│   └── RoleEntity.java
├── exception/
│   └── GlobalExceptionHandler.java
├── repositories/
│   ├── AuditLogRepo.java
│   ├── DonationRepo.java
│   ├── FoodCategoryRepo.java
│   ├── LoginAttemptRepo.java
│   ├── ProfileRepo.java
│   ├── RefreshTokenRepo.java
│   └── RoleRepo.java
├── security/
│   ├── JwtAuthEntryPoint.java
│   ├── JwtAuthTokenFilter.java
│   ├── JwtUtils.java
│   ├── SecurityConfig.java
│   └── UserDetailsServiceImpl.java
└── service/
    ├── AuditLogService.java
    ├── AuthService.java
    └── DonationService.java
```

## How to Use This Skill

When the user requests a change:

1. Identify the layer the user is referring to (controller, service, entity, etc.)
2. Trace the dependency chain: Controller → Service → Repository → Entity
3. Check all DTOs that map to/from the entities involved
4. Check SecurityConfig if the endpoint needs auth/permit changes
5. Read ALL affected files before making changes
6. Modify files in dependency order (entity → repository → service → controller → DTO → security config)
7. After changes, compile to verify: `cd backend && .\mvnw compile -q`
