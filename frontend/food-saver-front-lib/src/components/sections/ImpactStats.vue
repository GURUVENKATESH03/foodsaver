<template>
  <section class="bg-[var(--color-bg-deep)] section-padding">
    <div class="container-narrow text-center">
      <span class="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-white/[0.06] text-xs font-semibold text-[var(--color-text-light-muted)] tracking-wider uppercase mb-4">
        Our impact
      </span>
      <h2 class="font-display text-3xl sm:text-4xl md:text-5xl font-bold text-[var(--color-text-light)] tracking-tight mb-4">
        Small actions, big difference
      </h2>
      <p class="text-sm text-[var(--color-text-light-muted)] max-w-md mx-auto mb-12">
        Every shared meal means less waste and more connection. These are placeholder stats — real numbers coming soon.
      </p>

      <div class="grid grid-cols-1 sm:grid-cols-3 gap-8 sm:gap-12">
        <div v-for="(stat, i) in stats" :key="i" class="reveal">
          <div class="chalk-counter text-5xl sm:text-6xl mb-2">{{ stat.value }}</div>
          <div class="chalk-label text-base">{{ stat.label }}</div>
          <p class="text-xs text-[var(--color-text-light-muted)] mt-1">{{ stat.subtext }}</p>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
const stats = [
  {
    value: '1,240+',
    label: 'meals shared',
    subtext: 'Since launch — placeholder'
  },
  {
    value: '840 kg',
    label: 'food waste avoided',
    subtext: 'Estimated — placeholder'
  },
  {
    value: '380+',
    label: 'active hosts',
    subtext: 'In your community — placeholder'
  }
]
</script>
