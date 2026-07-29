<template>
  <component
    :is="tag"
    :class="[
      'inline-flex items-center justify-center gap-2 font-semibold leading-none transition-all duration-150 cursor-pointer',
      'disabled:opacity-50 disabled:cursor-not-allowed',
      variantClasses
    ]"
    v-bind="$attrs"
  >
    <slot />
  </component>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  variant: {
    type: String,
    default: 'primary',
    validator: v => ['primary', 'secondary', 'outline', 'ghost'].includes(v)
  },
  size: {
    type: String,
    default: 'md',
    validator: v => ['sm', 'md', 'lg'].includes(v)
  },
  tag: {
    type: String,
    default: 'button'
  }
})

const sizeClasses = computed(() => {
  const sizes = {
    sm: 'px-4 py-2 text-xs',
    md: 'px-6 py-3 text-sm',
    lg: 'px-8 py-4 text-base'
  }
  return sizes[props.size]
})

const variantClasses = computed(() => {
  const base = sizeClasses.value
  const variants = {
    primary: `${base} bg-[var(--color-primary)] text-[var(--color-text-dark)] rounded-xl hover:bg-[var(--color-primary-hover)] hover:-translate-y-[1px] hover:shadow-md active:translate-y-0`,
    secondary: `${base} bg-[var(--color-bg-deep)] text-[var(--color-text-light)] rounded-xl hover:bg-[#3E3023] hover:-translate-y-[1px] hover:shadow-md active:translate-y-0`,
    outline: `${base} bg-transparent text-[var(--color-text-dark)] border-[1.5px] border-[var(--color-border)] rounded-xl hover:border-[var(--color-text-muted)] hover:bg-black/[0.02]`,
    ghost: `${base} bg-transparent text-[var(--color-text-dark)] rounded-xl hover:bg-black/[0.04]`
  }
  return variants[props.variant]
})
</script>
