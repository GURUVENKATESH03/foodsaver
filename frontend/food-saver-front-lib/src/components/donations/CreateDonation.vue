<template>
  <div class="max-w-2xl mx-auto">
    <!-- Header -->
    <div class="mb-6">
      <router-link to="/donations" class="text-sm font-medium text-[var(--color-secondary)] hover:text-[var(--color-secondary-hover)] flex items-center gap-1.5 mb-3 transition-colors">
        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
        </svg>
        Back to Donations
      </router-link>
      <h1 class="font-display text-2xl font-bold text-[var(--color-text-dark)]">Share a Meal</h1>
      <p class="text-sm text-[var(--color-text-muted)] mt-1">List your surplus food for someone nearby to claim</p>
    </div>

    <!-- Form -->
    <form @submit.prevent="handleSubmit" class="bg-white rounded-2xl border border-[var(--color-border)] p-8 space-y-6">
      <!-- Food Category -->
      <div>
        <label class="block text-xs font-semibold text-[var(--color-text-dark)] tracking-wide uppercase mb-3">Food Category</label>
        <FoodCategorySelect v-model="form.category" />
      </div>

      <!-- Title & Description -->
      <div class="space-y-5">
        <div class="space-y-1.5">
          <label for="title" class="block text-xs font-semibold text-[var(--color-text-dark)] tracking-wide uppercase">Title</label>
          <input
            id="title"
            v-model="form.title"
            type="text"
            required
            placeholder="e.g., Fresh Vegetables from Farmers Market"
            class="block w-full h-11 rounded-xl border border-[var(--color-border)] bg-white px-4 text-sm text-[var(--color-text-dark)] placeholder:text-[var(--color-text-muted)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20"
          />
        </div>
        <div class="space-y-1.5">
          <label for="description" class="block text-xs font-semibold text-[var(--color-text-dark)] tracking-wide uppercase">Description</label>
          <textarea
            id="description"
            v-model="form.description"
            rows="3"
            placeholder="Describe the food, condition, and any special instructions..."
            class="block w-full rounded-xl border border-[var(--color-border)] bg-white px-4 py-3 text-sm text-[var(--color-text-dark)] placeholder:text-[var(--color-text-muted)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20 resize-none"
          />
        </div>
      </div>

      <!-- Quantity & Unit -->
      <div class="grid grid-cols-2 gap-5">
        <div class="space-y-1.5">
          <label for="quantity" class="block text-xs font-semibold text-[var(--color-text-dark)] tracking-wide uppercase">Quantity</label>
          <input
            id="quantity"
            v-model.number="form.quantity"
            type="number"
            required
            min="1"
            class="block w-full h-11 rounded-xl border border-[var(--color-border)] bg-white px-4 text-sm text-[var(--color-text-dark)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20"
          />
        </div>
        <div class="space-y-1.5">
          <label for="unit" class="block text-xs font-semibold text-[var(--color-text-dark)] tracking-wide uppercase">Unit</label>
          <select
            id="unit"
            v-model="form.unit"
            class="block w-full h-11 rounded-xl border border-[var(--color-border)] bg-white px-4 text-sm text-[var(--color-text-dark)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20"
          >
            <option value="kg">Kilograms (kg)</option>
            <option value="lbs">Pounds (lbs)</option>
            <option value="pieces">Pieces</option>
            <option value="boxes">Boxes</option>
            <option value="meals">Meals</option>
          </select>
        </div>
      </div>

      <!-- Expiry & Pickup -->
      <div class="grid grid-cols-2 gap-5">
        <div class="space-y-1.5">
          <label for="expiry" class="block text-xs font-semibold text-[var(--color-text-dark)] tracking-wide uppercase">Expiry Date</label>
          <input
            id="expiry"
            v-model="form.expiryDate"
            type="datetime-local"
            required
            class="block w-full h-11 rounded-xl border border-[var(--color-border)] bg-white px-4 text-sm text-[var(--color-text-dark)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20"
          />
        </div>
        <div class="space-y-1.5">
          <label for="pickup" class="block text-xs font-semibold text-[var(--color-text-dark)] tracking-wide uppercase">Pickup By</label>
          <input
            id="pickup"
            v-model="form.pickupDeadline"
            type="datetime-local"
            required
            class="block w-full h-11 rounded-xl border border-[var(--color-border)] bg-white px-4 text-sm text-[var(--color-text-dark)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20"
          />
        </div>
      </div>

      <!-- Location -->
      <div class="space-y-1.5">
        <label class="block text-xs font-semibold text-[var(--color-text-dark)] tracking-wide uppercase">Pickup Location</label>
        <div class="grid grid-cols-2 gap-3">
          <input
            v-model.number="form.latitude"
            type="number"
            step="any"
            placeholder="Latitude"
            class="block w-full h-11 rounded-xl border border-[var(--color-border)] bg-white px-4 text-sm text-[var(--color-text-dark)] placeholder:text-[var(--color-text-muted)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20"
          />
          <input
            v-model.number="form.longitude"
            type="number"
            step="any"
            placeholder="Longitude"
            class="block w-full h-11 rounded-xl border border-[var(--color-border)] bg-white px-4 text-sm text-[var(--color-text-dark)] placeholder:text-[var(--color-text-muted)] transition-all focus:border-[var(--color-primary)] focus:outline-none focus:ring-2 focus:ring-[var(--color-primary)]/20"
          />
        </div>
        <p class="text-xs text-[var(--color-text-muted)] mt-1.5">Enter coordinates or use the map to select a location</p>
      </div>

      <!-- Image Upload -->
      <div class="space-y-1.5">
        <label class="block text-xs font-semibold text-[var(--color-text-dark)] tracking-wide uppercase">Food Image</label>
        <div class="mt-1 flex justify-center px-6 pt-5 pb-6 border-2 border-dashed border-[var(--color-border)] rounded-xl hover:border-[var(--color-primary)]/40 transition-colors cursor-pointer bg-[var(--color-bg-warm)]/30">
          <div class="space-y-2 text-center">
            <svg class="mx-auto h-10 w-10 text-[var(--color-text-muted)]" stroke="currentColor" fill="none" viewBox="0 0 48 48">
              <path d="M28 8H12a4 4 0 00-4 4v20m32-12v8m0 0v8a4 4 0 01-4 4H12a4 4 0 01-4-4v-4m32-4l-3.172-3.172a4 4 0 00-5.656 0L28 28M8 32l9.172-9.172a4 4 0 015.656 0L28 28m0 0l4 4m4-24h8m-4-4v8m-12 4h.02" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
            </svg>
            <div class="text-sm">
              <span class="font-medium text-[var(--color-primary)] hover:text-[var(--color-primary-hover)] cursor-pointer">Upload a photo</span>
            </div>
            <p class="text-xs text-[var(--color-text-muted)]">PNG, JPG up to 5MB</p>
          </div>
        </div>
      </div>

      <!-- Submit -->
      <div class="flex items-center justify-end gap-4 pt-5 border-t border-[var(--color-border)]">
        <router-link to="/donations" class="btn-outline text-sm">Cancel</router-link>
        <button
          type="submit"
          class="btn-primary text-sm"
          :disabled="submitting"
        >
          <svg v-if="submitting" class="animate-spin -ml-1 mr-2 h-4 w-4 text-[var(--color-text-dark)]" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
            <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4" />
            <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
          </svg>
          {{ submitting ? 'Posting...' : 'Post Meal' }}
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import FoodCategorySelect from '@/components/donations/FoodCategorySelect.vue'

const form = reactive({
  category: '',
  title: '',
  description: '',
  quantity: 1,
  unit: 'kg',
  expiryDate: '',
  pickupDeadline: '',
  latitude: null,
  longitude: null,
  image: null
})

const submitting = ref(false)

async function handleSubmit() {
  submitting.value = true
  try {
    // TODO: integrate with donation service
    await new Promise(resolve => setTimeout(resolve, 1000))
    console.log('Donation submitted:', { ...form })
  } finally {
    submitting.value = false
  }
}
</script>
