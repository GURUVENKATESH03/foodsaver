<template>
  <div class="px-6 py-4 border-t border-[var(--color-border)] bg-white">
    <form @submit.prevent="handleSubmit" class="flex items-end gap-3">
      <div class="flex-1 relative">
        <textarea
          v-model="message"
          rows="1"
          class="block w-full rounded-xl border border-[var(--color-border)] bg-[var(--color-bg-warm)] px-4 py-3 text-sm text-[var(--color-text-dark)] placeholder:text-[var(--color-text-muted)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20 resize-none"
          placeholder="Type a message..."
          @keydown.enter.prevent="handleSubmit"
          @input="autoResize"
          ref="textareaRef"
        />
      </div>
      <button
        type="submit"
        :disabled="!message.trim()"
        class="p-3 bg-[var(--color-primary)] text-[var(--color-text-dark)] rounded-xl hover:bg-[var(--color-primary-hover)] disabled:opacity-40 disabled:cursor-not-allowed transition-all flex-shrink-0"
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
