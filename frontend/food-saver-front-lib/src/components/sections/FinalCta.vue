<template>
  <section class="bg-[var(--color-bg-warm)] py-20 md:py-28">
    <div class="container-narrow text-center">
      <div class="max-w-xl mx-auto">
        <div class="text-5xl mb-6">🥘</div>
        <h2 class="font-display text-3xl sm:text-4xl md:text-5xl font-bold text-[var(--color-text-dark)] tracking-tight mb-4">
          Ready to start sharing?
        </h2>
        <p class="text-base text-[var(--color-text-muted)] leading-relaxed mb-8">
          Whether you have extra portions to share or you are looking for a meal nearby, join the community that is making sure good food finds a home.
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
