<template>
  <div class="bg-white rounded-2xl border border-slate-200 p-4 shadow-sm">
    <div class="flex flex-col md:flex-row gap-3">
      <!-- Search Input -->
      <div class="flex-1">
        <div class="relative">
          <svg class="absolute left-3.5 top-1/2 -translate-y-1/2 w-4 h-4 text-slate-400 pointer-events-none" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
          </svg>
          <input
            v-model="localFilters.search"
            type="text"
            placeholder="Search donations..."
            class="w-full h-11 pl-10 pr-4 bg-white border border-slate-300 rounded-xl text-sm font-body text-slate-900 placeholder:text-slate-400 focus:outline-none focus:border-teal-600 focus:ring-2 focus:ring-teal-600/20 transition-all"
            @input="onSearchInput"
          />
        </div>
      </div>

      <!-- Category Filter -->
      <div class="w-full md:w-44">
        <select
          v-model="localFilters.category"
          class="w-full h-11 px-3.5 bg-white border border-slate-300 rounded-xl text-sm font-body text-slate-900 focus:outline-none focus:border-teal-600 focus:ring-2 focus:ring-teal-600/20 transition-all cursor-pointer"
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
          class="w-full h-11 px-3.5 bg-white border border-slate-300 rounded-xl text-sm font-body text-slate-900 focus:outline-none focus:border-teal-600 focus:ring-2 focus:ring-teal-600/20 transition-all cursor-pointer"
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
          class="w-full h-11 px-3.5 bg-white border border-slate-300 rounded-xl text-sm font-body text-slate-900 focus:outline-none focus:border-teal-600 focus:ring-2 focus:ring-teal-600/20 transition-all cursor-pointer"
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
        class="h-11 px-5 bg-slate-100 hover:bg-slate-200 text-slate-700 font-semibold text-xs rounded-xl border border-slate-200 transition-all whitespace-nowrap"
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
