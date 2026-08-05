<template>
  <article
    class="bg-white rounded-xl border border-slate-100 shadow-sm hover:shadow-md hover:-translate-y-1 transition-all duration-200 flex flex-col"
    role="article"
    :aria-label="`Food item ${donation.title}, posted by ${donation.donorName}`"
  >
    <!-- Image area -->
    <div class="relative aspect-[16/9] overflow-hidden rounded-t-xl">
      <img
        v-if="donation.image"
        :src="donation.image"
        :alt="donation.title"
        class="w-full h-full object-cover transition-transform duration-300 group-hover:scale-105"
        loading="lazy"
      />
      <div v-else class="w-full h-full flex items-center justify-center bg-slate-100 text-4xl">
        🍲
      </div>

      <!-- Dietary tag pills (optional) -->
      <div v-if="donation.tags?.length" class="absolute top-3 left-3 flex flex-wrap gap-1.5">
        <span
          v-for="tag in donation.tags.slice(0, 2)"
          :key="tag"
          class="px-2.5 py-0.5 bg-white/90 backdrop-blur-sm rounded-full text-xs font-medium text-slate-800 border border-slate-200"
        >{{ tag }}</span>
        <span v-if="donation.tags.length > 2" class="px-2 py-0.5 bg-slate-800 text-white text-xs rounded-full">+{{ donation.tags.length - 2 }}</span>
      </div>
    </div>

    <!-- Content area -->
    <div class="p-4 flex flex-col flex-1">
      <h3 class="font-display text-base font-semibold text-slate-900 mb-1 line-clamp-1">
        {{ donation.title }}
      </h3>
      <p class="text-sm text-slate-500 mb-2 line-clamp-2">
        {{ donation.description }}
      </p>
      <div class="flex items-center text-xs text-slate-500 mb-2">
        <span class="flex items-center mr-3">
          <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 6l3 1m0 0l-3 9a5.002 5.002 0 006.001 0M6 7l3 9M6 7l6-2m6 2l3-1m-3 1l-3 9a5.002 5.002 0 006.001 0M18 7l3 9m-3-9l-6-2"/></svg>
          {{ donation.quantity }} {{ donation.unit }}
        </span>
        <span class="flex items-center">
          <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"/></svg>
          {{ donation.distance }} km
        </span>
      </div>
      <!-- Expiry countdown -->
      <div v-if="donation.pickupBy" class="mb-3">
        <span
          class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium"
          :class="isExpiringSoon(donation.pickupBy) ? 'bg-amber-100 text-amber-800' : 'bg-emerald-100 text-emerald-800'"
        >
          {{ isExpiringSoon(donation.pickupBy) ? 'Expiring Soon' : 'Available' }} – {{ donation.pickupBy }}
        </span>
      </div>
      <!-- Action button -->
      <button
        @click.stop="emitRequest"
        class="mt-auto w-full px-4 py-2 bg-teal-600 hover:bg-teal-500 text-white text-sm font-medium rounded-md shadow-sm transition-colors"
        :disabled="donation.claimed"
        aria-label="{{ donation.claimed ? 'Already claimed' : 'Claim this meal' }}"
      >
        {{ donation.claimed ? 'Claimed' : 'Claim Meal' }}
      </button>
    </div>
  </article>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  donation: {
    type: Object,
    required: true
  }
})

const emit = defineEmits(['request'])

function emitRequest() {
  if (!props.donation.claimed) {
    emit('request', props.donation.id)
  }
}

function isExpiringSoon(pickupBy) {
  // Simple heuristic: if pickupBy time is within next 6 hours
  const now = new Date()
  const pickup = new Date(pickupBy)
  const diffHours = (pickup - now) / (1000 * 60 * 60)
  return diffHours <= 6
}
</script>

<style scoped>
/* No custom CSS – everything is Tailwind utility based */
</style>
