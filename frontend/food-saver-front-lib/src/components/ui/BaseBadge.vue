<template>
  <span
    :class="[
      'inline-flex items-center gap-1.5 px-3 py-1 text-xs font-semibold rounded-full tracking-wider uppercase',
      variant === 'outline-deep' ? 'border border-[var(--color-border-deep)] text-[var(--color-text-light-muted)]' :
      variant === 'outline' ? 'border border-[var(--color-border)] text-[var(--color-text-muted)]' :
      variant === 'gold' ? 'bg-[var(--color-primary)]/20 text-[#A6732A]' :
      'bg-[var(--color-border)] text-[var(--color-text-muted)]'
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
