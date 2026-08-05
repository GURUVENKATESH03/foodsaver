<template>
  <span
    :class="[
      'inline-flex items-center gap-1 px-2.5 py-1 text-[11px] font-medium rounded-full leading-none font-body shadow-xs',
      colorClasses
    ]"
  >
    <slot />
  </span>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  color: {
    type: String,
    default: 'green',
    validator: v => ['green', 'gold', 'red', 'neutral'].includes(v)
  }
})

const colorClasses = computed(() => {
  const colors = {
    green: 'bg-[var(--color-primary)]/12 text-[var(--color-primary)] border border-[var(--color-primary)]/20',
    gold: 'bg-amber-500/12 text-amber-600 border border-amber-500/20',
    red: 'bg-red-500/12 text-red-600 border border-red-500/20',
    neutral: 'bg-slate-200 text-slate-600'
  }
  return colors[props.color] || colors.green
})
</script>
