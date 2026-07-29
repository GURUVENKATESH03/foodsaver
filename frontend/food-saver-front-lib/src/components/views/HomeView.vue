<template>
  <div>
    <!-- Hero Section -->
    <section class="relative bg-primary-700 text-white overflow-hidden">
      <div class="absolute inset-0 opacity-10">
        <svg class="w-full h-full" viewBox="0 0 100 100" preserveAspectRatio="none">
          <path d="M0 100 C 20 0 50 0 100 100 Z" fill="white" />
        </svg>
      </div>
      <div class="relative max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-24 lg:py-32">
        <div class="max-w-2xl">
          <h1 class="text-4xl lg:text-6xl font-bold tracking-tight mb-6">
            Share Food.<br />
            <span class="text-primary-300">Reduce Waste.</span><br />
            Feed Communities.
          </h1>
          <p class="text-lg text-primary-100 mb-8 max-w-lg">
            Meal Share connects food donors with NGOs and individuals to rescue surplus food 
            and deliver it to those in need.
          </p>
          <div class="flex flex-wrap gap-4">
            <router-link to="/auth/register" class="inline-flex items-center px-6 py-3 bg-white text-primary-700 font-semibold rounded-lg hover:bg-primary-50 transition-colors">
              Get Started
              <svg class="w-5 h-5 ml-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 8l4 4m0 0l-4 4m4-4H3" />
              </svg>
            </router-link>
            <router-link to="/donations" class="inline-flex items-center px-6 py-3 border-2 border-white text-white font-semibold rounded-lg hover:bg-white/10 transition-colors">
              Browse Food
            </router-link>
          </div>
        </div>
      </div>
    </section>
    
    <!-- Stats Section -->
    <section class="py-16 bg-white">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="grid grid-cols-2 lg:grid-cols-4 gap-8 text-center">
          <div>
            <div class="text-4xl font-bold text-primary-600 mb-2">12.5K</div>
            <div class="text-secondary-600">kg Food Saved</div>
          </div>
          <div>
            <div class="text-4xl font-bold text-primary-600 mb-2">3,800+</div>
            <div class="text-secondary-600">Donations Made</div>
          </div>
          <div>
            <div class="text-4xl font-bold text-primary-600 mb-2">500+</div>
            <div class="text-secondary-600">Active Donors</div>
          </div>
          <div>
            <div class="text-4xl font-bold text-primary-600 mb-2">200+</div>
            <div class="text-secondary-600">NGO Partners</div>
          </div>
        </div>
      </div>
    </section>
    
    <!-- How It Works -->
    <section class="py-16 bg-secondary-50">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-12">
          <h2 class="text-3xl font-bold text-secondary-900 mb-4">How It Works</h2>
          <p class="text-secondary-500 max-w-2xl mx-auto">Join our community in three simple steps to start making a difference.</p>
        </div>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div v-for="(step, index) in steps" :key="index" class="card p-8 text-center">
            <div class="w-16 h-16 bg-primary-100 rounded-2xl flex items-center justify-center mx-auto mb-6 text-3xl">
              {{ step.icon }}
            </div>
            <h3 class="text-xl font-semibold text-secondary-900 mb-3">{{ step.title }}</h3>
            <p class="text-secondary-500">{{ step.description }}</p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
const steps = [
  {
    icon: '📦',
    title: 'List Surplus Food',
    description: 'Restaurants, stores, and individuals can easily list excess food with details and pickup information.'
  },
  {
    icon: '🔍',
    title: 'Find & Request',
    description: 'NGOs and individuals browse available donations nearby and request what they need.'
  },
  {
    icon: '🚚',
    title: 'Pickup & Share',
    description: 'Coordinate pickup, rescue the food, and deliver it to those who need it most.'
  }
]
</script>