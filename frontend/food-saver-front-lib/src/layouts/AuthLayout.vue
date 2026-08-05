<template>
  <div class="min-h-screen bg-[var(--color-bg)] flex flex-col justify-between">
    <!-- Decorative top accent bar -->
    <div class="h-1 bg-[var(--color-primary)]" />

    <div class="flex-1 flex items-center justify-center px-4 py-12">
      <div class="w-full max-w-md">
        <!-- Logo + Brand -->
        <div class="text-center mb-8">
          <router-link to="/" class="inline-flex items-center gap-3 group">
            <div class="w-12 h-12 rounded-2xl bg-[var(--color-bg-dark)] flex items-center justify-center shadow-[var(--shadow-neu-raised)] transition-transform group-hover:scale-105">
              <svg class="w-6 h-6 text-[var(--color-primary)]" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
              </svg>
            </div>
            <span class="font-display text-2xl font-bold tracking-tight text-[var(--color-text-dark)]">Meal Share</span>
          </router-link>
        </div>

        <!-- Auth Neumorphic Card -->
        <div class="neu-card p-8">
          <slot />
        </div>

        <!-- Footer tag line -->
        <p class="mt-8 text-center text-xs text-[var(--color-text-muted)] font-body">
          Connecting neighbors to reduce food waste, together
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
</script>
