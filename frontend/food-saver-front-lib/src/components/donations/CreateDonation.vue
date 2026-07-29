<template>
  <div class="max-w-3xl mx-auto">
    <div class="mb-6">
      <router-link to="/donations" class="text-sm text-secondary-500 hover:text-primary-600 flex items-center mb-4">
        <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
        </svg>
        Back to Donations
      </router-link>
      <h1 class="text-2xl font-bold text-secondary-900">Create Donation</h1>
      <p class="text-secondary-500 mt-1">Share your surplus food with those in need</p>
    </div>
    
    <form @submit.prevent="handleSubmit" class="card p-8 space-y-6">
      <!-- Food Category -->
      <div>
        <label class="block text-sm font-medium text-secondary-700 mb-3">Food Category</label>
        <FoodCategorySelect v-model="form.category" />
      </div>
      
      <!-- Title & Description -->
      <div class="grid grid-cols-1 gap-6">
        <div>
          <label class="block text-sm font-medium text-secondary-700 mb-1">Title</label>
          <input 
            v-model="form.title"
            type="text"
            required
            class="input-field"
            placeholder="e.g., Fresh Vegetables from Farmers Market"
          />
        </div>
        <div>
          <label class="block text-sm font-medium text-secondary-700 mb-1">Description</label>
          <textarea 
            v-model="form.description"
            rows="3"
            class="input-field"
            placeholder="Describe the food items, condition, and any special instructions..."
          />
        </div>
      </div>
      
      <!-- Quantity & Unit -->
      <div class="grid grid-cols-2 gap-6">
        <div>
          <label class="block text-sm font-medium text-secondary-700 mb-1">Quantity</label>
          <input 
            v-model.number="form.quantity"
            type="number"
            required
            min="1"
            class="input-field"
          />
        </div>
        <div>
          <label class="block text-sm font-medium text-secondary-700 mb-1">Unit</label>
          <select v-model="form.unit" class="input-field">
            <option value="kg">Kilograms (kg)</option>
            <option value="lbs">Pounds (lbs)</option>
            <option value="pieces">Pieces</option>
            <option value="boxes">Boxes</option>
            <option value="meals">Meals</option>
          </select>
        </div>
      </div>
      
      <!-- Expiry & Pickup -->
      <div class="grid grid-cols-2 gap-6">
        <div>
          <label class="block text-sm font-medium text-secondary-700 mb-1">Expiry Date</label>
          <input 
            v-model="form.expiryDate"
            type="datetime-local"
            required
            class="input-field"
          />
        </div>
        <div>
          <label class="block text-sm font-medium text-secondary-700 mb-1">Pickup By</label>
          <input 
            v-model="form.pickupDeadline"
            type="datetime-local"
            required
            class="input-field"
          />
        </div>
      </div>
      
      <!-- Location -->
      <div>
        <label class="block text-sm font-medium text-secondary-700 mb-1">Pickup Location</label>
        <div class="grid grid-cols-2 gap-4 mb-3">
          <input v-model.number="form.latitude" type="number" step="any" placeholder="Latitude" class="input-field" />
          <input v-model.number="form.longitude" type="number" step="any" placeholder="Longitude" class="input-field" />
        </div>
        <p class="text-xs text-secondary-500">Enter coordinates or use the map to select location</p>
      </div>
      
      <!-- Image -->
      <div>
        <label class="block text-sm font-medium text-secondary-700 mb-1">Food Image</label>
        <div class="mt-1 flex justify-center px-6 pt-5 pb-6 border-2 border-secondary-300 border-dashed rounded-lg hover:border-primary-400 transition-colors cursor-pointer">
          <div class="space-y-1 text-center">
            <svg class="mx-auto h-12 w-12 text-secondary-400" stroke="currentColor" fill="none" viewBox="0 0 48 48">
              <path d="M28 8H12a4 4 0 00-4 4v20m32-12v8m0 0v8a4 4 0 01-4 4H12a4 4 0 01-4-4v-4m32-4l-3.172-3.172a4 4 0 00-5.656 0L28 28M8 32l9.172-9.172a4 4 0 015.656 0L28 28m0 0l4 4m4-24h8m-4-4v8m-12 4h.02" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
            </svg>
            <div class="flex text-sm text-secondary-600 justify-center">
              <span class="relative cursor-pointer bg-white rounded-md font-medium text-primary-600 hover:text-primary-500">
                Upload a file
              </span>
            </div>
            <p class="text-xs text-secondary-500">PNG, JPG, GIF up to 5MB</p>
          </div>
        </div>
      </div>
      
      <!-- Submit -->
      <div class="flex items-center justify-end space-x-4 pt-4 border-t border-secondary-200">
        <router-link to="/donations" class="btn-secondary">Cancel</router-link>
        <button 
          type="submit"
          class="btn-primary"
          :disabled="submitting"
        >
          <LoadingSpinner v-if="submitting" size="sm" class="mr-2" />
          {{ submitting ? 'Creating...' : 'Create Donation' }}
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useDonation } from '@/composables/useDonation'
import FoodCategorySelect from '@/components/donations/FoodCategorySelect.vue'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'

const { submitDonation } = useDonation()

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
    await submitDonation({ ...form })
  } finally {
    submitting.value = false
  }
}
</script>