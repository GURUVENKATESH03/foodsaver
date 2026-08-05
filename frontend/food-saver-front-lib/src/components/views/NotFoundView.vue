<template>
  <div class="min-h-screen flex items-center justify-center bg-[var(--color-bg-warm)] p-6">
    <div class="max-w-md w-full text-center bg-white rounded-3xl border border-[var(--color-border)] p-10 shadow-[var(--shadow-card)]">
      <div class="w-24 h-24 bg-[var(--color-primary)]/15 rounded-2xl flex items-center justify-center mx-auto mb-6">
        <span class="text-4xl">🍲</span>
      </div>
      <h1 class="font-display text-5xl font-extrabold text-[var(--color-text-dark)] mb-2">404</h1>
      <h2 class="font-display text-xl font-bold text-[var(--color-text-dark)] mb-3">Recipe Not Found</h2>
      <p class="text-sm text-[var(--color-text-muted)] mb-8">
        Looks like this meal or page has been claimed or moved to another kitchen.
      </p>
      <router-link to="/" class="btn-primary w-full justify-center">
        Return to Community Kitchen
      </router-link>
    </div>
  </div>
</template>

<script setup>
</script>