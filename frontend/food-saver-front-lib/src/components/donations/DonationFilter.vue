<template>
  <div class="card p-4">
    <div class="flex flex-col md:flex-row gap-4">
      <!-- Search -->
      <div class="flex-1">
        <div class="relative">
          <svg class="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-secondary-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
          </svg>
          <input
            v-model="localFilters.search"
            type="text"
            placeholder="Search donations..."
            class="input-field pl-10"
            @input="onSearchInput"
          />
        </div>
      </div>

      <!-- Category Filter -->
      <div class="w-full md:w-48">
        <select
          v-model="localFilters.category"
          class="input-field"
          @change="emitFilters"
        >
          <option value="">All Categories</option>
          <option v-for="cat in FOOD_CATEGORIES" :key="cat.id" :value="cat.id">
            {{ cat.icon }} {{ cat.label }}
          </option>
        </select>
      </div>

      <!-- Status Filter -->
      <div class="w-full md:w-40">
        <select
          v-model="localFilters.status"
          class="input-field"
          @change="emitFilters"
        >
          <option value="">All Status</option>
          <option value="AVAILABLE">Available</option>
          <option value="RESERVED">Reserved</option>
          <option value="PICKED_UP">Picked Up</option>
        </select>
      </div>

      <!-- Distance Filter -->
      <div class="w-full md:w-40">
        <select
          v-model="localFilters.distance"
          class="input-field"
          @change="emitFilters"
        >
          <option :value="5">Within 5 km</option>
          <option :value="10">Within 10 km</option>
          <option :value="25">Within 25 km</option>
          <option :value="50">Within 50 km</option>
          <option :value="100">Within 100 km</option>
        </select>
      </div>

      <!-- Clear Button -->
      <button
        @click="clearFilters"
        class="btn-secondary whitespace-nowrap"
      >
        Clear Filters
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
