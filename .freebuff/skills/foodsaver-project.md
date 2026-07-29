# FoodSaver / Meal Share - Project Context Skill

## Overview
Meal Share is a full-stack food reduction platform connecting food donors (restaurants, caterers, households) with individuals and NGOs in need. It reduces food waste by redirecting surplus food to those who need it most.

## Tech Stack
- **Frontend**: Vue.js 3 (Composition API + `<script setup>`), Vite, Pinia (state management), Vue Router 4, TailwindCSS v4, Leaflet (maps), Chart.js + vue-chartjs, Axios, date-fns, @vueuse/core
- **Backend**: Java 21, Spring Boot 3.5.0, Spring Security, JWT (jjwt 0.12.3), Spring Data JPA, PostgreSQL, Lombok
- **Build**: Maven (backend), Vite (frontend)
- **Database**: PostgreSQL (Supabase hosted)

## Project Structure

### Frontend (`frontend/food-saver-front-lib/`)
```
src/
├── App.vue                       # Root component with <router-view>
├── main.js                       # App bootstrap with Pinia + Router
├── router/
│   ├── index.js                  # Route definitions
│   └── guard.js                  # Navigation guards (auth, roles)
├── stores/                       # Pinia stores
│   ├── auth.js                   # Auth state, tokens, login/register/logout
│   ├── donation.js               # Donation CRUD, filters, pagination
│   ├── request.js                # Request management + stats
│   ├── notification.js           # Notification list + unread count
│   └── toast.js                  # Toast/snackbar messages
├── services/                     # Axios API clients
│   ├── api.js                    # Axios instance with JWT interceptors
│   ├── auth.service.js           # Auth endpoints
│   ├── donation.service.js       # Donation endpoints
│   ├── request.service.js        # Request endpoints
│   ├── notification.service.js   # Notification endpoints
│   └── chat.service.js           # Chat/conversation endpoints
├── composables/
│   ├── useAuth.js                # Auth helper (isAdmin, isDonor, isReceiver)
│   ├── useDonation.js            # Donation helper (load, submit, modify)
│   └── useMap.js                 # Leaflet map setup + markers
├── layouts/
│   ├── MainLayout.vue            # Appbar + Footer wrapper
│   ├── AuthLayout.vue            # Auth page layout
│   └── DashboardLayout.vue       # Sidebar + header layout
├── views/
│   ├── HomeView.vue              # Landing page with minimalist design
│   └── auth/
│       ├── LoginView.vue         # Login page
│       └── Signup.vue            # Signup page
├── components/
│   ├── Appbar.vue                # Top navigation bar
│   ├── AppbarSidebar.vue         # Dashboard sidebar
│   ├── AppFooter.vue             # Footer
│   ├── auth/
│   │   ├── LoginPage.vue         # Login form component (alternative)
│   │   └── RegisterView.vue      # Register form component (alternative)
│   ├── dashboard/
│   │   ├── AdminDashboard.vue    # Admin stats + charts + user table
│   │   ├── DonorDashboard.vue    # Donor stats + donations + pending requests
│   │   ├── ReceiverDashboard.vue # Receiver stats + nearby + requests
│   │   ├── RoleBasedDashboard.vue# Routes to correct dashboard by role
│   │   └── StatCard.vue          # Stat display card
│   ├── donations/
│   │   ├── CreateDonation.vue    # Donation creation form
│   │   ├── DonationCard.vue      # Donation preview card
│   │   ├── DonationList.vue      # Donation grid with filters/pagination
│   │   ├── DonationStatusBadge.vue# Status badge with colors
│   │   └── FoodCategorySelect.vue # Category picker grid
│   ├── maps/
│   │   ├── MapContainer.vue      # Leaflet map wrapper
│   │   └── MapView.vue           # Full map page with markers
│   ├── notifications/
│   │   ├── NotificationDropdown.vue# Dropdown with unread count
│   │   └── NotificationItem.vue  # Single notification row
│   ├── requests/
│   │   └── RequestCard.vue       # Request with approve/reject buttons
│   ├── chat/
│   │   ├── ChatView.vue          # Chat layout + sidebar
│   │   ├── ChatInput.vue         # Message input with auto-resize
│   │   ├── ConversationList.vue  # Conversation sidebar list
│   │   └── MessageThread.vue     # Message bubble display
│   ├── common/
│   │   ├── ToasterContainer.vue  # Toast notifications
│   │   ├── Errorboundary.vue     # Error boundary
│   │   └── LoadingSpinner.vue    # Loading spinner
│   ├── views/admin/
│   │   └── UserManagement.vue    # User management table
│   ├── RoleBadge.vue             # Role badge display
│   └── HelloWorld.vue            # Default/placeholder
├── utils/
│   ├── constants.js              # Roles, statuses, categories, config
│   ├── helpers.js                # formatDate, getExpiryStatus, generateAvatarUrl
│   └── storage.js                # localStorage wrapper
└── styles/
    └── styles.css                # Tailwind v4 with @theme + custom utilities
```

### Backend (`backend/`)
```
src/main/java/com/project/foodsaver/
├── FoodsaverApplication.java     # Spring Boot entry point
├── config/
│   └── CorsConfig.java           # CORS configuration
├── controller/
│   └── AuthController.java       # /api/auth endpoints (signup, login, refresh, logout)
├── service/
│   ├── AuthService.java          # Auth logic (login with lockout, signup, refresh)
│   └── AuditLogService.java      # Audit logging
├── security/
│   ├── SecurityConfig.java        # Spring Security + JWT filter chain
│   ├── JwtUtils.java             # Token generation/validation
│   ├── JwtAuthEntryPoint.java    # 401 JSON response handler
│   ├── JwtAuthTokenFilter.java   # OncePerRequestFilter for JWT
│   └── UserDetailsServiceImpl.java# Loads user from ProfileEntity
├── dto/
│   ├── AuthRequest.java          # Email + password
│   ├── AuthResponse.java         # Tokens + user info
│   ├── LoginRequest.java         # Name + email + password + device info
│   ├── ErrorResponse.java        # Error JSON structure
│   └── RefreshTokenRequest.java  # Refresh token string
├── entity/                       # JPA entities
│   ├── ProfileEntity.java        # Users (email, password, role, lock status)
│   ├── RoleEntity.java           # Roles (ADMIN, DONOR, RECEIVER, USER)
│   ├── RefreshTokenEntity.java   # Refresh tokens
│   ├── LoginAttemptEntity.java   # Login tracking
│   ├── AuditLogEntity.java       # Audit trail
│   ├── DonationEntity.java       # Food donations
│   ├── DonationRequestEntity.java# Request for donation
│   ├── DonationMetricsEntity.java# Analytics metrics
│   ├── FoodCategoryEntity.java   # Food categories
│   ├── MessageEntity.java        # Chat messages
│   ├── ConversationEntity.java   # Chat conversations
│   ├── NotificationEntity.java   # Notifications
│   ├── PasswordResetTokenEntity.java# Password reset
│   ├── PickupTrackingEntity.java # Pickup logistics
│   ├── RatingEntity.java         # Ratings
│   ├── ReportEntity.java         # Reports
│   └── RequestEntity.java        # General requests
└── repositories/
    ├── ProfileRepo.java          # User CRUD
    ├── RoleRepo.java             # Role CRUD
    ├── RefreshTokenRepo.java     # Refresh token CRUD
    ├── LoginAttemptRepo.java     # Login attempts CRUD
    └── AuditLogRepo.java         # Audit log CRUD
```

## Key Constants

### User Roles
- `ADMIN` — Platform management
- `DONOR` — Creates food donations
- `RECEIVER` — Browses and requests donations

### Donation Statuses
- `AVAILABLE`, `RESERVED`, `PICKED_UP`, `EXPIRED`, `CANCELLED`

### Request Statuses
- `PENDING`, `APPROVED`, `REJECTED`, `PICKUP_SCHEDULED`, `COMPLETED`, `CANCELLED`

## Key Design Decisions
- TailwindCSS v4 with `@theme` directive (not `@layer components`)
- Custom CSS utilities: `btn-primary`, `btn-secondary`, `card`, `input-field`, `sidebar-link`
- Axios instance with JWT interceptor + automatic token refresh queue
- Pinia stores return `{ success, error }` pattern from async actions
- Leaflet for map rendering (OpenStreetMap tiles)
- Backend uses BCrypt password encoding
- JWT tokens with 24h access token + 7d refresh token
- Account lockout after 5 failed login attempts (30 min lock)
- API base URL: `http://localhost:8080/api/v1` (frontend) → Spring Boot on `:8080`

## Architecture Notes
- Frontend API calls go through Axios -> HTTP -> Spring Boot REST controllers
- Frontend uses the Pinia Pattern: `services/` for HTTP, `stores/` for state, `composables/` for convenience wrappers
- `LoginView.vue` and `Signup.vue` in `views/auth/` use direct axios+localStorage (simpler standalone pages)
- `LoginPage.vue` and `RegisterView.vue` in `components/auth/` use the Pinia store pattern (integrated)
- Router guard in `guard.js` handles: auth initialization, route protection, role-based access
- Dashboard routes use `RoleBasedDashboard.vue` which renders role-specific dashboards

## Known Issues / TODOs
- Frontend signup response format mismatch: backend wraps AuthResponse in `{ "data": {...} }` but frontend checks `response.data.token`
- Some API service endpoints don't have matching backend controllers yet (chat, notifications CRUD, requests CRUD)
- The `/api/v1` prefix mismatch: frontend api.js baseURL uses `/api/v1` but AuthController uses `/api/auth` (not `/api/v1/auth`). Need alignment.

## Backend Donation API
### Endpoints
| Method | Path | Auth | Description |
|--------|------|------|-------------|
| GET | `/api/v1/donations` | Public (GET) | Paginated list with optional `search`, `categoryId`, `status`, `page`, `limit` params |
| GET | `/api/v1/donations/{id}` | Public (GET) | Single donation detail |
| GET | `/api/v1/donations/mine` | Auth required | Current donor's donations |
| POST | `/api/v1/donations` | Auth required | Create new donation |
| PUT | `/api/v1/donations/{id}` | Owner only | Update donation |
| PATCH | `/api/v1/donations/{id}/status` | Owner only | Update donation status |
| DELETE | `/api/v1/donations/{id}` | Owner/Admin | Delete donation |
| GET | `/api/v1/donations/nearby?lat=&lng=&radius=` | Public | Haversine-based nearby search |
| GET | `/api/v1/donations/categories` | Public | List food categories |

### Key Implementation Details
- **Haversine formula** for nearby search (native SQL query in DonationRepo)
- **@EntityGraph** on all repo query methods to eagerly fetch `donor` and `category` (prevents N+1)
- **Ownership validation** in update/delete - checks donor email matches JWT email
- **Admin override** allowed for delete operations
- **Status validation** restricts to: AVAILABLE, RESERVED, PICKED_UP, EXPIRED, CANCELLED
- **DTOs**: DonationRequest (input), DonationResponse (output), DonationListResponse (paginated)
