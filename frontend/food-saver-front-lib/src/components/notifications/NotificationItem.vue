<template>
  <div
    :class="[
      'px-5 py-3.5 cursor-pointer transition-colors hover:bg-[var(--color-bg-warm)] border-b border-[var(--color-border)] last:border-0',
      !notification.read ? 'bg-[var(--color-primary)]/4' : ''
    ]"
    @click="$emit('click')"
  >
    <div class="flex items-start gap-3">
      <div :class="['w-8 h-8 rounded-full flex items-center justify-center flex-shrink-0', iconBgClass]">
        <span class="text-sm">{{ icon }}</span>
      </div>
      <div class="flex-1 min-w-0">
        <p class="text-sm text-[var(--color-text-dark)]">
          <span class="font-semibold">{{ notification.title }}</span>
          {{ notification.message }}
        </p>
        <p class="text-xs text-[var(--color-text-muted)] mt-1 font-mono">{{ timeAgo }}</p>
      </div>
      <div v-if="!notification.read" class="w-2 h-2 bg-[var(--color-primary)] rounded-full flex-shrink-0 mt-1.5" />
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  notification: {
    type: Object,
    required: true
  }
})

defineEmits(['click'])

const iconMap = {
  donation: '🍎',
  request: '🤝',
  message: '💬',
  system: '🔔',
  pickup: '🚚'
}

const icon = computed(() => iconMap[props.notification.type] || '🔔')

const iconBgClass = computed(() => {
  const classes = {
    donation: 'bg-[var(--color-secondary)]/10',
    request: 'bg-[var(--color-primary)]/12',
    message: 'bg-purple-50',
    system: 'bg-[var(--color-primary)]/8',
    pickup: 'bg-[var(--color-urgent)]/8'
  }
  return classes[props.notification.type] || 'bg-[var(--color-bg-warm)]'
})

const timeAgo = computed(() => {
  if (!props.notification.timestamp) return 'Recently'
  try {
    const diff = Date.now() - new Date(props.notification.timestamp).getTime()
    const mins = Math.floor(diff / 60000)
    if (mins < 1) return 'Just now'
    if (mins < 60) return `${mins}m ago`
    const hours = Math.floor(mins / 60)
    if (hours < 24) return `${hours}h ago`
    if (hours < 48) return 'Yesterday'
    return `${Math.floor(hours / 24)}d ago`
  } catch {
    return 'Recently'
  }
})
</script>
