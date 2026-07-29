<template>
  <div class="min-h-screen flex items-center justify-center bg-secondary-50 p-4">
    <div class="text-center">
      <div class="w-24 h-24 bg-secondary-200 rounded-full flex items-center justify-center mx-auto mb-6">
        <span class="text-4xl">🔍</span>
      </div>
      <h1 class="text-4xl font-bold text-secondary-900 mb-2">404</h1>
      <p class="text-lg text-secondary-500 mb-8">Page not found</p>
      <router-link to="/" class="btn-primary">
        Go Home
      </router-link>
    </div>
  </div>
</template>