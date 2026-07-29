<template>
  <div>
    <h2 class="text-2xl font-bold text-secondary-900 mb-2">Create account</h2>
    <p class="text-secondary-500 mb-6">Join Meal Share and start reducing food waste</p>
    
    <form @submit.prevent="handleSubmit" class="space-y-4">
      <div class="grid grid-cols-2 gap-4">
        <div>
          <label class="block text-sm font-medium text-secondary-700 mb-1">First Name</label>
          <input v-model="form.firstName" type="text" required class="input-field" />
        </div>
        <div>
          <label class="block text-sm font-medium text-secondary-700 mb-1">Last Name</label>
          <input v-model="form.lastName" type="text" required class="input-field" />
        </div>
      </div>
      
      <div>
        <label class="block text-sm font-medium text-secondary-700 mb-1">Email</label>
        <input v-model="form.email" type="email" required class="input-field" placeholder="you@example.com" />
      </div>
      
      <div>
        <label class="block text-sm font-medium text-secondary-700 mb-1">Phone</label>
        <input v-model="form.phone" type="tel" class="input-field" placeholder="+1 (555) 000-0000" />
      </div>
      
      <div>
        <label class="block text-sm font-medium text-secondary-700 mb-1">I want to</label>
        <div class="grid grid-cols-2 gap-3">
          <label 
            v-for="role in roles" 
            :key="role.value"
            :class="[
              'flex items-center p-3 border rounded-lg cursor-pointer transition-colors',
              form.role === role.value 
                ? 'border-primary-500 bg-primary-50 text-primary-700' 
                : 'border-secondary-200 hover:border-primary-300'
            ]"
          >
            <input type="radio" v-model="form.role" :value="role.value" class="sr-only" />
            <div class="text-center w-full">
              <div class="text-lg mb-1">{{ role.icon }}</div>
              <div class="text-sm font-medium">{{ role.label }}</div>
            </div>
          </label>
        </div>
      </div>
      
      <div>
        <label class="block text-sm font-medium text-secondary-700 mb-1">Password</label>
        <input v-model="form.password" type="password" required minlength="8" class="input-field" />
        <p class="mt-1 text-xs text-secondary-500">Must be at least 8 characters</p>
      </div>
      
      <div>
        <label class="block text-sm font-medium text-secondary-700 mb-1">Confirm Password</label>
        <input v-model="form.confirmPassword" type="password" required class="input-field" />
      </div>
      
      <div class="flex items-start">
        <input 
          v-model="form.agreeTerms"
          type="checkbox" 
          required
          class="mt-1 rounded border-secondary-300 text-primary-600 focus:ring-primary-500"
        />
        <span class="ml-2 text-sm text-secondary-600">
          I agree to the <a href="#" class="text-primary-600 hover:underline">Terms</a> and 
          <a href="#" class="text-primary-600 hover:underline">Privacy Policy</a>
        </span>
      </div>
      
      <button type="submit" class="btn-primary w-full" :disabled="isLoading">
        <LoadingSpinner v-if="isLoading" size="sm" class="mr-2" />
        {{ isLoading ? 'Creating account...' : 'Create account' }}
      </button>
    </form>
    
    <p class="mt-6 text-center text-sm text-secondary-500">
      Already have an account? 
      <router-link to="/auth/login" class="font-medium text-primary-600 hover:text-primary-500">
        Sign in
      </router-link>
    </p>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useAuth } from '@/composables/useAuth'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'

const { register, isLoading } = useAuth()

const roles = [
  { value: 'DONOR', label: 'Donate Food', icon: '🍎' },
  { value: 'RECEIVER', label: 'Receive Food', icon: '🤝' }
]

const form = reactive({
  firstName: '',
  lastName: '',
  email: '',
  phone: '',
  role: 'DONOR',
  password: '',
  confirmPassword: '',
  agreeTerms: false
})

async function handleSubmit() {
  if (form.password !== form.confirmPassword) {
    alert('Passwords do not match')
    return
  }
  
  const result = await register({
    name: `${form.firstName} ${form.lastName}`,
    email: form.email,
    phone: form.phone,
    role: form.role,
    password: form.password
  })
  
  if (!result.success) {
    alert(result.error)
  }
}
</script>