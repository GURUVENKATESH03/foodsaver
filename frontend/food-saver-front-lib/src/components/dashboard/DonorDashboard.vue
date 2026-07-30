<template>
  <div class="space-y-8">
    <!-- Header -->
    <div>
      <h1 class="font-display text-2xl font-bold text-[var(--color-text-dark)]">Donor Dashboard</h1>
      <p class="text-sm text-[var(--color-text-muted)] mt-1">Manage your donations and track your impact</p>
    </div>

    <!-- Quick Actions -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-5">
      <router-link to="/donations/create" class="bg-white rounded-2xl border border-[var(--color-border)] p-6 hover:shadow-[var(--shadow-card-hover)] hover:-translate-y-0.5 transition-all duration-200 group">
        <div class="w-12 h-12 rounded-xl bg-[var(--color-primary)]/12 flex items-center justify-center mb-4 group-hover:bg-[var(--color-primary)]/20 transition-colors">
          <svg class="w-6 h-6 text-[var(--color-primary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
          </svg>
        </div>
        <h3 class="font-display text-lg font-bold text-[var(--color-text-dark)]">Share a Meal</h3>
        <p class="text-sm text-[var(--color-text-muted)] mt-1">List available food items</p>
      </router-link>

      <div class="bg-white rounded-2xl border border-[var(--color-border)] p-6">
        <div class="w-12 h-12 rounded-xl bg-[var(--color-secondary)]/12 flex items-center justify-center mb-4">
          <svg class="w-6 h-6 text-[var(--color-secondary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
          </svg>
        </div>
        <p class="font-display text-3xl font-bold text-[var(--color-text-dark)]">{{ stats.activeDonations }}</p>
        <p class="text-sm text-[var(--color-text-muted)] mt-1">Active Donations</p>
      </div>

      <div class="bg-white rounded-2xl border border-[var(--color-border)] p-6">
        <div class="w-12 h-12 rounded-xl bg-[var(--color-secondary)]/12 flex items-center justify-center mb-4">
          <svg class="w-6 h-6 text-[var(--color-secondary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3.055 11H5a2 2 0 012 2v1a2 2 0 002 2 2 2 0 012 2v2.945M8 3.935V5.5A2.5 2.5 0 0010.5 8h.5a2 2 0 012 2 2 2 0 104 0 2 2 0 012-2h1.064M15 20.488V18a2 2 0 012-2h3.064M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
        </div>
        <p class="font-display text-3xl font-bold text-[var(--color-text-dark)]">{{ stats.totalImpact }} kg</p>
        <p class="text-sm text-[var(--color-text-muted)] mt-1">Total Food Shared</p>
      </div>
    </div>

    <!-- My Donations -->
    <div class="bg-white rounded-2xl border border-[var(--color-border)] overflow-hidden">
      <div class="px-6 py-4 border-b border-[var(--color-border)] flex items-center justify-between">
        <h3 class="font-display text-lg font-bold text-[var(--color-text-dark)]">My Donations</h3>
        <router-link to="/donations" class="text-sm font-medium text-[var(--color-secondary)] hover:text-[var(--color-secondary-hover)] transition-colors">View all</router-link>
      </div>

      <div v-if="loading" class="p-8 text-center">
        <div class="w-8 h-8 border-2 border-[var(--color-primary)] border-t-transparent rounded-full animate-spin mx-auto" />
      </div>

      <div v-else-if="donations.length === 0" class="p-12 text-center">
        <div class="w-14 h-14 rounded-full bg-[var(--color-bg-warm)] flex items-center justify-center mx-auto mb-3">
          <svg class="w-7 h-7 text-[var(--color-text-muted)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
          </svg>
        </div>
        <p class="text-sm text-[var(--color-text-muted)]">No donations yet. Share your first meal!</p>
      </div>

      <div v-else class="divide-y divide-[var(--color-border)]">
        <div
          v-for="donation in donations"
          :key="donation.id"
          class="px-6 py-4 flex items-center justify-between hover:bg-[var(--color-bg-warm)]/50 transition-colors"
        >
          <div class="flex items-center gap-4">
            <div class="w-14 h-14 rounded-xl bg-[var(--color-bg-warm)] overflow-hidden flex-shrink-0">
              <div class="w-full h-full bg-gradient-to-br from-[var(--color-primary)]/20 to-[var(--color-secondary)]/20 flex items-center justify-center text-xl">
                🍲
              </div>
            </div>
            <div>
              <h4 class="text-sm font-semibold text-[var(--color-text-dark)]">{{ donation.title }}</h4>
              <p class="text-xs text-[var(--color-text-muted)] mt-0.5">{{ donation.quantity }} {{ donation.unit }}</p>
              <DonationStatusBadge :status="donation.status" class="mt-1" />
            </div>
          </div>
          <div class="flex items-center gap-3">
            <span class="text-xs text-[var(--color-text-muted)]">{{ donation.requestsCount || 0 }} requests</span>
            <router-link
              :to="`/donations/${donation.id}`"
              class="p-2 rounded-xl text-[var(--color-text-muted)] hover:text-[var(--color-primary)] hover:bg-[var(--color-primary)]/8 transition-all"
            >
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
              </svg>
            </router-link>
          </div>
        </div>
      </div>
    </div>

    <!-- Pending Requests -->
    <div class="bg-white rounded-2xl border border-[var(--color-border)] p-6">
      <h3 class="font-display text-lg font-bold text-[var(--color-text-dark)] mb-5">Pending Requests</h3>
      <div v-if="pendingRequests.length === 0" class="text-sm text-[var(--color-text-muted)] text-center py-6">
        No pending requests
      </div>
      <div v-else class="space-y-3">
        <RequestCard
          v-for="request in pendingRequests"
          :key="request.id"
          :request="request"
          @approve="handleApprove"
          @reject="handleReject"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useDonation } from '@/composables/useDonation'
import DonationStatusBadge from '@/components/donations/DonationStatusBadge.vue'
import RequestCard from '@/components/requests/RequestCard.vue'

const { donations, loadDonations } = useDonation()

const stats = ref({
  activeDonations: 5,
  totalImpact: 1250
})

const pendingRequests = ref([])
const loading = ref(true)

onMounted(async () => {
  try {
    await loadDonations()
  } finally {
    loading.value = false
  }

  // Mock pending requests
  pendingRequests.value = [
    {
      id: 1,
      requesterName: 'Community Center',
      requesterAvatar: '',
      message: 'We could really use this for our meal program',
      createdAt: '2024-01-15T10:30:00Z'
    }
  ]
})

function handleApprove(requestId) {
  pendingRequests.value = pendingRequests.value.filter(r => r.id !== requestId)
}

function handleReject(requestId) {
  pendingRequests.value = pendingRequests.value.filter(r => r.id !== requestId)
}
</script>
