<template>
  <!-- Mobile bottom navigation -->
  <nav
    v-if="visible"
    class="fixed bottom-0 left-0 right-0 z-40 bg-[var(--color-bg-deep)] border-t border-[var(--color-border-deep)] md:hidden safe-area-bottom"
  >
    <div class="flex items-center justify-around h-16 px-2">
      <router-link
        to="/"
        class="flex flex-col items-center gap-0.5 px-3 py-1 rounded-lg transition-colors"
        :class="$route.path === '/' ? 'text-[var(--color-primary)]' : 'text-[var(--color-text-light-muted)]'"
      >
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="m3 9 9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z" />
          <polyline points="9 22 9 12 15 12 15 22" />
        </svg>
        <span class="text-[10px] font-medium">Home</span>
      </router-link>

      <router-link
        to="/donations"
        class="flex flex-col items-center gap-0.5 px-3 py-1 rounded-lg transition-colors"
        :class="$route.path.startsWith('/donations') ? 'text-[var(--color-primary)]' : 'text-[var(--color-text-light-muted)]'"
      >
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M6 2 3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4Z" />
          <path d="M3 6h18" />
          <path d="M16 10a4 4 0 0 1-8 0" />
        </svg>
        <span class="text-[10px] font-medium">Browse</span>
      </router-link>

      <router-link
        to="/donations/create"
        class="flex flex-col items-center gap-0.5 px-3 py-1 rounded-lg transition-colors relative"
        :class="$route.path === '/donations/create' ? 'text-[var(--color-primary)]' : 'text-[var(--color-text-light-muted)]'"
      >
        <span class="flex items-center justify-center w-10 h-10 -mt-5 rounded-full bg-[var(--color-primary)] text-[var(--color-bg-deep)] shadow-lg">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
            <line x1="12" y1="5" x2="12" y2="19" />
            <line x1="5" y1="12" x2="19" y2="12" />
          </svg>
        </span>
        <span class="text-[10px] font-medium">Post</span>
      </router-link>

      <router-link
        to="/requests"
        class="flex flex-col items-center gap-0.5 px-3 py-1 rounded-lg transition-colors"
        :class="$route.path.startsWith('/requests') ? 'text-[var(--color-primary)]' : 'text-[var(--color-text-light-muted)]'"
      >
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2" />
          <circle cx="9" cy="7" r="4" />
          <path d="M22 21v-2a4 4 0 0 0-3-3.87" />
          <path d="M16 3.13a4 4 0 0 1 0 7.75" />
        </svg>
        <span class="text-[10px] font-medium">Requests</span>
      </router-link>

      <router-link
        to="/profile"
        class="flex flex-col items-center gap-0.5 px-3 py-1 rounded-lg transition-colors"
        :class="$route.path === '/profile' ? 'text-[var(--color-primary)]' : 'text-[var(--color-text-light-muted)]'"
      >
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="12" cy="12" r="3" />
          <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1-2.83 2.83l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-4 0v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83-2.83l.06-.06A1.65 1.65 0 0 0 4.68 15a1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1 0-4h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 2.83-2.83l.06.06A1.65 1.65 0 0 0 9 4.68a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 4 0v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 2.83l-.06.06A1.65 1.65 0 0 0 19.4 9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1z" />
        </svg>
        <span class="text-[10px] font-medium">Profile</span>
      </router-link>
    </div>
  </nav>
</template>

<script setup>
defineProps({
  visible: {
    type: Boolean,
    default: true
  }
})
</script>

<style scoped>
.safe-area-bottom {
  padding-bottom: env(safe-area-inset-bottom, 0px);
}
</style>
