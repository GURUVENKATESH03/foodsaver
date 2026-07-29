<template>
  <div class="flex items-center justify-center" :class="[sizeClass, containerClass]">
    <svg 
      class="animate-spin text-primary-600" 
      :class="spinnerSize"
      xmlns="http://www.w3.org/2000/svg" 
      fill="none" 
      viewBox="0 0 24 24"
    >
      <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
      <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
    </svg>
    <span v-if="text" class="ml-2 text-sm text-secondary-600">{{ text }}</span>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  size: { type: String, default: 'md' },
  text: { type: String, default: '' },
  fullScreen: Boolean
})

const sizeClass = computed(() => props.fullScreen ? 'h-screen w-full' : '')
const containerClass = computed(() => !props.fullScreen ? 'p-4' : '')

const spinnerSize = computed(() => ({
  sm: 'w-4 h-4',
  md: 'w-8 h-8',
  lg: 'w-12 h-12',
  xl: 'w-16 h-16'
}[props.size]))
</script>