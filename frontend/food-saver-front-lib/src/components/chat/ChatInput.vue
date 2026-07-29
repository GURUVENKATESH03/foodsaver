<template>
  <div class="px-6 py-4 border-t border-secondary-200">
    <form @submit.prevent="handleSubmit" class="flex items-end space-x-3">
      <div class="flex-1 relative">
        <textarea
          v-model="message"
          rows="1"
          class="w-full rounded-xl border-secondary-300 focus:border-primary-500 focus:ring-primary-500 resize-none py-3 px-4"
          placeholder="Type a message..."
          @keydown.enter.prevent="handleSubmit"
          @input="autoResize"
          ref="textareaRef"
        />
      </div>
      <button 
        type="submit"
        :disabled="!message.trim()"
        class="p-3 bg-primary-600 text-white rounded-xl hover:bg-primary-700 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
      >
        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8" />
        </svg>
      </button>
    </form>
  </div>
</template>

<script setup>
import { ref, nextTick } from 'vue'

const message = ref('')
const textareaRef = ref(null)

const emit = defineEmits(['send'])

function autoResize() {
  nextTick(() => {
    const el = textareaRef.value
    if (el) {
      el.style.height = 'auto'
      el.style.height = Math.min(el.scrollHeight, 120) + 'px'
    }
  })
}

function handleSubmit() {
  const content = message.value.trim()
  if (!content) return
  
  emit('send', content)
  message.value = ''
  
  nextTick(() => {
    if (textareaRef.value) {
      textareaRef.value.style.height = 'auto'
    }
  })
}
</script>