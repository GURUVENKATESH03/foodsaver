<template>
  <div class="neu-card p-6">
    <div class="flex items-start justify-between mb-4">
      <div
        :class="[
          'w-12 h-12 rounded-2xl flex items-center justify-center shadow-[var(--shadow-neu-sm)]',
          bgColorClass
        ]"
      >
        <svg class="w-6 h-6" :class="iconColorClass" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path v-if="icon === 'users'" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
          <path v-else-if="icon === 'donation'" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
          <path v-else-if="icon === 'request'" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-3 7h3m-3 4h3m-6-4h.01M9 16h.01" />
          <path v-else stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 6l3 1m0 0l-3 9a5.002 5.002 0 006.001 0M6 7l3 9M6 7l6-2m6 2l3-1m-3 1l-3 9a5.002 5.002 0 006.001 0M18 7l3 9m-3-9l-6-2m0-2v2m0 16V5m0 16H9m3 0h3" />
        </svg>
      </div>
      <span
        v-if="trend"
        :class="[
          'inline-flex items-center gap-0.5 text-xs font-semibold px-2.5 py-1 rounded-full font-mono',
          trendUp ? 'bg-emerald-500/10 text-emerald-600' : 'bg-red-500/10 text-red-600'
        ]"
      >
        <svg v-if="trendUp" class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 10l7-7m0 0l7 7m-7-7v18" />
        </svg>
        <svg v-else class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 14l-7 7m0 0l-7-7m7 7V3" />
        </svg>
        {{ trend }}
      </span>
    </div>
    <p class="font-mono text-3xl font-bold text-[var(--color-text-dark)]">{{ formattedValue }}</p>
    <p class="text-sm font-medium text-[var(--color-text-muted)] mt-1 font-body">{{ title }}</p>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  title: String,
  value: [Number, String],
  icon: {
    type: String,
    default: 'default'
  },
  trend: String,
  trendUp: Boolean,
  color: {
    type: String,
    default: 'primary'
  }
})

const formattedValue = computed(() => {
  if (typeof props.value === 'number') {
    return new Intl.NumberFormat('en-US').format(props.value)
  }
  return props.value
})

const colorMap = {
  primary: { bg: 'bg-[var(--color-primary)]/10', icon: 'text-[var(--color-primary)]' },
  green: { bg: 'bg-emerald-500/10', icon: 'text-emerald-600' },
  blue: { bg: 'bg-[var(--color-primary)]/10', icon: 'text-[var(--color-primary)]' },
  orange: { bg: 'bg-amber-500/10', icon: 'text-amber-600' },
  purple: { bg: 'bg-[var(--color-primary)]/10', icon: 'text-[var(--color-primary)]' }
}

const colorClasses = computed(() => colorMap[props.color] || colorMap.primary)
const bgColorClass = computed(() => colorClasses.value.bg)
const iconColorClass = computed(() => colorClasses.value.icon)
</script>
