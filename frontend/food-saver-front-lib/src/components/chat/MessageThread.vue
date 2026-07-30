<template>
  <div class="space-y-3">
    <div
      v-for="message in messages"
      :key="message.id"
      :class="[
        'flex',
        message.senderId === 'me' ? 'justify-end' : 'justify-start'
      ]"
    >
      <div :class="[
        'max-w-xs lg:max-w-md px-4 py-2.5 rounded-2xl',
        message.senderId === 'me'
          ? 'bg-[var(--color-primary)] text-[var(--color-text-dark)] rounded-br-[4px]'
          : 'bg-[var(--color-bg-warm)] text-[var(--color-text-dark)] rounded-bl-[4px] border border-[var(--color-border)]'
      ]">
        <p class="text-sm">{{ message.content }}</p>
        <p :class="[
          'text-[11px] mt-1 font-mono',
          message.senderId === 'me' ? 'text-[var(--color-text-dark)]/60' : 'text-[var(--color-text-muted)]'
        ]">
          {{ formatTime(message.timestamp) }}
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  messages: {
    type: Array,
    required: true
  }
})

function formatTime(timestamp) {
  if (!timestamp) return ''
  try {
    const d = new Date(timestamp)
    const h = d.getHours()
    const m = d.getMinutes().toString().padStart(2, '0')
    const ampm = h >= 12 ? 'pm' : 'am'
    return `${h % 12 || 12}:${m} ${ampm}`
  } catch {
    return ''
  }
}
</script>
