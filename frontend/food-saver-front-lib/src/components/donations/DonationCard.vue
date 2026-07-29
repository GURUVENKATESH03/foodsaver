<template>
  <div class="card overflow-hidden group hover:shadow-lg transition-all duration-300">
    <!-- Image -->
    <div class="relative h-48 overflow-hidden">
      <img 
        :src="donation.image || '/placeholder-food.jpg'" 
        :alt="donation.title"
        class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
      />
      <div class="absolute top-3 left-3">
        <span class="px-2 py-1 bg-white/90 backdrop-blur-sm rounded-lg text-xs font-medium text-secondary-700">
          {{ donation.category }}
        </span>
      </div>
      <div class="absolute top-3 right-3">
        <DonationStatusBadge :status="donation.status" />
      </div>
      <div v-if="donation.expiryDate" class="absolute bottom-3 left-3">
        <span :class="[
          'px-2 py-1 rounded-lg text-xs font-medium backdrop-blur-sm',
          expiryStatus.color === 'red' ? 'bg-red-500/90 text-white' :
          expiryStatus.color === 'orange' ? 'bg-orange-500/90 text-white' :
          'bg-white/90 text-secondary-700'
        ]">
          {{ expiryStatus.label }}
        </span>
      </div>
    </div>
    
    <!-- Content -->
    <div class="p-5">
      <h3 class="text-lg font-semibold text-secondary-900 mb-1 line-clamp-1">{{ donation.title }}</h3>
      <p class="text-sm text-secondary-500 mb-3 line-clamp-2">{{ donation.description }}</p>
      
      <div class="flex items-center justify-between text-sm text-secondary-600 mb-4">
        <div class="flex items-center">
          <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 6l3 1m0 0l-3 9a5.002 5.002 0 006.001 0M6 7l3 9M6 7l6-2m6 2l3-1m-3 1l-3 9a5.002 5.002 0 006.001 0M18 7l3 9m-3-9l-6-2m0-2v2m0 16V5m0 16H9m3 0h3" />
          </svg>
          {{ donation.quantity }} {{ donation.unit }}
        </div>
        <div class="flex items-center">
          <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
          </svg>
          {{ donation.distance || '2.5' }} km
        </div>
      </div>
      
      <div class="flex items-center justify-between pt-4 border-t border-secondary-100">
        <div class="flex items-center">
          <img 
            :src="donation.donorAvatar || generateAvatarUrl(donation.donorName)" 
            class="w-8 h-8 rounded-full mr-2"
          />
          <span class="text-sm text-secondary-600">{{ donation.donorName }}</span>
        </div>
        
        <div class="flex space-x-2">
          <router-link 
            :to="`/donations/${donation.id}`"
            class="btn-secondary text-xs px-3 py-1.5"
          >
            Details
          </router-link>
          <button 
            v-if="showRequest && donation.status === 'AVAILABLE'"
            @click="$emit('request', donation.id)"
            class="btn-primary text-xs px-3 py-1.5"
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
import DonationStatusBadge from './DonationStatusBadge.vue'
import { getExpiryStatus, generateAvatarUrl } from '@/utils/helpers'

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

const expiryStatus = computed(() => getExpiryStatus(props.donation.expiryDate))
</script>