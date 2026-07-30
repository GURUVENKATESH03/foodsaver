<template>
  <span
    :class="[
      'inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-[11px] font-semibold capitalize',
      statusClasses
    ]"
  >
    <span :class="['w-1.5 h-1.5 rounded-full', dotClasses]" />
    {{ label }}
  </span>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  status: {
    type: String,
    required: true
  }
})

const statusMap = {
  AVAILABLE: { classes: 'bg-[var(--color-secondary)]/10 text-[var(--color-secondary)]', dot: 'bg-[var(--color-secondary)]' },
  RESERVED: { classes: 'bg-[var(--color-primary)]/12 text-[#A6732A]', dot: 'bg-[var(--color-primary)]' },
  PICKED_UP: { classes: 'bg-blue-50 text-blue-700', dot: 'bg-blue-500' },
  EXPIRED: { classes: 'bg-[var(--color-urgent)]/10 text-[var(--color-urgent)]', dot: 'bg-[var(--color-urgent)]' },
  CANCELLED: { classes: 'bg-[var(--color-bg-warm)] text-[var(--color-text-muted)]', dot: 'bg-[var(--color-text-muted)]' },
  PENDING: { classes: 'bg-[var(--color-primary)]/12 text-[#A6732A]', dot: 'bg-[var(--color-primary)]' },
  APPROVED: { classes: 'bg-[var(--color-secondary)]/10 text-[var(--color-secondary)]', dot: 'bg-[var(--color-secondary)]' },
  REJECTED: { classes: 'bg-[var(--color-urgent)]/10 text-[var(--color-urgent)]', dot: 'bg-[var(--color-urgent)]' },
  COMPLETED: { classes: 'bg-[var(--color-secondary)]/10 text-[var(--color-secondary)]', dot: 'bg-[var(--color-secondary)]' }
}

const statusClasses = computed(() => statusMap[props.status]?.classes || 'bg-[var(--color-bg-warm)] text-[var(--color-text-muted)]')
const dotClasses = computed(() => statusMap[props.status]?.dot || 'bg-[var(--color-text-muted)]')
const label = computed(() => props.status?.toLowerCase().replace(/_/g, ' ') || 'unknown')
</script>
