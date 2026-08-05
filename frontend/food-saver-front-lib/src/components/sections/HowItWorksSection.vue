<template>
  <section class="bg-[var(--color-bg)] py-20 md:py-28">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="text-center mb-16">
        <span class="inline-flex items-center gap-2 px-3.5 py-1 rounded-full bg-[var(--color-primary)]/10 text-xs font-semibold text-[var(--color-primary)] tracking-wider uppercase mb-4 font-mono">
          How it works
        </span>
        <h2 class="font-display text-3xl sm:text-4xl md:text-5xl font-bold text-[var(--color-text-dark)] tracking-tight">
          Three steps to share
        </h2>
        <p class="mt-4 text-base text-[var(--color-text-muted)] max-w-lg mx-auto font-body">
          Whether you have extra portions or need a meal, the process is simple and transparent.
        </p>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-3 gap-8 md:gap-10">
        <div
          v-for="(step, index) in steps"
          :key="index"
          class="neu-card p-8 text-center flex flex-col items-center justify-between"
        >
          <div>
            <!-- Neumorphic Step number badge -->
            <div class="inline-flex items-center justify-center w-14 h-14 rounded-2xl bg-[var(--color-primary)] text-white text-xl font-bold font-mono shadow-[var(--shadow-neu-raised)] mb-6">
              {{ String(index + 1).padStart(2, '0') }}
            </div>

            <h3 class="font-display text-xl font-bold text-[var(--color-text-dark)] mb-3">
              {{ step.title }}
            </h3>
            <p class="text-sm text-[var(--color-text-muted)] leading-relaxed font-body">
              {{ step.description }}
            </p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
const steps = [
  {
    title: 'Post what you have',
    description: 'Snap a photo, add a few details — portions, pickup window, dietary tags. Posting takes under a minute.'
  },
  {
    title: 'Someone nearby claims it',
    description: 'Neighbors, students, or local community members browse available meals and request what they need.'
  },
  {
    title: 'Meet and share',
    description: 'Coordinate a quick pickup. Food gets rescued, hunger gets addressed, nothing goes to waste.'
  }
]
</script>
