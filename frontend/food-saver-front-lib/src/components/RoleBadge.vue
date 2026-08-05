<template>
  <span
    :class="[
      'inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-semibold font-mono capitalize',
      roleClasses[role] || 'bg-slate-200 text-slate-600'
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
  ADMIN: 'bg-purple-500/10 text-purple-600 border border-purple-500/20',
  DONOR: 'bg-[var(--color-primary)]/10 text-[var(--color-primary)] border border-[var(--color-primary)]/20',
  RECEIVER: 'bg-emerald-500/10 text-emerald-600 border border-emerald-500/20'
}
</script>
