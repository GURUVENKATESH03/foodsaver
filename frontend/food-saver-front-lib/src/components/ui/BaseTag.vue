<template>
  <span
    :class="[
      'inline-flex items-center gap-1 px-2 py-1 text-xs font-medium rounded-full leading-none',
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
    green: 'bg-[rgba(63,107,74,0.12)] text-[var(--color-secondary)]',
    gold: 'bg-[rgba(226,155,62,0.15)] text-[#A6732A]',
    red: 'bg-[rgba(180,71,46,0.12)] text-[var(--color-urgent)]',
    neutral: 'bg-[var(--color-border)] text-[var(--color-text-muted)]'
  }
  return colors[props.color]
})
</script>
