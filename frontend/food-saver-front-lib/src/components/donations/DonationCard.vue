<template>
  <div
    class="bg-white rounded-2xl border border-[var(--color-border)] overflow-hidden group hover:shadow-[var(--shadow-card-hover)] hover:-translate-y-0.5 transition-all duration-300 cursor-pointer"
    role="button"
    :tabindex="donation.id ? 0 : -1"
    @click="goToDetail"
    @keydown.enter.prevent="goToDetail"
    @keydown.space.prevent="goToDetail"
  >
    <!-- Image -->
    <div class="relative h-44 overflow-hidden">
      <div
        class="w-full h-full bg-gradient-to-br from-[var(--color-primary)]/15 to-[var(--color-secondary)]/15 flex items-center justify-center"
      >
        <span class="text-5xl opacity-40">🍲</span>
      </div>
      <div class="absolute top-3 left-3">
        <span class="px-2.5 py-1 bg-white/90 backdrop-blur-sm rounded-lg text-[11px] font-semibold text-[var(--color-text-dark)] shadow-xs">
          {{ categoryLabel }}
        </span>
      </div>
      <div class="absolute top-3 right-3">
        <DonationStatusBadge :status="donation.status" />
      </div>
      <div v-if="donation.expiryDate" class="absolute bottom-3 left-3">
        <span
          :class="[
            'px-2.5 py-1 rounded-lg text-[11px] font-semibold backdrop-blur-sm',
            expiryClass
          ]"
        >
          {{ expiryLabel }}
        </span>
      </div>
    </div>

    <!-- Content -->
    <div class="p-5">
      <h3 class="font-display text-base font-bold text-[var(--color-text-dark)] mb-1 line-clamp-1">{{ donation.title }}</h3>
      <p class="text-sm text-[var(--color-text-muted)] mb-3 line-clamp-2">{{ donation.description }}</p>

      <div class="flex items-center justify-between text-sm mb-4">
        <div class="flex items-center gap-1.5 text-[var(--color-text-muted)] font-mono text-xs">
          <svg class="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 6l3 1m0 0l-3 9a5.002 5.002 0 006.001 0M6 7l3 9M6 7l6-2m6 2l3-1m-3 1l-3 9a5.002 5.002 0 006.001 0M18 7l3 9m-3-9l-6-2m0-2v2m0 16V5m0 16H9m3 0h3" />
          </svg>
          {{ donation.quantity }} {{ donation.unit }}
        </div>
        <div class="flex items-center gap-1.5 text-[var(--color-text-muted)] font-mono text-xs">
          <svg class="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
          </svg>
          {{ donation.distance || '2.5' }} km
        </div>
      </div>

      <div class="flex items-center justify-between pt-4 border-t border-[var(--color-border)]">
        <div class="flex items-center gap-2">
          <div class="w-7 h-7 rounded-full bg-[var(--color-primary)]/15 flex items-center justify-center text-[10px] font-bold text-[var(--color-primary)]">
            {{ (donation.donorName || '?').charAt(0) }}
          </div>
          <span class="text-xs text-[var(--color-text-muted)]">{{ donation.donorName || 'Anonymous' }}</span>
        </div>

        <div class="flex gap-2">
          <button
            @click.stop="goToDetail"
            class="btn-outline text-[11px] px-3 py-1.5"
          >
            Details
          </button>
          <button
            v-if="showRequest && donation.status === 'AVAILABLE'"
            @click.stop="$emit('request', donation.id)"
            class="btn-primary text-[11px] px-3 py-1.5"
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

const expiryLabel = computed(() => {
  if (!props.donation.expiryDate) return ''
  // Simple label based on date
  return 'Available'
})

const expiryClass = computed(() => {
  return 'bg-white/90 text-[var(--color-text-dark)]'
})
</script>
