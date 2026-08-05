<template>
  <div
    class="bg-white rounded-2xl border border-slate-200/90 overflow-hidden shadow-sm hover:shadow-lg hover:border-slate-300 hover:-translate-y-1 transition-all duration-300 cursor-pointer flex flex-col justify-between"
    role="button"
    :tabindex="donation.id ? 0 : -1"
    @click="goToDetail"
    @keydown.enter.prevent="goToDetail"
    @keydown.space.prevent="goToDetail"
  >
    <div>
      <!-- Image header -->
      <div class="relative h-44 overflow-hidden bg-slate-100">
        <div
          class="w-full h-full bg-gradient-to-br from-slate-100 to-slate-200 flex items-center justify-center"
        >
          <span class="text-5xl opacity-70">🍲</span>
        </div>
        <div class="absolute top-3 left-3">
          <span class="px-2.5 py-1 bg-white/95 backdrop-blur-md rounded-lg text-[11px] font-semibold text-slate-800 shadow-xs border border-slate-200/60 font-body">
            {{ categoryLabel }}
          </span>
        </div>
        <div class="absolute top-3 right-3">
          <DonationStatusBadge :status="donation.status" />
        </div>
      </div>

      <!-- Content -->
      <div class="p-5">
        <h3 class="font-display text-base font-bold text-slate-900 mb-1 line-clamp-1">{{ donation.title }}</h3>
        <p class="text-sm text-slate-500 mb-4 line-clamp-2 font-body leading-relaxed">{{ donation.description }}</p>

        <div class="flex items-center justify-between text-xs font-mono mb-4 text-slate-500">
          <div class="flex items-center gap-1.5">
            <svg class="w-3.5 h-3.5 text-teal-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 6l3 1m0 0l-3 9a5.002 5.002 0 006.001 0M6 7l3 9M6 7l6-2m6 2l3-1m-3 1l-3 9a5.002 5.002 0 006.001 0M18 7l3 9m-3-9l-6-2m0-2v2m0 16V5m0 16H9m3 0h3" />
            </svg>
            <span>{{ donation.quantity }} {{ donation.unit }}</span>
          </div>
          <div class="flex items-center gap-1.5">
            <svg class="w-3.5 h-3.5 text-teal-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
            </svg>
            <span>{{ donation.distance || '2.5' }} km</span>
          </div>
        </div>
      </div>
    </div>

    <!-- Footer actions -->
    <div class="p-5 pt-0">
      <div class="flex items-center justify-between pt-4 border-t border-slate-100">
        <div class="flex items-center gap-2">
          <div class="w-7 h-7 rounded-full bg-teal-600 text-white flex items-center justify-center text-[10px] font-bold font-mono shadow-xs">
            {{ (donation.donorName || '?').charAt(0) }}
          </div>
          <span class="text-xs font-medium text-slate-600 font-body">{{ donation.donorName || 'Anonymous' }}</span>
        </div>

        <div class="flex gap-2">
          <button
            @click.stop="goToDetail"
            class="px-3 py-1.5 bg-slate-100 hover:bg-slate-200 text-slate-700 text-xs font-semibold rounded-lg border border-slate-200 transition-all font-body"
          >
            Details
          </button>
          <button
            v-if="showRequest && donation.status === 'AVAILABLE'"
            @click.stop="$emit('request', donation.id)"
            class="px-3 py-1.5 bg-teal-600 hover:bg-teal-700 text-white text-xs font-semibold rounded-lg shadow-xs transition-all font-body"
          >
            Request
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { FOOD_CATEGORIES } from '@/utils/constants'
import DonationStatusBadge from './DonationStatusBadge.vue'

const router = useRouter()

const props = defineProps({
  donation: {
    type: Object,
    required: true
  },
  showRequest: {
    type: Boolean,
    default: false
  }
})

defineEmits(['request'])

function goToDetail() {
  if (props.donation.id) {
    router.push(`/donations/${props.donation.id}`)
  }
}

const categoryLabel = computed(() => {
  const cat = FOOD_CATEGORIES.find(c => c.id === props.donation.category)
  return cat ? `${cat.icon} ${cat.label}` : props.donation.category || 'General'
})
</script>
