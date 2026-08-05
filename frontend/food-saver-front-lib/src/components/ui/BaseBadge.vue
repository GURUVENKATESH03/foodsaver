<template>
  <span
    :class="[
      'inline-flex items-center gap-1.5 px-3 py-1 text-xs font-semibold rounded-full tracking-wider uppercase font-mono shadow-xs',
      variant === 'outline-deep' ? 'border border-slate-700 text-slate-300 bg-slate-800/80' :
      variant === 'outline' ? 'border border-slate-300 text-slate-600 bg-white' :
      variant === 'gold' ? 'bg-amber-500/15 text-amber-600 border border-amber-500/20' :
      'bg-[var(--color-primary)]/10 text-[var(--color-primary)] border border-[var(--color-primary)]/20'
    ]"
  >
    <slot />
  </span>
</template>

<script setup>
defineProps({
  variant: {
    type: String,
    default: 'default',
    validator: v => ['default', 'gold', 'outline', 'outline-deep'].includes(v)
  }
})
</script>
