<template>
  <div class="neu-card p-4 flex items-center justify-between">
    <div class="flex items-center gap-3 min-w-0">
      <div class="w-10 h-10 rounded-full bg-[var(--color-primary)] text-white flex items-center justify-center text-sm font-bold font-mono shadow-sm flex-shrink-0">
        {{ (request.requesterName || '?').charAt(0) }}
      </div>
      <div class="min-w-0">
        <p class="text-sm font-semibold text-[var(--color-text-dark)] font-body truncate">{{ request.requesterName }}</p>
        <p class="text-xs text-[var(--color-text-muted)] font-body truncate">{{ request.message || 'No message provided' }}</p>
        <p class="text-[11px] text-slate-400 mt-0.5 font-mono">{{ timeAgo }}</p>
      </div>
    </div>

    <div class="flex items-center gap-2 flex-shrink-0">
      <button
        @click="$emit('approve', request.id)"
        class="px-3 py-1.5 bg-emerald-500/10 text-emerald-600 rounded-xl text-xs font-semibold hover:bg-emerald-500/20 transition-colors font-body"
      >
        Approve
      </button>
      <button
        @click="$emit('reject', request.id)"
        class="px-3 py-1.5 bg-red-500/10 text-red-600 rounded-xl text-xs font-semibold hover:bg-red-500/20 transition-colors font-body"
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
