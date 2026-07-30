<template>
  <div class="divide-y divide-[var(--color-border)]">
    <button
      v-for="conversation in conversations"
      :key="conversation.id"
      @click="$emit('select', conversation)"
      :class="[
        'w-full text-left px-4 py-3.5 hover:bg-white transition-colors flex items-center gap-3',
        activeId === conversation.id ? 'bg-white border-l-4 border-[var(--color-primary)]' : 'border-l-4 border-transparent'
      ]"
    >
      <div class="relative flex-shrink-0">
        <div class="w-11 h-11 rounded-full bg-[var(--color-primary)]/15 flex items-center justify-center text-base font-bold text-[var(--color-primary)]">
          {{ (conversation.participantName || '?').charAt(0) }}
        </div>
        <span
          v-if="conversation.unread > 0"
          class="absolute -top-1 -right-1 w-5 h-5 bg-[var(--color-urgent)] text-white text-[10px] font-bold rounded-full flex items-center justify-center shadow-sm"
        >
          {{ conversation.unread }}
        </span>
      </div>
      <div class="flex-1 min-w-0">
        <div class="flex items-center justify-between">
          <h4 class="text-sm font-semibold text-[var(--color-text-dark)] truncate">{{ conversation.participantName }}</h4>
          <span class="text-[11px] text-[var(--color-text-muted)] flex-shrink-0 font-mono">{{ formatTime(conversation.updatedAt) }}</span>
        </div>
        <p class="text-sm text-[var(--color-text-muted)] truncate mt-0.5">{{ conversation.lastMessage }}</p>
        <p class="text-[11px] text-[var(--color-text-muted)] truncate mt-0.5">{{ conversation.donationTitle }}</p>
      </div>
    </button>
  </div>
</template>

<script setup>
defineProps({
  conversations: {
    type: Array,
    required: true
  },
  activeId: [String, Number]
})

defineEmits(['select'])

function formatTime(date) {
  if (!date) return ''
  try {
    const diff = Date.now() - new Date(date).getTime()
    const mins = Math.floor(diff / 60000)
    if (mins < 1) return 'now'
    if (mins < 60) return `${mins}m`
    const hours = Math.floor(mins / 60)
    if (hours < 24) return `${hours}h`
    return `${Math.floor(hours / 24)}d`
  } catch {
    return ''
  }
}
</script>
