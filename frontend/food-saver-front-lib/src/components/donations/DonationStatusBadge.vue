<template>
  <span :class="[
    'inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium capitalize',
    statusClasses[status] || 'bg-secondary-100 text-secondary-800'
  ]">
    <span :class="['w-1.5 h-1.5 rounded-full mr-1.5', dotClasses[status] || 'bg-secondary-400']" />
    {{ formatStatus(status) }}
  </span>
</template>

<script setup>
defineProps({
  status: {
    type: String,
    required: true
  }
})

const statusClasses = {
  AVAILABLE: 'bg-green-100 text-green-800',
  RESERVED: 'bg-yellow-100 text-yellow-800',
  PICKED_UP: 'bg-blue-100 text-blue-800',
  EXPIRED: 'bg-red-100 text-red-800',
  CANCELLED: 'bg-secondary-100 text-secondary-800',
  PENDING: 'bg-yellow-100 text-yellow-800',
  APPROVED: 'bg-green-100 text-green-800',
  REJECTED: 'bg-red-100 text-red-800',
  COMPLETED: 'bg-green-100 text-green-800'
}

const dotClasses = {
  AVAILABLE: 'bg-green-500',
  RESERVED: 'bg-yellow-500',
  PICKED_UP: 'bg-blue-500',
  EXPIRED: 'bg-red-500',
  CANCELLED: 'bg-secondary-400',
  PENDING: 'bg-yellow-500',
  APPROVED: 'bg-green-500',
  REJECTED: 'bg-red-500',
  COMPLETED: 'bg-green-500'
}

function formatStatus(status) {
  return status?.toLowerCase().replace(/_/g, ' ') || 'unknown'
}
</script>