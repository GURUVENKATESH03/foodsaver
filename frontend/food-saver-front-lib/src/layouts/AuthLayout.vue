<template>
  <div class="min-h-screen bg-[var(--color-bg-warm)] flex flex-col">
    <!-- Decorative top bar -->
    <div class="h-1.5 bg-gradient-to-r from-[var(--color-primary)] via-[var(--color-secondary)] to-[var(--color-primary)]" />

    <div class="flex-1 flex items-center justify-center px-4 py-12">
      <div class="w-full max-w-md">
        <!-- Logo + Brand -->
        <div class="text-center mb-8">
          <router-link to="/" class="inline-flex items-center gap-2.5 group">
            <div class="w-11 h-11 rounded-xl bg-[var(--color-bg-deep)] flex items-center justify-center shadow-sm transition-transform group-hover:scale-105">
              <svg class="w-5.5 h-5.5 text-[var(--color-primary)]" width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
              </svg>
            </div>
            <span class="font-display text-xl font-bold text-[var(--color-text-dark)]">Meal Share</span>
          </router-link>
        </div>

        <!-- Auth Card -->
        <div class="bg-white rounded-2xl shadow-[var(--shadow-card)] border border-[var(--color-border)] p-8">
          <slot />
        </div>

        <!-- Footer -->
        <p class="mt-8 text-center text-xs text-[var(--color-text-muted)]">
          Reducing food waste, one meal at a time
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
</script>
