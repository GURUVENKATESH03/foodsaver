<template>
  <div class="min-h-screen bg-[var(--color-bg-warm)] font-body text-[var(--color-text-dark)] antialiased">
    <ErrorBoundary>
      <router-view />
      <ToasterContainer />
    </ErrorBoundary>
  </div>
</template>

<script setup>
import ErrorBoundary from '@/components/common/Errorboundary.vue'
import ToasterContainer from '@/components/common/ToasterContainer.vue'
</script>
