<template>
  <div class="min-h-screen bg-[var(--color-bg-warm)] flex flex-col">
    <AppHeader
      :is-authenticated="isAuthenticated"
      @logout="handleLogout"
    />

    <main class="flex-grow pt-16">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <slot />
      </div>
    </main>

    <AppFooter />
  </div>
</template>

<script setup>
import { useAuth } from '@/composables/useAuth'
import AppHeader from '@/components/layout/AppHeader.vue'
import AppFooter from '@/components/layout/AppFooter.vue'

const { isAuthenticated, logout } = useAuth()

function handleLogout() {
  logout()
}
</script>
