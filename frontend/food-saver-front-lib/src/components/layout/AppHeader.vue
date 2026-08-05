<template>
  <header class="sticky top-0 z-50 bg-[#1E293B] border-b border-slate-800 shadow-md">
    <nav class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 flex items-center justify-between h-16 md:h-18">
      <!-- Logo -->
      <router-link to="/" class="flex items-center gap-2.5 group">
        <span class="flex items-center justify-center w-9 h-9 rounded-xl bg-[var(--color-primary)] text-white font-bold text-lg shadow-sm transition-transform duration-200 group-hover:scale-105">
          M
        </span>
        <span class="font-display text-lg font-bold text-white tracking-tight hidden sm:block">
          Meal Share
        </span>
      </router-link>

      <!-- Desktop Nav -->
      <div class="hidden md:flex items-center gap-8">
        <router-link
          to="/donations"
          class="text-sm font-medium text-slate-300 hover:text-white transition-colors duration-150"
        >
          Browse meals
        </router-link>
        <router-link
          to="/donations/create"
          class="text-sm font-medium text-slate-300 hover:text-white transition-colors duration-150"
        >
          Share a meal
        </router-link>
        <router-link
          to="/map"
          class="text-sm font-medium text-slate-300 hover:text-white transition-colors duration-150"
        >
          Food map
        </router-link>
        <div class="w-px h-5 bg-slate-800" />
        <template v-if="isAuthenticated">
          <router-link
            to="/dashboard"
            class="text-sm font-medium text-[var(--color-primary)] hover:underline"
          >
            Dashboard
          </router-link>
          <button
            @click="handleLogout"
            class="text-sm font-medium text-red-400 hover:text-red-300 transition-colors"
          >
            Sign out
          </button>
        </template>
        <template v-else>
          <router-link
            to="/auth/login"
            class="text-sm font-medium text-slate-300 hover:text-white transition-colors"
          >
            Sign in
          </router-link>
          <router-link
            to="/auth/signup"
            class="btn-primary text-xs px-5 py-2.5"
          >
            Get started
          </router-link>
        </template>
      </div>

      <!-- Mobile hamburger -->
      <button
        @click="$emit('toggle-mobile')"
        class="md:hidden flex items-center justify-center w-10 h-10 rounded-lg text-white hover:bg-white/10 transition-colors"
        aria-label="Toggle navigation menu"
      >
        <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <line x1="3" y1="6" x2="21" y2="6" />
          <line x1="3" y1="12" x2="21" y2="12" />
          <line x1="3" y1="18" x2="21" y2="18" />
        </svg>
      </button>
    </nav>
  </header>
</template>

<script setup>
const props = defineProps({
  isAuthenticated: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['toggle-mobile', 'logout'])

function handleLogout() {
  emit('logout')
}
</script>
