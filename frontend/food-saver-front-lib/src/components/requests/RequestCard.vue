<template>
  <div class="flex items-center justify-between p-4 bg-secondary-50 rounded-lg">
    <div class="flex items-center space-x-3">
      <img 
        :src="request.requesterAvatar || generateAvatarUrl(request.requesterName)" 
        class="w-10 h-10 rounded-full"
      />
      <div>
        <p class="text-sm font-medium text-secondary-900">{{ request.requesterName }}</p>
        <p class="text-xs text-secondary-500">{{ request.message || 'No message' }}</p>
        <p class="text-xs text-secondary-400 mt-0.5">{{ formatDate(request.createdAt) }}</p>
      </div>
    </div>
    
    <div class="flex items-center space-x-2">
      <button 
        @click="$emit('approve', request.id)"
        class="px-3 py-1.5 bg-green-100 text-green-700 rounded-lg text-sm font-medium hover:bg-green-200 transition-colors"
      >
        Approve
      </button>
      <button 
        @click="$emit('reject', request.id)"
        class="px-3 py-1.5 bg-red-100 text-red-700 rounded-lg text-sm font-medium hover:bg-red-200 transition-colors"
      >
        Reject
      </button>
    </div>
  </div>
</template>

<script setup>
import { generateAvatarUrl, formatDate } from '@/utils/helpers'

defineProps({
  request: {
    type: Object,
    required: true
  }
})

defineEmits(['approve', 'reject'])
</script>