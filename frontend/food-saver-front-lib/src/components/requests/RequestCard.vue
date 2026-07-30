<template>
  <div class="flex items-center justify-between p-4 bg-[var(--color-bg-warm)] rounded-xl border border-[var(--color-border)] hover:bg-[var(--color-bg-warm)]/70 transition-colors">
    <div class="flex items-center gap-3 min-w-0">
      <div class="w-10 h-10 rounded-full bg-[var(--color-primary)]/15 flex items-center justify-center text-sm font-bold text-[var(--color-primary)] flex-shrink-0">
        {{ (request.requesterName || '?').charAt(0) }}
      </div>
      <div class="min-w-0">
        <p class="text-sm font-semibold text-[var(--color-text-dark)] truncate">{{ request.requesterName }}</p>
        <p class="text-xs text-[var(--color-text-muted)] truncate">{{ request.message || 'No message' }}</p>
        <p class="text-[11px] text-[var(--color-text-muted)] mt-0.5 font-mono">{{ timeAgo }}</p>
      </div>
    </div>

    <div class="flex items-center gap-2 flex-shrink-0">
      <button
        @click="$emit('approve', request.id)"
        class="px-3 py-1.5 bg-[var(--color-secondary)]/10 text-[var(--color-secondary)] rounded-lg text-xs font-semibold hover:bg-[var(--color-secondary)]/20 transition-colors"
      >
        Approve
      </button>
      <button
        @click="$emit('reject', request.id)"
        class="px-3 py-1.5 bg-[var(--color-urgent)]/8 text-[var(--color-urgent)] rounded-lg text-xs font-semibold hover:bg-[var(--color-urgent)]/15 transition-colors"
      >
        Reject
      </button>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  request: {
    type: Object,
    required: true
  }
})

defineEmits(['approve', 'reject'])

const timeAgo = computed(() => {
  if (!props.request.createdAt) return 'Recently'
  try {
    const diff = Date.now() - new Date(props.request.createdAt).getTime()
    const mins = Math.floor(diff / 60000)
    if (mins < 1) return 'Just now'
    if (mins < 60) return `${mins}m ago`
    const hours = Math.floor(mins / 60)
    if (hours < 24) return `${hours}h ago`
    return `${Math.floor(hours / 24)}d ago`
  } catch {
    return 'Recently'
  }
})
</script>
