<template>
  <div class="space-y-6">
    <!-- Header -->
    <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
      <div>
        <h1 class="font-display text-2xl font-bold text-[var(--color-text-dark)]">Available Donations</h1>
        <p class="text-sm text-[var(--color-text-muted)] mt-1">Browse and request food donations near you</p>
      </div>
      <router-link
        v-if="isDonor"
        to="/donations/create"
        class="btn-primary text-sm inline-flex items-center"
      >
        <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
        </svg>
        Share Food
      </router-link>
    </div>

    <!-- Filters -->
    <DonationFilter v-model="filters" @apply="applyFilters" />

    <!-- Grid -->
    <div v-if="loading" class="flex justify-center py-16">
      <div class="w-8 h-8 border-2 border-[var(--color-primary)] border-t-transparent rounded-full animate-spin" />
    </div>

    <!-- Empty state -->
    <div v-else-if="donations.length === 0" class="bg-white rounded-2xl border border-[var(--color-border)] p-12 text-center">
      <div class="w-16 h-16 rounded-2xl bg-[var(--color-bg-warm)] flex items-center justify-center mx-auto mb-4">
        <svg class="w-8 h-8 text-[var(--color-text-muted)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4" />
        </svg>
      </div>
      <h3 class="font-display text-lg font-bold text-[var(--color-text-dark)] mb-1">Nothing posted near you yet</h3>
      <p class="text-sm text-[var(--color-text-muted)]">Be the first to share a meal in your neighborhood.</p>
    </div>

    <!-- Donations grid -->
    <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
      <DonationCard
        v-for="donation in donations"
        :key="donation.id"
        :donation="donation"
        :show-request="isReceiver || !isAuthenticated"
        @request="handleRequest"
      />
    </div>

    <!-- Pagination -->
    <div v-if="pagination.totalPages > 1" class="flex justify-center mt-8">
      <div class="flex items-center gap-2">
        <button
          @click="changePage(pagination.page - 1)"
          :disabled="pagination.page === 1"
          class="p-2 rounded-xl border border-[var(--color-border)] text-[var(--color-text-muted)] hover:text-[var(--color-text-dark)] hover:bg-[var(--color-bg-warm)] disabled:opacity-40 disabled:cursor-not-allowed transition-all"
        >
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
          </svg>
        </button>
        <span class="text-sm text-[var(--color-text-muted)] px-2">
          {{ pagination.page }} / {{ pagination.totalPages }}
        </span>
        <button
          @click="changePage(pagination.page + 1)"
          :disabled="pagination.page === pagination.totalPages"
          class="p-2 rounded-xl border border-[var(--color-border)] text-[var(--color-text-muted)] hover:text-[var(--color-text-dark)] hover:bg-[var(--color-bg-warm)] disabled:opacity-40 disabled:cursor-not-allowed transition-all"
        >
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useDonation } from '@/composables/useDonation'
import { useAuth } from '@/composables/useAuth'
import DonationCard from '@/components/donations/DonationCard.vue'
import DonationFilter from '@/components/donations/DonationFilter.vue'

const { donations, loading, pagination, loadDonations, setFilters } = useDonation()
const { isDonor, isReceiver, isAuthenticated } = useAuth()

const filters = ref({
  category: '',
  status: 'AVAILABLE',
  search: ''
})

onMounted(() => {
  loadDonations()
})

function applyFilters() {
  setFilters(filters.value)
  loadDonations()
}

function changePage(page) {
  setFilters({ page })
  loadDonations()
}

function handleRequest(donationId) {
  console.log('Request donation:', donationId)
}
</script>
