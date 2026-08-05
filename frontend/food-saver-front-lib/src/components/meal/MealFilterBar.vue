<template>
  <div class="bg-white rounded-2xl border border-slate-200 p-4 shadow-sm flex flex-wrap items-center gap-3">
    <!-- Search -->
    <div class="relative flex-1 min-w-[200px] max-w-xs">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="14"
        height="14"
        viewBox="0 0 24 24"
        fill="none"
        stroke="currentColor"
        stroke-width="2"
        stroke-linecap="round"
        stroke-linejoin="round"
        class="absolute left-3.5 top-1/2 -translate-y-1/2 text-slate-400 pointer-events-none"
      >
        <circle cx="11" cy="11" r="8" />
        <path d="m21 21-4.35-4.35" />
      </svg>
      <input
        v-model="search"
        type="search"
        placeholder="Search meals..."
        class="w-full h-10 pl-9 pr-3 bg-white border border-slate-300 rounded-xl text-sm font-body text-slate-900 placeholder:text-slate-400 focus:outline-none focus:border-teal-600 focus:ring-2 focus:ring-teal-600/20 transition-all"
      />
    </div>

    <!-- Dietary filter -->
    <select
      v-model="dietary"
      class="h-10 px-3.5 bg-white border border-slate-300 rounded-xl text-sm font-body text-slate-900 focus:outline-none focus:border-teal-600 focus:ring-2 focus:ring-teal-600/20 cursor-pointer transition-all"
    >
      <option value="">All dietary</option>
      <option value="vegetarian">Vegetarian</option>
      <option value="vegan">Vegan</option>
      <option value="gluten-free">Gluten-Free</option>
      <option value="halal">Halal</option>
    </select>

    <!-- Distance -->
    <select
      v-model="distance"
      class="h-10 px-3.5 bg-white border border-slate-300 rounded-xl text-sm font-body text-slate-900 focus:outline-none focus:border-teal-600 focus:ring-2 focus:ring-teal-600/20 cursor-pointer transition-all"
    >
      <option value="">Any distance</option>
      <option value="1">Within 1 mi</option>
      <option value="3">Within 3 mi</option>
      <option value="5">Within 5 mi</option>
      <option value="10">Within 10 mi</option>
    </select>

    <!-- Status -->
    <label class="flex items-center gap-2 cursor-pointer select-none h-10 px-4 bg-slate-50 border border-slate-200 rounded-xl text-sm font-body text-slate-700 transition-all hover:bg-slate-100">
      <input
        v-model="readyNow"
        type="checkbox"
        class="w-4 h-4 rounded border-slate-300 text-teal-600 focus:ring-teal-500 cursor-pointer"
      />
      <span>Ready now</span>
    </label>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'

const emit = defineEmits(['filter'])

const search = ref('')
const dietary = ref('')
const distance = ref('')
const readyNow = ref(false)

watch([search, dietary, distance, readyNow], () => {
  emit('filter', {
    search: search.value,
    dietary: dietary.value,
    distance: distance.value,
    readyNow: readyNow.value
  })
})
</script>
