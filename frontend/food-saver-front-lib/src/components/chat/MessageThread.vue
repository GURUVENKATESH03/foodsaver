<template>
  <div class="space-y-4">
    <div
      v-for="message in messages"
      :key="message.id"
      :class="[
        'flex',
        message.senderId === 'me' ? 'justify-end' : 'justify-start'
      ]"
    >
      <div :class="[
        'max-w-xs lg:max-w-md px-4 py-2 rounded-2xl',
        message.senderId === 'me' 
          ? 'bg-primary-600 text-white rounded-br-none'
          : 'bg-secondary-100 text-secondary-900 rounded-bl-none'
      ]">
        <p class="text-sm">{{ message.content }}</p>
        <p :class="[
          'text-xs mt-1',
          message.senderId === 'me' ? 'text-primary-200' : 'text-secondary-500'
        ]">
          {{ formatTime(message.timestamp) }}
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { format } from 'date-fns'

defineProps({
  messages: {
    type: Array,
    required: true
  }
})

function formatTime(timestamp) {
  try {
    return format(new Date(timestamp), 'h:mm a')
  } catch {
    return ''
  }
}
</script>