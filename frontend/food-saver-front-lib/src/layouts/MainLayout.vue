<template>
  <div class="min-h-screen bg-secondary-50 flex flex-col">
    <AppNavbar />
    
    <main class="flex-grow pt-16">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <slot />
      </div>
    </main>
    
    <AppFooter />
  </div>
</template>

<script setup>
import AppNavbar from '@/components/Appbar.vue'
import AppFooter from '@/components/AppFooter.vue'
</script>