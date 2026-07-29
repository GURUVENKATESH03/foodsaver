<template>
  <div class="animate-fade-in">
    <!-- Dynamic Dashboard Based on Role -->
    <AdminDashboard v-if="isAdmin" />
    <DonorDashboard v-else-if="isDonor" />
    <ReceiverDashboard v-else-if="isReceiver" />
    <div v-else class="text-center py-12">
      <LoadingSpinner size="lg" />
    </div>
  </div>
</template>

<script setup>
import { useAuth } from '@/composables/useAuth'
import AdminDashboard from './AdminDashboard.vue'
import DonorDashboard from './DonorDashboard.vue'
import ReceiverDashboard from './ReceiverDashboard.vue'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'

const { isAdmin, isDonor, isReceiver } = useAuth()
</script>