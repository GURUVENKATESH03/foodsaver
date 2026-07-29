<template>
  <span 
    :class="[
      'inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium capitalize',
      roleClasses[role] || 'bg-secondary-100 text-secondary-800'
    ]"
  >
    {{ role?.toLowerCase() }}
  </span>
</template>

<script setup>
defineProps({
  role: {
    type: String,
    required: true,
    validator: (value) => ['ADMIN', 'DONOR', 'RECEIVER'].includes(value)
  }
})

const roleClasses = {
  ADMIN: 'bg-purple-100 text-purple-800',
  DONOR: 'bg-primary-100 text-primary-800',
  RECEIVER: 'bg-blue-100 text-blue-800'
}
</script>