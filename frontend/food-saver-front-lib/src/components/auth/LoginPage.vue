<template>
  <div>
    <h2 class="text-2xl font-bold text-secondary-900 mb-2">Welcome back</h2>
    <p class="text-secondary-500 mb-6">Sign in to your Meal Share account</p>
    
    <form @submit.prevent="handleSubmit" class="space-y-4">
      <div>
        <label class="block text-sm font-medium text-secondary-700 mb-1">Email</label>
        <input 
          v-model="form.email"
          type="email"
          required
          class="input-field"
          placeholder="you@example.com"
          :disabled="isLoading"
        />
      </div>
      
      <div>
        <label class="block text-sm font-medium text-secondary-700 mb-1">Password</label>
        <div class="relative">
          <input 
            v-model="form.password"
            :type="showPassword ? 'text' : 'password'"
            required
            class="input-field pr-10"
            placeholder="••••••••"
            :disabled="isLoading"
          />
          <button 
            type="button"
            @click="showPassword = !showPassword"
            class="absolute right-3 top-1/2 -translate-y-1/2 text-secondary-400 hover:text-secondary-600"
          >
            <svg v-if="showPassword" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
            </svg>
            <svg v-else class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.88 9.88l-3.29-3.29m7.532 7.532l3.29 3.29M3 3l3.59 3.59m0 0A9.953 9.953 0 0112 5c4.478 0 8.268 2.943 9.543 7a10.025 10.025 0 01-4.132 5.411m0 0L21 21" />
            </svg>
          </button>
        </div>
      </div>
      
      <div class="flex items-center justify-between">
        <label class="flex items-center">
          <input type="checkbox" class="rounded border-secondary-300 text-primary-600 focus:ring-primary-500" />
          <span class="ml-2 text-sm text-secondary-600">Remember me</span>
        </label>
        <a href="#" class="text-sm font-medium text-primary-600 hover:text-primary-500">Forgot password?</a>
      </div>
      
      <button 
        type="submit" 
        class="btn-primary w-full"
        :disabled="isLoading"
      >
        <LoadingSpinner v-if="isLoading" size="sm" class="mr-2" />
        {{ isLoading ? 'Signing in...' : 'Sign in' }}
      </button>
    </form>
    
    <p class="mt-6 text-center text-sm text-secondary-500">
      Don't have an account? 
      <router-link to="/auth/register" class="font-medium text-primary-600 hover:text-primary-500">
        Sign up
      </router-link>
    </p>
    
    <!-- Demo Credentials -->
    <div class="mt-6 p-4 bg-secondary-50 rounded-lg">
      <p class="text-xs font-medium text-secondary-500 uppercase tracking-wider mb-2">Demo Accounts</p>
      <div class="space-y-1 text-xs text-secondary-600">
        <p>Admin: admin@mealshare.org / admin123</p>
        <p>Donor: donor@mealshare.org / donor123</p>
        <p>Receiver: receiver@mealshare.org / receiver123</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useAuth } from '@/composables/useAuth'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'

const { login, isLoading } = useAuth()

const form = reactive({
  email: '',
  password: ''
})
const showPassword = ref(false)
const loginError = ref('')

async function handleSubmit() {
  loginError.value = ''
  const result = await login(form)
  if (!result.success) {
    loginError.value = result.error
  }
}
</script>