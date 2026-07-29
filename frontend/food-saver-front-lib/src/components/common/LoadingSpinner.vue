<template>
  <div v-if="fullScreen" class="fixed inset-0 flex items-center justify-center bg-secondary-50/80 z-50">
    <div class="flex flex-col items-center">
      <svg 
        class="animate-spin text-primary-600 h-10 w-10" 
        xmlns="http://www.w3.org/2000/svg" 
        fill="none" 
        viewBox="0 0 24 24"
      >
        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4" />
        <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
      </svg>
      <span v-if="text" class="mt-3 text-sm text-secondary-500">{{ text }}</span>
    </div>
  </div>
  <div v-else :class="['inline-flex items-center justify-center', sizeClasses[size]]">
    <svg 
      class="animate-spin text-primary-600" 
      :class="iconSizeClasses[size]"
      xmlns="http://www.w3.org/2000/svg" 
      fill="none" 
      viewBox="0 0 24 24"
    >
      <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4" />
      <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
    </svg>
    <span v-if="text" class="ml-2 text-sm text-secondary-500">{{ text }}</span>
  </div>
</template>

<script setup>
const props = defineProps({
  size: {
    type: String,
    default: 'md',
    validator: (value) => ['sm', 'md', 'lg'].includes(value)
  },
  text: {
    type: String,
    default: ''
  },
  fullScreen: {
    type: Boolean,
    default: false
  }
})

const sizeClasses = {
  sm: 'h-5 w-5',
  md: 'h-8 w-8',
  lg: 'h-12 w-12'
}

const iconSizeClasses = {
  sm: 'h-4 w-4',
  md: 'h-6 w-6',
  lg: 'h-8 w-8'
}
</script>
