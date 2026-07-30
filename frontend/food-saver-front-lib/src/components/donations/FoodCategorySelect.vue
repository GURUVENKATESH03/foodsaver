<template>
  <div class="grid grid-cols-2 sm:grid-cols-4 gap-3">
    <button
      v-for="category in FOOD_CATEGORIES"
      :key="category.id"
      type="button"
      @click="selectCategory(category.id)"
      :class="[
        'flex flex-col items-center gap-1.5 p-3.5 border-2 rounded-xl transition-all duration-200 cursor-pointer',
        modelValue === category.id
          ? 'border-[var(--color-primary)] bg-[var(--color-primary)]/8'
          : 'border-[var(--color-border)] bg-white hover:border-[var(--color-primary)]/40'
      ]"
    >
      <span class="text-2xl leading-none">{{ category.icon }}</span>
      <span
        class="text-[11px] font-semibold text-center leading-tight"
        :class="modelValue === category.id ? 'text-[var(--color-text-dark)]' : 'text-[var(--color-text-muted)]'"
      >
        {{ category.label }}
      </span>
    </button>
  </div>
</template>

<script setup>
import { FOOD_CATEGORIES } from '@/utils/constants'

const props = defineProps({
  modelValue: String
})

const emit = defineEmits(['update:modelValue'])

function selectCategory(id) {
  emit('update:modelValue', id)
}
</script>
