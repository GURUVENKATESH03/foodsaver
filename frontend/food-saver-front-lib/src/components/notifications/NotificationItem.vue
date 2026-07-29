<template>
  <div 
    :class="[
      'px-4 py-3 cursor-pointer transition-colors hover:bg-secondary-50 border-b border-secondary-100 last:border-0',
      !notification.read ? 'bg-primary-50/50' : ''
    ]"
    @click="$emit('click')"
  >
    <div class="flex items-start space-x-3">
      <div :class="[
        'w-8 h-8 rounded-full flex items-center justify-center flex-shrink-0',
        iconBgClass
      ]">
        <span class="text-sm">{{ icon }}</span>
      </div>
      <div class="flex-1 min-w-0">
        <p class="text-sm text-secondary-900">
          <span class="font-medium">{{ notification.title }}</span>
          {{ notification.message }}
        </p>
        <p class="text-xs text-secondary-500 mt-1">{{ timeAgo }}</p>
      </div>
      <div v-if="!notification.read" class="w-2 h-2 bg-primary-500 rounded-full flex-shrink-0 mt-1.5" />
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { formatDistanceToNow } from 'date-fns'

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
    donation: 'bg-green-100',
    request: 'bg-blue-100',
    message: 'bg-purple-100',
    system: 'bg-yellow-100',
    pickup: 'bg-orange-100'
  }
  return classes[props.notification.type] || 'bg-secondary-100'
})

const timeAgo = computed(() => {
  try {
    return formatDistanceToNow(new Date(props.notification.timestamp), { addSuffix: true })
  } catch {
    return 'Recently'
  }
})
</script>