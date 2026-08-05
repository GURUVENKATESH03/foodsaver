<template>
  <section class="bg-[#1E293B] py-20 md:py-28 text-slate-200">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
      <span class="inline-flex items-center gap-2 px-3.5 py-1 rounded-full bg-slate-800 border border-slate-700 text-xs font-semibold text-slate-300 tracking-wider uppercase mb-4 font-mono">
        Community Impact
      </span>
      <h2 class="font-display text-3xl sm:text-4xl md:text-5xl font-bold text-white tracking-tight mb-4">
        Small actions, big difference
      </h2>
      <p class="text-sm text-slate-300 max-w-md mx-auto mb-16 font-body">
        Every shared meal means less food waste and stronger neighborhood connections.
      </p>

      <div class="grid grid-cols-1 sm:grid-cols-3 gap-8 sm:gap-12">
        <div v-for="(stat, i) in stats" :key="i" class="p-6 rounded-2xl bg-slate-800/60 border border-slate-700/80 shadow-md">
          <div class="font-mono text-4xl sm:text-5xl font-bold text-[var(--color-primary)] mb-2">{{ stat.value }}</div>
          <div class="font-display font-semibold text-lg text-white mb-1">{{ stat.label }}</div>
          <p class="font-mono text-xs text-slate-400">{{ stat.subtext }}</p>
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
    subtext: 'Since launch'
  },
  {
    value: '840 kg',
    label: 'food waste avoided',
    subtext: 'Estimated savings'
  },
  {
    value: '380+',
    label: 'active hosts',
    subtext: 'In local community'
  }
]
</script>
