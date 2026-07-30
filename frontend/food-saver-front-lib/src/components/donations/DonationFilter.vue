<template>
  <div class="bg-white rounded-2xl border border-[var(--color-border)] p-4">
    <div class="flex flex-col md:flex-row gap-3">
      <!-- Search -->
      <div class="flex-1">
        <div class="relative">
          <svg class="absolute left-3.5 top-1/2 -translate-y-1/2 w-4 h-4 text-[var(--color-text-muted)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
          </svg>
          <input
            v-model="localFilters.search"
            type="text"
            placeholder="Search donations..."
            class="block w-full h-10 rounded-xl border border-[var(--color-border)] bg-white pl-10 pr-4 text-sm text-[var(--color-text-dark)] placeholder:text-[var(--color-text-muted)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20"
            @input="onSearchInput"
          />
        </div>
      </div>

      <!-- Category Filter -->
      <div class="w-full md:w-44">
        <select
          v-model="localFilters.category"
          class="block w-full h-10 rounded-xl border border-[var(--color-border)] bg-white px-3 text-sm text-[var(--color-text-dark)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20"
          @change="emitFilters"
        >
          <option value="">All Categories</option>
          <option v-for="cat in FOOD_CATEGORIES" :key="cat.id" :value="cat.id">
            {{ cat.icon }} {{ cat.label }}
          </option>
        </select>
      </div>

      <!-- Status Filter -->
      <div class="w-full md:w-36">
        <select
          v-model="localFilters.status"
          class="block w-full h-10 rounded-xl border border-[var(--color-border)] bg-white px-3 text-sm text-[var(--color-text-dark)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20"
          @change="emitFilters"
        >
          <option value="">All Status</option>
          <option value="AVAILABLE">Available</option>
          <option value="RESERVED">Reserved</option>
          <option value="PICKED_UP">Picked Up</option>
        </select>
      </div>

      <!-- Distance Filter -->
      <div class="w-full md:w-36">
        <select
          v-model="localFilters.distance"
          class="block w-full h-10 rounded-xl border border-[var(--color-border)] bg-white px-3 text-sm text-[var(--color-text-dark)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20"
          @change="emitFilters"
        >
          <option :value="5">Within 5 km</option>
          <option :value="10">Within 10 km</option>
          <option :value="25">Within 25 km</option>
          <option :value="50">Within 50 km</option>
          <option :value="100">Within 100 km</option>
        </select>
      </div>

      <!-- Clear -->
      <button
        @click="clearFilters"
        class="btn-outline h-10 text-xs whitespace-nowrap"
      >
        Clear
      </button>
    </div>
  </div>
</template>

<script setup>
import { reactive, watch } from 'vue'
import { FOOD_CATEGORIES } from '@/utils/constants'
import { debounce } from '@/utils/helpers'

const props = defineProps({
  modelValue: {
    type: Object,
    default: () => ({
      category: '',
      status: '',
      distance: 10,
      search: ''
    })
  }
})

const emit = defineEmits(['update:modelValue', 'apply'])

const localFilters = reactive({ ...props.modelValue })

watch(() => props.modelValue, (newVal) => {
  Object.assign(localFilters, newVal)
}, { deep: true })

const onSearchInput = debounce(() => {
  emitFilters()
}, 300)

function emitFilters() {
  emit('update:modelValue', { ...localFilters })
  emit('apply')
}

function clearFilters() {
  Object.assign(localFilters, {
    category: '',
    status: '',
    distance: 10,
    search: ''
  })
  emitFilters()
}
</script>
