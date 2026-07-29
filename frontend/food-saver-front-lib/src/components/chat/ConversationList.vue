<template>
  <div class="divide-y divide-secondary-200">
    <button
      v-for="conversation in conversations"
      :key="conversation.id"
      @click="$emit('select', conversation)"
      :class="[
        'w-full text-left px-4 py-3 hover:bg-white transition-colors flex items-center space-x-3',
        activeId === conversation.id ? 'bg-white border-l-4 border-primary-500' : 'border-l-4 border-transparent'
      ]"
    >
      <div class="relative">
        <img 
          :src="conversation.participantAvatar" 
          class="w-12 h-12 rounded-full"
        />
        <span 
          v-if="conversation.unread > 0"
          class="absolute -top-1 -right-1 w-5 h-5 bg-primary-500 text-white text-xs rounded-full flex items-center justify-center"
        >
          {{ conversation.unread }}
        </span>
      </div>
      <div class="flex-1 min-w-0">
        <div class="flex items-center justify-between">
          <h4 class="text-sm font-medium text-secondary-900 truncate">{{ conversation.participantName }}</h4>
          <span class="text-xs text-secondary-400">{{ formatTime(conversation.updatedAt) }}</span>
        </div>
        <p class="text-sm text-secondary-500 truncate">{{ conversation.lastMessage }}</p>
        <p class="text-xs text-secondary-400 truncate mt-0.5">{{ conversation.donationTitle }}</p>
      </div>
    </button>
  </div>
</template>

<script setup>
import { formatDistanceToNow } from 'date-fns'

defineProps({
  conversations: {
    type: Array,
    required: true
  },
  activeId: [String, Number]
})

defineEmits(['select'])

function formatTime(date) {
  try {
    return formatDistanceToNow(new Date(date), { addSuffix: false })
  } catch {
    return ''
  }
}
</script>