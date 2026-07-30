<template>
  <div class="animate-fade-in">
    <AdminDashboard v-if="isAdmin" />
    <DonorDashboard v-else-if="isDonor" />
    <ReceiverDashboard v-else-if="isReceiver" />
    <div v-else class="text-center py-16">
      <div class="w-16 h-16 rounded-2xl bg-[var(--color-bg-warm)] border border-[var(--color-border)] flex items-center justify-center mx-auto mb-4">
        <svg class="w-8 h-8 text-[var(--color-text-muted)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
        </svg>
      </div>
      <p class="text-[var(--color-text-muted)]">Select a role to get started</p>
    </div>
  </div>
</template>

<script setup>
import { useAuth } from '@/composables/useAuth'
import AdminDashboard from './AdminDashboard.vue'
import DonorDashboard from './DonorDashboard.vue'
import ReceiverDashboard from './ReceiverDashboard.vue'

const { isAdmin, isDonor, isReceiver } = useAuth()
</script>
