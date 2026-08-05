<template>
  <div class="min-h-screen bg-[var(--color-bg)]">
    <!-- Header -->
    <AppHeader
      :is-authenticated="isAuthenticated"
      @toggle-mobile="mobileOpen = !mobileOpen"
      @logout="handleLogout"
    />

    <!-- Hero -->
    <HeroSection />

    <!-- How it works -->
    <HowItWorksSection />

    <!-- Live meal feed -->
    <MealFeedSection />

    <!-- Trust & safety -->
    <TrustSection />

    <!-- Impact stats -->
    <ImpactStats />

    <!-- Testimonials -->
    <Testimonials />

    <!-- Final CTA -->
    <FinalCta />

    <!-- Footer -->
    <AppFooter />

    <!-- Mobile bottom nav -->
    <MobileNav :visible="true" />

    <!-- Mobile slide-over nav -->
    <div
      v-if="mobileOpen"
      class="fixed inset-0 z-50 md:hidden"
    >
      <div class="absolute inset-0 bg-black/50 backdrop-blur-sm" @click="mobileOpen = false" />
      <nav class="absolute top-0 left-0 bottom-0 w-72 bg-[#1E293B] p-8 flex flex-col gap-6 shadow-2xl">
        <div class="flex items-center justify-between mb-6 border-b border-slate-800 pb-4">
          <span class="font-display text-lg font-bold text-white">Meal Share</span>
          <button @click="mobileOpen = false" class="text-slate-400 hover:text-white transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <line x1="18" y1="6" x2="6" y2="18" />
              <line x1="6" y1="6" x2="18" y2="18" />
            </svg>
          </button>
        </div>

        <router-link
          v-for="link in mobileLinks"
          :key="link.path"
          :to="link.path"
          class="text-sm font-medium text-slate-300 hover:text-[var(--color-primary)] transition-colors py-2"
          @click="mobileOpen = false"
        >
          {{ link.label }}
        </router-link>

        <div class="mt-auto pt-6 border-t border-slate-800">
          <template v-if="!isAuthenticated">
            <router-link to="/auth/login" class="block text-sm font-medium text-slate-300 hover:text-white mb-3" @click="mobileOpen = false">
              Sign in
            </router-link>
            <router-link to="/auth/signup" class="btn-primary text-sm w-full text-center" @click="mobileOpen = false">
              Get started
            </router-link>
          </template>
          <button v-else @click="handleLogout" class="text-sm font-medium text-red-400 hover:text-red-300 w-full text-left">
            Sign out
          </button>
        </div>
      </nav>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import AppHeader from '@/components/layout/AppHeader.vue'
import AppFooter from '@/components/layout/AppFooter.vue'
import MobileNav from '@/components/layout/MobileNav.vue'
import HeroSection from '@/components/sections/HeroSection.vue'
import HowItWorksSection from '@/components/sections/HowItWorksSection.vue'
import MealFeedSection from '@/components/sections/MealFeedSection.vue'
import TrustSection from '@/components/sections/TrustSection.vue'
import ImpactStats from '@/components/sections/ImpactStats.vue'
import Testimonials from '@/components/sections/Testimonials.vue'
import FinalCta from '@/components/sections/FinalCta.vue'

const router = useRouter()

const mobileOpen = ref(false)
const isAuthenticated = ref(false)

const mobileLinks = [
  { path: '/', label: 'Home' },
  { path: '/donations', label: 'Browse meals' },
  { path: '/donations/create', label: 'Share a meal' },
  { path: '/map', label: 'Food map' },
  { path: '/requests', label: 'My requests' }
]

function handleLogout() {
  isAuthenticated.value = false
  router.push('/')
}
</script>
