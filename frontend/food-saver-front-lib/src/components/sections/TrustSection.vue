<template>
  <section class="bg-[var(--color-bg)] py-20 md:py-28 border-t border-slate-200">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="text-center mb-16">
        <span class="inline-flex items-center gap-2 px-3.5 py-1 rounded-full bg-[var(--color-primary)]/10 text-xs font-semibold text-[var(--color-primary)] tracking-wider uppercase mb-4 font-mono">
          Trust & safety
        </span>
        <h2 class="font-display text-3xl sm:text-4xl md:text-5xl font-bold text-[var(--color-text-dark)] tracking-tight">
          Share with confidence
        </h2>
        <p class="mt-4 text-base text-[var(--color-text-muted)] max-w-lg mx-auto font-body">
          Every meal shared through Meal Share comes with community safeguards so everyone feels safe.
        </p>
      </div>

      <div class="grid grid-cols-1 sm:grid-cols-3 gap-8">
        <div v-for="(item, i) in trustItems" :key="i" class="neu-card p-8 text-center flex flex-col items-center">
          <div class="inline-flex items-center justify-center w-16 h-16 rounded-2xl bg-white text-3xl mb-6 shadow-[var(--shadow-neu-raised)]">
            {{ item.icon }}
          </div>
          <h3 class="font-display text-lg font-bold text-[var(--color-text-dark)] mb-2">{{ item.title }}</h3>
          <p class="text-sm text-[var(--color-text-muted)] leading-relaxed font-body">{{ item.description }}</p>
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
    description: 'Everyone who shares food has a verified profile. You can see their sharing history and community ratings.'
  },
  {
    icon: '⭐',
    title: 'Community ratings',
    description: 'After each pickup, both sides can leave a rating. Consistent 5-star hosts are highlighted in the feed.'
  },
  {
    icon: '📋',
    title: 'Food safety tips',
    description: 'We provide clear guidelines for food storage, transport, and handling. Safe sharing is our priority.'
  }
]
</script>
