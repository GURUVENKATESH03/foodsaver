<template>
  <section class="bg-[var(--color-bg-deep)] relative overflow-hidden">
    <!-- Subtle texture pattern -->
    <div class="absolute inset-0 opacity-[0.03]" style="background-image: radial-gradient(circle at 1px 1px, white 1px, transparent 0); background-size: 24px 24px;" />

    <div class="container-wide relative py-20 md:py-28 lg:py-36">
      <div class="max-w-3xl mx-auto text-center">
        <!-- Eyebrow badge -->
        <span class="hero-fade-in inline-flex items-center gap-2 px-4 py-1.5 rounded-full border border-[var(--color-border-deep)] bg-white/[0.04] text-xs font-semibold text-[var(--color-text-light-muted)] tracking-wider uppercase mb-6">
          <span class="w-1.5 h-1.5 rounded-full bg-[var(--color-primary)] animate-pulse" />
          Community Driven
        </span>

        <!-- Headline -->
        <h1 class="hero-fade-in font-display text-5xl sm:text-6xl md:text-7xl font-bold text-[var(--color-text-light)] leading-[1.05] tracking-tight mb-6">
          Share a meal.<br />
          <span class="text-[var(--color-primary)]">Feed a neighbor.</span>
        </h1>

        <p class="hero-fade-in max-w-xl mx-auto text-base md:text-lg text-[var(--color-text-light-muted)] leading-relaxed mb-10">
          Got extra food? Someone nearby could use it. Meal Share connects your surplus with the people around you, one portion at a time.
        </p>

        <!-- CTAs -->
        <div class="hero-fade-in flex flex-col sm:flex-row items-center justify-center gap-4 mb-12">
          <router-link to="/donations/create" class="btn-primary text-base px-8 py-4">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
              <line x1="12" y1="5" x2="12" y2="19" />
              <line x1="5" y1="12" x2="19" y2="12" />
            </svg>
            Share a meal
          </router-link>
          <router-link to="/donations" class="btn-outline border-[var(--color-border-deep)] text-[var(--color-text-light)] hover:bg-white/5 text-base px-8 py-4">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="12" cy="12" r="10" />
              <path d="m4.93 4.93 4.24 4.24" />
              <path d="m14.83 9.17 4.24-4.24" />
              <path d="m14.83 14.83 4.24 4.24" />
              <path d="m9.17 14.83-4.24 4.24" />
            </svg>
            Find a meal near me
          </router-link>
        </div>

        <!-- Chalk-style counter -->
        <div class="hero-fade-in inline-flex items-center gap-4 px-6 py-3 rounded-2xl bg-white/[0.04] border border-[var(--color-border-deep)]">
          <span class="chalk-counter">1,240+</span>
          <div class="text-left">
            <span class="chalk-label block">meals shared</span>
            <span class="text-xs text-[var(--color-text-light-muted)]">this month — placeholder data</span>
          </div>
        </div>
      </div>
    </div>

    <!-- Bottom fade to next section -->
    <div class="h-16 bg-gradient-to-b from-[var(--color-bg-deep)] to-transparent" />
  </section>
</template>
