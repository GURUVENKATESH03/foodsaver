<template>
  <section class="bg-[var(--color-bg-warm)] section-padding">
    <div class="container-narrow">
      <div class="text-center mb-14">
        <span class="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-[var(--color-secondary)]/10 text-xs font-semibold text-[var(--color-secondary)] tracking-wider uppercase mb-4">
          Trust & safety
        </span>
        <h2 class="font-display text-3xl sm:text-4xl md:text-5xl font-bold text-[var(--color-text-dark)] tracking-tight">
          Share with confidence
        </h2>
        <p class="mt-4 text-base text-[var(--color-text-muted)] max-w-lg mx-auto">
          Every meal shared through Meal Share comes with basic safeguards so everyone feels safe.
        </p>
      </div>

      <div class="grid grid-cols-1 sm:grid-cols-3 gap-8">
        <div v-for="(item, i) in trustItems" :key="i" class="text-center p-6">
          <div class="inline-flex items-center justify-center w-16 h-16 rounded-2xl bg-white border border-[var(--color-border)] text-2xl mb-5 shadow-sm">
            {{ item.icon }}
          </div>
          <h3 class="font-display text-lg font-bold text-[var(--color-text-dark)] mb-2">{{ item.title }}</h3>
          <p class="text-sm text-[var(--color-text-muted)] leading-relaxed">{{ item.description }}</p>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
const trustItems = [
  {
    icon: '✅',
    title: 'Verified hosts',
    description: 'Everyone who shares food has a verified profile. You can see their sharing history and ratings from the community.'
  },
  {
    icon: '⭐',
    title: 'Community ratings',
    description: 'After each pickup, both sides can leave a rating. Consistent 5-star hosts are highlighted in the feed.'
  },
  {
    icon: '📋',
    title: 'Safety guidelines',
    description: 'We provide clear food-safety tips for storage, transport, and handling. If in doubt, leave it out.'
  }
]
</script>
