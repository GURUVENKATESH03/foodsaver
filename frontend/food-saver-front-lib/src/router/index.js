import { createRouter, createWebHistory } from 'vue-router'
import { setupRouterGuards } from './guard'

const routes = [
  // Public routes
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/HomeView.vue'),
    meta: { public: true, title: 'Home' }
  },
  
  // Auth routes
  {
    path: '/auth/login',
    name: 'Login',
    component: () => import('../views/auth/LoginView.vue'),
    meta: { public: true, title: 'Sign In' }
  },
  {
    path: '/auth/signup',
    name: 'Signup',
    component: () => import('../views/auth/Signup.vue'),
    meta: { public: true, title: 'Create Account' }
  },
  
  // Dashboard routes (role-based)
  {
    path: '/dashboard',
    component: () => import('@/layouts/DashboardLayout.vue'),
    meta: { requiresAuth: true, title: 'Dashboard' },
    children: [
      {
        path: '',
        name: 'Dashboard',
        component: () => import('@/components/dashboard/RoleBasedDashboard.vue'),
        meta: { title: 'Dashboard' }
      },
      {
        path: 'users',
        name: 'UserManagement',
        component: () => import('@/components/views/admin/UserManagement.vue'),
        meta: { requiresAuth: true, roles: ['ADMIN'], title: 'User Management' }
      },
      {
        path: 'analytics',
        name: 'Analytics',
        component: () => import('@/components/dashboard/AdminDashboard.vue'),
        meta: { requiresAuth: true, roles: ['ADMIN'], title: 'Analytics' }
      }
    ]
  },
  
  // Donation routes
  {
    path: '/donations',
    meta: { title: 'Donations' },
    children: [
      {
        path: '',
        name: 'Donations',
        component: () => import('@/components/donations/DonationList.vue'),
        meta: { title: 'Browse Donations' }
      },
      {
        path: 'create',
        name: 'CreateDonation',
        component: () => import('@/components/donations/CreateDonation.vue'),
        meta: { requiresAuth: true, roles: ['DONOR', 'ADMIN'], title: 'Create Donation' }
      },
      {
        path: ':id',
        name: 'DonationDetail',
        component: () => import('@/components/donations/DonationCard.vue'),
        meta: { title: 'Donation Details' }
      }
    ]
  },
  
  // Map route
  {
    path: '/map',
    name: 'Map',
    component: () => import('@/components/maps/MapView.vue'),
    meta: { title: 'Food Map' }
  },
  
  // Chat route
  {
    path: '/chat',
    name: 'Chat',
    component: () => import('@/components/chat/ChatView.vue'),
    meta: { requiresAuth: true, title: 'Messages' }
  },
  
  // Requests route
  {
    path: '/requests',
    name: 'Requests',
    component: () => import('@/components/requests/RequestCard.vue'),
    meta: { requiresAuth: true, title: 'My Requests' }
  },
  
  // Profile route
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('@/views/HomeView.vue'), // Placeholder - should be ProfileView
    meta: { requiresAuth: true, title: 'Profile' }
  },
  
  // Notifications route
  {
    path: '/notifications',
    name: 'Notifications',
    component: () => import('@/components/notifications/NotificationDropdown.vue'),
    meta: { requiresAuth: true, title: 'Notifications' }
  },
  
  // Catch-all 404
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/components/views/NotFoundView.vue'),
    meta: { public: true, title: 'Page Not Found' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) return savedPosition
    return { top: 0 }
  }
})

// Setup navigation guards
setupRouterGuards(router)

export default router
