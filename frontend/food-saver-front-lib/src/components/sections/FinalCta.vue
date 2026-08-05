<template>
  <section class="bg-[var(--color-bg)] py-20 md:py-28 border-t border-slate-200">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
      <div class="max-w-3xl mx-auto neu-card p-12 md:p-16">
        <div class="text-5xl mb-6">🥘</div>
        <h2 class="font-display text-3xl sm:text-4xl md:text-5xl font-bold text-[var(--color-text-dark)] tracking-tight mb-4">
          Ready to start sharing?
        </h2>
        <p class="text-base text-[var(--color-text-muted)] leading-relaxed mb-8 max-w-xl mx-auto font-body">
          Whether you have extra portions to share or you are looking for a meal nearby, join your local community to make sure good food never goes to waste.
        </p>
        <div class="flex flex-col sm:flex-row items-center justify-center gap-4">
          <router-link to="/auth/signup" class="btn-primary text-base px-8 py-4">
            Join Meal Share
          </router-link>
          <router-link to="/donations" class="btn-outline text-base px-8 py-4">
            Browse meals first
          </router-link>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
</script>
