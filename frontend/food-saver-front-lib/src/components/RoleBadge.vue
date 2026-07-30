<template>
  <span
    :class="[
      'inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-semibold capitalize',
      roleClasses[role] || 'bg-[var(--color-bg-warm)] text-[var(--color-text-muted)]'
    ]"
  >
    {{ role?.toLowerCase() }}
  </span>
</template>

<script setup>
defineProps({
  role: {
    type: String,
    required: true,
    validator: (value) => ['ADMIN', 'DONOR', 'RECEIVER'].includes(value)
  }
})

const roleClasses = {
  ADMIN: 'bg-purple-50 text-purple-700',
  DONOR: 'bg-[var(--color-primary)]/12 text-[var(--color-primary)]',
  RECEIVER: 'bg-[var(--color-secondary)]/10 text-[var(--color-secondary)]'
}
</script>
