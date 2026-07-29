<template>
  <div class="grid grid-cols-2 sm:grid-cols-4 gap-3">
    <button
      v-for="category in FOOD_CATEGORIES"
      :key="category.id"
      type="button"
      @click="selectCategory(category.id)"
      :class="[
        'flex flex-col items-center p-3 rounded-xl border-2 transition-all duration-200',
        modelValue === category.id
          ? 'border-primary-500 bg-primary-50 text-primary-700'
          : 'border-secondary-200 hover:border-primary-300 text-secondary-600'
      ]"
    >
      <span class="text-2xl mb-1">{{ category.icon }}</span>
      <span class="text-xs font-medium text-center">{{ category.label }}</span>
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