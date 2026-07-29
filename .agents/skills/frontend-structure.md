# Meal Share — Frontend Structure Skill

When the user asks to make changes, modify a feature, or understand the frontend, use this skill to identify ALL files affected by the change. This maps every Vue component, store, service, composable, and utility with their import relationships.

## Root Path

All frontend files are under: `frontend/food-saver-front-lib/`

## Entry Points

| File | Purpose | Depends On |
|------|---------|------------|
| `index.html` | HTML shell with Google Fonts + viewport | Nothing |
| `src/main.js` | App bootstrap (createApp, Pinia, Router) | `src/App.vue`, `src/router/index.js`, `src/styles/styles.css`, `src/assets/tokens.css` |
| `src/App.vue` | Root component with ErrorBoundary + router-view | `src/components/common/Errorboundary.vue`, `src/components/common/ToasterContainer.vue` |
| `vite.config.js` | Vite config (Vue plugin, Tailwind, `@` alias) | Nothing |
| `package.json` | Dependencies & scripts | Nothing |

## Router

| File | Purpose | Depends On |
|------|---------|------------|
| `src/router/index.js` | Route definitions (Home, Login, Signup, Dashboard, Donations, Map, Chat, Requests, Profile, Notifications, 404) | Route component files, `src/router/guard.js` |
| `src/router/guard.js` | Navigation guards (auth init, role-based access, redirects) | `src/stores/auth.js`, `src/stores/toast.js` |

## Stores (Pinia)

| File | Purpose | Depends On |
|------|---------|------------|
| `src/stores/auth.js` | Auth state, tokens, login/register/logout/refresh/initializeAuth | `src/services/auth.service.js`, `src/utils/storage.js` |
| `src/stores/donation.js` | Donation CRUD, filters, pagination | `src/services/donation.service.js` |
| `src/stores/request.js` | Request management + stats | `src/services/request.service.js` |
| `src/stores/notification.js` | Notification list + unread count | `src/services/notification.service.js` |
| `src/stores/toast.js` | Toast/snackbar messages | Nothing |

## Services (Axios API Clients)

| File | Purpose | Depends On |
|------|---------|------------|
| `src/services/api.js` | Axios instance with JWT interceptor + auto-refresh queue | `src/stores/auth.js`, `src/utils/storage.js` |
| `src/services/auth.service.js` | Auth endpoints (login, signup, refresh, logout) | `src/services/api.js` |
| `src/services/donation.service.js` | Donation CRUD + nearby + categories | `src/services/api.js` |
| `src/services/request.service.js` | Request endpoints | `src/services/api.js` |
| `src/services/notification.service.js` | Notification endpoints | `src/services/api.js` |
| `src/services/chat.service.js` | Chat/conversation endpoints | `src/services/api.js` |

## Composables

| File | Purpose | Depends On |
|------|---------|------------|
| `src/composables/useAuth.js` | Auth helpers (isAdmin, isDonor, isReceiver, user) | `src/stores/auth.js` |
| `src/composables/useDonation.js` | Donation helpers (load, submit, modify, setFilters) | `src/stores/donation.js` |
| `src/composables/useMap.js` | Leaflet map setup + markers | `leaflet` npm package |

## Layouts

| File | Purpose | Depends On |
|------|---------|------------|
| `src/layouts/MainLayout.vue` | Appbar + Footer wrapper | `src/components/layout/AppHeader.vue`, `src/components/layout/AppFooter.vue` |
| `src/layouts/AuthLayout.vue` | Auth page layout (logo + card) | Nothing |
| `src/layouts/DashboardLayout.vue` | Sidebar + header + notification dropdown | `src/components/AppbarSidebar.vue`, `src/components/notifications/NotificationDropdown.vue`, `src/utils/helpers.js` |

## Views (Routes)

| File | Route | Depends On |
|------|-------|------------|
| `src/views/HomeView.vue` | `/` — Landing page with all sections | `AppHeader`, `AppFooter`, `MobileNav`, `HeroSection`, `HowItWorksSection`, `MealFeedSection`, `TrustSection`, `ImpactStats`, `Testimonials`, `FinalCta` |
| `src/views/auth/LoginView.vue` | `/auth/login` | `axios` |
| `src/views/auth/Signup.vue` | `/auth/signup` | `axios` |

### Route Views (in `src/components/views/`)

| File | Route | Depends On |
|------|-------|------------|
| `src/components/views/HomeView.vue` | Legacy home (old design) | Nothing |
| `src/components/views/NotFoundView.vue` | `/:pathMatch(.*)*` — 404 page | Nothing |
| `src/components/views/admin/UserManagement.vue` | `/dashboard/users` — Admin user table | `src/utils/helpers.js` |

## Landing Page Sections (orchestrated by `src/views/HomeView.vue`)

| File | Purpose | Depends On |
|------|---------|------------|
| `src/components/layout/AppHeader.vue` | Sticky dark header with logo + nav + auth CTAs | Nothing |
| `src/components/layout/AppFooter.vue` | Dark footer with links + brand | Nothing |
| `src/components/layout/MobileNav.vue` | Bottom nav bar (Home/Browse/Post/Requests/Profile) | Nothing |
| `src/components/sections/HeroSection.vue` | Hero with headline, CTAs, chalk counter | `@/assets/tokens.css` (via `styles.css`) |
| `src/components/sections/HowItWorksSection.vue` | 3-step process with scroll reveal | Nothing |
| `src/components/sections/MealFeedSection.vue` | Filterable meal grid with mock data | `MealCard`, `MealFilterBar` |
| `src/components/sections/TrustSection.vue` | Verified hosts, ratings, safety | Nothing |
| `src/components/sections/ImpactStats.vue` | Chalk counters (meals, waste, hosts) | Nothing |
| `src/components/sections/Testimonials.vue` | Community story cards | Nothing |
| `src/components/sections/FinalCta.vue` | Sign-up CTA section | Nothing |

## Meal Components

| File | Purpose | Depends On |
|------|---------|------------|
| `src/components/meal/MealCard.vue` | Torn-edge recipe card with hover tilt + steam | `src/components/ui/BaseTag.vue` |
| `src/components/meal/MealFilterBar.vue` | Search, dietary, distance, ready-now filters | Nothing |

## UI Components

| File | Purpose | Depends On |
|------|---------|------------|
| `src/components/ui/BaseButton.vue` | Reusable button (primary/secondary/outline/ghost) | Nothing |
| `src/components/ui/BaseTag.vue` | Reusable tag (green/gold/red/neutral) | Nothing |
| `src/components/ui/BaseBadge.vue` | Reusable badge (default/gold/outline) | Nothing |

## Shared Components

| File | Purpose | Depends On |
|------|---------|------------|
| `src/components/AppbarSidebar.vue` | Dashboard sidebar navigation | Nothing |
| `src/components/RoleBadge.vue` | Role badge display | Nothing |
| `src/components/LoadingSpinner.vue` | Loading spinner (size/text/fullScreen props) | Nothing |

## Dashboard Components

| File | Purpose | Depends On |
|------|---------|------------|
| `src/components/dashboard/RoleBasedDashboard.vue` | Routes to role-specific dashboard | `AdminDashboard`, `DonorDashboard`, `ReceiverDashboard` |
| `src/components/dashboard/AdminDashboard.vue` | Admin stats + charts + user table | `StatCard`, `chart.js`, `vue-chartjs` |
| `src/components/dashboard/DonorDashboard.vue` | Donor stats + donations + pending requests | `StatCard` |
| `src/components/dashboard/ReceiverDashboard.vue` | Receiver stats + nearby + requests | `StatCard` |
| `src/components/dashboard/StatCard.vue` | Stat display card | Nothing |

## Donations Components

| File | Purpose | Depends On |
|------|---------|------------|
| `src/components/donations/DonationList.vue` | Donation grid with filters + pagination | `DonationCard`, `DonationFilter`, `DonationStatusBadge`, `LoadingSpinner` |
| `src/components/donations/DonationCard.vue` | Single donation card | Nothing |
| `src/components/donations/CreateDonation.vue` | Donation creation form | `FoodCategorySelect` |
| `src/components/donations/DonationStatusBadge.vue` | Status badge with color | Nothing |
| `src/components/donations/FoodCategorySelect.vue` | Category picker grid | Nothing |
| `src/components/donations/DonationFilter.vue` | Search/category/status/distance filters | Nothing |

## Auth Components

| File | Purpose | Depends On |
|------|---------|------------|
| `src/components/auth/LoginPage.vue` | Login form (Pinia pattern) | `src/stores/auth.js` |
| `src/components/auth/RegisterView.vue` | Register form (Pinia pattern) | `src/stores/auth.js` |

## Chat Components

| File | Purpose | Depends On |
|------|---------|------------|
| `src/components/chat/ChatView.vue` | Chat layout + sidebar | `ConversationList`, `MessageThread`, `ChatInput` |
| `src/components/chat/ConversationList.vue` | Conversation sidebar list | Nothing |
| `src/components/chat/MessageThread.vue` | Message bubble display | Nothing |
| `src/components/chat/ChatInput.vue` | Message input with auto-resize | Nothing |

## Map Components

| File | Purpose | Depends On |
|------|---------|------------|
| `src/components/maps/MapContainer.vue` | Leaflet map wrapper | `leaflet` |
| `src/components/maps/MapView.vue` | Full map page with markers | `MapContainer`, `useMap` composable |

## Notifications Components

| File | Purpose | Depends On |
|------|---------|------------|
| `src/components/notifications/NotificationDropdown.vue` | Dropdown with unread count | `NotificationItem`, `src/stores/notification.js` |
| `src/components/notifications/NotificationItem.vue` | Single notification row | Nothing |

## Requests Components

| File | Purpose | Depends On |
|------|---------|------------|
| `src/components/requests/RequestCard.vue` | Request with approve/reject buttons | Nothing |

## Common Components

| File | Purpose | Depends On |
|------|---------|------------|
| `src/components/common/ToasterContainer.vue` | Toast notification display | `src/stores/toast.js` |
| `src/components/common/Errorboundary.vue` | Error boundary catch | Nothing |

## Utilities & Styles

| File | Purpose | Depends On |
|------|---------|------------|
| `src/styles/styles.css` | Global styles (Tailwind v4 + @theme + @utility + animations) | `src/assets/tokens.css` |
| `src/assets/tokens.css` | Design token system (colors, type scale, spacing, shadows, radii) | Nothing |
| `src/utils/constants.js` | Roles, statuses, categories, config constants | Nothing |
| `src/utils/helpers.js` | formatDate, getExpiryStatus, generateAvatarUrl | `date-fns` |
| `src/utils/storage.js` | localStorage wrapper | Nothing |

## How to Use This Skill

When the user requests a change:

1. Identify the component/view/store/service the user is referring to
2. Check the "Depends On" column to find ALL related files
3. Also check which files DEPEND ON the changed file (reverse dependency — search other tables for the file name)
4. Read ALL affected files before making changes
5. Modify files in dependency order (utilities → stores → services → composables → components → views → router)
6. After changes, run the Vite build to verify: `cd frontend/food-saver-front-lib && npm run build`
